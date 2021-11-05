package monitoring.service;


import monitoring.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service  // TODO: Check how we can spin up several of these, also based on config.
public class PollingJanitor {
    private static final int POLLING_LIMIT = 10; // TODO: Load this from config

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

            Status status;
            if (statusCode == HttpStatus.OK) {
                status = Status.Ok;
                ServiceDTO serviceDTO = new ServiceDTO(s.getReference(), s.getName(), s.getUrl(), s.getCreatedTime(), status.toString());
                template.convertAndSend("/topic/monitoring", serviceDTO);
            } else {
                status = Status.Error;
            }
            serviceDAO.markPollingResult(status.toString(), s.getId());
        }
    }

}
