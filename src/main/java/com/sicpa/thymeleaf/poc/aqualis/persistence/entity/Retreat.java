package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_RETREAT")
public class Retreat implements Serializable {
	
	private static final long serialVersionUID = 2323225657688L;

	@Id
	@Column(name = "ID_RETREAT", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_RETREAT")
	@SequenceGenerator(sequenceName="SEQ_TB_RETREAT", name="SEQ_TB_RETREAT", allocationSize = 1)
	private Long id;
	
	@NotNull
	@Column(name = "NUM_RETREAT")
	private Long number;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_COORDINATOR")
	private String coordinator;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_BASE")
	private String base;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_TREASURER")
	private String treasurer;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_SECOND_TREASURER")
	private String secondTreasurer;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_SECRETARY")
	private String secretary;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_SECOND_SECRETARY")
	private String secondSecretary;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_CHEF")
	private String chef;
	
	@NotNull
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_SECOND_CHEF")
	private String secondChef;
	
	@NotNull
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_EXTERNAL_HEAD")
	private String externalHead;
	
	@NotNull
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_SECOND_EXTERNAL_HEAD")
	private String secondExternalHead;
	
	@NotNull
	@Column(name="DT_RETREAT")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@NotNull	
	@Column(name = "BL_RETREAT_ACTIVE")
	private boolean active = false;
	
	@NotNull	
	@Column(name = "BL_RETREAT_DELETED")
	private boolean deleted = false;	
	
	@ManyToOne
	@JoinColumn(name="XID_RETREAT_HOUSE")
	private RetreatHouse retreatHouse;
	
	@NotNull
	@NotEmpty
	@Length(max=20)
	@Column(name = "DS_TYPE")
	private String type;
	
	@NotNull
	@NotEmpty
	@Length(max=30)
	@Column(name = "DS_SECTOR")
	private String sector;
	
	public Retreat() {
		//Default constructor for hibernate
	}
	
	/**
	 * <p>
	 * Create a new user with an the id passed in
	 * </p>
	 * @param id
	 */
	public Retreat(Long id) {
		this.id = id;
	}
		
	public Retreat(Long id, Long number) {
		this.id = id;
		this.number = number;
	}
	
	/**
	 * <p>
	 * Create a new user based on another user
	 * </p>
	 * @param user
	 */
	public Retreat(Retreat c) {
		this.id = c.getId();
		this.number = c.getNumber();
		this.coordinator = c.getCoordinator();
		this.base = c.getBase();
		this.secretary = c.getSecretary();
		this.secondSecretary = c.getSecondSecretary();
		this.treasurer = c.getTreasurer();
		this.secondTreasurer = c.getSecondTreasurer();
		this.chef = c.getChef();
		this.secondChef = c.getSecondChef();
		this.externalHead = c.getExternalHead();
		this.secondExternalHead = c.getSecondExternalHead();
		this.active = c.isActive();
		this.deleted = c.isDeleted();
		this.retreatHouse = c.getRetreatHouse();
		this.date = c.getDate();
		this.type = c.getType();
		this.sector = c.getSector();
	}
	
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getTreasurer() {
		return treasurer;
	}

	public void setTreasurer(String treasurer) {
		this.treasurer = treasurer;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getSecondSecretary() {
		return secondSecretary;
	}

	public void setSecondSecretary(String secondSecretary) {
		this.secondSecretary = secondSecretary;
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}

	public String getSecondChef() {
		return secondChef;
	}

	public void setSecondChef(String secondChef) {
		this.secondChef = secondChef;
	}

	public String getExternalHead() {
		return externalHead;
	}

	public void setExternalHead(String externalHead) {
		this.externalHead = externalHead;
	}

	public String getSecondExternalHead() {
		return secondExternalHead;
	}

	public void setSecondExternalHead(String secondExternalHead) {
		this.secondExternalHead = secondExternalHead;
	}

	public RetreatHouse getRetreatHouse() {
		return retreatHouse;
	}

	public void setRetreatHouse(RetreatHouse retreatHouse) {
		this.retreatHouse = retreatHouse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getSecondTreasurer() {
		return secondTreasurer;
	}

	public void setSecondTreasurer(String secondTreasurer) {
		this.secondTreasurer = secondTreasurer;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object userObj) {
		if (this == userObj)
			return true;
		if (userObj == null)
			return false;
		if (!(userObj instanceof Retreat))
			return false;
		Retreat otherUser = (Retreat) userObj;
		if (id == null) {
			if (otherUser.id != null)
				return false;
		} else if (!id.equals(otherUser.id))
			return false;
		return true;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
}
