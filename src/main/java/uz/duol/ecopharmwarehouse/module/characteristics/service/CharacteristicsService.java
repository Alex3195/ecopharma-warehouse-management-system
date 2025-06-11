package uz.duol.ecopharmwarehouse.module.characteristics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.CharacteristicEntity;
import uz.duol.ecopharmwarehouse.module.characteristics.dto.CharacteristicsDTO;
import uz.duol.ecopharmwarehouse.module.characteristics.exception.CharacteristicsNotFoundException;
import uz.duol.ecopharmwarehouse.module.characteristics.mapper.CharacteristicsMapper;
import uz.duol.ecopharmwarehouse.module.characteristics.specification.CharacteristicSpecification;
import uz.duol.ecopharmwarehouse.repositories.CharacteristicsRepository;

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
        findById(id);
        var existing = mapper.toEntity(dto);
        repository.save(existing);
        return mapper.toDto(existing);
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
