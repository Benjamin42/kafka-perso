package com.amundi.gp.kafka.serialization.deserialize;

import java.util.Map;

import com.amundi.gp.kafka.dto.PositionDto;
import org.apache.kafka.common.serialization.Deserializer;

import com.amundi.gp.kafka.dto.InstrumentDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PositionDeserializer implements Deserializer<PositionDto> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public PositionDto deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		PositionDto position = null;
		try {
			position = mapper.readValue(arg1, PositionDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return position;
	}

}
