package com.amundi.gp.kafka.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.streams.StreamsConfig;

public class KafkaConfig {

	public static final String TOPIC_INSTRUMENT = "instrument-topic";
	public static final String TOPIC_QUOTATION = "quotation-topic";
	public static final String TOPIC_INVENTAIRE = "inventaire-topic";
	public static final String TOPIC_RESULT = "result-topic";

	// TODO : a transformer en configuration Spring
	public static Properties getProperties(Object keySerializer, Object valueSerializer) {
		Properties props = KafkaConfig.getProperties();
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

		return props;
	}

	public static Properties getProperties() {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.STATE_DIR_CONFIG, "C:\\Appli\\kafka\\tmp");

		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		return props;
	}
}
