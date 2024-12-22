package ace.charitan.notification.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements InternalNotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void testStreamResponse() {
        String response = "something is being streamed back";
        for (char character: response.toCharArray()) {
            messagingTemplate.convertAndSend("/topic/stream", String.valueOf(character));

        }
    }
}
