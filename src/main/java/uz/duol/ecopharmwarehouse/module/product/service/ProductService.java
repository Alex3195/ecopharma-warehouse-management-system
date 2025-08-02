package uz.duol.ecopharmwarehouse.module.product.service;

import jakarta.servlet.http.HttpServletResponse;
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
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

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

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<ProductEntity> spec = ProductSpecification.advancedFilter(request.getFilters());

        List<ProductEntity> allData = repository.findAll(spec);

        List<ProductDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "product");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=aggregation.xlsx");

        try {
            response.getOutputStream().write(excel);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write Excel to response", e);
        }
    }
}
