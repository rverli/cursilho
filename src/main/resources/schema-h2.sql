CREATE ROLE IF NOT EXISTS BCKOFF_APP;

/*****************************************
				RETREAT_HOUSE TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_RETREAT_HOUSE;
CREATE TABLE TB_RETREAT_HOUSE(
  ID_RETREAT_HOUSE NUMBER NOT NULL,
  DS_NAME VARCHAR2(200) NOT NULL,
  DS_RESPONSABLE VARCHAR2(200) NOT NULL,
  DS_EMAIL VARCHAR2(100),  
  DS_PHONENUMBER VARCHAR2(15) NOT NULL,
  BL_RETREAT_HOUSE_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_RETREAT_HOUSE_ACTIVE IN (0,1)), 
  BL_RETREAT_HOUSE_DELETED NUMBER(1,0) DEFAULT 0 NOT NULL  
);

ALTER TABLE TB_RETREAT_HOUSE ADD CONSTRAINT PK_TB_RETREAT_HOUSE PRIMARY KEY ( ID_RETREAT_HOUSE );
DROP SEQUENCE IF EXISTS SEQ_TB_RETREAT_HOUSE;
CREATE SEQUENCE SEQ_TB_RETREAT_HOUSE  INCREMENT BY 1 START WITH 20;
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_RETREAT_HOUSE TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_USER for TB_USER;

/*****************************************
				RETREAT TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_RETREAT;
CREATE TABLE TB_RETREAT(
  ID_RETREAT NUMBER NOT NULL,
  NUM_RETREAT NUMBER NOT NULL UNIQUE,
  DS_COORDINATOR VARCHAR2(200) NOT NULL,
  DS_BASE VARCHAR2(200) NOT NULL,
  DS_TREASURER VARCHAR2(200) NOT NULL,
  DS_SECOND_TREASURER VARCHAR2(200) NOT NULL,
  DS_SECRETARY VARCHAR2(200) NOT NULL,
  DS_SECOND_SECRETARY VARCHAR2(200) NOT NULL,
  DS_CHEF VARCHAR2(200) NOT NULL,
  DS_SECOND_CHEF VARCHAR2(200) NOT NULL,
  DS_EXTERNAL_HEAD VARCHAR2(200) NOT NULL,
  DS_SECOND_EXTERNAL_HEAD VARCHAR2(200) NOT NULL,
  DT_RETREAT TIMESTAMP(0) NOT NULL,
  BL_RETREAT_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_RETREAT_ACTIVE IN (0,1)), 
  BL_RETREAT_DELETED NUMBER(1,0) DEFAULT 0 NOT NULL,
  XID_RETREAT_HOUSE NUMBER,
  DS_TYPE VARCHAR2(20) NOT NULL,
  DS_SECTOR VARCHAR2(30) NOT NULL
);

ALTER TABLE TB_RETREAT ADD CONSTRAINT PK_TB_RETREAT PRIMARY KEY ( ID_RETREAT );
ALTER TABLE TB_RETREAT ADD CONSTRAINT FK_RETREAT_RETREAT_HOUSE FOREIGN KEY ( XID_RETREAT_HOUSE ) REFERENCES TB_RETREAT_HOUSE (ID_RETREAT_HOUSE);

DROP SEQUENCE IF EXISTS SEQ_TB_RETREAT;
CREATE SEQUENCE  SEQ_TB_RETREAT  INCREMENT BY 1 START WITH 20;
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_RETREAT TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_USER for TB_USER;

/*****************************************
				CURSILHISTA TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_CURSILHISTA;
CREATE TABLE TB_CURSILHISTA(
  ID NUMBER NOT NULL,
  NUM_CURSILHISTA NUMBER DEFAULT 0,
  DS_NAME VARCHAR2(200) NOT NULL,
  DS_PROFESSION VARCHAR2(200) NOT NULL,
  DS_EMAIL VARCHAR2(100),  
  DS_PHONENUMBER VARCHAR2(15),  
  DS_MOBILENUMBER VARCHAR2(15),
  DS_WORKNUMBER VARCHAR2(15),  
  BL_CURSILHISTA_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_CURSILHISTA_ACTIVE IN (0,1)), 
  BL_CURSILHISTA_DELETED NUMBER(1,0) DEFAULT 0 NOT NULL,
  XID_RETREAT NUMBER,
  DS_MARITAL_STATUS VARCHAR2(100) NOT NULL,
  DS_GENDER VARCHAR2(50) NOT NULL,
  DS_RELIGION VARCHAR2(100) DEFAULT 'Catolicismo Romano' NOT NULL,  
  DS_NICKNAME VARCHAR2(100),
  DT_BIRTH TIMESTAMP(0) NOT NULL,
  DT_CREATE TIMESTAMP(0) NOT NULL,
  DT_LAST_UPDATE TIMESTAMP(0) NOT NULL,
  DS_CPF VARCHAR2(15),
  DS_ID VARCHAR2(12),
  DS_COMPANY_WORK VARCHAR2(200),
  DS_FUNCTION_WORK VARCHAR2(100),
  DS_NAME_SPOUSE VARCHAR2(200),
  DS_SACRAMENTS_LEVEL VARCHAR2(20),
  BL_ACTIVE_CHURCH NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_ACTIVE_CHURCH IN (0,1)),--Ativo na igreja
  DS_PARISH_CHURCH VARCHAR2(100),--Paroquia
  DS_STUDENT VARCHAR2(12),--Estudante? Não/Escola/Universidade
  DS_LEVEL_EDUCATION VARCHAR2(20),--Grau de instrucao
  DS_PLACE_WORK VARCHAR2(100),
  BL_HEALTH_PROBLEM NUMBER(1,0) NOT NULL  CHECK (BL_HEALTH_PROBLEM IN (0,1)),--Problema de saúde
  DS_HEALTH_PROBLEM VARCHAR2(100),--Qual problema de saúde
  BL_SOME_DRUGS NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_SOME_DRUGS IN (0,1)),--Problema de saúde
  BL_DIET_REC NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_DIET_REC IN (0,1)),--Dieta por recomendacao medica  
  DS_NAME_PRESENTER VARCHAR2(200),
  XID_RETREAT_PRESENTER NUMBER,
  DS_PHONENUMBER_PRESENTER VARCHAR2(15),  
  DS_MOBILENUMBER_PRESENTER VARCHAR2(15),
  DS_WORKNUMBER_PRESENTER VARCHAR2(15),
  DS_PRESENTER_CADIDATE VARCHAR2(1000),--Grau de conhecimento com o candidato
  BL_PRESENTER_PARTICIPATE_CUR NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_PRESENTER_PARTICIPATE_CUR IN (0,1)),
  DS_PARTICIPATE_CURSILHO VARCHAR2(100),--Qual comunidade de cursilho participa
  DS_COMMUNITY VARCHAR2(100),
  DS_SECTOR VARCHAR2(100),
  DS_EMAIL_PRESENTER VARCHAR2(100)
);

ALTER TABLE TB_CURSILHISTA ADD CONSTRAINT PK_TB_CURSILHISTA PRIMARY KEY ( ID );
ALTER TABLE TB_CURSILHISTA ADD CONSTRAINT FK_CURSILHISTA_RETREAT FOREIGN KEY ( XID_RETREAT ) REFERENCES TB_RETREAT (ID_RETREAT);
ALTER TABLE TB_CURSILHISTA ADD CONSTRAINT FK_PRESENTER_RETREAT FOREIGN KEY ( XID_RETREAT_PRESENTER ) REFERENCES TB_RETREAT (ID_RETREAT);

DROP SEQUENCE IF EXISTS SEQ_TB_CURSILHISTA;
CREATE SEQUENCE  SEQ_TB_CURSILHISTA  INCREMENT BY 1 START WITH 20;
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_CURSILHISTA TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_USER for TB_USER;

/*****************************************
				ADDRESS TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_ADDRESS;
create table TB_ADDRESS(
  ID_ADDRESS NUMBER NOT NULL,
  DS_CEP VARCHAR2(10) NOT NULL,
  DS_ADDRESS VARCHAR2(200) NOT NULL,
  DS_COMPLEMENT VARCHAR2(200) NULL,
  DS_NEIGHBORHOOD VARCHAR2(200) NOT NULL,
  DS_CITY VARCHAR2(200) NOT NULL,
  DS_STATE CHAR(2) NOT NULL,
  NM_IBGE NUMBER(6,0) NULL,
  TP_ADDRESS_TYPE CHAR(1) check (TP_ADDRESS_TYPE in ('S', 'E')),
  XID_CURSILHISTA NUMBER,
  XID_RETREAT_HOUSE NUMBER,
  BL_ADDRESS_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL, 
  BL_ADDRESS_DELETED NUMBER(1,0) DEFAULT 0 NOT NULL    
);

ALTER TABLE TB_ADDRESS ADD constraint PK_TB_ADDRESS primary key (ID_ADDRESS);
ALTER TABLE TB_ADDRESS ADD constraint FK_TB_CURSILHISTA foreign key(XID_CURSILHISTA) references TB_CURSILHISTA(ID);
ALTER TABLE TB_ADDRESS ADD constraint FK_TB_RETREAT_HOUSE foreign key(XID_RETREAT_HOUSE) references TB_RETREAT_HOUSE(ID_RETREAT_HOUSE);

DROP SEQUENCE IF EXISTS SEQ_TB_ADDRESS;
CREATE SEQUENCE SEQ_TB_ADDRESS INCREMENT BY 1 START WITH 20;
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_ADDRESS TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_ADDRESS for TB_ADDRESS;

/*****************************************
				USER TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_USER;
CREATE TABLE TB_USER(
  ID_USER NUMBER NOT NULL,
  DS_PASSWORD VARCHAR2(160) NOT NULL,
  DS_NAME VARCHAR2(200) NOT NULL,
  DS_EMAIL VARCHAR2(100) NOT NULL,  
  DS_PHONENUMBER VARCHAR2(15) NOT NULL,
  BL_USER_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_USER_ACTIVE IN (0,1)), 
  BL_USER_DELETED NUMBER(1,0) DEFAULT 0 NOT NULL, 
  XID_RETREAT NUMBER NOT NULL
);

ALTER TABLE TB_USER ADD CONSTRAINT PK_TB_USER PRIMARY KEY ( ID_USER );
ALTER TABLE TB_USER ADD CONSTRAINT FK_USER_RETREAT FOREIGN KEY ( XID_RETREAT ) REFERENCES TB_RETREAT (ID_RETREAT);

DROP SEQUENCE IF EXISTS SEQ_TB_USER;
CREATE SEQUENCE  SEQ_TB_USER  INCREMENT BY 1 START WITH 20;
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_USER TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_USER for TB_USER;


DROP TABLE IF EXISTS TB_USER_AUD;
CREATE TABLE TB_USER_AUD(
  ID_USER NUMBER NOT NULL,
  REV integer not null,
  REVTYPE tinyint,
  DS_NAME VARCHAR2(200) NOT NULL,
  DS_EMAIL VARCHAR2(100) NOT NULL ,
  DS_CPF VARCHAR2(14) NOT NULL ,
  DS_PHONENUMBER VARCHAR2(15) NOT NULL,
  BL_USER_ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL  CHECK (BL_USER_ACTIVE IN (0,1)), 
  BL_USER_DELETED NUMBER(1,0) DEFAULT 0 NOT NULL 
);

ALTER TABLE TB_USER_AUD ADD CONSTRAINT PK_TB_USER_AUD PRIMARY KEY ( ID_USER, REV );
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_USER TO BCKOFF_APP;


create table TB_REVINFO (
        ID_REVINFO NUMBER ,
        NU_REVTSTMP bigint,
        DS_USERNAME VARCHAR(200)
 );
CREATE SEQUENCE  SEQ_TB_REVINFO  INCREMENT BY 1 START WITH 20;
 
/*****************************************
			PROFILE TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_PROFILE;
CREATE TABLE TB_PROFILE(
    ID_PROFILE NUMBER NOT NULL , 
	DS_PROFILE VARCHAR2(50) NOT NULL,
	DS_DESCRIPTION VARCHAR2(100) NOT NULL
);

ALTER TABLE TB_PROFILE ADD CONSTRAINT PK_PROFILE PRIMARY KEY ( ID_PROFILE );
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_PROFILE TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_PROFILE for TB_PROFILE;

/*****************************************
			USER ROLE SEQ				
*****************************************/
DROP SEQUENCE IF EXISTS SEQ_TB_PROFILE;
CREATE SEQUENCE SEQ_TB_PROFILE INCREMENT BY 1 START WITH 20;


