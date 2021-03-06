package com.amundi.gp.kafka.serialization.serialize;

import java.util.Map;

import com.amundi.gp.kafka.dto.QuotationDto;
import org.apache.kafka.common.serialization.Serializer;

import com.amundi.gp.kafka.dto.PositionDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QuotationSerializer implements Serializer<QuotationDto> {

    @Override public void configure(Map<String, ?> map, boolean b) {
    }
    @Override public byte[] serialize(String arg0, QuotationDto arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
    @Override public void close() {
    }

}
