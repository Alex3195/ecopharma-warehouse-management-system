package uz.duol.ecopharmwarehouse.module.task.dto;

import lombok.Data;
import uz.duol.ecopharmwarehouse.enums.TaskStatusEnum;
import uz.duol.ecopharmwarehouse.enums.TaskTypeEnum;
import uz.duol.ecopharmwarehouse.module.location.dto.LocationDTO;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDTO {
    private Long id;

    private String name;

    private TaskTypeEnum taskType;

    private TaskStatusEnum taskStatus;

    private String assignedTo;

    private UserDTO assignedToUser;

    private LocalDateTime dueDate;

    private Long productId;

    private ProductDTO product;

    private List<LocationDTO> location;
}
