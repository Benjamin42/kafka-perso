package com.amundi.gp.kafka.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionPtfDto {
	private String isin;
	private Double qty;
	private Double amt;
	private Date date;
	private Long ptfId;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public PositionPtfDto(@JsonProperty("isin") String isin, @JsonProperty("qty") Double qty,
						  @JsonProperty("amt") Double amt, @JsonProperty("date") Date date,
						  @JsonProperty("ptfId") Long ptfId) {
		this.isin = isin;
		this.qty = qty;
		this.amt = amt;
		this.date = date;
		this.ptfId = ptfId;
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

	public Long getPtfId() {
		return ptfId;
	}

	public void setPtfId(Long ptfId) {
		this.ptfId = ptfId;
	}
}
