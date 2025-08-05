package uz.duol.ecopharmwarehouse.module.address.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;
import uz.duol.ecopharmwarehouse.module.address.exception.AddressNotFoundException;
import uz.duol.ecopharmwarehouse.module.address.mapper.AddressMapper;
import uz.duol.ecopharmwarehouse.repositories.AddressRepository;
import uz.duol.ecopharmwarehouse.repositories.WarehouseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AddressServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private WarehouseRepository warehouseRepository;

    private AddressEntity entity;
    private AddressDTO dto;

    @BeforeEach
    void setUp() {
        dto = new AddressDTO();
        dto.setId(1L);
        dto.setCity("New York");
        dto.setCountry("US");
        dto.setLatitude(21.2);
        dto.setLongitude(22.2);
        dto.setState("US");
        dto.setPostalCode("Postal code");
        dto.setAdditionalInfo("Additional info");
        dto.setStreet("Street");

        entity = new AddressEntity();
        entity.setId(1L);
        entity.setCity("New York");
        entity.setCountry("US");
        entity.setLatitude(21.2);
        entity.setLongitude(22.2);
        entity.setState("US");
        entity.setPostalCode("Postal code");
        entity.setAdditionalInfo("Additional info");
        entity.setStreet("Street");
    }

    @Test
    void testCreate() {
        when(addressMapper.toEntity(any(AddressDTO.class))).thenReturn(entity);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(entity);
        when(addressMapper.toDto(any(AddressEntity.class))).thenReturn(dto);

        AddressDTO actual = addressService.create(dto);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());

        verify(addressRepository, times(1)).save(any(AddressEntity.class));

    }

    @Test
    void testFindById() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(addressMapper.toDto(any(AddressEntity.class))).thenReturn(dto);

        AddressDTO actual = addressService.findById(1L);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());

    }

    @Test
    void testFindByIdThenNotFound() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());
        AddressNotFoundException e = assertThrows(AddressNotFoundException.class, () -> addressService.findById(1L));
        assertEquals("Address not found", e.getMessage());
    }

    @Test
    void testUpdate() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(addressMapper.toDto(any(AddressEntity.class))).thenReturn(dto);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(entity);
        when(addressMapper.toEntity(any(AddressDTO.class))).thenReturn(entity);

        AddressDTO actual = addressService.update(1L, dto);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
        verify(addressRepository, times(1)).save(any(AddressEntity.class));
    }

    @Test
    void testUpdateThenNotFound() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());
        AddressNotFoundException e = assertThrows(AddressNotFoundException.class, () -> addressService.delete(1L));
        assertEquals("Address not found", e.getMessage());
    }

    @Test
    void testDelete() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(addressMapper.toDto(any(AddressEntity.class))).thenReturn(dto);
        when(warehouseRepository.existsByAddressId(anyLong())).thenReturn(false);

        addressService.delete(1L);

        verify(addressRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteThenNotFound() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());
        AddressNotFoundException e = assertThrows(AddressNotFoundException.class, () -> addressService.delete(1L));
        assertEquals("Address not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Page<AddressEntity> page = new PageImpl<>(List.of(entity));
        when(addressRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(addressMapper.toDto(any(AddressEntity.class))).thenReturn(dto);

        DataTableResponse<AddressDTO> result = addressService.findAll(new DataTableRequest());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getData().size());
        assertEquals(dto.toString(), result.getData().get(0).toString());
    }
}
