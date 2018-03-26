package com.amundi.gp.kafka.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionPtfValDto {
	private String isin;
	private Double qty;
	private Double amt;
	private Date date;
	private Long ptfId;

	private String label;
	private Long instNum;
	private String typeActif;
	private String zoneGeo;
	private String secteur;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public PositionPtfValDto(@JsonProperty("instrument") InstrumentDto instrument,
			@JsonProperty("position") PositionPtfDto position) {
		if (position != null) {
			this.isin = position.getIsin();
			this.qty = position.getQty();
			this.amt = position.getAmt();
			this.date = position.getDate();
			this.ptfId = position.getPtfId();
		}
		if (instrument != null) {
			this.label = instrument.getLabel();
			this.instNum = instrument.getInstNum();
			this.typeActif = instrument.getTypeActif();
			this.zoneGeo = instrument.getZoneGeo();
			this.secteur = instrument.getSecteur();
		}
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getInstNum() {
		return instNum;
	}

	public void setInstNum(Long instNum) {
		this.instNum = instNum;
	}

	public String getTypeActif() {
		return typeActif;
	}

	public void setTypeActif(String typeActif) {
		this.typeActif = typeActif;
	}

	public String getZoneGeo() {
		return zoneGeo;
	}

	public void setZoneGeo(String zoneGeo) {
		this.zoneGeo = zoneGeo;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}
}
