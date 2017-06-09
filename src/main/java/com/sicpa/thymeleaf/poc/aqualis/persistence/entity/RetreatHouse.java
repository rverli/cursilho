package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TB_RETREAT_HOUSE")
public class RetreatHouse implements Serializable {
	
	private static final long serialVersionUID = 2323225657688L;

	@Id
	@Column(name = "ID_RETREAT_HOUSE", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_RETREAT_HOUSE")
	@SequenceGenerator(sequenceName="SEQ_TB_RETREAT_HOUSE", name="SEQ_TB_RETREAT_HOUSE", allocationSize = 1)
	private Long id;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_NAME")
	private String name;
	
	@Length(max=200)
	@Column(name = "DS_RESPONSABLE")
	private String responsable;
	
	@Length(max=100)
	@Column(name = "DS_EMAIL")
	private String email;
	
	@NotNull
	@NotEmpty
	@Length(max=15)
	@Column(name = "DS_PHONENUMBER")
	private String phoneNumber;
	
	@NotNull	
	@Column(name = "BL_RETREAT_HOUSE_ACTIVE")
	private boolean active = false;
	
	@NotNull	
	@Column(name = "BL_RETREAT_HOUSE_DELETED")
	private boolean deleted = false;	
	
	@Valid
	@OrderBy(value= "id ASC")
	@OneToMany(mappedBy="retreatHouse", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Address> address;
	
	public RetreatHouse() {
		//Default constructor for hibernate
	}
	
	public RetreatHouse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * <p>
	 * Create a new user based on another user
	 * </p>
	 * @param user
	 */
	public RetreatHouse(RetreatHouse c) {
		this.id = c.getId();
		this.name = c.getName();
		this.email = c.getEmail();
		this.responsable = c.getResponsable();
		this.active = c.isActive();
		this.deleted = c.isDeleted();
		this.phoneNumber = c.getPhoneNumber();
	}

	/**
	 * <p>
	 * Create a new user with an the id passed in
	 * </p>
	 * @param id
	 */
	public RetreatHouse(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object userObj) {
		if (this == userObj)
			return true;
		if (userObj == null)
			return false;
		if (!(userObj instanceof RetreatHouse))
			return false;
		RetreatHouse otherUser = (RetreatHouse) userObj;
		if (id == null) {
			if (otherUser.id != null)
				return false;
		} else if (!id.equals(otherUser.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RetreatHouse [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (responsable != null)
			builder.append("responsable=").append(responsable).append(", ");
		if (email != null)
			builder.append("email=").append(email).append(", ");
		if (phoneNumber != null)
			builder.append("phoneNumber=").append(phoneNumber).append(", ");
		builder.append("active=").append(active).append(", deleted=").append(deleted).append(", ");
		builder.append("]");
		return builder.toString();
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
}
