package uz.duol.ecopharmwarehouse.module.sector.characteristics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.dto.SectorCharacteristicDTO;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.exception.SectorCharacteristicsNotFoundException;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.mapper.SectorCharacteristicsMapper;
import uz.duol.ecopharmwarehouse.module.sector.characteristics.specification.SectorCharacteristicsSpecification;
import uz.duol.ecopharmwarehouse.repositories.SectorCharacteristicsRepository;

@Service
@RequiredArgsConstructor
public class SectorCharacteristicsService {
    private final SectorCharacteristicsRepository repository;
    private final SectorCharacteristicsMapper mapper;

    public SectorCharacteristicDTO create(SectorCharacteristicDTO dto) {
        var e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    public SectorCharacteristicDTO findById(Long id) {
        SectorCharacteristicEntity entity = repository.findByIdAndStatusIsNot(id, Status.DELETED).orElseThrow(() -> new SectorCharacteristicsNotFoundException("Sector characteristic not found"));
        return mapper.toDto(entity);
    }

    public SectorCharacteristicDTO update(Long id, SectorCharacteristicDTO dto) {
        findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        SectorCharacteristicDTO dto = findById(id);
        var entity = mapper.toEntity(dto);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    public Page<SectorCharacteristicDTO> findAll(String search, Pageable pageable) {
        Specification<SectorCharacteristicEntity> spec = Specification.where(SectorCharacteristicsSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(SectorCharacteristicsSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
