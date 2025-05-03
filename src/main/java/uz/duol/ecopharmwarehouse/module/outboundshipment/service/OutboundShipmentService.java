package uz.duol.ecopharmwarehouse.module.outboundshipment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.OutboundShipmentEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.outboundshipment.dto.OutboundShipmentDto;
import uz.duol.ecopharmwarehouse.module.outboundshipment.mapper.OutboundShipmentMapper;
import uz.duol.ecopharmwarehouse.module.outboundshipment.specification.OutboundShipmentSpecification;
import uz.duol.ecopharmwarehouse.repositories.OutboundShipmentRepository;

@Service
@RequiredArgsConstructor
public class OutboundShipmentService {
    private final OutboundShipmentMapper mapper;
    private final OutboundShipmentRepository repository;

    public OutboundShipmentDto create(OutboundShipmentDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public OutboundShipmentDto findById(Long id) {
        OutboundShipmentEntity entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Outbound shipment not found"));
        return mapper.toDto(entity);
    }

    public OutboundShipmentDto update(Long id, OutboundShipmentDto dto) {
        OutboundShipmentDto data = findById(id);
        OutboundShipmentEntity entity = mapper.toEntity(dto);
        entity.setId(data.getId());
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        OutboundShipmentDto data = findById(id);
        OutboundShipmentEntity entity = mapper.toEntity(data);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    public Page<OutboundShipmentDto> findAll(String search, Pageable pageable) {
        Specification<OutboundShipmentEntity> spec = OutboundShipmentSpecification.isActive();
        if (!search.isBlank()) {
            spec = spec.and(OutboundShipmentSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
