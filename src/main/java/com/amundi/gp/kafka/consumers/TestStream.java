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

import com.amundi.gp.kafka.dto.PositionDto;
import com.amundi.gp.kafka.serialization.deserialize.PositionDeserializer;
import com.amundi.gp.kafka.serialization.deserialize.PositionPtfDeserializer;
import com.amundi.gp.kafka.serialization.serialize.PositionPtfSerializer;
import com.amundi.gp.kafka.serialization.serialize.PositionSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import com.amundi.gp.kafka.config.KafkaConfig;
import com.amundi.gp.kafka.dto.InstrumentDto;
import com.amundi.gp.kafka.dto.InventaireDto;
import com.amundi.gp.kafka.dto.PositionPtfDto;
import com.amundi.gp.kafka.dto.PositionPtfValDto;
import com.amundi.gp.kafka.serialization.deserialize.InstrumentDeserializer;
import com.amundi.gp.kafka.serialization.deserialize.InventaireDeserializer;
import com.amundi.gp.kafka.serialization.deserialize.PositionValDeserializer;
import com.amundi.gp.kafka.serialization.serialize.InstrumentSerializer;
import com.amundi.gp.kafka.serialization.serialize.InventaireSerializer;
import com.amundi.gp.kafka.serialization.serialize.PositionValSerializer;

public class TestStream {

	public static void main(String[] args) throws Exception {
		Serde<InventaireDto> inventaireSerde = Serdes.serdeFrom(new InventaireSerializer(),
				new InventaireDeserializer());
		Serde<PositionPtfValDto> positionValSerde = Serdes.serdeFrom(new PositionValSerializer(),
				new PositionValDeserializer());
		Serde<PositionPtfDto> positionPtfSerde = Serdes.serdeFrom(new PositionPtfSerializer(), new PositionPtfDeserializer());
		Serde<PositionDto> positionSerde = Serdes.serdeFrom(new PositionSerializer(), new PositionDeserializer());
		Serde<InstrumentDto> instrumentSerde = Serdes.serdeFrom(new InstrumentSerializer(),
				new InstrumentDeserializer());

		final StreamsBuilder builder = new StreamsBuilder();

		// Input Streams
		KStream<Long, InventaireDto> inventaireStream = builder.stream(KafkaConfig.TOPIC_INVENTAIRE,
				Consumed.with(Serdes.Long(), inventaireSerde));

		KTable<String, InstrumentDto> instrumentKTable = builder.table(KafkaConfig.TOPIC_INSTRUMENT,
				Consumed.with(Serdes.String(), instrumentSerde));

		// Remise a plat des positions
		inventaireStream
				.map((ptfId, inventaire) -> new KeyValue<>(ptfId, inventaire.getPositions()))
				.flatMapValues(value -> value)
				.map((ptfId, position) -> new KeyValue<>(position.getIsin(), new PositionPtfDto(position.getIsin(),
						position.getQty(), position.getAmt(), position.getDate(), ptfId)))
				.join(instrumentKTable, (position, instrument) -> new PositionPtfValDto(instrument, position))
				.to(KafkaConfig.TOPIC_RESULT, Produced.with(Serdes.String(), positionValSerde));
				/*.map((key, value) -> new KeyValue<>(value.getPtfId(), value))
				.to(KafkaConfig.TOPIC_RESULT, Produced.with(Serdes.Long(), positionValSerde));*/

		//.to(KafkaConfig.TOPIC_RESULT, Produced.with(Serdes.Long(), positionValSerde));

		//.selectKey((s, position) -> position.getIsin());
		//.to(KafkaConfig.TOPIC_RESULT, Produced.with(Serdes.String(), positionSerde));
		//.join(instrumentKTable, (position, instrument) -> new KeyValue(position.getPtfId(), instrument.));

		// inventaireStream.join(instrumentKTable, (position, instrument) -> new KeyValue(position.getPtfId(), instrument.));

		// Jointure avec les instruments
		//inventaireStream.join(instrumentKTable, (inventaire, instrument) -> new KeyValue(inventaire.getPtfId(), instrument.));

		Topology topology = builder.build();
		KafkaStreams streams = new KafkaStreams(topology, KafkaConfig.getProperties());

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
