package uz.duol.ecopharmwarehouse;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@EnableConfigurationProperties()
@EntityScan(basePackages = "uz.duol.ecopharmwarehouse")
@EnableJpaRepositories(basePackages = "uz.duol.ecopharmwarehouse")
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
public class EcoPharmaWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoPharmaWarehouseApplication.class, args);
    }

}
