package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_CURSILHISTA")
public class Cursilhista implements Serializable {
	
	private static final long serialVersionUID = 2323225657688L;

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TB_CURSILHISTA")
	@SequenceGenerator(sequenceName="SEQ_TB_CURSILHISTA", name="SEQ_TB_CURSILHISTA", allocationSize = 1)
	private Long id;
	
	@Column(name = "NUM_CURSILHISTA")
	private Long numberCursilhista = 0L;
	
	@NotNull  	
	@NotEmpty 
	@Length(max=200)
	@Column(name = "DS_NAME")
	private String name;
	
	@NotNull
	@NotEmpty
	@Column(name = "DS_MARITAL_STATUS")
	private String maritalStatus;
	
	@NotNull
	@NotEmpty
	@Column(name = "DS_GENDER")
	private String gender;
	
	@NotNull
	@NotEmpty
	@Column(name = "DS_RELIGION")
	private String religion = "Catolicismo Romano";
	
	@NotNull
	@Column(name="DT_BIRTH")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	@NotNull
	@Column(name = "DT_CREATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateCreate;
	
	@NotNull
	@Column(name = "DT_LAST_UPDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateLastUpdate;

	@NotNull
	@Column(name = "BL_ACTIVE_CHURCH")
	private Boolean activeChurch = false;
	
	@NotNull
	@Column(name = "BL_HEALTH_PROBLEM")
	private Boolean healthProblem = false;
	
	@NotNull
	@Column(name = "BL_SOME_DRUGS")
	private Boolean drugs = false;
	
	@NotNull
	@Column(name = "BL_DIET_REC")
	private Boolean diet = false;
	
	@NotNull	
	@Column(name = "BL_CURSILHISTA_ACTIVE")
	private boolean active = false;
	
	@NotNull	
	@Column(name = "BL_CURSILHISTA_DELETED")
	private boolean deleted = false;
	
	//########################################
	
	@Length(max=200)
	@Column(name = "DS_PROFESSION")
	private String profession;
	
	@Email	
	@Length(max=100)
	@Column(name = "DS_EMAIL")
	private String email;
	
	@Length(max=15)
	@Column(name = "DS_PHONENUMBER")
	private String phoneNumber;
	
	@Length(max=15)
	@Column(name = "DS_MOBILENUMBER")
	private String mobileNumber;
	
	@Length(max=15)
	@Column(name = "DS_WORKNUMBER")
	private String workNumber;  
	
	@Valid
	@OrderBy(value= "id ASC")
	@OneToMany(mappedBy="cursilhista", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Address> address;
	
	@ManyToOne
	@JoinColumn(name="XID_RETREAT")
	private Retreat retreat;
	
	@Transient
	private Long numberRetreat;
	
	@Length(max=200)
	@Column(name = "DS_NICKNAME")
	private String nickname;
	
	//@CPF
	@Length(max=15)
	@Column(name = "DS_CPF")
	private String cpf;
	
	@Length(max=12)
	@Column(name = "DS_ID")
	private String identity;
	
	@Length(max=200)
	@Column(name = "DS_COMPANY_WORK")
	private String companyWork;

	@Length(max=100)
	@Column(name = "DS_FUNCTION_WORK")
	private String functionWork;
	
	@Length(max=200)
	@Column(name = "DS_NAME_SPOUSE")
	private String nameSpouser;
	
	@Length(max=100)
	@Column(name = "DS_PARISH_CHURCH")
	private String parishChurch;
	
	@Length(max=12)
	@Column(name = "DS_STUDENT")
	private String student;
	
	@Length(max=20)
	@Column(name = "DS_LEVEL_EDUCATION")
	private String levelEducation;
	
	@Length(max=100)
	@Column(name = "DS_PLACE_WORK")
	private String placeWork;
	
	@Length(max=100)
	@Column(name = "DS_HEALTH_PROBLEM")
	private String healthProblemDesc;
	
	@Length(max=200)
	@Column(name = "DS_NAME_PRESENTER")
	private String namePresenter;
	
	@ManyToOne
	@JoinColumn(name="XID_RETREAT_PRESENTER")
	private Retreat retreatPresenter;
	
	@Length(max=15)
	@Column(name = "DS_PHONENUMBER_PRESENTER")
	private String phoneNumberPresenter;
	
	@Length(max=15)
	@Column(name = "DS_MOBILENUMBER_PRESENTER")
	private String mobileNumberPresenter;
	
	@Length(max=15)
	@Column(name = "DS_WORKNUMBER_PRESENTER")
	private String workNumberPresenter;
	
	@Length(max=500)
	@Column(name = "DS_PRESENTER_CADIDATE")
	private String presenterCandidate;
	
	@Column(name = "BL_PRESENTER_PARTICIPATE_CUR")
	private Boolean presenterParticipateCursilho;
	
	@Length(max=100)
	@Column(name = "DS_PARTICIPATE_CURSILHO")
	private String presenterParticipateCursilhoDesc;

	@Email	
	@Length(max=100)
	@Column(name = "DS_EMAIL_PRESENTER")
	private String emailPresenter;
	
	@Length(max=100)
	@Column(name = "DS_COMMUNITY")
	private String community;
	
	@Length(max=100)
	@Column(name = "DS_SECTOR")
	private String sector;
	
	@Length(max=20)
	@Column(name = "DS_SACRAMENTS_LEVEL")
	private String sacramentsLevel;
	
	public Cursilhista() {
		//Default constructor for hibernate
	}
	
	public Cursilhista(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * <p>
	 * Create a new user with an the id passed in
	 * </p>
	 * @param id
	 */
	public Cursilhista(Long id) {
		this.id = id;
	}
	
	public void setRetreat(Retreat retreat) {
		this.retreat = retreat;
		
		if ( retreat != null ) {
			setNumberRetreat(retreat.getNumber());
		}
	}

	public Long getNumberRetreat() {
		return retreat != null ? retreat.getNumber() : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumberCursilhista() {
		return numberCursilhista;
	}

	public void setNumberCursilhista(Long numberCursilhista) {
		
		if ( numberCursilhista == null ) {
			this.numberCursilhista = 0L;
		} else {
			this.numberCursilhista = numberCursilhista;	
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
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

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getCompanyWork() {
		return companyWork;
	}

	public void setCompanyWork(String companyWork) {
		this.companyWork = companyWork;
	}

	public String getFunctionWork() {
		return functionWork;
	}

	public void setFunctionWork(String functionWork) {
		this.functionWork = functionWork;
	}

	public String getNameSpouser() {
		return nameSpouser;
	}

	public void setNameSpouser(String nameSpouser) {
		this.nameSpouser = nameSpouser;
	}

	public Boolean getActiveChurch() {
		return activeChurch;
	}

	public void setActiveChurch(Boolean activeChurch) {
		this.activeChurch = activeChurch;
	}

	public String getParishChurch() {
		return parishChurch;
	}

	public void setParishChurch(String parishChurch) {
		this.parishChurch = parishChurch;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getLevelEducation() {
		return levelEducation;
	}

	public void setLevelEducation(String levelEducation) {
		this.levelEducation = levelEducation;
	}

	public String getPlaceWork() {
		return placeWork;
	}

	public void setPlaceWork(String placeWork) {
		this.placeWork = placeWork;
	}

	public Boolean getHealthProblem() {
		return healthProblem;
	}

	public void setHealthProblem(Boolean healthProblem) {
		this.healthProblem = healthProblem;
	}

	public String getHealthProblemDesc() {
		return healthProblemDesc;
	}

	public void setHealthProblemDesc(String healthProblemDesc) {
		this.healthProblemDesc = healthProblemDesc;
	}

	public Boolean getDrugs() {
		return drugs;
	}

	public void setDrugs(Boolean drugs) {
		this.drugs = drugs;
	}

	public Boolean getDiet() {
		return diet;
	}

	public void setDiet(Boolean diet) {
		this.diet = diet;
	}

	public String getNamePresenter() {
		return namePresenter;
	}

	public void setNamePresenter(String namePresenter) {
		this.namePresenter = namePresenter;
	}

	public Retreat getRetreatPresenter() {
		return retreatPresenter;
	}

	public void setRetreatPresenter(Retreat retreatPresenter) {
		this.retreatPresenter = retreatPresenter;
	}

	public String getPhoneNumberPresenter() {
		return phoneNumberPresenter;
	}

	public void setPhoneNumberPresenter(String phoneNumberPresenter) {
		this.phoneNumberPresenter = phoneNumberPresenter;
	}

	public String getMobileNumberPresenter() {
		return mobileNumberPresenter;
	}

	public void setMobileNumberPresenter(String mobileNumberPresenter) {
		this.mobileNumberPresenter = mobileNumberPresenter;
	}

	public String getWorkNumberPresenter() {
		return workNumberPresenter;
	}

	public void setWorkNumberPresenter(String workNumberPresenter) {
		this.workNumberPresenter = workNumberPresenter;
	}

	public String getPresenterCandidate() {
		return presenterCandidate;
	}

	public void setPresenterCandidate(String presenterCandidate) {
		this.presenterCandidate = presenterCandidate;
	}

	public Boolean getPresenterParticipateCursilho() {
		return presenterParticipateCursilho;
	}

	public void setPresenterParticipateCursilho(Boolean presenterParticipateCursilho) {
		this.presenterParticipateCursilho = presenterParticipateCursilho;
	}

	public String getPresenterParticipateCursilhoDesc() {
		return presenterParticipateCursilhoDesc;
	}

	public void setPresenterParticipateCursilhoDesc(String presenterParticipateCursilhoDesc) {
		this.presenterParticipateCursilhoDesc = presenterParticipateCursilhoDesc;
	}

	public String getEmailPresenter() {
		return emailPresenter;
	}

	public void setEmailPresenter(String emailPresenter) {
		this.emailPresenter = emailPresenter;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSacramentsLevel() {
		return sacramentsLevel;
	}

	public void setSacramentsLevel(String sacramentsLevel) {
		this.sacramentsLevel = sacramentsLevel;
	}

	public Retreat getRetreat() {
		return retreat;
	}

	public void setNumberRetreat(Long numberRetreat) {
		this.numberRetreat = numberRetreat;
	}

	
}
