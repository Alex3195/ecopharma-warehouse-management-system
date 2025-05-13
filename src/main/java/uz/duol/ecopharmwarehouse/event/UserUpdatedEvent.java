package uz.duol.ecopharmwarehouse.event;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdatedEvent {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String hikvisionAccessId;
    private String telegramNickName;
    private List<Long> departmentId;
    private String performedBy;
}
