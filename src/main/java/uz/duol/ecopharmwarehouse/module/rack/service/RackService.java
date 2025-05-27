package uz.duol.ecopharmwarehouse.module.rack.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        var e = repository.findById(id)
                .orElseThrow(() -> new RackNotFoundException("Rack not found"));
        var dto = rackMapper.toDto(e);
        var floors = floorRepository.findByRackIdAndStatusIsNot(id, Status.DELETED);
        return getRackDTO(dto, floors);
    }

    @Transactional
    public RackDTO update(Long id, RackDTO rackDTO) {
        findById(id);
        var e = rackMapper.toEntity(rackDTO);
        e.setId(id);
        return rackMapper.toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        RackDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    @Transactional(readOnly = true)
    public Page<RackInfo> findAll(String search, Pageable pageable) {
        Specification<RackEntity> spec = Specification.where(null);
        if (search != null && !search.isEmpty()) {
            spec = spec.and(RackSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(item -> {
            var dto = rackMapper.toDto(item);
            var floors = floorRepository.findByRackIdAndStatusIsNot(item.getId(), Status.DELETED);
            var rackDto = getRackDTO(dto, floors);

            return getRackInfo(rackDto);
        });
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
