package monitoring.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ServiceDAO extends CrudRepository<Service, UUID> {}
