package monitoring.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service extends EntityWithUUID {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private Date createdTime;

    @Column(nullable = false)
    private String status;

    @Type(type = "pg-uuid")
    @Column(nullable = false)
    private UUID reference;
}
