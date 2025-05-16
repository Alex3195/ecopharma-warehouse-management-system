package uz.duol.ecopharmwarehouse.module.settings.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;
import uz.duol.ecopharmwarehouse.module.settings.exception.SettingNotFoundException;
import uz.duol.ecopharmwarehouse.module.settings.mapper.SettingMapper;
import uz.duol.ecopharmwarehouse.module.settings.specification.SettingsSpecification;
import uz.duol.ecopharmwarehouse.repositories.SettingsRepository;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepository repository;
    private final SettingMapper mapper;

    public SettingsDTO create(SettingsDTO dto) {
        var e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    public SettingsDTO findById(Long id) {
        var e = repository.findById(id)
                .orElseThrow(() -> new SettingNotFoundException("Setting not found"));
        return mapper.toDto(e);
    }

    public SettingsDTO update(Long id, SettingsDTO dto) {
        findById(id);
        var e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    public void delete(Long id) {
        SettingsDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    public Page<SettingsDTO> findAll(String search, Pageable pageable) {
        Specification<SettingsEntity> spec = Specification.where(SettingsSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(SettingsSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
