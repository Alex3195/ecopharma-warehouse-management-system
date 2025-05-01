package uz.duol.ecopharmwarehouse.module.location.service;

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

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;
    private final LocationMapper mapper;

    public LocationDTO create(LocationDTO dto) {
        LocationEntity entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public LocationDTO findById(Long id) {
        LocationEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        return mapper.toDto(e);
    }

    public LocationDTO update(Long id, LocationDTO dto) {
        findById(id);
        LocationEntity entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        LocationDTO dto = findById(id);
        LocationEntity entity = mapper.toEntity(dto);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    public Page<LocationDTO> findAll(String name, Pageable pageable) {
        Specification<LocationEntity> spec = Specification.where(LocationSpecification.isActive());
        if (name != null && !name.isEmpty()) {
            spec = spec.and(LocationSpecification.hasText(name));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    public LocationDTO findByBarcode(String locationBarcode) {
        LocationEntity entity = repository.findByBarcodeAndStatusIsNot(locationBarcode, Status.DELETED)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        return mapper.toDto(entity);
    }
}
