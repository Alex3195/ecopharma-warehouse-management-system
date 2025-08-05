package uz.duol.ecopharmwarehouse.module.customersupplier.service;

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
import uz.duol.ecopharmwarehouse.entity.CustomerSupplierEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.customersupplier.dto.CustomerSupplierDto;
import uz.duol.ecopharmwarehouse.module.customersupplier.mapper.CustomerSupplierMapper;
import uz.duol.ecopharmwarehouse.module.customersupplier.specification.CustomerSupplierSpecification;
import uz.duol.ecopharmwarehouse.repositories.CustomerSupplierRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerSupplierService {

    private final CustomerSupplierRepository repository;
    private final CustomerSupplierMapper mapper;

    @Transactional
    public CustomerSupplierDto create(CustomerSupplierDto dto) {
        var entity = mapper.toEntity(dto);
        var savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public CustomerSupplierDto findById(String id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer supplier not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public CustomerSupplierDto update(String id, CustomerSupplierDto dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer Supplier not found"));

        mapper.update(entity, dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(String id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer Supplier not found"));

        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    @Transactional
    public DataTableResponse<CustomerSupplierDto> findAll(DataTableRequest request) {
        Specification<CustomerSupplierEntity> spec = CustomerSupplierSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<CustomerSupplierEntity> spec = CustomerSupplierSpecification.advancedFilter(request.getFilters());

        List<CustomerSupplierEntity> allData = repository.findAll(spec);

        List<CustomerSupplierDto> addressDTOList = allData.stream()
                .map(mapper::toDto)
                .toList();

        byte[] excel = ExcelExportUtil.exportToExcel(addressDTOList, fieldNames, columnNames, "customer_supplier");

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
