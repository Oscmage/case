package monitoring.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
class Service {
    @Id
    @Type(type = "pg-uuid")
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String url;

    @Column()
    private Date createdTime;

    @Column(nullable = false)
    private String status;

    @Type(type = "pg-uuid")
    @Column()
    private UUID reference;

    @Column()
    private boolean polling;

    @Column()
    private Date updated;

    public Service(String name, String url, String status) {
        this.name = name;
        this.url = url;
        this.status = status;
    }
}
