package monitoring.service;


import monitoring.MonitoringAPI;
import monitoring.dto.ServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service  // TODO: Check how we can spin up several of these + based on config.
public class ServicePollingJanitor {
    private static final int POLLING_LIMIT = 10; // TODO: Load this from config

    @Autowired
    private ServicePoller servicePoller;

    @Autowired
    private ServiceInterface serviceInterface;
    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void startPolling() {
        List<Service> services = serviceInterface.findServicesToPoll(POLLING_LIMIT);

        for (Service s: services) {
            servicePoller.updateStatus(s);
        }
    }
}
