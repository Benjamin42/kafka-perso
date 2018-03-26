package com.amundi.gp.kafka.serialization.deserialize;

import java.util.Map;

import com.amundi.gp.kafka.dto.PositionPtfDto;
import org.apache.kafka.common.serialization.Deserializer;

import com.amundi.gp.kafka.dto.PositionPtfValDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PositionPtfDeserializer implements Deserializer<PositionPtfDto> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public PositionPtfDto deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		PositionPtfDto position = null;
		try {
			position = mapper.readValue(arg1, PositionPtfDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return position;
	}

}
