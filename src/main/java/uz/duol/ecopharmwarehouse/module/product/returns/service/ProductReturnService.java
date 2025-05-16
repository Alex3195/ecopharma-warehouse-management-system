package uz.duol.ecopharmwarehouse.module.product.returns.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.ProductReturnEntity;
import uz.duol.ecopharmwarehouse.module.product.returns.dto.ProductReturnDto;
import uz.duol.ecopharmwarehouse.module.product.returns.mapper.ProductReturnMapper;
import uz.duol.ecopharmwarehouse.module.product.returns.specification.ProductReturnSpecification;
import uz.duol.ecopharmwarehouse.repositories.ProductReturnRepository;

@Service
@RequiredArgsConstructor
public class ProductReturnService {
    private final ProductReturnRepository repository;
    private final ProductReturnMapper mapper;

    public ProductReturnDto create(ProductReturnDto dto) {
        var entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public ProductReturnDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product return not found"));
        return mapper.toDto(entity);
    }

    public ProductReturnDto update(Long id, ProductReturnDto dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product return not found"));
        mapper.updateEntity(entity, dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public void delete(Long id) {
        var dto = findById(id);
        repository.deleteById(dto.getId());
    }

    public Page<ProductReturnDto> findAll(String search, Pageable pageable) {
        Specification<ProductReturnEntity> isActive = ProductReturnSpecification.isActive();
        if (search != null && !search.isEmpty()) {
            isActive = isActive.and(ProductReturnSpecification.hasText(search));
        }
        return repository.findAll(isActive, pageable).map(mapper::toDto);
    }
}
