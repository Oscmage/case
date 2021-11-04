package monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringApplication.class, args);
    }

}
