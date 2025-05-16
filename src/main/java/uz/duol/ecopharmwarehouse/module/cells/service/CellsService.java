package uz.duol.ecopharmwarehouse.module.cells.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.CellEntity;
import uz.duol.ecopharmwarehouse.module.cells.dto.CellDTO;
import uz.duol.ecopharmwarehouse.module.cells.exception.CellNotFoundException;
import uz.duol.ecopharmwarehouse.module.cells.mapper.CellsMapper;
import uz.duol.ecopharmwarehouse.module.cells.specification.CellSpecification;
import uz.duol.ecopharmwarehouse.repositories.CellsRepository;

@Service
@RequiredArgsConstructor
public class CellsService {
    private final CellsRepository repository;
    private final CellsMapper mapper;

    public CellDTO create(CellDTO dto) {
        var e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    public CellDTO findById(Long id) {
        var e = repository.findById(id)
                .orElseThrow(() -> new CellNotFoundException("Cell not found"));
        return mapper.toDto(e);
    }

    public CellDTO update(Long id, CellDTO dto) {
        findById(id);
        var e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    public void delete(Long id) {
        CellDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    public Page<CellDTO> findAll(String search, Pageable pageable) {
        Specification<CellEntity> spec = Specification.where(null);
        if (search != null && !search.isEmpty()) {
            spec = spec.and(CellSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
