package monitoring.service;

import monitoring.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ServiceInterface {

    @Autowired
    private ServiceDAO serviceDAO;

    @Transactional
    public ServiceDTO createService(String name, String url) throws IllegalStateException {
        Optional<ServiceTable> existingService = serviceDAO.findByUrl(url);
        if (existingService.isPresent()) {
            throw new IllegalStateException("Monitoring for this URL already exists");
        }

        ServiceTable result = serviceDAO.save(new ServiceTable(
                name, url, ServiceStatus.Pending.toString()
        ));
        return new ServiceDTO(
            result.getReference(), result.getName(), result.getUrl(), result.getCreatedTime(), result.getStatus()
        );
    }
}
