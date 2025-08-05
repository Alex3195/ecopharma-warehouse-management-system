package uz.duol.ecopharmwarehouse.module.users.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.entity.UserEntity;
import uz.duol.ecopharmwarehouse.enums.Status;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
import uz.duol.ecopharmwarehouse.module.users.dto.UserUpdateDto;
import uz.duol.ecopharmwarehouse.module.users.exception.UserNotFoundException;
import uz.duol.ecopharmwarehouse.module.users.mapper.UserMapper;
import uz.duol.ecopharmwarehouse.module.users.specification.UserSpecification;
import uz.duol.ecopharmwarehouse.repositories.UserRepository;
import uz.duol.ecopharmwarehouse.utils.ExcelExportUtil;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;
    @Qualifier("userMapper")
    private final UserMapper mapper;

    public UserDTO create(UserDTO dto) {
        var e = mapper.toEntity(dto);
        if (dto.getPerformedBy() != null) {
            e.setCreatedBy(dto.getPerformedBy());
        }
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(String id) {
        var e = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapper.toDto(e);
    }

    @Transactional
    public UserDTO update(String id, UserUpdateDto request) {
        UserDTO dto = findById(id);
        var e = mapper.toEntity(dto);
        mapper.updateUserFromDto(request, e);
        e.setId(id);
        repository.save(e);
        return mapper.toDto(e);
    }

    @Transactional
    public void delete(String id, String performedBy) {
        UserDTO dto = findById(id);
        var e = mapper.toEntity(dto);
        e.setStatus(Status.DELETED);
        if (performedBy != null) {
            e.setUpdatedBy(performedBy);
        }
        repository.save(e);
    }

    @Transactional
    public DataTableResponse<UserDTO> findAll(DataTableRequest request) {
        Specification<UserEntity> spec = UserSpecification.advancedFilter(request.getFilters());
        Pageable pageable = PageUtil.getPageable(request);
        var page = repository.findAll(spec, pageable).map(mapper::toDto);
        return new DataTableResponse<>(page);
    }

    @Transactional(readOnly = true)
    public void exportToExcel(HttpServletResponse response, DataTableRequest request, List<String> columnNames, List<String> fieldNames) {
        Specification<UserEntity> spec = UserSpecification.advancedFilter(request.getFilters());

        List<UserEntity> allData = repository.findAll(spec);

        List<UserDTO> dtos = allData.stream().map(mapper::toDto).toList();

        byte[] excel = ExcelExportUtil.exportToExcel(dtos, fieldNames, columnNames, "users");

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
