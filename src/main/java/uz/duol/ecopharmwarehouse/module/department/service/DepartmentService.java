package uz.duol.ecopharmwarehouse.module.department.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.DepartmentEntity;
import uz.duol.ecopharmwarehouse.module.department.dto.DepartmentDto;
import uz.duol.ecopharmwarehouse.module.department.mapper.DepartmentMapper;
import uz.duol.ecopharmwarehouse.module.department.specification.DepartmentSpecification;
import uz.duol.ecopharmwarehouse.repositories.DepartmentRepository;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    @Transactional
    public DepartmentDto create(DepartmentDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Department with this name already exists");
        }
        var entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }
    @Transactional
    public DepartmentDto update(Long id, DepartmentDto dto) {
        var existingDepartment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        if (!existingDepartment.getName().equals(dto.getName()) && repository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Department with this name already exists");
        }

        existingDepartment.setName(dto.getName());
        existingDepartment.setDescription(dto.getDescription());
        repository.save(existingDepartment);
        return mapper.toDto(existingDepartment);
    }
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Department not found");
        }
        repository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public DepartmentDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        return mapper.toDto(entity);
    }
    @Transactional(readOnly = true)
    public Page<DepartmentDto> findAll(String search, Pageable pageable) {
        Specification<DepartmentEntity> spec = DepartmentSpecification.isActive()
                .and(DepartmentSpecification.hasText(search));
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
