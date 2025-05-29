package uz.duol.ecopharmwarehouse.module.printer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.PrinterSettingsEntity;
import uz.duol.ecopharmwarehouse.module.printer.dto.PrinterSettingsDto;
import uz.duol.ecopharmwarehouse.module.printer.mapper.PrinterSettingMapper;
import uz.duol.ecopharmwarehouse.module.printer.specification.PrinterSettingSpecification;
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

    public Page<PrinterSettingsDto> findAll(String search, Long departmentId, Pageable pageable) {
        Specification<PrinterSettingsEntity> spec = PrinterSettingSpecification.isActive()
                .and(PrinterSettingSpecification.hasText(search))
                .and(PrinterSettingSpecification.departmentIdEquals(departmentId));
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
