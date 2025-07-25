package uz.duol.ecopharmwarehouse.module.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.UnitsEntity;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.unit.exception.UnitNotFoundException;
import uz.duol.ecopharmwarehouse.module.unit.mapper.UnitMapper;
import uz.duol.ecopharmwarehouse.module.unit.specification.UnitSpecification;
import uz.duol.ecopharmwarehouse.repositories.UnitsRepository;

@Service
@RequiredArgsConstructor
public class UnitsService {
    private final UnitsRepository repository;
    private final UnitMapper mapper;

    public UnitsDTO create(UnitsDTO dto) {
        var entity = mapper.toEntity(dto);
        if (dto.getPerformedBy() != null) {
            entity.setCreatedBy(dto.getPerformedBy());
        }
        return mapper.toDto(repository.save(entity));
    }

    public UnitsDTO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new UnitNotFoundException("Unit not found"));
        return mapper.toDto(entity);
    }

    public UnitsDTO update(Long id, UnitsDTO dto) {
        findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        UnitsDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    public DataTableResponse<UnitsDTO> findAll(DataTableRequest request) {
        Specification<UnitsEntity> spec = UnitSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }
}
