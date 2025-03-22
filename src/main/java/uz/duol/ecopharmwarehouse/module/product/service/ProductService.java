package uz.duol.ecopharmwarehouse.module.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.exception.ProductNotFundException;
import uz.duol.ecopharmwarehouse.module.product.mapper.ProductMapper;
import uz.duol.ecopharmwarehouse.module.product.specification.ProductSpecification;
import uz.duol.ecopharmwarehouse.repositories.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Transactional
    public ProductDTO create(ProductDTO dto) {
        ProductEntity productEntity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(productEntity));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        ProductEntity productEntity = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new ProductNotFundException("Product not found"));
        return mapper.toDto(productEntity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        findById(id);
        ProductEntity productEntity = mapper.toEntity(dto);
        productEntity.setId(id);
        return mapper.toDto(repository.save(productEntity));

    }

    @Transactional
    public void delete(Long id) {
        ProductDTO productDTO = findById(id);
        ProductEntity productEntity = mapper.toEntity(productDTO);
        productEntity.setStatus(Status.DELETED);
        repository.save(productEntity);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String search, Pageable pageable) {
        Specification<ProductEntity> spec = Specification.where(ProductSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(ProductSpecification.hasName(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
