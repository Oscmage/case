package monitoring;

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

import java.util.Date;
import java.util.UUID;

@RestController
public class WebSocketTextController {

    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody CreateMonitoringDTO createMonitoringDTO) {
        template.convertAndSend("/topic/message", createMonitoringDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<Service> createMonitoring(@RequestBody CreateMonitoringDTOV2 createMonitoringDTO) {
        //template.convertAndSend("/topic/message", createMonitoringDTO);
        // TODO Make this a db operation
        Service s = new Service(
                UUID.randomUUID(),
                createMonitoringDTO.getName(),
                createMonitoringDTO.getUrl(),
                new Date(),
                Status.Pending
        );
        return new ResponseEntity<>(s,HttpStatus.CREATED);
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
