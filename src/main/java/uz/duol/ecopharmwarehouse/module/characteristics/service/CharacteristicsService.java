package uz.duol.ecopharmwarehouse.module.characteristics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.CharacterValuesEntity;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.characteristics.exception.CharacteristicsNotFoundException;
import uz.duol.ecopharmwarehouse.module.characteristics.mapper.CharacteristicsMapper;
import uz.duol.ecopharmwarehouse.module.characteristics.specification.CharacteristicSpecification;
import uz.duol.ecopharmwarehouse.module.characteristics.values.dto.CharacteristicValueDto;
import uz.duol.ecopharmwarehouse.repositories.CharacteristicValuesRepository;
import uz.duol.ecopharmwarehouse.repositories.CharacteristicsRepository;
import uz.duol.ecopharmwarehouse.repositories.SectorCharacteristicsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacteristicsService {
    private final CharacteristicsRepository repository;
    @Qualifier("characteristicsMapper")
    private final CharacteristicsMapper mapper;
    private final CharacteristicValuesRepository characteristicValuesRepository;
    private final SectorCharacteristicsRepository sectorCharacteristicsRepository;

    @Transactional
    public CharacteristicsDTO create(CharacteristicsDTO dto) {
        var e = mapper.toEntity(dto);
        if (e.getValues() != null) {
            e.getValues().forEach(value -> value.setCharacteristic(e));
        }
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public CharacteristicsDTO findById(Long id) {
        var e = repository.findById(id)
                .orElseThrow(() -> new CharacteristicsNotFoundException("Characteristics not found"));
        return mapper.toDto(e);
    }

    @Transactional
    public CharacteristicsDTO update(Long id, CharacteristicsDTO dto) {
        CharacteristicEntity existing = repository.findById(id)
                .orElseThrow(() -> new CharacteristicsNotFoundException("Characteristics not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setType(dto.getType());

        Map<Long, CharacterValuesEntity> existingValuesMap = existing.getValues().stream()
                .collect(Collectors.toMap(CharacterValuesEntity::getId, Function.identity()));

        List<CharacterValuesEntity> updatedValues = new ArrayList<>();

        for (CharacteristicValueDto dtoValue : dto.getValues()) {
            if (dtoValue.getId() != null && existingValuesMap.containsKey(dtoValue.getId())) {
                CharacterValuesEntity existingValue = existingValuesMap.get(dtoValue.getId());
                existingValue.setValue(dtoValue.getValue());
                updatedValues.add(existingValue);
            } else {
                CharacterValuesEntity newValue = new CharacterValuesEntity();
                newValue.setValue(dtoValue.getValue());
                newValue.setCharacteristic(existing);
                updatedValues.add(newValue);
            }
        }

        List<CharacterValuesEntity> valuesToRemove = existing.getValues().stream()
                .filter(v -> !updatedValues.contains(v))
                .toList();

        existing.getValues().clear();

        existing.getValues().addAll(updatedValues);

        if (!valuesToRemove.isEmpty()) {
            characteristicValuesRepository.deleteAll(valuesToRemove);
            characteristicValuesRepository.flush();
        }
        repository.save(existing);
        return mapper.toDto(existing);
    }


    @Transactional
    public void delete(Long id) {
        var e = repository.findById(id).orElseThrow(() -> new CharacteristicsNotFoundException("Characteristics not found"));
        if(sectorCharacteristicsRepository.existsByCharacteristicId(id)){
            throw new RuntimeException("You cannot delete this characteristic because it bind with some of the sectors");
        }
        e.setStatus(Status.DELETED);
        repository.save(e);
        if (e.getValues() != null) {
            characteristicValuesRepository.deleteAll(e.getValues());
        }
    }

    @Transactional(readOnly = true)
    public Page<CharacteristicsDTO> findAll(String search, Pageable pageable) {
        Specification<CharacteristicEntity> spec = CharacteristicSpecification.isActive();
        if (search != null && !search.isEmpty()) {
            spec = spec.and(CharacteristicSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
