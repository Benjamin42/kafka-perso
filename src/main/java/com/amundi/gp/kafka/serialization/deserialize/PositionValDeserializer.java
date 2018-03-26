package com.amundi.gp.kafka.serialization.deserialize;

import java.util.Map;

import com.amundi.gp.kafka.dto.PositionPtfValDto;
import org.apache.kafka.common.serialization.Deserializer;

import com.amundi.gp.kafka.dto.PositionDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PositionValDeserializer implements Deserializer<PositionPtfValDto> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public PositionPtfValDto deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		PositionPtfValDto position = null;
		try {
			position = mapper.readValue(arg1, PositionPtfValDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return position;
	}

}
