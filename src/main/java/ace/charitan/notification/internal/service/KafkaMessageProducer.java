package ace.charitan.notification.internal.service;

import ace.charitan.common.dto.donation.GetCharityDonationStatisticsRequestDto;
import ace.charitan.common.dto.donation.GetCharityDonationStatisticsWrapperDto;
import ace.charitan.common.dto.donation.GetDonorDonationStatisticsRequestDto;
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

        GetDonorDonationStatisticsRequestDto dto = new GetDonorDonationStatisticsRequestDto("d2bd087c-3a6a-4179-91c2-b8595ebc92d3");
        ProducerRecord<String, Object> record = new ProducerRecord<>("donor-donation-statistics", dto);
        record.headers().add(REPLY_TOPIC, REPLY_TOPIC.getBytes());
        RequestReplyFuture<String, Object, Object> future = replyingKafkaTemplate.sendAndReceive(record);

        Object response = future.get().value();

        System.out.println("Donor: " + response);

        GetCharityDonationStatisticsWrapperDto wrapper = new GetCharityDonationStatisticsWrapperDto(List.of("123", "abc"));
        GetCharityDonationStatisticsRequestDto dto2 = new GetCharityDonationStatisticsRequestDto(wrapper);
        ProducerRecord<String, Object> record2 = new ProducerRecord<>("charity-donation-statistics", dto2);
        record2.headers().add(REPLY_TOPIC, REPLY_TOPIC.getBytes());
        RequestReplyFuture<String, Object, Object> future2 = replyingKafkaTemplate.sendAndReceive(record2);

        Object response2 = future2.get().value();

        System.out.println("Charity: " + response2);
    }

}