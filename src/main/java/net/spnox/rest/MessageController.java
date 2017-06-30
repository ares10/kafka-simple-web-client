package net.spnox.rest;

import net.spnox.storage.MessageBufferStorage;
import net.spnox.storage.MessagePayload;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class MessageController {

    @Autowired
    private MessageBufferStorage bufferRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/topic/{topic}/messages", method = RequestMethod.GET)
    public Object getMessages(@PathVariable(value="topic") String topic) {
        Object[] records = this.bufferRepo.getTopicBuffer(topic).toArray();
        ArrayUtils.reverse(records);
        return records;
    }

    @RequestMapping(value = "/topic/{topic}/messages", method = RequestMethod.POST)
    public ResponseEntity<MessagePayload> sendMessage(@PathVariable(value="topic") String topic, @RequestBody String payload) throws InterruptedException, ExecutionException{
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, payload);
        while (!future.isDone()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
        }
        SendResult<String, String> result = future.get();
        return ResponseEntity.ok(
                new MessagePayload(result.getProducerRecord().key(), result.getRecordMetadata().topic(), result.getRecordMetadata().offset(), result.getRecordMetadata().partition(), result.getProducerRecord().value()));
    }
}
