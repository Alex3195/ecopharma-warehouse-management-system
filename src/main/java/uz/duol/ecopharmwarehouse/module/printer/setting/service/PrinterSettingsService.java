package uz.duol.ecopharmwarehouse.module.printer.setting.service;

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
import uz.duol.ecopharmwarehouse.entity.PrinterSettingsEntity;
import uz.duol.ecopharmwarehouse.module.printer.setting.dto.PrinterSettingsDto;
import uz.duol.ecopharmwarehouse.module.printer.setting.mapper.PrinterSettingMapper;
import uz.duol.ecopharmwarehouse.module.printer.setting.specification.PrinterSettingSpecification;
import uz.duol.ecopharmwarehouse.repositories.PrinterSettingsRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrinterSettingsService {
    private final PrinterSettingsRepository repository;
    private final PrinterSettingMapper mapper;

    public PrinterSettingsDto create(PrinterSettingsDto dto) {
        var entity = mapper.toEntity(dto);
        var savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    public PrinterSettingsDto update(Long id, PrinterSettingsDto dto) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Printer settings not found"));
        mapper.updateEntityFromDto(dto, entity);
        if (dto.getIsDefaultPrinter() != null && dto.getIsDefaultPrinter()) {
            repository.updateAllToNotDefault(dto.getDepartmentId());
        }
        var updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public PrinterSettingsDto findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Printer settings not found"));
        return mapper.toDto(entity);
    }

    public PrinterSettingsDto getDefaultPrinterSettings(Long departmentId) {
        var entity = repository.findByIsDefaultTrueAndDepartmentIdEquals(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Default printer settings not found"));
        return mapper.toDto(entity);
    }

    public DataTableResponse<PrinterSettingsDto> findAll(DataTableRequest request) {
        Specification<PrinterSettingsEntity> spec = PrinterSettingSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<PrinterSettingsEntity> spec = PrinterSettingSpecification.advancedFilter(request.getFilters());

        List<PrinterSettingsEntity> allData = repository.findAll(spec);

        List<PrinterSettingsDto> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "printer_settings");

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
