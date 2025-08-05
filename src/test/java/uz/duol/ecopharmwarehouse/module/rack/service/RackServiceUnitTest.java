package uz.duol.ecopharmwarehouse.module.rack.service;

import org.jetbrains.annotations.NotNull;
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
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.RackTypeEnum;
import uz.duol.ecopharmwarehouse.module.floor.dto.FloorDTO;
import uz.duol.ecopharmwarehouse.module.location.service.LocationService;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackInfo;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackRequest;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackUpdateRequest;
import uz.duol.ecopharmwarehouse.module.rack.exception.RackNotFoundException;
import uz.duol.ecopharmwarehouse.module.rack.mapper.RackMapper;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;
import uz.duol.ecopharmwarehouse.module.sector.service.SectorService;
import uz.duol.ecopharmwarehouse.module.warehouse.dto.WarehouseDTO;
import uz.duol.ecopharmwarehouse.repositories.CellsRepository;
import uz.duol.ecopharmwarehouse.repositories.FloorRepository;
import uz.duol.ecopharmwarehouse.repositories.RackRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RackServiceUnitTest extends BaseUnitTest {
    @InjectMocks
    private RackService service;
    @Mock
    private SectorService sectorService;
    @Mock
    private LocationService locationService;
    @Mock
    private FloorRepository floorRepository;
    @Mock
    private CellsRepository cellsRepository;
    @Mock
    private RackRepository repository;
    @Mock
    private RackMapper mapper;

    private RackEntity entity;
    private RackDTO dto;
    private RackUpdateRequest updateRequest;

    @BeforeEach
    public void setUp() {
        entity = new RackEntity();
        entity.setId(1L);
        entity.setName("Rack 1");
        entity.setType(RackTypeEnum.PALLET_RACKING);
        entity.setSectorId(1L);
        entity.setHeight(3.0);
        entity.setWidth(3.0);
        entity.setDepth(3.0);
        entity.setFloors(List.of(new FloorEntity()));

        dto = new RackDTO();
        dto.setId(1L);
        dto.setName("Rack 1");
        dto.setType(RackTypeEnum.PALLET_RACKING);
        dto.setSectorId(1L);
        dto.setHeight(3.0);
        dto.setWidth(3.0);
        dto.setDepth(3.0);
        dto.setFloors(List.of(new FloorDTO()));

        updateRequest = new RackUpdateRequest();
        dto.setId(1L);
        dto.setName("Rack 1");
        dto.setType(RackTypeEnum.PALLET_RACKING);
        dto.setSectorId(1L);
        dto.setHeight(3.0);
        dto.setWidth(3.0);
        dto.setDepth(3.0);
    }

    @Test
    void testCreate() {
        RackRequest request = new RackRequest();
        request.setId(1L);
        request.setName("Rack A");
        request.setDepth(100.0);
        request.setHeight(200.0);
        request.setWidth(300.0);
        request.setType(RackTypeEnum.PALLET_RACKING);
        request.setSectorId(10L);
        request.setFloors(2);
        request.setCells(3);
        WarehouseDTO warehouse = new WarehouseDTO();
        warehouse.setName("Warehouse A");
        SectorDTO sector = new SectorDTO();
        sector.setId(10L);
        sector.setName("Sector A");
        sector.setWarehouseId(1L);
        sector.setWarehouseId(2L);
        sector.setWarehouse(warehouse);

        List<FloorEntity> floors = getFloorEntities(request);
        entity.setFloors(floors);

        when(sectorService.findById(anyLong())).thenReturn(sector);
        when(repository.saveAndFlush(any(RackEntity.class))).thenReturn(entity);
        when(floorRepository.saveAndFlush(any(FloorEntity.class))).thenReturn(new FloorEntity());
        when(cellsRepository.saveAll(anyList())).thenReturn(List.of(new CellEntity()));
        when(mapper.toDto(any(RackEntity.class))).thenReturn(dto);
        when(locationService.create(any())).thenReturn(null);

        RackDTO result = service.create(request);

        assertNotNull(result);
        assertEquals(dto.toString(), result.toString());

        verify(repository, times(1)).saveAndFlush(any(RackEntity.class));
        verify(mapper, times(1)).toDto(any(RackEntity.class));
    }

    private static @NotNull List<FloorEntity> getFloorEntities(RackRequest request) {
        List<FloorEntity> floors = new ArrayList<>();
        for (int i = 0; i < request.getFloors(); i++) {
            FloorEntity floor = new FloorEntity();
            floor.setLevel(i);
            floor.setHeight(request.getHeight() / request.getFloors());

            List<CellEntity> cells = getCellEntities(request, i);
            floor.setCells(cells);
            floors.add(floor);
        }
        return floors;
    }

    private static @NotNull List<CellEntity> getCellEntities(RackRequest request, int i) {
        List<CellEntity> cells = new ArrayList<>();
        for (int j = 0; j < request.getCells(); j++) {
            CellEntity cell = new CellEntity();
            cell.setCode(request.getName() + "-" + i + "-" + j);
            cell.setWidth(request.getWidth() / request.getCells());
            cell.setHeight(request.getHeight() / request.getFloors());
            cell.setIsEmpty(true);
            cell.setDepth(request.getDepth());
            cells.add(cell);
        }
        return cells;
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(RackEntity.class))).thenReturn(dto);

        RackDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(dto.toString(), result.toString());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        RackNotFoundException e = assertThrows(RackNotFoundException.class, () -> service.findById(1L));

        assertNotNull(e);
        assertEquals("Rack not found", e.getMessage());
    }

    @Test
    void testFindAll() {
        Page<RackEntity> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(any(RackEntity.class))).thenReturn(dto);

        var actual = service.findAll(new DataTableRequest());

        assertNotNull(actual);
        assertEquals(1, actual.getTotalElements());
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toEntity(any(RackDTO.class))).thenReturn(entity);
        when(repository.save(any(RackEntity.class))).thenReturn(entity);
        when(mapper.toDto(any(RackEntity.class))).thenReturn(dto);

        RackDTO result = service.update(1L, updateRequest);

        assertNotNull(result);
    }

    @Test
    void testUpdateNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        RackNotFoundException e = assertThrows(RackNotFoundException.class, () -> service.update(1L, updateRequest));

        assertNotNull(e);
        assertEquals("Rack not found", e.getMessage());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(RackEntity.class))).thenReturn(dto);
        doNothing().when(repository).deleteById(anyLong());
        service.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        RackNotFoundException e = assertThrows(RackNotFoundException.class, () -> service.delete(1L));

        assertNotNull(e);
        assertEquals("Rack not found", e.getMessage());
    }
}
