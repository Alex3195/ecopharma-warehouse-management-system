package uz.duol.ecopharmwarehouse.module.address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.AddressEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;
import uz.duol.ecopharmwarehouse.module.address.exception.AddressNotFoundException;
import uz.duol.ecopharmwarehouse.module.address.mapper.AddressMapper;
import uz.duol.ecopharmwarehouse.module.address.specification.AddressSpecification;
import uz.duol.ecopharmwarehouse.repositories.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;
    @Qualifier("addressMapper")
    private final AddressMapper mapper;

    public AddressDTO create(AddressDTO dto) {
        AddressEntity entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public AddressDTO findById(Long id) {
        AddressEntity entity = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        return mapper.toDto(entity);
    }

    public AddressDTO update(Long id, AddressDTO dto) {
        findById(id);
        AddressEntity entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        AddressDTO dto = findById(id);
        AddressEntity entity = mapper.toEntity(dto);
        entity.setStatus(Status.DELETED);
        repository.save(entity);
    }

    public Page<AddressDTO> findAll(String search, Pageable pageable) {
        Specification<AddressEntity> spec = Specification.where(AddressSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(AddressSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
