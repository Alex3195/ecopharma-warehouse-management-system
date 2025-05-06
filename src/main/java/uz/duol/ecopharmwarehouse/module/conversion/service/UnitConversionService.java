package uz.duol.ecopharmwarehouse.module.conversion.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.UnitConversionEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;
import uz.duol.ecopharmwarehouse.module.conversion.mapper.ConversionMapper;
import uz.duol.ecopharmwarehouse.module.conversion.specification.ConversionSpecification;
import uz.duol.ecopharmwarehouse.repositories.UnitsConversionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitConversionService {
    private final UnitsConversionRepository repository;
    @Qualifier("conversionMapper")
    private final ConversionMapper mapper;

    @Transactional
    public UnitConversionDto create(UnitConversionDto dto) {
        UnitConversionEntity entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public UnitConversionDto update(Long id, UnitConversionDto dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found"));

        UnitConversionEntity updatedEntity = mapper.toEntity(dto);
        updatedEntity.setId(id);

        return mapper.toDto(repository.save(updatedEntity));
    }

    @Transactional
    public void delete(Long id) {
        UnitConversionEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found"));

        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UnitConversionDto> get(Long baseUnitId, Long alternativeUnitId) {
        Specification<UnitConversionEntity> spec = ConversionSpecification.isActive()
                .and(ConversionSpecification.hasBaseUnitIdAndAlternativeUnitId(baseUnitId, alternativeUnitId));

        List<UnitConversionEntity> entities = repository.findAll(spec);
        return entities.stream().map(item -> {
            UnitConversionDto dto = mapper.toDto(item);
            dto.setBaseUnitSymbol(item.getBaseUnit().getSymbol());
            dto.setAlternativeUnitSymbol(item.getAlternativeUnit().getSymbol());
            return dto;
        }).toList();
    }

    @Transactional
    public Page<UnitConversionDto> getByMainUnitId(Long id, Pageable pageable) {
        Specification<UnitConversionEntity> spec = ConversionSpecification.isActive()
                .and(ConversionSpecification.hasBaseUnitId(id));
        return repository.findAll(spec, pageable).map(item -> {
            UnitConversionDto dto = mapper.toDto(item);
            dto.setBaseUnitSymbol(item.getBaseUnit().getSymbol());
            dto.setAlternativeUnitSymbol(item.getAlternativeUnit().getSymbol());
            return dto;
        });
    }

    @Transactional
    public void deleteAll(List<Long> ids) {
        List<UnitConversionEntity> entities = repository.findAllById(ids);
        entities.forEach(entity -> entity.setStatus(Status.DELETED));
        repository.saveAll(entities);
    }
}

