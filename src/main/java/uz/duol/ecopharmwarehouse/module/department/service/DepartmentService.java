package uz.duol.ecopharmwarehouse.module.department.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.DepartmentEntity;
import uz.duol.ecopharmwarehouse.module.department.dto.DepartmentDto;
import uz.duol.ecopharmwarehouse.module.department.mapper.DepartmentMapper;
import uz.duol.ecopharmwarehouse.module.department.specification.DepartmentSpecification;
import uz.duol.ecopharmwarehouse.repositories.DepartmentRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

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
    public DataTableResponse<DepartmentDto> findAll(DataTableRequest request) {
        Specification<DepartmentEntity> spec = DepartmentSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<DepartmentEntity> spec = DepartmentSpecification.advancedFilter(request.getFilters());

        List<DepartmentEntity> allData = repository.findAll(spec);

        List<DepartmentDto> addressDTOList = allData.stream()
                .map(mapper::toDto)
                .toList();

        byte[] excel = ExcelExportUtil.exportToExcel(addressDTOList, fieldNames, columnNames, "department");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=aggregation.xlsx");

        try {
            response.getOutputStream().write(excel);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write Excel to response", e);
        }
    }
}
