package uz.duol.ecopharmwarehouse.module.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.entity.UserEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
import uz.duol.ecopharmwarehouse.module.users.exception.UserNotFoundException;
import uz.duol.ecopharmwarehouse.module.users.mapper.UserMapper;
import uz.duol.ecopharmwarehouse.module.users.specification.UserSpecification;
import uz.duol.ecopharmwarehouse.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDTO create(UserDTO dto) {
        UserEntity e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    public UserDTO findById(Long id) {
        UserEntity e = repository.findByIdAndStatusIsNot(id, Status.DELETED)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapper.toDto(e);
    }

    public UserDTO update(Long id, UserDTO dto) {
        findById(id);
        UserEntity e = mapper.toEntity(dto);
        e.setId(id);
        return mapper.toDto(repository.save(e));
    }

    public void delete(Long id) {
        UserDTO dto = findById(id);
        UserEntity e = mapper.toEntity(dto);
        e.setStatus(Status.DELETED);
        repository.save(e);
    }

    public Page<UserDTO> findAll(String search, Pageable pageable) {
        Specification<UserEntity> spec = Specification.where(UserSpecification.isActive());
        if (search != null && !search.isEmpty()) {
            spec = spec.and(UserSpecification.hasText(search));
        }
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
}
