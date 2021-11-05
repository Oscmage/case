package monitoring;

import monitoring.domain.Service;
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
    public void receiveMonitoring(@Payload Service service) {
        // receive message from client
    }

    @SendTo("/topic/monitoring")
    public Service broadcastMonitoring(@Payload Service service) {
        return service;
    }
}
