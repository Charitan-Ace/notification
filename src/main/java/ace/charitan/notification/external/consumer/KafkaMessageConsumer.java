package ace.charitan.notification.external.consumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class KafkaMessageConsumer {

    @KafkaListener(topics = "donation-test", groupId = "donation")
    public void listen(String message) {
        System.out.println("Donation microservice received message: " + message);
    }

}
