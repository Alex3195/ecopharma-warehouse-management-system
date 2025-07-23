package uz.duol.ecopharmwarehouse.module.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.ProductEntity;
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
        var productEntity = mapper.toEntity(dto);
        if (dto.getPerformedBy() != null) {
            productEntity.setCreatedBy(dto.getPerformedBy());
        }
        return mapper.toDto(repository.save(productEntity));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        var productEntity = repository.findById(id).orElseThrow(() -> new ProductNotFundException("Product not found"));
        return mapper.toDto(productEntity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        findById(id);
        var productEntity = mapper.toEntity(dto);
        productEntity.setId(id);
        return mapper.toDto(repository.save(productEntity));

    }

    @Transactional
    public void delete(Long id) {
        ProductDTO productDTO = findById(id);
        repository.deleteById(productDTO.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<ProductDTO> findAll(DataTableRequest request) {
        Specification<ProductEntity> spec = ProductSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }
}
