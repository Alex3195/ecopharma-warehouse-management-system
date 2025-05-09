package uz.duol.ecopharmwarehouse.module.inbound.receipt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.CrossDockingEntity;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.mapper.InboundReceiptMapper;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.specification.InboundReceiptSpecification;
import uz.duol.ecopharmwarehouse.repositories.CrossDockingRepository;
import uz.duol.ecopharmwarehouse.repositories.InboundReceiptRepository;

@Service
@RequiredArgsConstructor
public class InboundReceiptService {
    private final InboundReceiptRepository repository;
    @Qualifier("inboundReceiptMapper")
    private final InboundReceiptMapper mapper;
    private final CrossDockingRepository crossDockingRepository;

    @Transactional
    public InboundReceiptDto create(InboundReceiptDto receipt) {
        InboundReceiptEntity e = mapper.toEntity(receipt);
        repository.save(e);
        if (receipt.getCrossDockType() != null) {
            CrossDockingEntity crossDockingEntity = crossDockingEntityFromRequest(receipt);
            crossDockingRepository.save(crossDockingEntity);
        }
        return mapper.toDto(e);
    }

    private CrossDockingEntity crossDockingEntityFromRequest(InboundReceiptDto request) {
        CrossDockingEntity crossDockingEntity = new CrossDockingEntity();
        crossDockingEntity.setInboundReceiptId(request.getId());
        crossDockingEntity.setCrossDockType(request.getCrossDockType());
        crossDockingEntity.setProcessingTime(request.getProcessingTime());
        return crossDockingEntity;
    }

    @Transactional(readOnly = true)
    public InboundReceiptDto findById(Long id) {
        InboundReceiptEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InboundReceiptEntity not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public InboundReceiptDto update(Long id, InboundReceiptDto receipt) {
        InboundReceiptDto dto = findById(id);
        InboundReceiptEntity entity = mapper.toEntity(receipt);
        entity.setId(dto.getId());
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        InboundReceiptDto dto = findById(id);
        InboundReceiptEntity entity = mapper.toEntity(dto);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Page<InboundReceiptDto> findAll(String search, Pageable pageable) {

        Specification<InboundReceiptEntity> spec = InboundReceiptSpecification.isActive();
        if (search != null) {
            spec = spec.and(InboundReceiptSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