/*****************************************
			USER PROFILE TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_USER_PROFILE;
CREATE TABLE TB_USER_PROFILE(
	XID_USER NUMBER NOT NULL , 
	XID_PROFILE NUMBER NOT NULL
);

ALTER TABLE TB_USER_PROFILE ADD CONSTRAINT PK_USER_PROFILE PRIMARY KEY ( XID_USER, XID_PROFILE );
ALTER TABLE TB_USER_PROFILE ADD CONSTRAINT FK_USER_PROFILE FOREIGN KEY ( XID_USER ) REFERENCES TB_USER (ID_USER);
ALTER TABLE TB_USER_PROFILE ADD CONSTRAINT FK_PROFILE_USER_PROFILE FOREIGN KEY ( XID_PROFILE ) REFERENCES TB_PROFILE (ID_PROFILE);
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_USER_PROFILE TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_USER_PROFILE for TB_USER_PROFILE;

/*****************************************
			USER PROFILE SEQ				
*****************************************/
DROP SEQUENCE IF EXISTS SEQ_TB_USER_PROFILE;
CREATE SEQUENCE SEQ_TB_USER_PROFILE INCREMENT BY 1 START WITH 20;
-- GRANT
--GRANT SELECT, ALTER ON SEQ_TB_USER_PROFILE TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.SEQ_USER_PROFILE_ID for SEQ_USER_PROFILE_ID;

