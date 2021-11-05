package monitoring.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServiceDAO extends CrudRepository<Service, UUID> {
    Optional<Service> findByUrl(String url);
}
