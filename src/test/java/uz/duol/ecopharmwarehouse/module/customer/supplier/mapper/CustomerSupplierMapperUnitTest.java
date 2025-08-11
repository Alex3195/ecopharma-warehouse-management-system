package uz.duol.ecopharmwarehouse.module.customer.supplier.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.CustomerSupplierEntity;
import uz.duol.ecopharmwarehouse.enums.CustomerSupplierEnum;
import uz.duol.ecopharmwarehouse.module.customersupplier.dto.CustomerSupplierDto;
import uz.duol.ecopharmwarehouse.module.customersupplier.mapper.CustomerSupplierMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CustomerSupplierMapperUnitTest extends BaseUnitTest {
    @Autowired
    private CustomerSupplierMapper mapper;

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
    void testToDto() {
        var actual = mapper.toDto(entity);

        assertEquals(actual.toString(), dto.toString());
    }

    @Test
    void testToEntity() {
        var actual = mapper.toEntity(dto);

        assertEquals(actual.toString(), entity.toString());
    }

}
