package net.spnox.kafka;

import net.spnox.storage.MessageBufferStorage;
import org.apache.commons.collections.Buffer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class MessageListener {

	@Autowired
	private MessageBufferStorage bufferRepo;

	@KafkaListener(id = "foo2", topics = {"${kafka.topics.topic1}", "${kafka.topics.topic2}"}, group = "${kafka.consumer.group-id}")
	public void listen(ConsumerRecord<?, ?> record) {
		Buffer buffer = bufferRepo.getTopicBuffer(record.topic());
		buffer.add(record.value());

	}

}