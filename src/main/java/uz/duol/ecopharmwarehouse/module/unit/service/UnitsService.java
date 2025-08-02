package uz.duol.ecopharmwarehouse.module.unit.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.unit.exception.UnitNotFoundException;
import uz.duol.ecopharmwarehouse.module.unit.mapper.UnitMapper;
import uz.duol.ecopharmwarehouse.module.unit.specification.UnitSpecification;
import uz.duol.ecopharmwarehouse.repositories.UnitsRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

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
        var entity = repository.findById(id)
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
        repository.deleteById(dto.getId());
    }

    public DataTableResponse<UnitsDTO> findAll(DataTableRequest request) {
        Specification<UnitsEntity> spec = UnitSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<UnitsEntity> spec = UnitSpecification.advancedFilter(request.getFilters());

        List<UnitsEntity> allData = repository.findAll(spec);

        List<UnitsDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "unit");

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
