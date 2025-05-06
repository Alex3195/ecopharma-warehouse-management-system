package uz.duol.ecopharmwarehouse.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent{
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String hikvisionAccessId;
    private String telegramNickName;
    private List<Long> departmentId;
}
