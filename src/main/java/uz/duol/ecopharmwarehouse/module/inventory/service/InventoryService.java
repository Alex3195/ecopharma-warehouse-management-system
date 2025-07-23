package uz.duol.ecopharmwarehouse.module.inventory.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.inventory.dto.InventoryDto;
import uz.duol.ecopharmwarehouse.module.inventory.mapper.InventoryMapper;
import uz.duol.ecopharmwarehouse.module.inventory.specification.InventorySpecification;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.service.LocationService;
import uz.duol.ecopharmwarehouse.repositories.InventoryRepository;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper mapper;
    private final InventoryRepository repository;
    private final LocationService locationService;

    @Transactional
    public InventoryDto create(InventoryDto dto) {
        LocationDTO locationDTO = locationService.findByBarcode(dto.getLocationBarcode());
        if (locationDTO.getAvailable()) {
            var e = mapper.toEntity(dto);
            repository.save(e);
            locationDTO.setAvailable(false);
            locationService.update(locationDTO.getId(), locationDTO);
            return mapper.toDto(e);
        } else {
            throw new RuntimeException("Location is not empty");
        }

    }

    public String findLocationCodeByProductBarCode(String productBarCode) {
        var entity = repository.findByProductBarcodeAndStatusIsNot(productBarCode, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Product bar code not found"));
        return entity.getLocationBarcode();
    }

    public InventoryDto updateProductLocation(String productBarCode, String locationCode) {
        var entity = repository.findByProductBarcodeAndStatusIsNot(productBarCode, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Product bar code not found"));
        entity.setLocationBarcode(locationCode);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public DataTableResponse<InventoryDto> getProductLocationByItsBarcode(DataTableRequest request) {
        Specification<InventoryEntity> spec = InventorySpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    public void delete(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        repository.deleteById(entity.getId());
    }
}
