package uz.duol.ecopharmwarehouse.module.users.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.RoleEnum;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;

import java.util.List;

@Data
public class UserDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private RoleEnum role;

    private List<TaskDTO> tasks;
}
