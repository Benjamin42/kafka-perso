package com.amundi.gp.kafka.processor;

import com.amundi.gp.kafka.dto.InventaireDto;
import com.amundi.gp.kafka.dto.PositionDto;
import org.apache.kafka.streams.processor.AbstractProcessor;

public class InventaireSpliter extends AbstractProcessor<String, InventaireDto> {

	@Override
	public void process(String key, InventaireDto value) {
	    for (PositionDto position : value.getPositions()) {
            context().forward(position.getIsin(), position);
            context().commit();
        }
	}

}
