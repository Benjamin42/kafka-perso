package com.amundi.gp.kafka.serialization.serialize;

import java.util.Map;

import com.amundi.gp.kafka.dto.InstrumentDto;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InstrumentSerializer implements Serializer<InstrumentDto> {

    @Override public void configure(Map<String, ?> map, boolean b) {
    }
    @Override public byte[] serialize(String arg0, InstrumentDto arg1) {
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
