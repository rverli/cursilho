package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AddressType;

@Entity
@Table(name = "TB_ADDRESS")
public class Address implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ADDRESS", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_ADDRESS")
	@SequenceGenerator(sequenceName="SEQ_TB_ADDRESS", name="SEQ_TB_ADDRESS", allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "XID_CURSILHISTA", referencedColumnName="ID")
	private Cursilhista cursilhista;
	
	@ManyToOne
	@JoinColumn(name = "XID_RETREAT_HOUSE", referencedColumnName="ID_RETREAT_HOUSE")
	private RetreatHouse retreatHouse;
	
	@NotNull
	@NotEmpty
	@Length(max=10)
	@Column(name = "DS_CEP", nullable = false)
	private String cep;
	
	@NotNull
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_ADDRESS", nullable = false)
	private String companyAddress;
	
	@Length(max=200)
	@Column(name = "DS_COMPLEMENT")
	private String complement;
	
	@NotNull
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_NEIGHBORHOOD", nullable = false)
	private String neighborhood;
	
	@NotNull
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_CITY", nullable = false)
	private String city;
	
	@NotNull
	@NotEmpty
	@Length(max=2)
	@Column(name = "DS_STATE", nullable = false)
	private String state;
	
	@Column(name = "NM_IBGE")
	private Long ibge;
	
	@Column(name = "TP_ADDRESS_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	
	@Column(name = "BL_ADDRESS_ACTIVE", nullable = false)
	private boolean active;

	@Column(name = "BL_ADDRESS_DELETED", nullable = false)
	private boolean deleted;	
	
	public Address() {
		// default constructor
	}
	
	public Address(AddressType e) {
		addressType = e;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getIbge() {
		return ibge;
	}

	public void setIbge(Long ibge) {
		this.ibge = ibge;
	}
	
	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object addressObj) {
		if (this == addressObj)
			return true;
		if (addressObj == null)
			return false;
		if (getClass() != addressObj.getClass())
			return false;
		Address otherAddress = (Address) addressObj;
		if (id == null) {
			if (otherAddress.id != null)
				return false;
		} else if (!id.equals(otherAddress.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Address [id=")
		 .append( id )
		 .append(", cursilhista=")
		 .append( cursilhista != null ? cursilhista.getId() : "" )
		 .append(", retreatHouse=")
		 .append( getRetreatHouse() != null ? getRetreatHouse().getId() : "" )
		 .append(", cep=")
		 .append( cep ) 
		 .append(", address=")
		 .append( companyAddress )
		 .append( ", complement=")
		 .append( complement )
		 .append( ", neighborhood=")
		 .append( neighborhood )
		 .append( ", city=" )
		 .append( city )
		 .append( ", state=" )
		 .append( state )
		 .append( ", ibge=")
		 .append( ibge )
		 .append( ", addressType=" )
		 .append( addressType )
		 .append( ", active=" )
		 .append( active )
		 .append( ", deleted=" )
		 .append( deleted )
		 .append( "]");
		
		return sb.toString();
	}

	public Cursilhista getCursilhista() {
		return cursilhista;
	}

	public void setCursilhista(Cursilhista cursilhista) {
		this.cursilhista = cursilhista;
	}

	public RetreatHouse getRetreatHouse() {
		return retreatHouse;
	}

	public void setRetreatHouse(RetreatHouse retreatHouse) {
		this.retreatHouse = retreatHouse;
	}

	
}