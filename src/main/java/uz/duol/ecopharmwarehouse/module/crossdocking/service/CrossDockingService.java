package uz.duol.ecopharmwarehouse.module.crossdocking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
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
        var entity = crossDockingRepository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Cross docking not found"));

        crossDockingMapper.updateEntity(entity, crossDockingDto);
        return crossDockingMapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        var entity = crossDockingRepository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("Cross docking not found"));
        entity.setStatus(Status.DELETED);
        crossDockingRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public Page<CrossDockingDto> findAll(Pageable pageable) {
        Specification<CrossDockingEntity> spec = CrossDockingSpecification.isActive();
        return crossDockingRepository.findAll(spec, pageable)
                .map(crossDockingMapper::toDto);
    }
}
