package com.amundi.gp.kafka.serialization.deserialize;

import java.util.Map;

import com.amundi.gp.kafka.dto.InventaireDto;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InventaireDeserializer implements Deserializer<InventaireDto> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public InventaireDto deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		InventaireDto inventaire = null;
		try {
			inventaire = mapper.readValue(arg1, InventaireDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inventaire;
	}

}
