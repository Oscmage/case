package monitoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MonitoringApplication.class)
class MonitoringApplicationTests {

    @Autowired
    private MonitoringAPI monitoringAPI;

    @Test
    void contextLoads() throws Exception {
        assertThat(monitoringAPI).isNotNull();
    }
}
