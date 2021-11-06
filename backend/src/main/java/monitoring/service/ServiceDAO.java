package monitoring.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceDAO extends CrudRepository<Service, UUID> {
    Optional<Service> findByUrl(String url);

    @Modifying
    @Query(value = "UPDATE service SET polling = TRUE WHERE id IN (SELECT id FROM Service s WHERE " +
            "(s.updated < NOW() - INTERVAL '10 SECONDS' AND NOT polling) OR" +
            "(s.updated < NOW() - INTERVAL '180 SECONDS' AND polling) LIMIT ?1 FOR UPDATE SKIP LOCKED) RETURNING *;"
            , nativeQuery = true)
    List<Service> findServicesToPoll(int pollingLimit);

    @Query(value = "SELECT * FROM service LIMIT ?1"
            , nativeQuery = true)
    List<Service> findServices(int limit);

    // TODO: Potentially move callers of this method to a domain service class (doesn't exist yet) to encapsulate this
    //  behaviour.
    @Modifying
    @Query(value = "UPDATE service SET polling = FALSE, status = ?1 WHERE id  = ?2", nativeQuery = true)
    void markPollingResult(String status, UUID id);
}
