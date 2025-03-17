package uz.duol.ecopharmwarehouse.module.product.metadata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.ProductMetadataEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.product.metadata.dto.ProductMetadataDTO;
import uz.duol.ecopharmwarehouse.module.product.metadata.exception.ProductMetadataNotFoundException;
import uz.duol.ecopharmwarehouse.module.product.metadata.mapper.ProductMetadataMapper;
import uz.duol.ecopharmwarehouse.repositories.ProductMetadataRepository;

@Service
@RequiredArgsConstructor
public class ProductMetaDataService {
    private final ProductMetadataMapper mapper;
    private final ProductMetadataRepository repository;

    @Transactional
    public ProductMetadataDTO create(ProductMetadataDTO dto) {
        ProductMetadataEntity e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public ProductMetadataDTO findById(Long id) {
        ProductMetadataEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new ProductMetadataNotFoundException("Metadata not found"));
        return mapper.toDto(e);
    }

    @Transactional
    public ProductMetadataDTO update(Long id, ProductMetadataDTO dto) {
        findById(id);
        ProductMetadataEntity e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        ProductMetadataDTO dto = findById(id);
        ProductMetadataEntity e = mapper.toEntity(dto);
        e.setStatus(Status.DELETED);
        repository.save(e);
    }
}
