package monitoring.repository;


import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;


@MappedSuperclass
public class EntityWithUUID {
    @Id
    @Type(type = "pg-uuid")
    private UUID id;
}
