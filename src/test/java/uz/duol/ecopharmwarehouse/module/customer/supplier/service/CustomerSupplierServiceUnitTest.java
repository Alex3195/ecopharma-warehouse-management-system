package uz.duol.ecopharmwarehouse.module.customer.supplier.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.entity.CustomerSupplierEntity;
import uz.duol.ecopharmwarehouse.enums.CustomerSupplierEnum;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.customersupplier.dto.CustomerSupplierDto;
import uz.duol.ecopharmwarehouse.module.customersupplier.mapper.CustomerSupplierMapper;
import uz.duol.ecopharmwarehouse.module.customersupplier.service.CustomerSupplierService;
import uz.duol.ecopharmwarehouse.module.customersupplier.specification.CustomerSupplierSpecification;
import uz.duol.ecopharmwarehouse.repositories.CustomerSupplierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerSupplierServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private CustomerSupplierService customerSupplierService;
    @Mock
    private CustomerSupplierRepository repository;
    @Mock
    private CustomerSupplierMapper mapper;
    @Mock
    private CustomerSupplierSpecification specification;

    private CustomerSupplierDto dto;
    private CustomerSupplierEntity entity;

    @BeforeEach
    void setUp() {
        dto = new CustomerSupplierDto();
        dto.setId("1-id");
        dto.setFirstName("Dava");
        dto.setLastName("Abdusalomov");
        dto.setUsername("dava");
        dto.setPhone("998500043703");
        dto.setUserType(CustomerSupplierEnum.SUPPLIER);

        entity = new CustomerSupplierEntity();
        entity.setId("1-id");
        entity.setFirstName("Dava");
        entity.setLastName("Abdusalomov");
        entity.setPhone("998500043703");
        entity.setUsername("dava");
        entity.setUserType(CustomerSupplierEnum.SUPPLIER);
    }

    @Test
    void create() {
        when(mapper.toEntity(any(CustomerSupplierDto.class))).thenReturn(entity);
        when(mapper.toDto(any(CustomerSupplierEntity.class))).thenReturn(dto);
        when(repository.save(any(CustomerSupplierEntity.class))).thenReturn(entity);


        CustomerSupplierDto actual = customerSupplierService.create(dto);

        assertNotNull(actual);
        assertEquals(actual.toString(), dto.toString());
        verify(repository, times(1)).save(any(CustomerSupplierEntity.class));
    }

    @Test
    void testUpdate() {
        when(mapper.toDto(any(CustomerSupplierEntity.class))).thenReturn(dto);
        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        when(repository.save(any(CustomerSupplierEntity.class))).thenReturn(entity);
        doNothing().when(mapper).update(any(CustomerSupplierEntity.class), any(CustomerSupplierDto.class));

        CustomerSupplierDto actual = customerSupplierService.update("1-id", dto);

        assertNotNull(actual);
        assertEquals(actual.toString(), dto.toString());
    }


    @Test
    void testFindById() {
        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(CustomerSupplierEntity.class))).thenReturn(dto);

        CustomerSupplierDto actual = customerSupplierService.findById("1-id");

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
        verify(repository, times(1)).findById(anyString());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyString())).thenReturn(Optional.of(entity));

        customerSupplierService.delete("1-id");

        assertEquals(Status.DELETED, entity.getStatus());
    }

    @Test
    void testFindAll() {
        List<CustomerSupplierEntity> entityList = new ArrayList<>();
        entityList.add(entity);

        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(entityList));

        when(mapper.toDto(any(CustomerSupplierEntity.class))).thenReturn(dto);

        DataTableRequest request = new DataTableRequest();
        request.setPage(0);
        request.setSize(10);
        request.setFilters(Map.of());

        DataTableResponse<CustomerSupplierDto> results = customerSupplierService.findAll(request);

        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
        assertEquals(dto.toString(), results.getData().getFirst().toString());

        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(1)).toDto(any(CustomerSupplierEntity.class));
    }
}
