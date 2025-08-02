package uz.duol.ecopharmwarehouse.module.inventory.snapshot.servcie;

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
import uz.duol.ecopharmwarehouse.entity.InventoryEntity;
import uz.duol.ecopharmwarehouse.entity.InventorySnapshotEntity;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.dto.InventorySnapshotDto;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.mapper.InventorySnapshotMapper;
import uz.duol.ecopharmwarehouse.module.inventory.snapshot.specification.InventorySnapshotSpecification;
import uz.duol.ecopharmwarehouse.repositories.InventoryRepository;
import uz.duol.ecopharmwarehouse.repositories.InventorySnapshotRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

@Service
@RequiredArgsConstructor
public class InventorySnapshotService {
    private final InventoryRepository inventoryRepository;
    private final InventorySnapshotRepository repository;
    private final InventorySnapshotMapper mapper;

    @Transactional
    public void generateSnapshot() {
        List<InventoryEntity> currentInventory = inventoryRepository.findAll();
        LocalDateTime snapshotTime = LocalDateTime.now();

        List<InventorySnapshotEntity> snapshots = currentInventory.stream().map(inv -> {
            InventorySnapshotEntity snapshot = new InventorySnapshotEntity();
            snapshot.setProductId(inv.getProductId());
            snapshot.setLocationId(inv.getLocationId());
            snapshot.setQuantity(inv.getQuantity());
            snapshot.setUnitId(inv.getUnitId());
            snapshot.setSnapshotTime(snapshotTime);
            return snapshot;
        }).toList();

        repository.saveAll(snapshots);
    }

    @Transactional
    public void deleteOldSnapshots(int olderThanDays) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(olderThanDays);
        repository.deleteBySnapshotTimeBefore(cutoff);
    }

    @Transactional(readOnly = true)
    public DataTableResponse<InventorySnapshotDto> findAll(DataTableRequest request) {
        Specification<InventorySnapshotEntity> spec = InventorySnapshotSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public InventorySnapshotDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));
        return mapper.toDto(entity);
    }

    public InventorySnapshotDto create(InventorySnapshotDto dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public InventorySnapshotDto update(Long id, InventorySnapshotDto dto) {
        var existing = findById(id);
        mapper.updateDto(existing, dto);
        var entity = mapper.toEntity(existing);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        var existing = findById(id);
        repository.deleteById(existing.getId());
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<InventorySnapshotEntity> spec = InventorySnapshotSpecification.advancedFilter(request.getFilters());

        List<InventorySnapshotEntity> allData = repository.findAll(spec);

        List<InventorySnapshotDto> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "inventory_snapshot");

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
