package uz.duol.ecopharmwarehouse.module.crossdocking.service;

import jakarta.persistence.EntityNotFoundException;
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
}