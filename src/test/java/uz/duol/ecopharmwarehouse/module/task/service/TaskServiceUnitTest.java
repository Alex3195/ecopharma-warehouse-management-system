package uz.duol.ecopharmwarehouse.module.task.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.LocationEntity;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.enums.TaskStatusEnum;
import uz.duol.ecopharmwarehouse.enums.TaskTypeEnum;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;
import uz.duol.ecopharmwarehouse.module.task.mapper.TaskMapper;
import uz.duol.ecopharmwarehouse.module.users.mapper.UserMapper;
import uz.duol.ecopharmwarehouse.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TaskServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private TaskService service;
    @Mock
    private UserMapper userMapper;
    @Mock
    private TaskRepository repository;
    @Mock
    private TaskMapper mapper;

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
    void testCreate() {
        when(mapper.toEntity(any(TaskDTO.class))).thenReturn(entity);
        when(repository.save(any(TaskEntity.class))).thenReturn(entity);
        when(mapper.toDto(any(TaskEntity.class))).thenReturn(dto);

        TaskDTO actual = service.create(dto);

        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(TaskEntity.class))).thenReturn(dto);
        doNothing().when(mapper).updateEntity(any(TaskEntity.class), any(TaskDTO.class));
        when(repository.save(any(TaskEntity.class))).thenReturn(entity);

        TaskDTO actual = service.update(566954L, dto);
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(TaskEntity.class))).thenReturn(dto);

        TaskDTO actual = service.findById(566954L);
        assertNotNull(actual);
        assertEquals(dto.toString(), actual.toString());
    }

    @Test
    void testFindAll() {
        Page<TaskEntity> page = new PageImpl<>(List.of(entity));
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(any(TaskEntity.class))).thenReturn(dto);

        Page<TaskDTO> actual = service.findAll(null, null, pageable);
        assertNotNull(actual);
        assertEquals(1, actual.getTotalPages());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(TaskEntity.class))).thenReturn(dto);
        doNothing().when(repository).deleteById(anyLong());

        service.delete(566954L);

        verify(repository, times(1)).deleteById(anyLong());

    }
}
