/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amundi.gp.kafka.consumers;

import java.util.concurrent.CountDownLatch;

import com.amundi.gp.kafka.processor.InventaireSpliter;
import com.amundi.gp.kafka.serialization.deserialize.InstrumentDeserializer;
import com.amundi.gp.kafka.serialization.deserialize.InventaireDeserializer;
import com.amundi.gp.kafka.serialization.serialize.InstrumentSerializer;
import com.amundi.gp.kafka.serialization.serialize.InventaireSerializer;
import com.amundi.gp.kafka.serialization.serialize.PositionSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;

import com.amundi.gp.kafka.config.KafkaConfig;
import com.amundi.gp.kafka.dto.InstrumentDto;
import com.amundi.gp.kafka.dto.InventaireDto;
import org.apache.kafka.streams.Topology;

public class TestTopology {

	public static void main(String[] args) throws Exception {
        Serde<InventaireDto> inventaireSerde = Serdes.serdeFrom(new InventaireSerializer(),
                new InventaireDeserializer());
        Serde<InstrumentDto> instrumentSerde = Serdes.serdeFrom(new InstrumentSerializer(),
                new InstrumentDeserializer());



		final StreamsBuilder builder = new StreamsBuilder();

        Topology topology = builder.build();
        topology.addSource("inventaireSource", Serdes.String().deserializer(), new InventaireDeserializer(), KafkaConfig.TOPIC_INVENTAIRE);
        topology.addSource("instrumentSource", Serdes.String().deserializer(), new InstrumentDeserializer(), KafkaConfig.TOPIC_INSTRUMENT);

        topology.addProcessor("inventaireProcess", () -> new InventaireSpliter(), "inventaireSource");
        topology.addSink("positionSink", KafkaConfig.TOPIC_RESULT, Serdes.String().serializer(), new PositionSerializer(), "inventaireProcess");

		final KafkaStreams streams = new KafkaStreams(topology, KafkaConfig.getProperties());

        final CountDownLatch latch = new CountDownLatch(1);

		// attach shutdown handler to catch control-c
		Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
			@Override
			public void run() {
				streams.close();
				latch.countDown();
			}
		});

		try {
			streams.start();
			latch.await();
		} catch (Throwable e) {
			System.exit(1);
		}
		System.exit(0);
	}
}
