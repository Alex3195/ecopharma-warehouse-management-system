package uz.duol.ecopharmwarehouse.module.product.location.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.ProductLocationByItsBarcodeAndCellCodeEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.service.LocationService;
import uz.duol.ecopharmwarehouse.module.product.location.dto.ProductLocationByItsBarcodeAndLocationCodeDto;
import uz.duol.ecopharmwarehouse.module.product.location.mapper.ProductLocationByItsBarcodeAndCellCodeMapper;
import uz.duol.ecopharmwarehouse.module.product.location.specification.ProductLocationByItsBarcodeAndCellCodeSpecification;
import uz.duol.ecopharmwarehouse.repositories.ProductLocationByItsBarcodeAndCellCodeRepository;

@Service
@RequiredArgsConstructor
public class ProductLocationByItsBarcodeAndCellCodeService {
    private final ProductLocationByItsBarcodeAndCellCodeMapper mapper;
    private final ProductLocationByItsBarcodeAndCellCodeRepository repository;
    private final LocationService locationService;

    @Transactional
    public ProductLocationByItsBarcodeAndLocationCodeDto create(ProductLocationByItsBarcodeAndLocationCodeDto dto) {
        LocationDTO locationDTO = locationService.findByBarcode(dto.getLocationBarcode());
        if (locationDTO.getAvailable()) {
            ProductLocationByItsBarcodeAndCellCodeEntity e = mapper.toEntity(dto);
            repository.save(e);
            locationDTO.setAvailable(false);
            locationService.update(locationDTO.getId(), locationDTO);
            return mapper.toDto(e);
        } else {
            throw new RuntimeException("Location is not empty");
        }

    }

    public String findByProductBarCodeItsLocationCode(String productBarCode) {
        ProductLocationByItsBarcodeAndCellCodeEntity entity = repository.findByProductBarcodeAndStatusIsNot(productBarCode, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Product bar code not found"));
        return entity.getLocationBarcode();
    }

    public ProductLocationByItsBarcodeAndLocationCodeDto update(String productBarCode, String locationCode) {
        ProductLocationByItsBarcodeAndCellCodeEntity entity = repository.findByProductBarcodeAndStatusIsNot(productBarCode, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Product bar code not found"));
        entity.setLocationBarcode(locationCode);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public Page<ProductLocationByItsBarcodeAndLocationCodeDto> getProductLocationByItsBarcode(String search, Pageable pageable) {
        Specification<ProductLocationByItsBarcodeAndCellCodeEntity> spec = ProductLocationByItsBarcodeAndCellCodeSpecification.isActive();
        if (!search.isBlank()) {
            spec = spec.and(ProductLocationByItsBarcodeAndCellCodeSpecification.hasText(search));
        }
        Page<ProductLocationByItsBarcodeAndCellCodeEntity> entities = repository.findAll(spec, pageable);
        return entities.map(mapper::toDto);
    }
}
