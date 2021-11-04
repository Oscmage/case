package monitoring.repository;


import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;


@MappedSuperclass
public class EntityWithUUID {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
}
