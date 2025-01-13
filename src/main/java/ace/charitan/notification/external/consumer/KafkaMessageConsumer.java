package ace.charitan.notification.external.consumer;


import ace.charitan.common.dto.donation.SendDonationNotificationDto;
import ace.charitan.common.dto.notification.payment.HaltedProjectDonorNotificationRequestDto;
import ace.charitan.common.dto.notification.subscription.NotificationNewProjectSubscription.NotificationNewProjectSubscriptionRequestDto;
import ace.charitan.notification.external.service.ExternalNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
class KafkaMessageConsumer {
    @Autowired
    private ExternalNotificationService service;

    @KafkaListener(topics = "donation-notification")
    public void listen(SendDonationNotificationDto dto) {
        service.testNotification();
    }

    @KafkaListener(topics = "subscription-notification-new-project")
    public void handleSubscriptionNotificationNewProject(NotificationNewProjectSubscriptionRequestDto dto) {
        for (String donorId : dto.getDonorIdList()) {
            service.sendNewProjectNotification(donorId, dto.getProject());
        }
    }

    @KafkaListener(topics = "notification.project.halt.donor")
    public void handleCancelDonationSubscriptionNotification(HaltedProjectDonorNotificationRequestDto dto) {
        for (String donorId : dto.getDonorIds()) {
            service.sendCancelledProjectNotification(donorId, dto.getProject());
        }

    }
}

