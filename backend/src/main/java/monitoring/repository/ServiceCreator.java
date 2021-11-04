package monitoring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class ServiceCreator {

    @Autowired
    private ServiceDAO serviceDAO;

    @Transactional
    public void createService(String name, String url) {
        monitoring.repository.Service s = new monitoring.repository.Service(
            name, url, new Date(), UUID.randomUUID()
        );
        serviceDAO.save(s);
    }
}
