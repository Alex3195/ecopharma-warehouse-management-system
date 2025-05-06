package uz.duol.ecopharmwarehouse.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import uz.duol.ecopharmwarehouse.config.RabbitMQConfig;
import uz.duol.ecopharmwarehouse.event.ProductCreateEvent;
import uz.duol.ecopharmwarehouse.event.UnitConversionCreateEvent;
import uz.duol.ecopharmwarehouse.event.UnitCreateEvent;
import uz.duol.ecopharmwarehouse.event.UserCreatedEvent;
import uz.duol.ecopharmwarehouse.module.conversion.dto.UnitConversionDto;
import uz.duol.ecopharmwarehouse.module.conversion.service.UnitConversionService;
import uz.duol.ecopharmwarehouse.module.product.dto.ProductDTO;
import uz.duol.ecopharmwarehouse.module.product.service.ProductService;
import uz.duol.ecopharmwarehouse.module.unit.dto.UnitsDTO;
import uz.duol.ecopharmwarehouse.module.unit.service.UnitsService;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
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

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_CREATED_QUEUE)
    public void handleProductCreated(ProductCreateEvent event) {
        log.info("Received user created event: {}", event);
        ProductDTO product = getProductDto(event);
        productService.create(product);
    }


    private ProductDTO getProductDto(ProductCreateEvent event) {
        ProductDTO productDto = new ProductDTO();
        productDto.setId(event.getId());
        productDto.setName(event.getName());
        productDto.setDescription(event.getDescription());
        productDto.setProductType(event.getGroupName());
        return productDto;
    }

    @RabbitListener(queues = RabbitMQConfig.UNIT_CREATED_QUEUE)
    public void handleUnitCreated(UnitCreateEvent event) {
        log.info("Received user created event: {}", event);
        UnitsDTO unit = getUnitDto(event);
        unitsService.create(unit);
    }

    private UnitsDTO getUnitDto(UnitCreateEvent event) {
        UnitsDTO unitsDTO = new UnitsDTO();
        unitsDTO.setId(event.getId());
        unitsDTO.setName(event.getName());
        unitsDTO.setCode(event.getCode());
        unitsDTO.setSymbol(event.getSymbol());
        unitsDTO.setInternationalAbbreviation(event.getInternationalAbbreviation());
        return null;
    }

    @RabbitListener(queues = RabbitMQConfig.UNIT_CONVERSION_CREATED_QUEUE)
    public void handleUnitConversionCreated(UnitConversionCreateEvent event) {
        log.info("Received user created event: {}", event);
        UnitConversionDto dto = getUnitConversion(event);
        unitConversionService.create(dto);
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
        return dto;
    }


}
