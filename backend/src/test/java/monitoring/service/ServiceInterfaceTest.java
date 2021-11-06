package monitoring.service;

import monitoring.dto.ServiceDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceInterfaceTest {

    private static String url;
    private static String name;
    private static Service service;
    private static final ServiceDAO serviceDAOMock = mock(ServiceDAO.class);
    private static ServiceInterface serviceInterface;

    @BeforeAll
    static void beforeAll() {
        url = "Url";
        name = "Name";
        service = new Service(name, url, ServiceStatus.Pending.toString());
        serviceInterface = new ServiceInterface(serviceDAOMock);
    }

    @Test
    void createServiceSuccess() {
        // Given
        Service returnValue = new Service(UUID.randomUUID(), name, url, new Date(), ServiceStatus.Pending.toString(), UUID.randomUUID(), false, new Date());
        when(serviceDAOMock.save(service)).thenReturn(returnValue);

        // When
        ServiceDTO result = serviceInterface.createService(name, url);
        assertNotNull(result);
    }

    @Test
    void createServiceDuplicateThrows() {
        // Given
        when(serviceDAOMock.findByUrl(url)).thenReturn(java.util.Optional.of(service));

        // When / Then
        assertThrows(IllegalStateException.class, () -> serviceInterface.createService(name, url));
    }

    @Test
    void listServicesSuccess() {
        // Given
        List<Service> l = new ArrayList<>();
        l.add(service);
        when(serviceDAOMock.findServices(anyInt())).thenReturn(l);

        // When
        List<ServiceDTO> result = serviceInterface.listServices();

        // Then
        assertEquals(1, result.size());
        assertEquals(url , result.get(0).getUrl());
    }

    @Test
    void listServicesEmptyResult() {
        // Given
        List<Service> l = new ArrayList<>();
        when(serviceDAOMock.findServices(anyInt())).thenReturn(l);

        // When
        List<ServiceDTO> result = serviceInterface.listServices();

        // Then
        assertEquals(0, result.size());
    }
}