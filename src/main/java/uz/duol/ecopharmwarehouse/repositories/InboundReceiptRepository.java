package uz.duol.ecopharmwarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.duol.ecopharmwarehouse.entity.InboundReceiptEntity;

public interface InboundReceiptRepository extends JpaRepository<InboundReceiptEntity, Long>, JpaSpecificationExecutor<InboundReceiptEntity> {
}
