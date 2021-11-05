package monitoring;

import monitoring.domain.CreateMonitoringDTO;
import monitoring.domain.CreateMonitoringDTOV2;
import monitoring.domain.Service;
import monitoring.domain.ServiceCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketTextController {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    ServiceCreator serviceCreator;

    @CrossOrigin(origins = "http://localhost:3000")  // TODO: Remove when shipping to prod
    @PostMapping("/create")
    public ResponseEntity<Service> sendMonitoring(@RequestBody CreateMonitoringDTOV2 createMonitoringDTO) {
        try {
            Service s = this.serviceCreator.createService(createMonitoringDTO.getName(), createMonitoringDTO.getUrl());
            return new ResponseEntity<>(s, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }  catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @MessageMapping("/sendMonitoring")
    public void receiveMonitoring(@Payload CreateMonitoringDTO textMessageDTO) {
        // receive message from client
    }

    @SendTo("/topic/monitoring")
    public CreateMonitoringDTOV2 broadcastMonitoring(@Payload CreateMonitoringDTOV2 createMonitoringDTO) {
        System.out.println("Sending monitoring update");
        return createMonitoringDTO;
    }
}
