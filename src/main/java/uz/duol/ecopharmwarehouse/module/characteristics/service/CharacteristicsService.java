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
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.characteristics.exception.CharacteristicsNotFoundException;
import uz.duol.ecopharmwarehouse.module.characteristics.mapper.CharacteristicsMapper;
import uz.duol.ecopharmwarehouse.module.characteristics.specification.CharacteristicSpecification;
import uz.duol.ecopharmwarehouse.repositories.CharacteristicsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicsService {
    private final CharacteristicsRepository repository;
    @Qualifier("characteristicsMapper")
    private final CharacteristicsMapper mapper;

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
        // Fetch the managed entity from DB
        CharacteristicEntity existing = repository.findById(id)
                .orElseThrow(() -> new CharacteristicsNotFoundException("Characteristics not found"));

        // Update simple fields
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setType(dto.getType());

        // Sync values manually
        List<CharacterValuesEntity> newValues = dto.getValues().stream().map(valueDto -> {
            CharacterValuesEntity valEntity = new CharacterValuesEntity();
            valEntity.setId(valueDto.getId()); // if null, treated as new
            valEntity.setValue(valueDto.getValue());
            valEntity.setCharacteristic(existing); // maintain relationship
            return valEntity;
        }).toList();

        // Remove orphaned values
        existing.getValues().removeIf(oldVal ->
                newValues.stream().noneMatch(newVal ->
                        newVal.getId() != null && newVal.getId().equals(oldVal.getId())
                )
        );

        // Add or update values
        for (CharacterValuesEntity newVal : newValues) {
            if (newVal.getId() == null || existing.getValues().stream().noneMatch(ev -> ev.getId().equals(newVal.getId()))) {
                existing.getValues().add(newVal);
            } else {
                // Optional: update existing value (if needed)
                CharacterValuesEntity existingVal = existing.getValues().stream()
                        .filter(ev -> ev.getId().equals(newVal.getId()))
                        .findFirst().orElseThrow();
                existingVal.setValue(newVal.getValue());
            }
        }

        // Persist changes
        return mapper.toDto(repository.save(existing));
    }



    @Transactional
    public void delete(Long id) {
        CharacteristicsDTO dto = findById(id);
        repository.deleteById(dto.getId());
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
