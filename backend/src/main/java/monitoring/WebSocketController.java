package monitoring;

import monitoring.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/sendMonitoring")
    public void receiveMonitoring(@Payload ServiceDTO service) {
        // receive message from client
    }

    @SendTo("/topic/monitoring")
    public ServiceDTO broadcastMonitoring(@Payload ServiceDTO service) {
        return service;
    }
}
