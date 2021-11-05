package monitoring.domain;

import monitoring.repository.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceCreator {

    @Autowired
    private ServiceDAO serviceDAO;

    @Transactional
    public monitoring.domain.Service createService(String name, String url) throws IllegalStateException {
        Optional<monitoring.repository.Service> existingService = serviceDAO.findByUrl(url);
        if (existingService.isPresent()) {
            throw new IllegalStateException("Monitoring for this URL already exists");
        }

        monitoring.repository.Service s = new monitoring.repository.Service(
            name, url, new Date(), "Pending", UUID.randomUUID()
        );
        monitoring.repository.Service result = serviceDAO.save(s);
        return new monitoring.domain.Service(
                result.getReference(), result.getName(), result.getUrl(), result.getCreatedTime(), result.getStatus()
        );
    }
}
