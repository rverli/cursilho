package com.sicpa.thymeleaf.poc.aqualis.persistence.dto;

import java.io.Serializable;
import java.util.List;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Cursilhista;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;

public class Report implements Serializable {
	
	private static final long serialVersionUID = 2323225657688L;

	private Retreat retreat;
	private List<Cursilhista> cursilhistas;
	
	private List<Retreat> retreats;

	public Retreat getRetreat() {
		return retreat;
	}

	public void setRetreat(Retreat retreat) {
		this.retreat = retreat;
	}

	public List<Cursilhista> getCursilhistas() {
		return cursilhistas;
	}

	public void setCursilhistas(List<Cursilhista> cursilhistas) {
		this.cursilhistas = cursilhistas;
	}

	public List<Retreat> getRetreats() {
		return retreats;
	}

	public void setRetreats(List<Retreat> retreats) {
		this.retreats = retreats;
	}
}
