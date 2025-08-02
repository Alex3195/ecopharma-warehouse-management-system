package uz.duol.ecopharmwarehouse.module.crossdocking.service;

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
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;
import uz.duol.ecopharmwarehouse.module.crossdocking.mapper.CrossDockingMapper;
import uz.duol.ecopharmwarehouse.module.crossdocking.specification.CrossDockingSpecification;
import uz.duol.ecopharmwarehouse.repositories.CrossDockingRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrossDockingService {

    private final CrossDockingRepository crossDockingRepository;
    private final CrossDockingMapper crossDockingMapper;

    @Transactional
    public CrossDockingDto create(CrossDockingDto crossDockingDto) {
        var entity = crossDockingMapper.toEntity(crossDockingDto);
        var savedEntity = crossDockingRepository.save(entity);
        return crossDockingMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public CrossDockingDto findById(Long id) {
        var entity = crossDockingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cross docking not found"));
        return crossDockingMapper.toDto(entity);
    }

    @Transactional
    public CrossDockingDto update(Long id, CrossDockingDto crossDockingDto) {
        var entity = crossDockingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cross docking not found"));

        crossDockingMapper.updateEntity(entity, crossDockingDto);
        crossDockingRepository.save(entity);
        return crossDockingMapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        var entity = crossDockingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cross docking not found"));
        crossDockingRepository.deleteById(entity.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<CrossDockingDto> findAll(DataTableRequest request) {
        Specification<CrossDockingEntity> spec = CrossDockingSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = crossDockingRepository.findAll(spec, pageable)
                .map(crossDockingMapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<CrossDockingEntity> spec = CrossDockingSpecification.advancedFilter(request.getFilters());

        List<CrossDockingEntity> allData = crossDockingRepository.findAll(spec);

        List<CrossDockingDto> addressDTOList = allData.stream()
                .map(crossDockingMapper::toDto)
                .toList();

        byte[] excel = ExcelExportUtil.exportToExcel(addressDTOList, fieldNames, columnNames, "crossDocking");

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