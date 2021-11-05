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

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody CreateMonitoringDTO createMonitoringDTO) {
        template.convertAndSend("/topic/message", createMonitoringDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")  // TODO: Remove when shipping to prod
    @PostMapping("/create")
    public ResponseEntity<Service> createMonitoring(@RequestBody CreateMonitoringDTOV2 createMonitoringDTO) {
        //template.convertAndSend("/topic/message", createMonitoringDTO);
        try {
            Service s = this.serviceCreator.createService(createMonitoringDTO.getName(), createMonitoringDTO.getUrl());
            return new ResponseEntity<>(s, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }  catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload CreateMonitoringDTO textMessageDTO) {
        // receive message from client
    }


    @SendTo("/topic/message")
    public CreateMonitoringDTO broadcastMessage(@Payload CreateMonitoringDTO createMonitoringDTO) {
        return createMonitoringDTO;
    }
}
