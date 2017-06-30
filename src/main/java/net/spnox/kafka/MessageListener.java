package net.spnox.kafka;

import net.spnox.storage.MessageBufferStorage;
import net.spnox.storage.MessagePayload;
import org.apache.commons.collections.Buffer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class MessageListener {

	@Autowired
	private MessageBufferStorage bufferRepo;

	@KafkaListener(id = "foo2", topics = "#{configProps.topics}", group = "${kafka.consumer.group-id}")
	public void listen(ConsumerRecord<String, Object> record) {
		Buffer buffer = bufferRepo.getTopicBuffer(record.topic());
		buffer.add(new MessagePayload(record.key(), record.topic(), record.offset(), record.partition(), record.value()));
	}

}