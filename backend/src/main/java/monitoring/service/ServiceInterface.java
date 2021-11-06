package monitoring.service;

import monitoring.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service
public class ServiceInterface {
    private int FIND_SERVICE_LIMIT = 100;

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
    protected List<Service> findServicesToPoll(int pollingLimit) {
        return serviceDAO.findServicesToPoll(pollingLimit);
    }

    @Transactional
    public List<ServiceDTO> listServices() {
        List<Service> serviceList = serviceDAO.findServices(FIND_SERVICE_LIMIT);

        return serviceList.stream().map(service -> new ServiceDTO(service.getReference(), service.getName(), service.getUrl(), service.getCreatedTime(), service.getStatus())).collect(Collectors.toList());
    }
}
