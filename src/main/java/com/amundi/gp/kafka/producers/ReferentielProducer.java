package com.amundi.gp.kafka.producers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.amundi.gp.kafka.config.KafkaConfig;
import com.amundi.gp.kafka.dto.InstrumentDto;
import com.amundi.gp.kafka.dto.InventaireDto;
import com.amundi.gp.kafka.dto.PositionDto;
import com.amundi.gp.kafka.dto.QuotationDto;
import com.amundi.gp.kafka.serialization.serialize.InstrumentSerializer;
import com.amundi.gp.kafka.serialization.serialize.QuotationSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferentielProducer {

	private static Logger logger = LoggerFactory.getLogger(ReferentielProducer.class);

	/**
	 * Requete en base pour générer le fichier :
	 SELECT i.INS_ID, i.ISIN, i.LABEL, i.INST_NUM, act.LABEL, geo.LABEL, sect.LABEL, q1.quote, q1.quote_date
	 FROM REF_INSTRUMENT i
	 JOIN MGS_COMMON.TYPE act ON i.ASSET_TYPE_ID = act.TYP_ID
	 JOIN MGS_COMMON.TYPE geo ON i.GEOGRAPHIC_ZONE_ID = geo.TYP_ID
	 JOIN MGS_COMMON.TYPE sect ON i.SECTOR_ID = sect.TYP_ID
	 JOIN REF_QUOTATION q1 ON q1.INS_ID = i.INS_ID AND q1.LAST = 1 AND q1.PROVIDER_ID = 1399
	 WHERE i.INS_ID in (SELECT INS_ID FROM POSITION p
	 WHERE DATE_COMPTABLE = to_date('20/03/2018', 'DD/MM/YYYY')
	 AND p.PTF_ID in (SELECT PTF_ID FROM PTF_PORTEFEUILLE WHERE STATUT = 'GEST' AND PNA_ENT_ID in (2, 3) AND PTF_ID < 36000)
	 );
	 */

	@Test
	public void referentielProducerTest() {
		// Instrument producing
		Producer<String, InstrumentDto> producerInstrument = new KafkaProducer<>(KafkaConfig.getProperties(
				StringSerializer.class, InstrumentSerializer.class));

		// Quotation producing
		/*Producer<String, QuotationDto> producerQuotation = new KafkaProducer<>(KafkaConfig.getProperties(
				StringSerializer.class, QuotationSerializer.class));*/

		String csvFile = "src/main/resources/instrument.csv";
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {
				String[] splitedLine = line.split(";");

				if (splitedLine[0].equals("ISIN")) {
					// Skip first line
					continue;
				}

				InstrumentDto instrument = new InstrumentDto(splitedLine[0], splitedLine[1],
						Long.parseLong(splitedLine[2]), splitedLine[3], splitedLine[4], splitedLine[5]);

				producerInstrument.send(new ProducerRecord<>(KafkaConfig.TOPIC_INSTRUMENT, instrument.getIsin(),
						instrument));

				/*QuotationDto quotation = new QuotationDto(splitedLine[0], Double.parseDouble(splitedLine[6]),
						new Date());

				producerQuotation
						.send(new ProducerRecord<>(KafkaConfig.TOPIC_QUOTATION, quotation.getIsin(), quotation));*/
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		producerInstrument.close();
		//producerQuotation.close();
	}

}
