package uz.duol.ecopharmwarehouse.jobs;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.duol.ecopharmwarehouse.config.MinioProperties;
import uz.duol.ecopharmwarehouse.entity.JobsEntity;
import uz.duol.ecopharmwarehouse.repositories.JobsRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.zip.GZIPOutputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseBackupScheduler {
    private final JobsRepository repository;
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.application.name:eco-pharma-wms-service}")
    private String serviceName;

    @Value("${backup.useShell:false}")
    private boolean useShell;

    @Scheduled(cron = "0 0 2 * * *")
    public void dailyBackup() {
        runBackup("daily");
    }

    @Scheduled(cron = "0 0 3 * * 0")
    public void weeklyBackup() {
        runBackup("weekly");
    }

    @Scheduled(cron = "0 0 4 1 * *")
    public void monthlyBackup() {
        runBackup("monthly");
    }

    private void runBackup(String type) {
        LocalDate today = LocalDate.now();
        String filename = String.format("%s-%s-%s.sql.gz", serviceName, type, today);
        String objectPath = String.format("wms-service/%s/%d/%02d/%02d/%s", serviceName, today.getYear(), today.getMonthValue(), today.getDayOfMonth(), filename);
        JobsEntity job = new JobsEntity();
        job.setJobName("Database Backup_" + type);
        job.setJobStatus("Started");
        repository.save(job);
        try {
            String host = extractHost(dbUrl);
            String port = extractPort(dbUrl);
            String dbName = extractDatabaseName(dbUrl);

            ProcessBuilder pb = useShell ? new ProcessBuilder("sh", "-c", String.format("PGPASSWORD=%s pg_dump -h %s -p %s -U %s -d %s | gzip", dbPassword, host, port, dbUser, dbName)) : new ProcessBuilder("pg_dump", "-h", host, "-p", port, "-U", dbUser, "-d", dbName, "--no-password");

            pb.environment().put("PGPASSWORD", dbPassword);
            Process process = pb.start();

            InputStream inputStream;

            if (!useShell) {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                try (GZIPOutputStream gzip = new GZIPOutputStream(byteOut)) {
                    process.getInputStream().transferTo(gzip);
                }
                inputStream = new ByteArrayInputStream(byteOut.toByteArray());
            } else {
                inputStream = process.getInputStream();
            }

            minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(objectPath).stream(inputStream, -1, 10 * 1024 * 1024).contentType("application/gzip").build());

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                job.setJobStatus("Completed");
                repository.save(job);
                log.info("{} backup complete: {}", type, objectPath);
            } else {
                job.setJobStatus("Failed");
                repository.save(job);
                log.error("pg_dump failed with exit code {}", exitCode);
                try (InputStream errorStream = process.getErrorStream()) {
                    errorStream.transferTo(System.err);
                }
            }

        } catch (Exception e) {
            job.setJobStatus("Failed");
            repository.save(job);
            log.error("{} backup failed", type, e);
        }
    }

    private String extractDatabaseName(String jdbcUrl) {
        return jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1);
    }

    private String extractHost(String jdbcUrl) {
        String tmp = jdbcUrl.replace("jdbc:postgresql://", "");
        return tmp.split(":")[0];
    }

    private String extractPort(String jdbcUrl) {
        String tmp = jdbcUrl.replace("jdbc:postgresql://", "");
        return tmp.split(":")[1].split("/")[0];
    }
}
