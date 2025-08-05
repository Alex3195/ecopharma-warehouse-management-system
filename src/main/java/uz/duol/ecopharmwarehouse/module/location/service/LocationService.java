package uz.duol.ecopharmwarehouse.module.location.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.exception.LocationNotFoundException;
import uz.duol.ecopharmwarehouse.module.location.mapper.LocationMapper;
import uz.duol.ecopharmwarehouse.module.location.specification.LocationSpecification;
import uz.duol.ecopharmwarehouse.repositories.LocationRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;
    private final LocationMapper mapper;

    public LocationDTO create(LocationDTO dto) {
        var entity = mapper.toEntity(dto);
        String barcode = generateBarCode();
        while (repository.existsByBarcode(barcode)) {
            barcode = generateBarCode();
        }
        entity.setBarcode(barcode);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public LocationDTO findById(Long id) {
        var e = repository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        return mapper.toDto(e);
    }

    public LocationDTO update(Long id, LocationDTO dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        mapper.update(existing, dto);
        repository.save(existing);
        return mapper.toDto(existing);
    }

    public void delete(Long id) {
        LocationDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    private String generateBarCode() {
        int length = 20;
        StringBuilder numeric = new StringBuilder();
        while (numeric.length() < length) {
            numeric.append(UUID.randomUUID().toString().replaceAll("[^0-9]", ""));
        }
        return numeric.substring(0, length);
    }

    public DataTableResponse<LocationDTO> findAll(DataTableRequest request) {
        Specification<LocationEntity> spec = LocationSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    public LocationDTO findByBarcode(String locationBarcode) {
        var entity = repository.findByBarcodeAndStatusIsNot(locationBarcode, Status.DELETED)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<LocationEntity> spec = LocationSpecification.advancedFilter(request.getFilters());

        List<LocationEntity> allData = repository.findAll(spec);

        List<LocationDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "location");

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
