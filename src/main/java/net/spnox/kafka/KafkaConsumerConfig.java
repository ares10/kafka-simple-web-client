package net.spnox.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	@Value("${kafka.bootstrap.servers}")
	private String bootstrapServers;
	@Value("${kafka.consumer.auto-commit.enabled}")
	private boolean autoCommitEnabled;
	@Value("${kafka.consumer.auto-commit.interval-ms}")
	private Long autoCommitIntervalMs;
	@Value("${kafka.consumer.session-timeout-ms}")
	private String sessionTimeoutMs;
	@Value("${kafka.consumer.group-id}")
	private String groupId;
	@Value("${kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;

	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {

		System.out.println("Servers:"+bootstrapServers);
		System.out.println("autoCommitEnabled:"+this.autoCommitEnabled);
		System.out.println("autoCommitIntervalMs:"+autoCommitIntervalMs);
		System.out.println("sessionTimeoutMs:"+sessionTimeoutMs);
		System.out.println("groupId:"+groupId);
		System.out.println("autoOffsetReset:"+autoOffsetReset);

		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, this.autoCommitEnabled);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, this.autoCommitIntervalMs);
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, this.sessionTimeoutMs);
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.autoOffsetReset);
		return propsMap;
	}

	@Bean
	public MessageListener listener() {
		return new MessageListener();
	}
}
