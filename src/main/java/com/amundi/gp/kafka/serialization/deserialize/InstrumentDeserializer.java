package com.amundi.gp.kafka.serialization.deserialize;

import com.amundi.gp.kafka.dto.InstrumentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class InstrumentDeserializer implements Deserializer<InstrumentDto> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public InstrumentDto deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		InstrumentDto instrument = null;
		try {
            instrument = mapper.readValue(arg1, InstrumentDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instrument;
	}

}
