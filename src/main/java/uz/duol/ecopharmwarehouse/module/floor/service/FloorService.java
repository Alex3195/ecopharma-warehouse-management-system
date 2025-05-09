package uz.duol.ecopharmwarehouse.module.floor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.floor.exception.FloorNotFoundException;
import uz.duol.ecopharmwarehouse.module.floor.mapper.FloorMapper;
import uz.duol.ecopharmwarehouse.module.floor.specification.FloorSpecification;
import uz.duol.ecopharmwarehouse.repositories.FloorRepository;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final FloorRepository repository;
    private final FloorMapper mapper;

    @Transactional
    public FloorDTO create(FloorDTO dto) {
        FloorEntity e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public FloorDTO findById(Long id) {
        FloorEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new FloorNotFoundException("Floor not found"));
        return mapper.toDto(e);
    }

    @Transactional
    public FloorDTO update(Long id, FloorDTO dto) {
        findById(id);
        FloorEntity e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        FloorDTO dto = findById(id);
        FloorEntity e = mapper.toEntity(dto);
        e.setStatus(Status.DELETED);
        repository.save(e);
    }

    @Transactional(readOnly = true)
    public Page<FloorDTO> findAll(Pageable pageable) {
        Specification<FloorEntity> spec = Specification.where((FloorSpecification.isActive()));

        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
