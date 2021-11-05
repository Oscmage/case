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
            // TODO: Make this run in it's own thread.
            updateStatus(s);
        }
    }

    private void updateStatus(Service s) {
        ResponseEntity<String> response = callServiceUrl(s);

        if (isOk(response)) {
           markOk(s);
        } else {
            markError(s);
        }
    }
    
    private ResponseEntity<String> callServiceUrl(Service s) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);
        return restTemplate.exchange(s.getUrl(), HttpMethod.GET, requestEntity, String.class);
    }

    private boolean isOk(ResponseEntity<String> response) {
        return response.getStatusCode() == HttpStatus.OK;
    }

    private void markOk(Service s) {
        String status = Status.Ok.toString();
        ServiceDTO serviceDTO = new ServiceDTO(s.getReference(), s.getName(), s.getUrl(), s.getCreatedTime(), status);
        template.convertAndSend("/topic/monitoring", serviceDTO);
        serviceDAO.markPollingResult(status, s.getId());
    }

    private void markError(Service s) {
        String status = Status.Error.toString();
        serviceDAO.markPollingResult(status, s.getId());
    }
}
