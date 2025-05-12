package uz.duol.ecopharmwarehouse.module.transport.label.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.transport.label.dto.TransportLabelDto;
import uz.duol.ecopharmwarehouse.module.transport.label.mapper.TransportLabelMapper;
import uz.duol.ecopharmwarehouse.module.transport.label.specification.TransportLabelSpecification;
import uz.duol.ecopharmwarehouse.repositories.TransportLabelRepository;

@Service
@RequiredArgsConstructor
public class TransportLabelService {
    private final TransportLabelRepository repository;
    private final TransportLabelMapper mapper;

    public TransportLabelDto create(TransportLabelDto dto) {
        var entity = mapper.toEntity(dto);
        var savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    public TransportLabelDto update(Long id, TransportLabelDto dto) {
        var entity = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new RuntimeException("Transport label not found"));
        mapper.updateEntity(entity, dto);
        var updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    public TransportLabelDto findById(Long id) {
        var entity = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new RuntimeException("Transport label not found"));
        return mapper.toDto(entity);
    }

    public void delete(Long id) {
        var label = findById(id);
        var entity = mapper.toEntity(label);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    public Page<TransportLabelDto> findAll(String search, Pageable pageable) {
        var spec = TransportLabelSpecification.isActive();
        if (search != null && !search.isBlank()) {
            spec = spec.and(TransportLabelSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);

    }
}
