package monitoring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service  // TODO: Check how we can spin up several of these + based on config.
public class ServicePollingJanitor {
    private static final int POLLING_LIMIT = 10; // TODO: Load this from config

    @Autowired
    private ServicePoller servicePoller;

    @Autowired
    private ServiceInterface serviceInterface;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void startPolling() {
        List<Service> services = serviceInterface.findServicesToPoll(POLLING_LIMIT);

        for (Service s : services) {
            servicePoller.updateStatus(s);
        }
    }
}
