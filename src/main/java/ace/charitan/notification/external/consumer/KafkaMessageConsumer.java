package ace.charitan.notification.external.consumer;


import ace.charitan.common.dto.TestKafkaMessageDto;
import ace.charitan.common.dto.donation.SendDonationNotificationDto;
import ace.charitan.notification.external.service.ExternalNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.REPLY_TOPIC;

@Service
class KafkaMessageConsumer {
    @Autowired
    private ExternalNotificationService service;

    @KafkaListener(topics = "donation-notification", groupId = "notification")
    public void listen(SendDonationNotificationDto dto) {
        service.testNotification();
    }

    @KafkaListener(topics = "john-request", groupId = "notification")
    @SendTo(REPLY_TOPIC)
    public TestKafkaMessageDto testRequestResponseTemplate(TestKafkaMessageDto dto) {
        System.out.println("Received John Request: " + dto.toString());

        return new TestKafkaMessageDto("John Response", "Notification response for John Request");
    }
}
