package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TB_PAGE")
public class Page implements Serializable {

	private static final long serialVersionUID = -8394438829206118195L;

	@Id
	@Column(name="ID_PAGE", nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_PAGE")
	@SequenceGenerator(sequenceName="SEQ_TB_PAGE", name="SEQ_TB_PAGE", allocationSize = 1)
	private Long id;
	
	@Column(name="DS_PATH", nullable=false)
	private String path; //user/edit 
	
	@Column(name="DS_TITLE", nullable=false)
	private String title; // #app.web.page.user.edit
	
	@Column(name="DS_ACTION")
	private String action; // #app.web.action.user.edit
	
	@ManyToOne
	@JoinColumn(name="XID_PARENT")
	private Page parent;
	
	public Page() {/*Needed by hibernate*/}
	
	public Page(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Page getParent() {
		return parent;
	}

	public void setParent(Page parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (path != null)
			builder.append("path=").append(path).append(", ");
		if (title != null)
			builder.append("title=").append(title).append(", ");
		if (action != null)
			builder.append("action=").append(action).append(", ");
		if (parent != null)
			builder.append("parent=").append(parent);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
