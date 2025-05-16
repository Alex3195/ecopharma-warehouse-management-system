package uz.duol.ecopharmwarehouse.module.outboundshipment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.mapper.OutboundShipmentMapper;
import uz.duol.ecopharmwarehouse.module.outboundshipment.specification.OutboundShipmentSpecification;
import uz.duol.ecopharmwarehouse.repositories.OutboundShipmentRepository;

@Service
@RequiredArgsConstructor
public class OutboundShipmentService {
    private final OutboundShipmentMapper mapper;
    private final OutboundShipmentRepository repository;

    @Transactional
    public OutboundShipmentDto create(OutboundShipmentDto dto) {
        var entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public OutboundShipmentDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Outbound shipment not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public OutboundShipmentDto update(Long id, OutboundShipmentDto dto) {
        OutboundShipmentDto data = findById(id);
        var entity = mapper.toEntity(dto);
        entity.setId(data.getId());
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        OutboundShipmentDto data = findById(id);
        repository.deleteById(data.getId());
    }

    public Page<OutboundShipmentDto> findAll(String search, Pageable pageable) {
        Specification<OutboundShipmentEntity> spec = Specification.where(null);
        if (!search.isBlank()) {
            spec = spec.and(OutboundShipmentSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
