package monitoring.domain;

import java.util.Date;
import java.util.UUID;

public class Service {
    private final UUID reference;
    private final String name;
    private final String url;
    private final Date creationTime;
    private final String status;

    public Service(UUID reference, String name, String url, Date created, String status) {
        this.reference = reference;
        this.name = name;
        this.url = url;
        this.creationTime = created;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getStatus() {
        return status;
    }

    public UUID getReference() {
        return reference;
    }
}