/*****************************************
			ROLE TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_ROLE;
CREATE TABLE TB_ROLE(
    ID_ROLE NUMBER NOT NULL , 
	DS_ROLE VARCHAR2(50) NOT NULL,
	DS_DESCRIPTION VARCHAR2(100) NOT NULL,
);

ALTER TABLE TB_ROLE ADD CONSTRAINT PK_ROLE PRIMARY KEY ( ID_ROLE );
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_ROLE TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_USER_ROLE for TB_USER_ROLE;

/*****************************************
			ROLE SEQ				
*****************************************/
DROP SEQUENCE IF EXISTS SEQ_TB_ROLE;
CREATE SEQUENCE SEQ_TB_ROLE INCREMENT BY 1 START WITH 20;

/*****************************************
			PROFILE ROLE TABLE				
*****************************************/
DROP TABLE IF EXISTS TB_PROFILE_ROLE;
CREATE TABLE TB_PROFILE_ROLE(
	XID_PROFILE NUMBER NOT NULL , 
	XID_ROLE NUMBER NOT NULL
);

ALTER TABLE TB_PROFILE_ROLE ADD CONSTRAINT PK_PROFILE_ROLE PRIMARY KEY ( XID_PROFILE, XID_ROLE );
ALTER TABLE TB_PROFILE_ROLE ADD CONSTRAINT FK_ROLE FOREIGN KEY ( XID_ROLE ) REFERENCES TB_ROLE (ID_ROLE);
ALTER TABLE TB_PROFILE_ROLE ADD CONSTRAINT FK_PROFILE_PROFILE_ROL FOREIGN KEY ( XID_PROFILE ) REFERENCES TB_PROFILE (ID_PROFILE);
-- GRANT
GRANT SELECT, INSERT, UPDATE, DELETE ON TB_PROFILE_ROLE TO BCKOFF_APP;
-- SYNONYM
--CREATE SYNONYM BCKOFF_APP.TB_PROFILE_ROLE for TB_PROFILE_ROLE;

