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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.ProfileType;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatch;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.password.PasswordGroup;

/**
 * <p>
 * Class representing a User in the system
 * </p>
 *
 */
@Entity
@Table(name = "TB_USER")
@FieldMatch(first = "password", second = "confirmPassword", message = "The confirmation password does not match the requested password.", groups = FieldMatchGroup.class)
public class User implements Serializable {
	
	private static final long serialVersionUID = 2323225657688L;

	@Id
	@Column(name = "ID_USER", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_USER")
	@SequenceGenerator(sequenceName="SEQ_TB_USER", name="SEQ_TB_USER", allocationSize = 1)
	private Long id;
	
	@NotNull(groups = PasswordGroup.class)
	@NotEmpty(groups = PasswordGroup.class)
	@Length(max=160)
	@Column(name = "DS_PASSWORD", nullable = false)
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@NotNull	
	@NotEmpty
	@Length(max=200)
	@Column(name = "DS_NAME", nullable = false)
	private String name;
	
	@Email
	@NotNull	
	@NotEmpty
	@Length(max=100)
	@Column(name = "DS_EMAIL", nullable = false, unique = true)
	private String email;
	
	@NotNull
	@NotEmpty
	@Length(max=15)
	@Column(name = "DS_PHONENUMBER", nullable = false)
	private String phoneNumber;
	
	@NotNull	
	@Column(name = "BL_USER_ACTIVE", nullable = false)
	private boolean active = false;
	
	@NotNull	
	@Column(name = "BL_USER_DELETED", nullable = false)
	private boolean deleted = false;	
	
	@ManyToMany(targetEntity=Profile.class, cascade=CascadeType.ALL)
	@JoinTable(name="TB_USER_PROFILE", 
		inverseJoinColumns={@JoinColumn(name="XID_PROFILE", referencedColumnName="ID_PROFILE")},
		joinColumns={@JoinColumn(name="XID_USER", referencedColumnName="ID_USER")}
	)
	private List<Profile> profiles;
	
	@ManyToOne
	@JoinColumn(name="XID_RETREAT")
	private Retreat retreat;
	
	public User() {
		//Default constructor for hibernate
	}
	
	/**
	 * <p>
	 * Create a new user based on another user
	 * </p>
	 * @param user
	 */
	public User(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.active = user.isActive();
		this.deleted = user.isDeleted();
		this.password = user.getPassword();
		this.phoneNumber = user.getPhoneNumber();
		this.retreat = user.getRetreat();
	}

	/**
	 * <p>
	 * Create a new user with an the id passed in
	 * </p>
	 * @param id
	 */
	public User(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public List<Profile> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Retreat getRetreat() {
		return retreat;
	}

	public void setRetreat(Retreat retreat) {
		this.retreat = retreat;
	}
	
	/**
	 * <p>
	 * Validates if the user has the role
	 * </p>
	 * @param role
	 * @return
	 */
	public boolean hasProfile(ProfileType profile){
		return this.profiles != null && !this.profiles.isEmpty() &&
				this.profiles.toString().contains(profile.toString());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object userObj) {
		if (this == userObj)
			return true;
		if (userObj == null)
			return false;
		if (!(userObj instanceof User))
			return false;
		User otherUser = (User) userObj;
		if (id == null) {
			if (otherUser.id != null)
				return false;
		} else if (!id.equals(otherUser.id))
			return false;
		return true;
	}
}
