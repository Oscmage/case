package monitoring.service;

import monitoring.dto.ServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
class ServicePoller {
    Logger logger = LoggerFactory.getLogger(ServicePoller.class);

    @Autowired
    private ServiceInterface serviceInterface;
    @Autowired
    private SimpMessagingTemplate template;

    @Async
    protected void updateStatus(Service s) {
        try {
            ResponseEntity<String> response = callServiceUrl(s);
            if (isOk(response)) {
                markOk(s);
            } else {
                markError(s);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            markError(s);
        }
    }

    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(10_000);

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(10_000);
        return clientHttpRequestFactory;
    }

    private ResponseEntity<String> callServiceUrl(Service s) {
        // TODO: Check if there are simpler ways to do this.
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);
        return restTemplate.exchange(s.getUrl(), HttpMethod.GET, requestEntity, String.class);
    }

    private boolean isOk(ResponseEntity<String> response) {
        return response.getStatusCode() == HttpStatus.OK;
    }

    private void markOk(Service s) {
        String status = ServiceStatus.Ok.toString();
        ServiceDTO serviceDTO = new ServiceDTO(s.getReference(), s.getName(), s.getUrl(), s.getCreatedTime(), status);
        logger.info("Trying to send service over socket");
        template.convertAndSend("/topic/monitoring", serviceDTO);
        serviceInterface.markPollingResult(status, s.getId());
    }

    private void markError(Service s) {
        String status = ServiceStatus.Error.toString();
        serviceInterface.markPollingResult(status, s.getId());
    }
}
