package uz.duol.ecopharmwarehouse.module.product.returns.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.ProductReturnEntity;
import uz.duol.ecopharmwarehouse.module.product.returns.dto.ProductReturnDto;
import uz.duol.ecopharmwarehouse.module.product.returns.mapper.ProductReturnMapper;
import uz.duol.ecopharmwarehouse.module.product.returns.specification.ProductReturnSpecification;
import uz.duol.ecopharmwarehouse.repositories.ProductReturnRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

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

    public DataTableResponse<ProductReturnDto> findAll(DataTableRequest request) {
        Specification<ProductReturnEntity> isActive = ProductReturnSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(isActive, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<ProductReturnEntity> spec = ProductReturnSpecification.advancedFilter(request.getFilters());

        List<ProductReturnEntity> allData = repository.findAll(spec);

        List<ProductReturnDto> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "product_return");

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
