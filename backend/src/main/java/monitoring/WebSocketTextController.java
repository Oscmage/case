package monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketTextController {

    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody CreateMonitoringDTO createMonitoringDTO) {
        template.convertAndSend("/topic/message", createMonitoringDTO);
        return new ResponseEntity<>(HttpStatus.OK);
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
