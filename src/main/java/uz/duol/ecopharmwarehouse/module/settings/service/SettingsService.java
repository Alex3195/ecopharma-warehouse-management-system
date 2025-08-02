package uz.duol.ecopharmwarehouse.module.settings.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;
import uz.duol.ecopharmwarehouse.module.settings.exception.SettingNotFoundException;
import uz.duol.ecopharmwarehouse.module.settings.mapper.SettingMapper;
import uz.duol.ecopharmwarehouse.module.settings.specification.SettingsSpecification;
import uz.duol.ecopharmwarehouse.repositories.SettingsRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepository repository;
    private final SettingMapper mapper;

    public SettingsDTO create(SettingsDTO dto) {
        var e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    public SettingsDTO findById(Long id) {
        var e = repository.findById(id)
                .orElseThrow(() -> new SettingNotFoundException("Setting not found"));
        return mapper.toDto(e);
    }

    public SettingsDTO update(Long id, SettingsDTO dto) {
        findById(id);
        var e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    public void delete(Long id) {
        SettingsDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    public DataTableResponse<SettingsDTO> findAll(DataTableRequest request) {
        Specification<SettingsEntity> spec = SettingsSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page =repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<SettingsEntity> spec = SettingsSpecification.advancedFilter(request.getFilters());

        List<SettingsEntity> allData = repository.findAll(spec);

        List<SettingsDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "settings");

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
