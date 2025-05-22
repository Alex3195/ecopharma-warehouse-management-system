package uz.duol.ecopharmwarehouse.module.task.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.enums.TaskStatusEnum;
import uz.duol.ecopharmwarehouse.enums.TaskTypeEnum;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskMapperUnitTest extends BaseUnitTest {
    @Autowired
    private TaskMapper taskMapper;

    private TaskEntity entity;
    private TaskDTO dto;

    @BeforeEach
    void setUp() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(1L);
        locationDTO.setName("Location 1");
        locationDTO.setBarcode("123456789");
        locationDTO.setAvailable(true);
        locationDTO.setCell(12L);
        locationDTO.setFloor(32L);
        locationDTO.setSector(54L);
        locationDTO.setRack(32L);
        locationDTO.setWarehouseId(12L);

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setId(1L);
        locationEntity.setName("Location 1");
        locationEntity.setBarcode("123456789");
        locationEntity.setIsEmpty(true);
        locationEntity.setCell(12L);
        locationEntity.setFloor(32L);
        locationEntity.setSector(54L);
        locationEntity.setRack(32L);
        locationEntity.setWarehouseId(12L);


        dto = new TaskDTO();
        dto.setId(566954L);
        dto.setName("Task shipping");
        dto.setAssignedTo("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        dto.setProductId(8001L);
        dto.setTaskType(TaskTypeEnum.PACK);
        dto.setDueDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        dto.setTaskStatus(TaskStatusEnum.PENDING);
        dto.setLocation(List.of(locationDTO));

        entity = new TaskEntity();
        entity.setId(566954L);
        entity.setName("Task shipping");
        entity.setAssignedTo("4a6b7165-2e61-4b35-9afb-5f576ee13049");
        entity.setProductId(8001L);
        entity.setTaskType(TaskTypeEnum.PACK);
        entity.setDueDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        entity.setTaskStatus(TaskStatusEnum.PENDING);
        entity.setLocation(List.of(locationEntity));
    }

    @Test
    void testToEntity() {
        TaskEntity result = taskMapper.toEntity(dto);
        assertEquals(entity.toString(), result.toString());
    }

    @Test
    void testToDto() {
        TaskDTO result = taskMapper.toDto(entity);
        assertEquals(dto.toString(), result.toString());
    }
}
