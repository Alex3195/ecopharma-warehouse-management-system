package uz.duol.ecopharmwarehouse.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import uz.duol.ecopharmwarehouse.config.SpringContext;
import uz.duol.ecopharmwarehouse.config.security.CustomUserDetails;
import uz.duol.ecopharmwarehouse.entity.AuditTrailEntity;
import uz.duol.ecopharmwarehouse.repositories.AuditTrailRepository;
import uz.duol.ecopharmwarehouse.utils.WMSUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Component
public class AuditTrailListener {

    @PrePersist
    public void prePersist(Object entity) {
        saveAudit(entity, "INSERT", null);
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        saveAudit(entity, "UPDATE", toJson(entity));
    }

    @PreRemove
    public void preRemove(Object entity) {
        saveAudit(entity, "DELETE", toJson(entity));
    }

    private void saveAudit(Object entity, String action, String oldValue) {
        AuditTrailEntity audit = new AuditTrailEntity();
        audit.setTableName(entity.getClass().getSimpleName());
        audit.setActionType(action);
        audit.setOldValue(oldValue);
        audit.setActionTime(LocalDateTime.now());

        try {
            Field idField = entity.getClass().getDeclaredField("id");
            ReflectionUtils.makeAccessible(idField);
            Object idValue = idField.get(entity);
            audit.setRecordId(idValue != null ? Long.valueOf(idValue.toString()) : null);
        } catch (Exception ignored) {}

        audit.setNewValue(toJson(entity));

        CustomUserDetails user = WMSUtils.getCurrentUserDetails();
        if (user != null) {
            audit.setPerformedById(user.getUserId());
        }

        getAuditTrailRepository().save(audit);
    }

    private AuditTrailRepository getAuditTrailRepository() {
        return SpringContext.getBean(AuditTrailRepository.class);
    }

    private ObjectMapper getObjectMapper() {
        return SpringContext.getBean(ObjectMapper.class);
    }

    private String toJson(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "JSON_SERIALIZATION_ERROR: " + e.getMessage();
        }
    }
}
