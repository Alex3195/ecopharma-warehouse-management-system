package uz.duol.ecopharmwarehouse.module.users.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.module.task.dto.TaskDTO;

import java.util.List;

@Data
public class UserDTO {
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<TaskDTO> tasks;
}
