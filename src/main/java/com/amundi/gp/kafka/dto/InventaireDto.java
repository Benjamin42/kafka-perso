package com.amundi.gp.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class InventaireDto {
    @JsonProperty
    private Long ptfId;
    private List<PositionDto> positions;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public InventaireDto(@JsonProperty("ptfId") Long ptfId) {
        this.ptfId = ptfId;
        this.positions = new ArrayList<>();
    }

    public Long getPtfId() {
        return ptfId;
    }

    public void setPtfId(Long ptfId) {
        this.ptfId = ptfId;
    }

    public List<PositionDto> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDto> positions) {
        this.positions = positions;
    }
}
