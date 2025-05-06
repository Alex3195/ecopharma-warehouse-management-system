package uz.duol.ecopharmwarehouse.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.config.RabbitMQConfig;
import uz.duol.ecopharmwarehouse.event.UserCreatedEvent;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
import uz.duol.ecopharmwarehouse.module.users.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventListener {
    private final UserService userService;

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Received user created event: {}", event);
        UserDTO user = getUserDto(event);
        userService.create(user);
    }

    private UserDTO getUserDto(UserCreatedEvent event) {
        UserDTO userDto = new UserDTO();
        userDto.setId(event.getId());
        userDto.setFirstName(event.getFirstName());
        userDto.setLastName(event.getLastName());
        userDto.setEmail(event.getEmail());
        userDto.setPhone(event.getPhone());
        userDto.setTelegramNickName(event.getTelegramNickName());
        userDto.setHikvisionAccessId(event.getHikvisionAccessId());
        return userDto;
    }
}
