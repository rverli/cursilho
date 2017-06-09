package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.RoleType;

@Entity
@Table(name = "TB_PROFILE")
public class Profile implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PROFILE", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_PROFILE")
	@SequenceGenerator(sequenceName="SEQ_TB_PROFILE", name="SEQ_TB_PROFILE", allocationSize = 1)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Column(name = "DS_PROFILE", nullable = false)
	private String name;

	@NotNull
	@NotEmpty
	@Column(name = "DS_DESCRIPTION", nullable = false)
	private String description;
	
	@ManyToMany(targetEntity=Role.class, cascade=CascadeType.PERSIST)
	@JoinTable(name="TB_PROFILE_ROLE", 
		inverseJoinColumns={@JoinColumn(name="XID_ROLE", referencedColumnName="ID_ROLE")},
		joinColumns={@JoinColumn(name="XID_PROFILE", referencedColumnName="ID_PROFILE")}
	)
	private List<Role> roles;

	public Profile() {
		//default constructor necessary to framework
	}
	
	/**
	 * Create a new Profile object with name
	 * @param name the name of profile
	 */
	public Profile(String name) {
		this.name = name;
	}

	/**
	 * Create a new profile object with id
	 * @param id the profile id
	 */
	public Profile(Long id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * <p>
	 * Validates if the user has the role
	 * </p>
	 * @param role
	 * @return
	 */
	public boolean hasRole(RoleType role){
		return this.roles != null && !this.roles.isEmpty() &&
				this.roles.toString().contains(role.toString());
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", nome=" + name + ", description=" + description + "]";
	}
	
}
