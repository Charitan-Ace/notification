package ace.charitan.notification.internal.service;

import ace.charitan.common.dto.donation.*;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.kafka.support.KafkaHeaders.REPLY_TOPIC;

@Service
class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    public void sendMessage(String topic, String message) {
        System.out.println("Notification microservice sent message: " + message);
        kafkaTemplate.send(topic, message);
    }

    public void test() throws ExecutionException, InterruptedException {

        ProducerRecord<String, Object> record = new ProducerRecord<>("donors-of-the-month", null);
        RequestReplyFuture<String, Object, Object> future = replyingKafkaTemplate.sendAndReceive(record);

        Object response = future.get().value();

        System.out.println("Donor: " + response);

        GetCharityDonorsOfTheMonthWrapperDto wrapper = new GetCharityDonorsOfTheMonthWrapperDto(List.of("123", "abc"));
        GetCharityDonorsOfTheMonthRequestDto dto2 = new GetCharityDonorsOfTheMonthRequestDto(wrapper);
        ProducerRecord<String, Object> record2 = new ProducerRecord<>("charity-donors-of-the-month", dto2);
        RequestReplyFuture<String, Object, Object> future2 = replyingKafkaTemplate.sendAndReceive(record2);

        Object response2 = future2.get().value();

        System.out.println("Charity: " + response2);
    }

}