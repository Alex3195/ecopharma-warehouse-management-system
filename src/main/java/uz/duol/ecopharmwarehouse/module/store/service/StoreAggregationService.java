package uz.duol.ecopharmwarehouse.module.store.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.StoreAggregationsWithAlternativeUnitEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.mapper.StoreAggregationWithAlternativeUnitMapper;
import uz.duol.ecopharmwarehouse.module.store.specification.StoreAggregationWithAlternativeUnitSpecification;
import uz.duol.ecopharmwarehouse.repositories.StoreAggregationWithAlternativeUnitRepository;

@Service
@RequiredArgsConstructor
public class StoreAggregationService {
    private final StoreAggregationWithAlternativeUnitRepository repository;
    private final StoreAggregationWithAlternativeUnitMapper mapper;

    public StoreSyncRequest createAndReturnBarCode(StoreSyncRequest request) {
        StoreAggregationsWithAlternativeUnitEntity e = mapper.toEntity(request);
        String barCode = generateBarCode();
        while (repository.existsByBarcode(barCode)) {
            barCode = generateBarCode();
        }
        e.setBarcode(generateBarCode());
        repository.save(e);
        return mapper.toDto(e);
    }

    public StoreSyncRequest findById(Long id) {
        StoreAggregationsWithAlternativeUnitEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));
        return mapper.toDto(e);
    }

    public void delete(Long id) {
        StoreAggregationsWithAlternativeUnitEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));
        e.setStatus(Status.DELETED);
        repository.save(e);
    }

    public StoreSyncRequest update(Long id, StoreSyncRequest request) {
        findById(id);
        StoreAggregationsWithAlternativeUnitEntity e = mapper.toEntity(request);
        e.setId(id);
        repository.save(e);
        return mapper.toDto(e);
    }

    public Page<StoreSyncRequest> findAll(String search, Pageable pageable) {
        Specification<StoreAggregationsWithAlternativeUnitEntity> spec = StoreAggregationWithAlternativeUnitSpecification.isActive();
        if (search != null && !search.isEmpty()) {
            spec = spec.and(StoreAggregationWithAlternativeUnitSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    private String generateBarCode() {
        return String.format("%020d", (long) (Math.random() * 1_000_000_000_000L));
    }
}
