package uz.duol.ecopharmwarehouse.module.address.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;
import uz.duol.ecopharmwarehouse.module.address.exception.AddressNotFoundException;
import uz.duol.ecopharmwarehouse.module.address.mapper.AddressMapper;
import uz.duol.ecopharmwarehouse.module.address.specification.AddressSpecification;
import uz.duol.ecopharmwarehouse.repositories.AddressRepository;
import uz.duol.ecopharmwarehouse.repositories.WarehouseRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;
    @Qualifier("addressMapper")
    private final AddressMapper mapper;
    private final WarehouseRepository warehouseRepository;

    public AddressDTO create(AddressDTO dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public AddressDTO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        return mapper.toDto(entity);
    }

    public AddressDTO update(Long id, AddressDTO dto) {
        findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        AddressDTO dto = findById(id);
        if (warehouseRepository.existsByAddressId(id)) {
            throw new RuntimeException("You cannot delete this address because it bind to one of the warehouses");
        }
        repository.deleteById(dto.getId());
    }

    public DataTableResponse<AddressDTO> findAll(DataTableRequest request) {
        Specification<AddressEntity> spec = AddressSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<AddressEntity> spec = AddressSpecification.advancedFilter(request.getFilters());

        List<AddressEntity> allData = repository.findAll(spec);

        List<AddressDTO> addressDTOList = allData.stream()
                .map(mapper::toDto)
                .toList();

        byte[] excel = ExcelExportUtil.exportToExcel(addressDTOList, fieldNames, columnNames, "address");

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
