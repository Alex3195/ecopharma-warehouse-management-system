package uz.duol.ecopharmwarehouse.module.transport.label.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;
import uz.duol.ecopharmwarehouse.module.transport.label.mapper.TransportLabelMapper;
import uz.duol.ecopharmwarehouse.module.transport.label.specification.TransportLabelSpecification;
import uz.duol.ecopharmwarehouse.repositories.TransportLabelRepository;

@Service
@RequiredArgsConstructor
public class TransportLabelService {
    private final TransportLabelRepository repository;
    private final TransportLabelMapper mapper;

    @Transactional
    public TransportLabelDto create(TransportLabelDto dto) {
        var entity = mapper.toEntity(dto);
        var savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Transactional
    public TransportLabelDto update(Long id, TransportLabelDto dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport label not found"));
        mapper.updateEntity(entity, dto);
        var updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    @Transactional(readOnly = true)
    public TransportLabelDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport label not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        var label = findById(id);
        repository.deleteById(label.getId());
    }

    @Transactional(readOnly = true)
    public DataTableResponse<TransportLabelDto> findAll(DataTableRequest request) {
        var spec = TransportLabelSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);

    }
}
