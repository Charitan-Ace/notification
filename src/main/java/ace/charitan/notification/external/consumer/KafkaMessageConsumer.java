package ace.charitan.notification.external.consumer;


import ace.charitan.common.dto.TestKafkaMessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
class KafkaMessageConsumer {

    @KafkaListener(topics = "donation-notification", groupId = "donation")
    public void listen(TestKafkaMessageDto dto) {
        System.out.println("Donation microservice received message");
        System.out.println(dto);
    }

}
