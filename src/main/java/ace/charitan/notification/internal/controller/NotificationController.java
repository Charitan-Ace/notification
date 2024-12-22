package ace.charitan.notification.internal.controller;

import ace.charitan.notification.internal.dto.TestStreamMessageRequestDto;
import ace.charitan.notification.internal.service.InternalNotificationService;
import ace.charitan.notification.internal.service.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("http://localhost:3000")
public class NotificationController {

    @Autowired
    private InternalNotificationService service;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Notification sendMessage(@Payload Notification notification) {
        return notification;
    }

    @PostMapping
    public ResponseEntity<String> testStreamMessage() {
        service.testStreamResponse();
        return ResponseEntity.ok("done");

    }
}
