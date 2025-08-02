package uz.duol.ecopharmwarehouse.module.store.service;

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
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;
import uz.duol.ecopharmwarehouse.module.settings.specification.SettingsSpecification;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.mapper.StoreAggregationWithAlternativeUnitMapper;
import uz.duol.ecopharmwarehouse.module.store.specification.StoreAggregationWithAlternativeUnitSpecification;
import uz.duol.ecopharmwarehouse.repositories.StoreAggregationWithAlternativeUnitRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreAggregationService {
    private final StoreAggregationWithAlternativeUnitRepository repository;
    private final StoreAggregationWithAlternativeUnitMapper mapper;

    @Transactional
    public StoreSyncRequest createAndReturnBarCode(StoreSyncRequest request) {
        var e = mapper.toEntity(request);

        String barCode = generateBarCode();
        while (repository.existsByBarcode(barCode)) {
            barCode = generateBarCode();
        }

        e.setBarcode(barCode);
        repository.save(e);
        return mapper.toDto(e);
    }


    @Transactional(readOnly = true)
    public StoreSyncRequest findById(Long id) {
        var e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Store not found"));
        return mapper.toDto(e);
    }

    @Transactional
    public void delete(Long id) {
        var dto = findById(id);
        repository.deleteById(dto.getId());
    }

    @Transactional
    public StoreSyncRequest update(Long id, StoreSyncRequest request) {
        findById(id);
        var e = mapper.toEntity(request);
        e.setId(id);
        repository.save(e);
        return mapper.toDto(e);
    }

    @Transactional(readOnly = true)
    public DataTableResponse<StoreSyncRequest> findAll(DataTableRequest request) {
        var spec = StoreAggregationWithAlternativeUnitSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    private String generateBarCode() {
        int length = 20;
        StringBuilder numeric = new StringBuilder();
        while (numeric.length() < length) {
            numeric.append(UUID.randomUUID().toString().replaceAll("[^0-9]", ""));
        }
        return numeric.substring(0, length);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<StoreAggregationsWithAlternativeUnitEntity> spec = StoreAggregationWithAlternativeUnitSpecification.advancedFilter(request.getFilters());

        List<StoreAggregationsWithAlternativeUnitEntity> allData = repository.findAll(spec);

        List<StoreSyncRequest> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "store_aggregation");

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
