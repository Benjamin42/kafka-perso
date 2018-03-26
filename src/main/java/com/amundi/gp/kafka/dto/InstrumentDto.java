package com.amundi.gp.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentDto {

	private String isin;
	private String label;
	private Long instNum;
	private String typeActif;
	private String zoneGeo;
	private String secteur;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public InstrumentDto(@JsonProperty("isin") String isin, @JsonProperty("label") String label,
			@JsonProperty("instNum") Long instNum, @JsonProperty("typeActif") String typeActif,
			@JsonProperty("zoneGeo") String zoneGeo, @JsonProperty("secteur") String secteur) {
		this.isin = isin;
		this.label = label;
		this.instNum = instNum;
		this.typeActif = typeActif;
		this.zoneGeo = zoneGeo;
		this.secteur = secteur;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
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
