package monitoring.service;

import monitoring.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceInterface {

    @Autowired
    private ServiceDAO serviceDAO;

    @Transactional
    public ServiceDTO createService(String name, String url) throws IllegalStateException {
        Optional<Service> existingService = serviceDAO.findByUrl(url);
        if (existingService.isPresent()) {
            throw new IllegalStateException("Monitoring for this URL already exists");
        }

        Service result = serviceDAO.save(new Service(
                name, url, ServiceStatus.Pending.toString()
        ));
        return new ServiceDTO(
            result.getReference(), result.getName(), result.getUrl(), result.getCreatedTime(), result.getStatus()
        );
    }

    @Transactional
    public void markPollingResult(String status, UUID id) {
        serviceDAO.markPollingResult(status, id);
    }

    @Transactional
    public List<Service> findServicesToPoll(int pollingLimit) {
        return serviceDAO.findServicesToPoll(pollingLimit);
    }
}
