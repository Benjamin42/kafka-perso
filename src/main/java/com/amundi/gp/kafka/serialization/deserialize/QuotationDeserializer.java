package com.amundi.gp.kafka.serialization.deserialize;

import java.util.Map;

import com.amundi.gp.kafka.dto.QuotationDto;
import org.apache.kafka.common.serialization.Deserializer;

import com.amundi.gp.kafka.dto.InstrumentDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QuotationDeserializer implements Deserializer<QuotationDto> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public QuotationDto deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		QuotationDto quotation = null;
		try {
			quotation = mapper.readValue(arg1, QuotationDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quotation;
	}

}
