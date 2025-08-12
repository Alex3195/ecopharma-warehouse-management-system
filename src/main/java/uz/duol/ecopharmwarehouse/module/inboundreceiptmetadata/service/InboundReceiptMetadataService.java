package uz.duol.ecopharmwarehouse.module.inboundreceiptmetadata.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.module.inboundreceiptmetadata.dto.InboundReceiptMetadataDto;
import uz.duol.ecopharmwarehouse.module.inboundreceiptmetadata.mapper.InboundReceiptMetadataMapper;
import uz.duol.ecopharmwarehouse.repositories.InboundReceiptMetadataRepository;

@Service
@RequiredArgsConstructor
public class InboundReceiptMetadataService {

    private final InboundReceiptMetadataMapper mapper;
    private final InboundReceiptMetadataRepository repository;

    @Transactional
    public InboundReceiptMetadataDto create(InboundReceiptMetadataDto dto) {
        var e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public InboundReceiptMetadataDto findById(Long id){
        var e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inbound Receipt Metadata is not found!"));
        return mapper.toDto(e);
    }

    @Transactional
    public InboundReceiptMetadataDto update(Long id, InboundReceiptMetadataDto dto) {
        findById(id);
        var e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        InboundReceiptMetadataDto dto = findById(id);
        repository.deleteById(dto.getId());
    }
}
