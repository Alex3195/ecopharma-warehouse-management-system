package uz.duol.ecopharmwarehouse.module.sector.characteristics.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.exception.SectorCharacteristicsNotFoundException;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.mapper.SectorCharacteristicsMapper;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.specification.SectorCharacteristicsSpecification;
import uz.duol.ecopharmwarehouse.repositories.SectorCharacteristicsRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorCharacteristicsService {
    private final SectorCharacteristicsRepository repository;
    private final SectorCharacteristicsMapper mapper;

    @Transactional
    public SectorCharacteristicDTO create(SectorCharacteristicDTO dto) {
        var e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public SectorCharacteristicDTO findById(Long id) {
        SectorCharacteristicEntity entity = repository.findById(id)
                .orElseThrow(() -> new SectorCharacteristicsNotFoundException("Sector characteristic not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public SectorCharacteristicDTO update(Long id, SectorCharacteristicDTO dto) {
        var existing = repository.findById(id).orElseThrow(() -> new SectorCharacteristicsNotFoundException("Sector characteristic not found"));
        mapper.updateExistingEntity(existing,dto);
        return mapper.toDto(repository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        SectorCharacteristicDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<SectorCharacteristicDTO> findAll(DataTableRequest request) {
        Specification<SectorCharacteristicEntity> spec = SectorCharacteristicsSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<SectorCharacteristicEntity> spec = SectorCharacteristicsSpecification.advancedFilter(request.getFilters());

        List<SectorCharacteristicEntity> allData = repository.findAll(spec);

        List<SectorCharacteristicDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "sector_characteristics");

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
