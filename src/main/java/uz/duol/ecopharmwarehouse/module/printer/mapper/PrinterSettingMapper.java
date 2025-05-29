package uz.duol.ecopharmwarehouse.module.printer.mapper;

import org.mapstruct.*;
import uz.duol.ecopharmwarehouse.entity.PrinterSettingsEntity;
import uz.duol.ecopharmwarehouse.module.printer.dto.PrinterSettingsDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PrinterSettingMapper {

    @Mapping(target = "departmentName", ignore = true)
    @Mapping(target = "isDefaultPrinter", source = "isDefault")
    PrinterSettingsDto toDto(PrinterSettingsEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "printMode", ignore = true)
    @Mapping(target = "isDefault", source = "isDefaultPrinter")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    PrinterSettingsEntity toEntity(PrinterSettingsDto dto);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "isDefault", source = "isDefaultPrinter")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.ERROR)
    void updateEntityFromDto(PrinterSettingsDto dto, @MappingTarget PrinterSettingsEntity entity);
}
