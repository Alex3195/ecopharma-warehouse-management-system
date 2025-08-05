package uz.duol.ecopharmwarehouse.module.outboundshipment.service;

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
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.mapper.OutboundShipmentMapper;
import uz.duol.ecopharmwarehouse.module.outboundshipment.specification.OutboundShipmentSpecification;
import uz.duol.ecopharmwarehouse.repositories.OutboundShipmentRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboundShipmentService {
    private final OutboundShipmentMapper mapper;
    private final OutboundShipmentRepository repository;

    @Transactional
    public OutboundShipmentDto create(OutboundShipmentDto dto) {
        var entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public OutboundShipmentDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Outbound shipment not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public OutboundShipmentDto update(Long id, OutboundShipmentDto dto) {
        OutboundShipmentDto data = findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(data.getId());
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        OutboundShipmentDto data = findById(id);
        repository.deleteById(data.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<OutboundShipmentDto> findAll(DataTableRequest request) {
        Specification<OutboundShipmentEntity> spec = OutboundShipmentSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<OutboundShipmentEntity> spec = OutboundShipmentSpecification.advancedFilter(request.getFilters());

        List<OutboundShipmentEntity> allData = repository.findAll(spec);

        List<OutboundShipmentDto> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "outbound_shipment");

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
