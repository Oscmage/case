package monitoring.janitor;


import monitoring.domain.CreateMonitoringDTO;
import monitoring.repository.Service;
import monitoring.repository.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service
public class PollingJanitor{
    private static final int POLLING_LIMIT = 10;
    @Autowired
    private ServiceDAO serviceDAO;
    @Autowired
    SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void startPolling() {
        System.out.println("STARTED POLLING");
        List<Service> services = serviceDAO.findServicesToPoll(POLLING_LIMIT);

        System.out.println("Found services : " + services.size());
        for (Service s: services) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            HttpEntity requestEntity = new HttpEntity<>(requestHeaders);
            ResponseEntity<String> response = restTemplate.exchange(s.getUrl(), HttpMethod.GET, requestEntity, String.class);
            HttpStatus statusCode = response.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                serviceDAO.markPollingResult("Ok", s.getId());
                monitoring.domain.Service serviceDTO = new monitoring.domain.Service(s.getReference(), s.getName(), s.getUrl(), s.getCreatedTime(), "Ok");
                template.convertAndSend("/topic/monitoring", serviceDTO);
            } else {
                serviceDAO.markPollingResult("Error", s.getId());
            }
        }
    }

}
