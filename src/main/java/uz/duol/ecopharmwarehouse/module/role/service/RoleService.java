package uz.duol.ecopharmwarehouse.module.role.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.common.DataTableRequest;
import uz.duol.ecopharmwarehouse.common.DataTableResponse;
import uz.duol.ecopharmwarehouse.common.PageUtil;
import uz.duol.ecopharmwarehouse.module.role.dto.RoleDto;
import uz.duol.ecopharmwarehouse.module.role.mapper.RoleMapper;
import uz.duol.ecopharmwarehouse.module.role.specification.RoleSpecification;
import uz.duol.ecopharmwarehouse.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleDto create(RoleDto roleDto) {
        var role = roleMapper.toEntity(roleDto);
        roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    public RoleDto findByName(String name) {
        var role = roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return roleMapper.toDto(role);
    }

    public DataTableResponse<RoleDto> findAll(DataTableRequest request) {
        var spec = RoleSpecification.advancedFilter(request.getFilters());
        var pageable = PageUtil.getPageable(request);
        var page = roleRepository.findAll(spec, pageable)
                .map(roleMapper::toDto);
        return new DataTableResponse<>(page);
    }

    public RoleDto update(String name, RoleDto roleDto) {
        var role = roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    public void delete(String name) {
        var role = roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        roleRepository.delete(role);
    }
}
