package ace.charitan.notification.internal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Objects;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        logger.info(String.valueOf(event.getMessage().getHeaders()));
        GenericMessage message = (GenericMessage) event.getMessage().getHeaders().get("simpConnectMessage");
        if (message != null) {
            Map<String, String> sessionAttributes = (Map<String, String>) message.getHeaders().get("simpSessionAttributes");
            if (sessionAttributes != null) {
                Object userId = sessionAttributes.get("id");
                if (userId != null) {
                    logger.info("WebSocket connection established. User ID: {}", userId);
                } else {
                    logger.warn("WebSocket connection established with no user ID in session attributes. Connection might be unauthenticated.");
                }
            }
        } else {
            logger.error("Could not extract message from Websocket event");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : {}", username);

            Notification notification = new Notification();

            messagingTemplate.convertAndSend("/topic/public", notification);
        }
    }
}
