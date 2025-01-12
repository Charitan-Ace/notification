package ace.charitan.notification.internal.service;

import ace.charitan.notification.external.service.ExternalNotificationService;
import ace.charitan.notification.internal.auth.AuthUtils;
import ace.charitan.notification.internal.dto.InternalNotificationDto;
import ace.charitan.notification.internal.dto.SaveNotificationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class NotificationServiceImpl implements InternalNotificationService, ExternalNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private KafkaMessageProducer producer;

    @Override
    public void testNotification() {
        UserDetails userDetails = AuthUtils.getUserDetails();
        String userId = userDetails != null ? userDetails.getUsername() : "abc";
        Notification notification = new Notification(null, null, "Are you seeing this?", "userId", NotificationType.DONATION, 1L, 1L);
        messagingTemplate.convertAndSendToUser(userId, "/topic/stream", notification);
        System.out.println("Done :). Sent to " + userId);
        repository.save(notification);
    }

    public Notification createNotification(SaveNotificationRequestDto dto) {
        Notification notification = new Notification(null, null, dto.getMessage(), dto.getUserId(), dto.getNotificationType(), dto.getProjectId(), dto.getDonationId());
        messagingTemplate.convertAndSendToUser(dto.getUserId(), "/topic/notification", notification);
        return repository.save(notification);
    }

    @Override
    public void test() throws ExecutionException, InterruptedException {
        producer.test();
    }
    
    @Override
    public PagedModel<InternalNotificationDto> getMyNotifications(Pageable pageable) {
        UserDetails userDetails = AuthUtils.getUserDetails();
        String userId = userDetails != null ? userDetails.getUsername() : "abc";
        return new PagedModel<>(repository.findAllByUserId(userId, pageable).map(notification -> notification)) ;

    }
}
