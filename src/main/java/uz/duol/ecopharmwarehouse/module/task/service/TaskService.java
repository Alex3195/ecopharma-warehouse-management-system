package uz.duol.ecopharmwarehouse.module.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;
import uz.duol.ecopharmwarehouse.module.task.exception.TaskNotFoundException;
import uz.duol.ecopharmwarehouse.module.task.mapper.TaskMapper;
import uz.duol.ecopharmwarehouse.module.task.specification.TaskSpecification;
import uz.duol.ecopharmwarehouse.module.users.mapper.UserMapper;
import uz.duol.ecopharmwarehouse.repositories.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final UserMapper userMapper;

    @Transactional
    public TaskDTO create(TaskDTO taskDTO) {
        var e = mapper.toEntity(taskDTO);
        repository.save(e);
        return mapper.toDto(e);
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        var e = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        var dto = mapper.toDto(e);
        dto.setAssignedToUser(userMapper.toDto(e.getAssignedToUser()));
        return dto;
    }

    @Transactional
    public TaskDTO update(Long id, TaskDTO taskDTO) {
        var e = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        mapper.updateEntity(e, taskDTO);
        repository.save(e);
        var dto = mapper.toDto(e);
        dto.setAssignedToUser(userMapper.toDto(e.getAssignedToUser()));
        return dto;
    }

    @Transactional
    public void delete(Long id) {
        TaskDTO dto = findById(id);
        repository.deleteById(dto.getId());
    }

    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(String search, String assignedTo, Pageable pageable) {
        Specification<TaskEntity> spec = Specification.where(TaskSpecification.isActive());
        if (search != null) {
            spec = spec.and(TaskSpecification.hasText(search));
        }
        if (assignedTo != null) {
            spec = spec.and(TaskSpecification.hasAssignedTo(assignedTo));
        }
        return repository.findAll(spec, pageable).map(item -> {
            var dto = mapper.toDto(item);
            dto.setAssignedToUser(userMapper.toDto(item.getAssignedToUser()));
            return dto;
        });
    }

}
