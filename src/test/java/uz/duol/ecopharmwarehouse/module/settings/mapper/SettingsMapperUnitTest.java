package uz.duol.ecopharmwarehouse.module.settings.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uz.duol.ecopharmwarehouse.common.BaseUnitTest;
import uz.duol.ecopharmwarehouse.entity.SettingsEntity;
import uz.duol.ecopharmwarehouse.module.settings.dto.SettingsDTO;
import uz.duol.ecopharmwarehouse.module.settings.service.SettingsService;
import uz.duol.ecopharmwarehouse.repositories.SettingsRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsMapperUnitTest extends BaseUnitTest {
    private SettingMapper mapper = Mappers.getMapper(SettingMapper.class);

    private SettingsEntity entity;
    private SettingsDTO dto;

    @BeforeEach
    public void setUp() {
        entity = new SettingsEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setValue("value");

        dto = new SettingsDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setValue("value");
    }

    @Test
    void testToEntity() {
        SettingsEntity result = mapper.toEntity(dto);
        assertEquals(entity.toString(), result.toString());
    }

    @Test
    void testToDto() {
        SettingsDTO result = mapper.toDto(entity);
        assertEquals(dto.toString(), result.toString());
    }
}
