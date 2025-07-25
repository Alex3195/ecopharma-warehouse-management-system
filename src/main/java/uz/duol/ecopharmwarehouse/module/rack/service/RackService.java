package uz.duol.ecopharmwarehouse.module.rack.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.entity.FloorEntity;
import uz.duol.ecopharmwarehouse.entity.RackEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.cells.mapper.CellsMapper;
import uz.duol.ecopharmwarehouse.module.floor.mapper.FloorMapper;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.location.service.LocationService;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackDTO;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackInfo;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackRequest;
import uz.duol.ecopharmwarehouse.module.rack.dto.RackUpdateRequest;
import uz.duol.ecopharmwarehouse.module.rack.exception.RackNotFoundException;
import uz.duol.ecopharmwarehouse.module.rack.mapper.RackMapper;
import uz.duol.ecopharmwarehouse.module.rack.specification.RackSpecification;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;
import uz.duol.ecopharmwarehouse.module.sector.service.SectorService;
import uz.duol.ecopharmwarehouse.repositories.CellsRepository;
import uz.duol.ecopharmwarehouse.repositories.FloorRepository;
import uz.duol.ecopharmwarehouse.repositories.RackRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RackService {
    private final RackRepository repository;
    private final LocationService locationService;
    private final SectorService sectorService;
    private final FloorRepository floorRepository;
    private final CellsRepository cellsRepository;
    private final FloorMapper floorMapper;
    private final CellsMapper cellsMapper;
    private final RackMapper rackMapper;

    @Transactional
    public RackDTO create(RackRequest request) {
        // First save just the rack to get its ID
        RackEntity rackEntity = new RackEntity();
        rackEntity.setName(request.getName());
        rackEntity.setDepth(request.getDepth());
        rackEntity.setHeight(request.getHeight());
        rackEntity.setWidth(request.getWidth());
        rackEntity.setType(request.getType());
        rackEntity.setSectorId(request.getSectorId());
        rackEntity = repository.saveAndFlush(rackEntity);

        // Now create floors
        for (int i = 0; i < request.getFloors(); i++) {
            FloorEntity floor = new FloorEntity();
            floor.setLevel(i);
            floor.setHeight(request.getHeight() / request.getFloors());
            floor.setRack(rackEntity);
            floor.setRackId(rackEntity.getId());

            // Save floor to get its ID
            floor = floorRepository.saveAndFlush(floor);

            // Now create cells with the floor's ID
            List<CellEntity> cells = new ArrayList<>();
            for (int j = 0; j < request.getCells(); j++) {
                CellEntity cell = new CellEntity();
                cell.setCode(request.getName() + "-" + i + "-" + j);
                cell.setWidth(request.getWidth() / request.getCells());
                cell.setHeight(request.getHeight() / request.getFloors());
                cell.setIsEmpty(true);
                cell.setDepth(request.getDepth());
                cell.setFloor(floor);
                cell.setFloorId(floor.getId()); // Now floor has an ID
                cells.add(cell);
            }

            // Save all cells for this floor
            cells = cellsRepository.saveAll(cells);
            floor.setCells(cells);
            rackEntity.getFloors().add(floor);
        }

        createLocations(rackEntity);
        return rackMapper.toDto(rackEntity);
    }

    @Transactional(readOnly = true)
    protected void createLocations(RackEntity rackEntity) {
        SectorDTO sector = sectorService.findById(rackEntity.getSectorId());
        rackEntity.getFloors().forEach(floor -> floor.getCells().forEach(cell -> {
            LocationDTO location = new LocationDTO();
            location.setName(sector.getWarehouse().getName() + "-" + sector.getName() + "-" + rackEntity.getName() + "-" + floor.getLevel() + "-" + cell.getCode());
            location.setWarehouseId(sector.getWarehouse().getId());
            location.setSector(rackEntity.getSectorId());
            location.setRack(rackEntity.getId());
            location.setFloor(floor.getId());
            location.setCell(cell.getId());
            location.setAvailable(true);
            locationService.create(location);
        }));
    }


    @Transactional(readOnly = true)
    public RackDTO findById(Long id) {
        var e = repository.findById(id).orElseThrow(() -> new RackNotFoundException("Rack not found"));
        var dto = rackMapper.toDto(e);
        var floors = floorRepository.findByRackIdAndStatusIsNot(id, Status.DELETED);
        return getRackDTO(dto, floors);
    }

    @Transactional
    public RackDTO update(Long id, RackUpdateRequest request) {
        var rack = repository.findById(id).orElseThrow(() -> new RackNotFoundException("Rack not found"));
        rackMapper.updateEntity(rack, request);
        repository.save(rack);
        return rackMapper.toDto(rack);
    }

    @Transactional(readOnly = true)
    public List<RackDTO> findBySectorId(Long sectorId) {
        var racks = repository.findBySectorId(sectorId);
        return racks.stream().map(rackMapper::toDto).toList();
    }

    @Transactional
    public void delete(Long id) {
        RackDTO dto = findById(id);
        if (cellsIsEmpty(id)) {
            throw new RuntimeException("Rack has products and you cannot delete it.");
        } else {
            deleteAllFloorsAndItsCellsByRackId(id);
            repository.deleteById(dto.getId());
        }
    }

    private boolean cellsIsEmpty(Long rackId) {
        List<FloorEntity> floors = floorRepository.findByRackIdAndStatusIsNot(rackId, Status.DELETED);
        for (FloorEntity floor : floors) {
            List<CellEntity> cells = cellsRepository.findByFloorIdAndStatusIsNot(floor.getId(), Status.DELETED);
            long emptyCellsCount = cells.stream().filter(cell -> !cell.getIsEmpty()).count();
            boolean emptyCells = emptyCellsCount == 0;
            if (emptyCells) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    protected void deleteAllFloorsAndItsCellsByRackId(Long rackId) {
        List<FloorEntity> floors = floorRepository.findByRackIdAndStatusIsNot(rackId, Status.DELETED);
        floors.forEach(floor -> {
            List<CellEntity> cells = cellsRepository.findByFloorIdAndStatusIsNot(floor.getId(), Status.DELETED);
            cells.forEach(cell -> cell.setStatus(Status.DELETED));
            cellsRepository.saveAllAndFlush(cells);
            floor.setStatus(Status.DELETED);
        });
        floorRepository.saveAllAndFlush(floors);
    }

    @Transactional(readOnly = true)
    public DataTableResponse<RackInfo> findAll(DataTableRequest request) {
        Specification<RackEntity> spec = RackSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(item -> {
            var dto = rackMapper.toDto(item);
            var floors = floorRepository.findByRackIdAndStatusIsNot(item.getId(), Status.DELETED);
            var rackDto = getRackDTO(dto, floors);

            return getRackInfo(rackDto);
        });
        return new DataTableResponse<>(page);
    }

    private RackInfo getRackInfo(RackDTO rackDto) {
        var rackInfo = new RackInfo();
        rackInfo.setId(rackDto.getId());
        rackInfo.setName(rackDto.getName());
        rackInfo.setType(rackDto.getType());
        rackInfo.setHeight(rackDto.getHeight());
        rackInfo.setWidth(rackDto.getWidth());
        rackInfo.setDepth(rackDto.getDepth());
        rackInfo.setSectorId(rackDto.getSectorId());
        rackInfo.setSector(rackDto.getSector());
        rackInfo.setFloorCount(rackDto.getFloors().size());
        if (rackDto.getFloors() != null && !rackDto.getFloors().isEmpty()) {
            rackInfo.setCellCount(rackDto.getFloors().getFirst().getCells().size());
            rackInfo.setSumOfCells(rackDto.getFloors().getFirst().getCells().size() * rackDto.getFloors().size());
        }

        return rackInfo;
    }

    @NotNull
    private RackDTO getRackDTO(RackDTO dto, List<FloorEntity> floors) {
        if (floors == null || floors.isEmpty()) {
            dto.setFloors(new ArrayList<>());
            return dto;
        }
        dto.setFloors(floors.stream().map(floorMapper::toDto).toList());

        dto.getFloors().forEach(floor -> {
            var cells = cellsRepository.findByFloorIdAndStatusIsNot(floor.getId(), Status.DELETED);
            floor.setCells(cells.stream().map(cellsMapper::toDto).collect(Collectors.toList()));
        });
        return dto;
    }
}
