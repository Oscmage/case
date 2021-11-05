package monitoring.repository;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@MappedSuperclass
public class EntityWithUUID {
    @Column(nullable = false)
    private UUID id;
}
