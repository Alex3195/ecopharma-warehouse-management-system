package uz.duol.ecopharmwarehouse.module.sector.characteristics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.SectorCharacteristicEntity;
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
        SectorCharacteristicEntity entity = repository.findById(id)
                .orElseThrow(() -> new SectorCharacteristicsNotFoundException("Sector characteristic not found"));
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
        repository.deleteById(dto.getId());
    }

    public DataTableResponse<SectorCharacteristicDTO> findAll(DataTableRequest request) {
        Specification<SectorCharacteristicEntity> spec = SectorCharacteristicsSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }
}
