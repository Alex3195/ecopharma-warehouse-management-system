package uz.duol.ecopharmwarehouse.module.rack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;
import uz.duol.ecopharmwarehouse.module.rack.exception.RackNotFoundException;
import uz.duol.ecopharmwarehouse.module.rack.mapper.RackMapper;
import uz.duol.ecopharmwarehouse.module.rack.specification.RackSpecification;
import uz.duol.ecopharmwarehouse.repositories.RackRepository;

@Service
@RequiredArgsConstructor
public class RackService {
    private final RackRepository repository;
    private final RackMapper mapper;

    @Transactional
    public RackDTO create(RackDTO rackDTO) {
        RackEntity rackEntity = mapper.toEntity(rackDTO);
        return mapper.toDTO(repository.save(rackEntity));
    }

    @Transactional(readOnly = true)
    public RackDTO findById(Long id) {
        RackEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new RackNotFoundException("Rack not found"));
        return mapper.toDTO(e);
    }

    @Transactional
    public RackDTO update(Long id, RackDTO rackDTO) {
        findById(id);
        RackEntity e = mapper.toEntity(rackDTO);
        e.setId(id);
        return mapper.toDTO(repository.save(e));
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
        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }
}
