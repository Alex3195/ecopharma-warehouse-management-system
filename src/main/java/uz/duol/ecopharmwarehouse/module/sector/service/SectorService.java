package uz.duol.ecopharmwarehouse.module.sector.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;
import uz.duol.ecopharmwarehouse.module.sector.exception.SectorNotFoundException;
import uz.duol.ecopharmwarehouse.module.sector.mapper.SectorMapper;
import uz.duol.ecopharmwarehouse.module.sector.specification.SectorSpecification;
import uz.duol.ecopharmwarehouse.repositories.RackRepository;
import uz.duol.ecopharmwarehouse.repositories.SectorRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository repository;
    private final RackRepository rackRepository;
    private final SectorMapper mapper;

    @Transactional
    public SectorDTO create(SectorDTO dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public SectorDTO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new SectorNotFoundException("Sector not found"));
        return mapToDtoAndSetNumberOfRackInSector(entity);
    }

    @Transactional
    public SectorDTO update(Long id, SectorDTO dto) {
        findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(id);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        SectorDTO dto = findById(id);
        if (rackRepository.existsBySectorId(id)) {
            throw new RuntimeException("You cannot delete this sector because it has racks");
        }
        repository.deleteById(dto.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<SectorDTO> findAll(DataTableRequest request) {
        Specification<SectorEntity> spec = SectorSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(this::mapToDtoAndSetNumberOfRackInSector);
        return new DataTableResponse<>(page);
    }

    private SectorDTO mapToDtoAndSetNumberOfRackInSector(SectorEntity e) {
        var dto = mapper.toDto(e);
        var numberOfRacks = rackRepository.countBySectorId(e.getId());
        dto.setNumberOfRacks(numberOfRacks);
        return dto;
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<SectorEntity> spec = SectorSpecification.advancedFilter(request.getFilters());

        List<SectorEntity> allData = repository.findAll(spec);

        List<SectorDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "sector");

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
