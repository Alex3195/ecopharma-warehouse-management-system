package uz.duol.ecopharmwarehouse.module.printer.setting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.PrinterSettingsEntity;
import uz.duol.ecopharmwarehouse.module.printer.setting.dto.PrinterSettingsDto;
import uz.duol.ecopharmwarehouse.module.printer.setting.mapper.PrinterSettingMapper;
import uz.duol.ecopharmwarehouse.module.printer.setting.specification.PrinterSettingSpecification;
import uz.duol.ecopharmwarehouse.repositories.PrinterSettingsRepository;

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
}
