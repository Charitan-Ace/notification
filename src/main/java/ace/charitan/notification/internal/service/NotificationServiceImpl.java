package ace.charitan.notification.internal.service;

import ace.charitan.notification.external.service.ExternalNotificationService;
import ace.charitan.notification.internal.auth.AuthUtils;
import ace.charitan.notification.internal.dto.SaveNotificationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements InternalNotificationService, ExternalNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository repository;

    @Override
    public void testNotification() {
        Notification notification = new Notification(null, null, "Are you seeing this?", NotificationType.DONATION, 1L, 1L);
        UserDetails userDetails = AuthUtils.getUserDetails();
        String userId = userDetails != null ? userDetails.getUsername() : "abc";
        messagingTemplate.convertAndSendToUser(userId, "/topic/stream", notification);
        System.out.println("Done :). Sent to " + userId);
        repository.save(notification);
    }

    public Notification createNotification(SaveNotificationRequestDto dto) {
        Notification notification = new Notification(null, null, dto.getMessage(), dto.getNotificationType(), dto.getProjectId(), dto.getDonationId());
        messagingTemplate.convertAndSend(notification);
        return repository.save(notification);
    }
}
