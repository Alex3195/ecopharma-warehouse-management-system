package uz.duol.ecopharmwarehouse.module.rack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackRequest;
import uz.duol.ecopharmwarehouse.module.rack.exception.RackNotFoundException;
import uz.duol.ecopharmwarehouse.module.rack.mapper.RackMapper;
import uz.duol.ecopharmwarehouse.module.rack.specification.RackSpecification;
import uz.duol.ecopharmwarehouse.repositories.RackRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RackService {
    private final RackRepository repository;
    @Qualifier("rackMapper")
    private final RackMapper mapper;

    @Transactional
    public RackDTO create(RackRequest request) {
        RackEntity rackEntity = rackEntityFromRequest(request);
        return mapper.toDto(repository.save(rackEntity));
    }

    private RackEntity rackEntityFromRequest(RackRequest request) {
        RackEntity rackEntity = new RackEntity();
        rackEntity.setId(request.getId());
        rackEntity.setName(request.getName());
        rackEntity.setDepth(request.getDepth());
        rackEntity.setHeight(request.getHeight());
        rackEntity.setWidth(request.getWidth());
        rackEntity.setType(request.getType());
        rackEntity.setSectorId(request.getSectorId());
        for (int i = 0; i < request.getFloors(); i++) {
            FloorEntity floor = new FloorEntity();
            floor.setLevel(i);
            floor.setHeight(request.getHeight() / request.getFloors());
            for (int j = 0; j < request.getCells(); j++) {
                CellEntity cell = new CellEntity();
                cell.setCode(request.getName() + "-" + i + "-" + j);
                cell.setWidth(request.getWidth() / request.getCells());
                cell.setHeight(request.getHeight() / request.getFloors());
                cell.setIsEmpty(true);
                cell.setDepth(request.getDepth());
                floor.getCells().add(cell);
            }
            rackEntity.getFloors().add(floor);
        }
        return rackEntity;
    }

    @Transactional(readOnly = true)
    public RackDTO findById(Long id) {
        RackEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new RackNotFoundException("Rack not found"));
        return mapper.toDto(e);
    }

    @Transactional
    public RackDTO update(Long id, RackDTO rackDTO) {
        findById(id);
        RackEntity e = mapper.toEntity(rackDTO);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        RackDTO dto = findById(id);
        RackEntity e = mapper.toEntity(dto);
        e.setStatus(Status.DELETED);
        repository.save(e);
    }

    @Transactional(readOnly = true)
    public Page<RackDTO> findAll(String search, Pageable pageable) {
        Specification<RackEntity> spec = Specification.where(RackSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(RackSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
