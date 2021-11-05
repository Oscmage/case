package monitoring.service;

import monitoring.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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

        ServiceTable s = new ServiceTable(
            UUID.randomUUID(), name, url, new Date(), ServiceStatus.Pending.toString(), UUID.randomUUID(), false, new Date()
        );
        ServiceTable result = serviceDAO.save(s);
        return new ServiceDTO(
            result.getReference(), result.getName(), result.getUrl(), result.getCreatedTime(), result.getStatus()
        );
    }
}
