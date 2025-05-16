package uz.duol.ecopharmwarehouse.module.location.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.exception.LocationNotFoundException;
import uz.duol.ecopharmwarehouse.module.location.mapper.LocationMapper;
import uz.duol.ecopharmwarehouse.module.location.specification.LocationSpecification;
import uz.duol.ecopharmwarehouse.repositories.LocationRepository;

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
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
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

    public Page<LocationDTO> findAll(String name, Pageable pageable) {
        Specification<LocationEntity> spec = Specification.where(null);
        if (name != null && !name.isEmpty()) {
            spec = spec.and(LocationSpecification.hasText(name));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    public LocationDTO findByBarcode(String locationBarcode) {
        var entity = repository.findByBarcodeAndStatusIsNot(locationBarcode, Status.DELETED)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        return mapper.toDto(entity);
    }
}