/*****************************************
				PAGE TABLE				
*****************************************/

DROP TABLE IF EXISTS TB_PAGE;
create table TB_PAGE(
  ID_PAGE NUMBER NOT NULL,
  DS_TITLE VARCHAR2(200) NOT NULL,
  DS_PATH VARCHAR2(100) NOT NULL,
  DS_ACTION VARCHAR2(100) NOT NULL,
  XID_PARENT NUMBER
);
alter table TB_PAGE add constraint PK_TB_PAGE primary key (ID_PAGE);
alter table TB_PAGE add constraint FK_TB_PAGE foreign key(XID_PARENT) references TB_PAGE(ID_PAGE);
alter table TB_PAGE  add constraint UK_DS_PATH unique (DS_PATH);

DROP SEQUENCE IF EXISTS SEQ_TB_PAGE;
CREATE SEQUENCE  SEQ_TB_PAGE INCREMENT BY 1 START WITH 20 ;

/*****************************************
				AUDIT TABLE				
*****************************************/

DROP TABLE IF EXISTS TB_AUDIT;
create table TB_AUDIT(
  ID_AUDIT NUMBER NOT NULL,
  XID_PAGE NUMBER NOT NULL,
  XID_USER NUMBER NOT NULL,
  NM_OPERATION_TYPE NUMBER(2,0) NOT NULL, 
  DT_ACTION_TIME TIMESTAMP NOT NULL,
  DS_HOSTNAME VARCHAR2(200) NOT NULL,
  DS_IP VARCHAR2(20) NOT NULL
);

