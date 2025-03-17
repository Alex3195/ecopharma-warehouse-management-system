package uz.duol.ecopharmwarehouse.module.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.WarehouseEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;
import uz.duol.ecopharmwarehouse.module.warehouse.exception.WarehouseNotFoundException;
import uz.duol.ecopharmwarehouse.module.warehouse.mapper.WarehouseMapper;
import uz.duol.ecopharmwarehouse.module.warehouse.specification.WarehouseSpecification;
import uz.duol.ecopharmwarehouse.repositories.WarehouseRepository;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository repository;
    private final WarehouseMapper mapper;

    @Transactional
    public WarehouseDTO create(WarehouseDTO dto) {
        WarehouseEntity e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public WarehouseDTO findById(Long id) {
        WarehouseEntity entity = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public WarehouseDTO update(Long id, WarehouseDTO dto) {
        findById(id);
        WarehouseEntity e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        WarehouseDTO dto = findById(id);
        WarehouseEntity e = mapper.toEntity(dto);
        e.setStatus(Status.DELETED);
        repository.save(e);
    }

    @Transactional(readOnly = true)
    public Page<WarehouseDTO> findAll(String search, Pageable pageable) {
        Specification<WarehouseEntity> spec = Specification.where(WarehouseSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(WarehouseSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
