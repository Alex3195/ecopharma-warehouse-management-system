package uz.duol.ecopharmwarehouse.module.inbound.receipt.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;
import uz.duol.ecopharmwarehouse.module.crossdocking.service.CrossDockingService;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.mapper.InboundReceiptMapper;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.specification.InboundReceiptSpecification;
import uz.duol.ecopharmwarehouse.module.inboundreceiptmetadata.mapper.InboundReceiptMetadataMapper;
import uz.duol.ecopharmwarehouse.repositories.InboundReceiptRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InboundReceiptService {
    private final InboundReceiptRepository repository;
    @Qualifier("inboundReceiptMapper")
    private final InboundReceiptMapper mapper;
    private final CrossDockingService crossDockingService;
    private final InboundReceiptMetadataMapper metadataMapper;

    @Transactional
    public InboundReceiptDto create(InboundReceiptDto receipt) {
        var e = mapper.toEntity(receipt);

        if (e.getInboundReceiptMetadata() != null) {
            e.getInboundReceiptMetadata().forEach((m) -> {
                m.setInboundReceipt(e);
            });
        }

        repository.save(e);

        if (receipt.getCrossDockType() != null) {
            crossDockingService.create(crossDockingEntityFromRequest(receipt));
        }

        return mapper.toDto(e);
    }


    private CrossDockingDto crossDockingEntityFromRequest(InboundReceiptDto request) {
        CrossDockingDto crossDocking = new CrossDockingDto();
        crossDocking.setInboundReceiptId(request.getId());
        crossDocking.setCrossDockType(request.getCrossDockType());
        crossDocking.setProcessingTime(request.getProcessingTime());
        return crossDocking;
    }

    @Transactional(readOnly = true)
    public InboundReceiptDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InboundReceiptEntity not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public InboundReceiptDto update(Long id, InboundReceiptDto receipt) {
        InboundReceiptDto dto = findById(id);
        var entity = mapper.toEntity(receipt);
        entity.setId(dto.getId());
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        InboundReceiptDto dto = findById(id);
        repository.deleteById(dto.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<InboundReceiptDto> findAll(DataTableRequest request) {

        Specification<InboundReceiptEntity> spec = InboundReceiptSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<InboundReceiptEntity> spec = InboundReceiptSpecification.advancedFilter(request.getFilters());

        List<InboundReceiptEntity> allData = repository.findAll(spec);

        List<InboundReceiptDto> addressDTOList = allData.stream()
                .map(mapper::toDto)
                .toList();

        byte[] excel = ExcelExportUtil.exportToExcel(addressDTOList, fieldNames, columnNames, "inboundReceipt");

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
