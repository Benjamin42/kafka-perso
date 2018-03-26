package com.amundi.gp.kafka.producers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amundi.gp.kafka.dto.InventaireDto;
import com.amundi.gp.kafka.dto.PositionDto;
import com.amundi.gp.kafka.serialization.serialize.InventaireSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amundi.gp.kafka.config.KafkaConfig;

public class InventaireProducer {
	private static Logger logger = LoggerFactory.getLogger(ReferentielProducer.class);

	private static Integer MAX_PUBLISH = 1;

	@Test
	public void inventaireProducerTest() {
		// Inventaire producing
		Producer<Long, InventaireDto> producerInventaire = new KafkaProducer<>(KafkaConfig.getProperties(
				LongSerializer.class, InventaireSerializer.class));

		List<InventaireDto> inventaires = generateInventairesFromFile();
		Integer count = 1;
		for (InventaireDto inventaire : inventaires) {
			producerInventaire.send(new ProducerRecord<>(KafkaConfig.TOPIC_INVENTAIRE,
					inventaire.getPtfId(), inventaire));
            if (MAX_PUBLISH != null) {
                count++;
                if (count >= MAX_PUBLISH) {
                    break;
                }
            }
		}

		producerInventaire.close();
	}

	/**
	 * Requete en base pour générer le fichier :
		 SELECT p.ptf_id, i.ISIN, i.LABEL, p.QUANTITY, p.AMOUNT, p.DATE_COMPTABLE
		 FROM POSITION p
		 	JOIN REF_INSTRUMENT i on p.INS_ID = i.INS_ID
		 WHERE DATE_COMPTABLE = to_date('20/03/2018', 'DD/MM/YYYY')
			 AND p.PTF_ID in (SELECT PTF_ID FROM PTF_PORTEFEUILLE WHERE STATUT = 'GEST' AND PNA_ENT_ID in (2, 3) AND PTF_ID < 36000)
		 ORDER BY p.PTF_ID;
	 */
	private List<InventaireDto> generateInventairesFromFile() {
		List<InventaireDto> inventaires = new ArrayList<>();

		String csvFile = "src/main/resources/inventaire.csv";
		String line;

		InventaireDto inventaire = null;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {
				String[] splitedLine = line.split(";");

				if (splitedLine[0].equals("PTF_ID")) {
					continue;
				}

                Long ptfId = Long.parseLong(splitedLine[0]);
                if (inventaire == null || !inventaire.getPtfId().equals(ptfId)) {
                    inventaire = new InventaireDto(ptfId);
                    inventaires.add(inventaire);
                }

                PositionDto position = new PositionDto(splitedLine[1], Double.parseDouble(splitedLine[3]),
						Double.parseDouble(splitedLine[4]), new Date());
                inventaire.getPositions().add(position);
            }

		} catch (IOException e) {
			e.printStackTrace();
		}

		return inventaires;
	}
}
