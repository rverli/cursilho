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

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;


@Entity
@Table(name="TB_AUDIT")
public class Audit implements Serializable {
	
	private static final long serialVersionUID = 9160549184225339418L;

	@Id
	@Column(name="ID_AUDIT", nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_AUDIT")
	@SequenceGenerator(sequenceName="SEQ_TB_AUDIT", name="SEQ_TB_AUDIT", allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="XID_PAGE")
	private Page page;

	@Column(name="NM_OPERATION_TYPE")
	private AuditingOperationType auditingOperationType; 
	
	@Column(name="DT_ACTION_TIME", nullable=false)
	private Date dateTime;
	
	@ManyToOne
	@JoinColumn(name="XID_USER")
	private User user;
	
	@Column(name="DS_HOSTNAME", nullable=false)
	private String hostName;
	
	@Column(name="DS_IP", nullable=false)
	private String ip;

	public Audit() {/*needed by hibernate*/	}
	
	public Audit(Page page, AuditingOperationType auditingOperationType, User user, String hostName, String ip) {
		this.page = page;
		this.auditingOperationType = auditingOperationType;
		this.user = user;
		this.hostName = hostName;
		this.ip = ip;
		this.dateTime = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String iP) {
		this.ip = iP;
	}

	public AuditingOperationType getAuditingOperationType() {
		return auditingOperationType;
	}

	public void setAuditingOperationType(AuditingOperationType auditingOperationType) {
		this.auditingOperationType = auditingOperationType;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object auditObj) {
		if (this == auditObj)
			return true;
		if (auditObj == null)
			return false;
		if (getClass() != auditObj.getClass())
			return false;
		Audit otherAudit = (Audit) auditObj;
		if (id == null) {
			if (otherAudit.id != null)
				return false;
		} else if (!id.equals(otherAudit.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Audit [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (page != null)
			builder.append("page=").append(page).append(", ");
		if (auditingOperationType != null)
			builder.append("auditingOperationType=").append(auditingOperationType).append(", ");
		if (dateTime != null)
			builder.append("dateTime=").append(dateTime).append(", ");
		if (user != null)
			builder.append("user=").append(user).append(", ");
		if (hostName != null)
			builder.append("hostName=").append(hostName).append(", ");
		if (ip != null)
			builder.append("IP=").append(ip);
		builder.append("]");
		return builder.toString();
	}
	
}
