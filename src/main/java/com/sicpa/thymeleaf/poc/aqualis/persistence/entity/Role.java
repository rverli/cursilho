package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TB_ROLE")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ROLE", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_ROLE")
	@SequenceGenerator(sequenceName="SEQ_TB_ROLE", name="SEQ_TB_ROLE", allocationSize = 1)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Column(name = "DS_ROLE", nullable = false)
	private String nome;

	@NotNull
	@NotEmpty
	@Column(name = "DS_DESCRIPTION", nullable = false)
	private String description;

	public Role() {
		//default constructor necessary to framework
	}
	
	public Role(String nome) {
		this.nome = nome;
	}

	public Role(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", nome=" + nome + ", description=" + description + "]";
	}
	
}
