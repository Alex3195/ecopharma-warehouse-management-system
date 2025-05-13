package uz.duol.ecopharmwarehouse.module.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.unit.exception.UnitNotFoundException;
import uz.duol.ecopharmwarehouse.module.unit.mapper.UnitMapper;
import uz.duol.ecopharmwarehouse.module.unit.specification.UnitSpecification;
import uz.duol.ecopharmwarehouse.repositories.UnitsRepository;

@Service
@RequiredArgsConstructor
public class UnitsService {
    private final UnitsRepository repository;
    private final UnitMapper mapper;

    public UnitsDTO create(UnitsDTO dto) {
        var entity = mapper.toEntity(dto);
        if (dto.getPerformedBy() != null) {
            entity.setCreatedBy(dto.getPerformedBy());
        }
        return mapper.toDto(repository.save(entity));
    }

    public UnitsDTO findById(Long id) {
        var entity = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new UnitNotFoundException("Unit not found"));
        return mapper.toDto(entity);
    }

    public UnitsDTO update(Long id, UnitsDTO dto) {
        findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        UnitsDTO dto = findById(id);
        var entity = mapper.toEntity(dto);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    public Page<UnitsDTO> findAll(String search, Pageable pageable) {
        Specification<UnitsEntity> spec = Specification.where(UnitSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(UnitSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
