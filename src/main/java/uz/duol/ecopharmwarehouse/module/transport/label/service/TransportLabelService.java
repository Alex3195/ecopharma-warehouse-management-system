package uz.duol.ecopharmwarehouse.module.transport.label.service;

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
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.entity.TransportLabelEntity;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;
import uz.duol.ecopharmwarehouse.module.task.specification.TaskSpecification;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;
import uz.duol.ecopharmwarehouse.module.transport.label.mapper.TransportLabelMapper;
import uz.duol.ecopharmwarehouse.module.transport.label.specification.TransportLabelSpecification;
import uz.duol.ecopharmwarehouse.repositories.TransportLabelRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportLabelService {
    private final TransportLabelRepository repository;
    private final TransportLabelMapper mapper;

    @Transactional
    public TransportLabelDto create(TransportLabelDto dto) {
        var entity = mapper.toEntity(dto);
        var savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Transactional
    public TransportLabelDto update(Long id, TransportLabelDto dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport label not found"));
        mapper.updateEntity(entity, dto);
        var updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    @Transactional(readOnly = true)
    public TransportLabelDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport label not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        var label = findById(id);
        repository.deleteById(label.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<TransportLabelDto> findAll(DataTableRequest request) {
        var spec = TransportLabelSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);

    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<TransportLabelEntity> spec = TransportLabelSpecification.advancedFilter(request.getFilters());

        List<TransportLabelEntity> allData = repository.findAll(spec);

        List<TransportLabelDto> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "transport_label");

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
