package uz.duol.ecopharmwarehouse.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.config.RabbitMQConfig;
import uz.duol.ecopharmwarehouse.event.*;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;
import uz.duol.ecopharmwarehouse.module.conversion.service.UnitConversionService;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.service.ProductService;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.unit.service.UnitsService;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
import uz.duol.ecopharmwarehouse.module.users.dto.UserUpdateDto;
import uz.duol.ecopharmwarehouse.module.users.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventListener {
    private final UserService userService;
    private final ProductService productService;
    private final UnitsService unitsService;
    private final UnitConversionService unitConversionService;

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
    public void handleUserCreated(UserCreatedEvent event) {
        UserDTO user = getUserDto(event);
        try {
            userService.create(user);
        } catch (Exception ignored) {
        }
    }

    private UserDTO getUserDto(UserCreatedEvent event) {
        UserDTO userDto = new UserDTO();
        userDto.setId(event.getId());
        userDto.setFirstName(event.getFirstName());
        userDto.setLastName(event.getLastName());
        userDto.setEmail(event.getEmail());
        userDto.setPhone(event.getPhone());
        userDto.setUsername(event.getUsername());
        userDto.setTelegramNickName(event.getTelegramNickName());
        userDto.setHikvisionAccessId(event.getHikvisionAccessId());
        userDto.setPerformedBy(event.getPerformedBy());
        return userDto;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_UPDATED_QUEUE)
    public void handleUserUpdated(UserUpdatedEvent event) {
        UserUpdateDto user = getUserUpdateDto(event);
        try {
            userService.update(event.getId(), user);
        } catch (Exception ignored) {
        }
    }


    private UserUpdateDto getUserUpdateDto(UserUpdatedEvent event) {
        UserUpdateDto user = new UserUpdateDto();
        user.setFirstName(event.getFirstName());
        user.setLastName(event.getLastName());
        user.setEmail(event.getEmail());
        user.setPhone(event.getPhone());
        user.setUsername(event.getUsername());
        user.setTelegramNickName(event.getTelegramNickName());
        user.setHikvisionAccessId(event.getHikvisionAccessId());
        return user;

    }

    @RabbitListener(queues = RabbitMQConfig.USER_DELETED_QUEUE)
    public void handleUserDeleted(UserDeleteEvent event) {
        try {
            userService.delete(event.getUserId(), event.getPerformedBy());
        } catch (Exception ignored) {
        }
    }


    @RabbitListener(queues = RabbitMQConfig.PRODUCT_CREATED_QUEUE)
    public void handleProductCreated(ProductCreateEvent event) {
        ProductDTO product = getProductDto(event);
        try {
            productService.create(product);
        } catch (Exception ignored) {
        }
    }


    private ProductDTO getProductDto(ProductCreateEvent event) {
        ProductDTO productDto = new ProductDTO();
        productDto.setId(event.getId());
        productDto.setName(event.getName());
        productDto.setDescription(event.getDescription());
        productDto.setProductType(event.getGroupName());
        productDto.setPerformedBy(event.getPerformedBy());
        return productDto;
    }

    @RabbitListener(queues = RabbitMQConfig.UNIT_CREATED_QUEUE)
    public void handleUnitCreated(UnitCreateEvent event) {
        UnitsDTO unit = getUnitDto(event);
        try {
            unitsService.create(unit);
        } catch (Exception ignored) {
        }
    }

    private UnitsDTO getUnitDto(UnitCreateEvent event) {
        UnitsDTO unitsDTO = new UnitsDTO();
        unitsDTO.setId(event.getId());
        unitsDTO.setName(event.getName());
        unitsDTO.setCode(event.getCode());
        unitsDTO.setSymbol(event.getSymbol());
        unitsDTO.setInternationalAbbreviation(event.getInternationalAbbreviation());
        unitsDTO.setPerformedBy(event.getPerformedBy());
        return unitsDTO;
    }

    @RabbitListener(queues = RabbitMQConfig.UNIT_CONVERSION_CREATED_QUEUE)
    public void handleUnitConversionCreated(UnitConversionCreateEvent event) {
        UnitConversionDto dto = getUnitConversion(event);
        try {
            unitConversionService.create(dto);
        } catch (Exception ignored) {
        }
    }

    private UnitConversionDto getUnitConversion(UnitConversionCreateEvent event) {
        UnitConversionDto dto = new UnitConversionDto();
        dto.setId(event.getId());
        dto.setProductId(event.getProductId());
        dto.setBaseUnitId(event.getBaseUnitId());
        dto.setAlternativeUnitId(event.getAlternativeUnitId());
        dto.setBaseUnitSymbol(event.getBaseUnitSymbol());
        dto.setAlternativeUnitSymbol(event.getAlternativeUnitSymbol());
        dto.setBaseConversionFactor(event.getBaseConversionFactor());
        dto.setAlternativeConversionFactor(event.getAlternativeConversionFactor());
        dto.setPerformedBy(event.getPerformedBy());
        return dto;
    }


}