alter table TB_AUDIT add constraint PK_TB_AUDIT primary key (ID_AUDIT);

alter table TB_AUDIT add constraint FK_TB_USER_2 foreign key(XID_USER) references TB_USER(ID_USER);
alter table TB_AUDIT add constraint FK_TB_PAGE_2 foreign key(XID_PAGE) references TB_PAGE(ID_PAGE);

DROP SEQUENCE IF EXISTS SEQ_TB_AUDIT;
CREATE SEQUENCE  SEQ_TB_AUDIT  INCREMENT BY 1 START WITH 20 ;

/*****************************************
          TB_USER_REQUEST_ACCESS				
*****************************************/
DROP TABLE IF EXISTS TB_USER_REQUEST_ACCESS;
CREATE TABLE TB_USER_REQUEST_ACCESS (
  ID_USER_REQUEST_ACCESSS NUMBER(10) NOT NULL PRIMARY KEY,
  XID_USER  NUMBER(8) NOT NULL,
  DS_REQUEST_CODE VARCHAR2(40) NOT NULL,
  DT_REQUEST_VALID TIMESTAMP(6) NOT NULL
);

ALTER TABLE TB_USER_REQUEST_ACCESS ADD CONSTRAINT FK_TB_USER_REQUEST_ACCESS_USER FOREIGN KEY (XID_USER)
      REFERENCES TB_USER (ID_USER);
      
ALTER TABLE TB_USER_REQUEST_ACCESS ADD CONSTRAINT UK_TB_USER_REQUEST_ACCESS_CODE UNIQUE (DS_REQUEST_CODE);

DROP SEQUENCE IF EXISTS SEQ_TB_USER_REQUEST_ACCESS;
CREATE SEQUENCE SEQ_TB_USER_REQUEST_ACCESS INCREMENT BY 1 START WITH 20;

