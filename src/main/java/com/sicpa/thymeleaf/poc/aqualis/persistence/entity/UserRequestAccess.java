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

/**
 * Entity for used to create a new password in a case a user forgot
 * @author ekoetsier
 *
 */
@Entity
@Table(name="TB_USER_REQUEST_ACCESS")
public class UserRequestAccess implements Serializable {
	
	private static final long serialVersionUID = 9160549184225339418L;

	@Id
	@Column(name="ID_USER_REQUEST_ACCESSS", nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_USER_REQUEST_ACCESS")
	@SequenceGenerator(sequenceName="SEQ_TB_USER_REQUEST_ACCESS", name="SEQ_TB_USER_REQUEST_ACCESS", allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="XID_USER")
	private User user;

	@Column(name="DS_REQUEST_CODE")
	private String requestCode; 
	
	@Column(name="DT_REQUEST_VALID", nullable=false)
	private Date requestValid;

	UserRequestAccess() {/*needed by hibernate*/	}
	
	public UserRequestAccess(User user, String requestCode, Date requestValid) {
		this.user = user;
		this.requestCode = requestCode;
		this.requestValid = requestValid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public Date getRequestValid() {
		return requestValid;
	}

	public void setRequestValid(Date requestValid) {
		this.requestValid = requestValid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRequestAccess other = (UserRequestAccess) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRequestAccess [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (requestCode != null)
			builder.append("requestCode=").append(requestCode).append(", ");
		if (requestValid != null)
			builder.append("requestValid=").append(requestValid);
		builder.append("]");
		return builder.toString();
	}
	
}
