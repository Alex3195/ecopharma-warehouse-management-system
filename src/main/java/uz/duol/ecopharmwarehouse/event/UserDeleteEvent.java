package uz.duol.ecopharmwarehouse.event;

import lombok.Data;

@Data
public class UserDeleteEvent {
    private String userId;
    private String performedBy;
}
