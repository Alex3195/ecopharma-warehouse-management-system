package uz.duol.ecopharmwarehouse.module.inbound.receipt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;
import uz.duol.ecopharmwarehouse.module.crossdocking.dto.CrossDockingDto;
import uz.duol.ecopharmwarehouse.module.crossdocking.service.CrossDockingService;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.dto.InboundReceiptDto;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.mapper.InboundReceiptMapper;
import uz.duol.ecopharmwarehouse.module.inbound.receipt.specification.InboundReceiptSpecification;
import uz.duol.ecopharmwarehouse.repositories.InboundReceiptRepository;

@Service
@RequiredArgsConstructor
public class InboundReceiptService {
    private final InboundReceiptRepository repository;
    @Qualifier("inboundReceiptMapper")
    private final InboundReceiptMapper mapper;
    private final CrossDockingService crossDockingService;

    @Transactional
    public InboundReceiptDto create(InboundReceiptDto receipt) {
        var e = mapper.toEntity(receipt);
        repository.save(e);
        if (receipt.getCrossDockType() != null) {
            var crossDockingEntity = crossDockingEntityFromRequest(receipt);
            crossDockingService.create(crossDockingEntity);
        }
        return mapper.toDto(e);
    }

    private CrossDockingDto crossDockingEntityFromRequest(InboundReceiptDto request) {
        CrossDockingDto crossDocking = new CrossDockingDto();
        crossDocking.setInboundReceiptId(request.getId());
        crossDocking.setCrossDockType(request.getCrossDockType());
        crossDocking.setProcessingTime(request.getProcessingTime());
        return crossDocking;
    }

    @Transactional(readOnly = true)
    public InboundReceiptDto findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InboundReceiptEntity not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public InboundReceiptDto update(Long id, InboundReceiptDto receipt) {
        InboundReceiptDto dto = findById(id);
        var entity = mapper.toEntity(receipt);
        entity.setId(dto.getId());
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        InboundReceiptDto dto = findById(id);
        repository.deleteById(dto.getId());
    }

    @Transactional(readOnly = true)
    public Page<InboundReceiptDto> findAll(String search, Pageable pageable) {

        Specification<InboundReceiptEntity> spec = Specification.where(null);
        if (search != null) {
            spec = spec.and(InboundReceiptSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
