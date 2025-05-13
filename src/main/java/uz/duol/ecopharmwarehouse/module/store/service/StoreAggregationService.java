package uz.duol.ecopharmwarehouse.module.store.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.mapper.StoreAggregationWithAlternativeUnitMapper;
import uz.duol.ecopharmwarehouse.module.store.specification.StoreAggregationWithAlternativeUnitSpecification;
import uz.duol.ecopharmwarehouse.repositories.StoreAggregationWithAlternativeUnitRepository;

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
        e.setBarcode(generateBarCode());
        repository.save(e);
        return mapper.toDto(e);
    }

    @Transactional(readOnly = true)
    public StoreSyncRequest findById(Long id) {
        var e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));
        return mapper.toDto(e);
    }
    @Transactional
    public void delete(Long id) {
        var e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));
        e.setStatus(Status.DELETED);
        repository.save(e);
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
    public Page<StoreSyncRequest> findAll(String search, Pageable pageable) {
        var spec = StoreAggregationWithAlternativeUnitSpecification.isActive();
        if (search != null && !search.isEmpty()) {
            spec = spec.and(StoreAggregationWithAlternativeUnitSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    private String generateBarCode() {
        int length = 20;
        StringBuilder numeric = new StringBuilder();
        while (numeric.length() < length) {
            numeric.append(UUID.randomUUID().toString().replaceAll("[^0-9]", ""));
        }
        return numeric.substring(0, length);
    }
}
