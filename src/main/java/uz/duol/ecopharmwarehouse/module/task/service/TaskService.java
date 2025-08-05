package uz.duol.ecopharmwarehouse.module.task.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.TaskEntity;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;
import uz.duol.ecopharmwarehouse.module.task.exception.TaskNotFoundException;
import uz.duol.ecopharmwarehouse.module.task.mapper.TaskMapper;
import uz.duol.ecopharmwarehouse.module.task.specification.TaskSpecification;
import uz.duol.ecopharmwarehouse.module.users.mapper.UserMapper;
import uz.duol.ecopharmwarehouse.repositories.TaskRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

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
    public DataTableResponse<TaskDTO> findAll(DataTableRequest request) {
        Specification<TaskEntity> spec = TaskSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(item -> {
            var dto = mapper.toDto(item);
            dto.setAssignedToUser(userMapper.toDto(item.getAssignedToUser()));
            return dto;
        });
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<TaskEntity> spec = TaskSpecification.advancedFilter(request.getFilters());

        List<TaskEntity> allData = repository.findAll(spec);

        List<TaskDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "task");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=aggregation.xlsx");

        try {
            response.getOutputStream().write(excel);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write Excel to response", e);
        }
    }
}
