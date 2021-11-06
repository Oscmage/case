package monitoring;

import monitoring.dto.CreateMonitoringDTO;
import monitoring.dto.ServiceDTO;
import monitoring.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MonitoringAPI {
    @Autowired
    ServiceInterface serviceInterface;

    @CrossOrigin(origins = "http://localhost:3000")  // TODO: Remove when shipping to prod
    @PostMapping("/create")  // TODO: Add versioning
    public ResponseEntity<ServiceDTO> createMonitoring(@RequestBody CreateMonitoringDTO createMonitoringDTO) {
        try {
            ServiceDTO s = serviceInterface.createService(createMonitoringDTO.getName(), createMonitoringDTO.getUrl());
            return new ResponseEntity<>(s, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }  catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")  // TODO: Remove when shipping to prod
    @GetMapping("/list")  // TODO: Add versioning
    public ResponseEntity<List<ServiceDTO>> listMonitoring() {
        try {
            List<ServiceDTO> serviceDTOList = serviceInterface.listServices();
            return new ResponseEntity<>(serviceDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
