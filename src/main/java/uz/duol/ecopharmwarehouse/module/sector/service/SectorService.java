package uz.duol.ecopharmwarehouse.module.sector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.SectorEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
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

    public SectorDTO create(SectorDTO dto) {
        SectorEntity entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public SectorDTO findById(Long id) {
        SectorEntity entity = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new SectorNotFoundException("Sector not found"));
        return mapper.toDto(entity);
    }

    public SectorDTO update(Long id, SectorDTO dto) {
        findById(id);
        SectorEntity entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        SectorDTO dto = findById(id);
        SectorEntity entity = mapper.toEntity(dto);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    public Page<SectorDTO> findAll(String search, Pageable pageable) {
        Specification<SectorEntity> spec = Specification.where(SectorSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(SectorSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
