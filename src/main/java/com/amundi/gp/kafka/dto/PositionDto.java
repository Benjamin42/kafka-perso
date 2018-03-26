package com.amundi.gp.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

public class PositionDto {
	private String isin;
	private Double qty;
	private Double amt;
	private Date date;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public PositionDto(@JsonProperty("isin") String isin, @JsonProperty("qty") Double qty,
			@JsonProperty("amt") Double amt, @JsonProperty("date") Date date) {
		this.isin = isin;
		this.qty = qty;
		this.amt = amt;
		this.date = date;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
