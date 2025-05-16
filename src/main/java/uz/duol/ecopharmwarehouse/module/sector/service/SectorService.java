package uz.duol.ecopharmwarehouse.module.sector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.module.sector.dto.SectorDTO;
import uz.duol.ecopharmwarehouse.module.sector.exception.SectorNotFoundException;
import uz.duol.ecopharmwarehouse.module.sector.mapper.SectorMapper;
import uz.duol.ecopharmwarehouse.module.sector.specification.SectorSpecification;
import uz.duol.ecopharmwarehouse.repositories.SectorRepository;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository repository;
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
        return mapper.toDto(entity);
    }

    @Transactional
    public SectorDTO update(Long id, SectorDTO dto) {
        findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        SectorDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    @Transactional(readOnly = true)
    public Page<SectorDTO> findAll(String search, Pageable pageable) {
        Specification<SectorEntity> spec = Specification.where(SectorSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(SectorSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
