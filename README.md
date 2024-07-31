# CSV TO DB 프로젝트
Spring Batch를 이용하여 CSV 파일 형태의 대용량 데이터를 DB에 저장하는 프로젝트이다.

공공데이터 포털에서 제공해주는 "전국일반음식점표준데이터"를 DB에 저장하기 위해 음식점 엔티티가 필요하다.

이 프로젝트의 기술 스택은 다음과 같다.

## 기술 스택
본 프로젝트는 Spring boot 3.3.2와 MySql DB 기반으로 되어 있는 프로젝트입니다. 

- Java JDK 17
- Spring Boot 3.3.2 (Spring Framework 6.1.11)
- Spring Batch 5.1.2
- Spring Data JPA 3.3.2
- MySql 8.3.0
- Lombok 1.18.34
- Gradle 8.8

## 설치 및 실행

**Install**

```
$ git clone git@github.com:YoonSoYeon/springBatch.git

or

$ git clone https://github.com/YoonSoYeon/springBatch.git
```

**Run**

```
$ gradlew clean && gradlew build
$ java -jar ./build/libs/springBatch-0.0.1-SNAPSHOT.jar
```

application.properties에 mysql에 대한 접속 정보는 다음과 같이 설정되어 있다.

```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/batch?&rewriteBatchedStatements=true
spring.datasource.username=batch
spring.datasource.password=batch
```

rewriteBatchedStatements=true는 mysql에서 batchIntert를 하기 위해 필요한 옵션이다.

## DB ERD
![image](https://github.com/user-attachments/assets/a975108b-3d87-4632-87d2-c5e3a186d12a)

## DDL

파일 위치는 [DDL.SQL](https://github.com/YoonSoYeon/springBatch/blob/main/batchApplication/src/main/resources/DDL.SQL) 이다.

```sql

CREATE TABLE BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME DATETIME(6) NOT NULL,
	START_TIME DATETIME(6) DEFAULT NULL ,
	END_TIME DATETIME(6) DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME(6),
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	PARAMETER_NAME VARCHAR(100) NOT NULL ,
	PARAMETER_TYPE VARCHAR(100) NOT NULL ,
	PARAMETER_VALUE VARCHAR(2500) ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	CREATE_TIME DATETIME(6) NOT NULL,
	START_TIME DATETIME(6) DEFAULT NULL ,
	END_TIME DATETIME(6) DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME(6),
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);

CREATE TABLE `restaurant` (
  `area` double DEFAULT NULL,
  `deposit` double DEFAULT NULL,
  `facotry_office_employees` int DEFAULT NULL,
  `facotry_sales_employess` int DEFAULT NULL,
  `factory_production_employees` int DEFAULT NULL,
  `female` int DEFAULT NULL,
  `head_office_employees` int DEFAULT NULL,
  `male` int DEFAULT NULL,
  `positionx` double DEFAULT NULL,
  `positiony` double DEFAULT NULL,
  `rent` double DEFAULT NULL,
  `total_employees` int DEFAULT NULL,
  `total_size` double DEFAULT NULL,
  `restaurant_id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `approval_date` varchar(255) DEFAULT NULL,
  `building_owner_name` varchar(255) DEFAULT NULL,
  `business_name` varchar(255) DEFAULT NULL,
  `business_type` varchar(255) DEFAULT NULL,
  `cancel_date` varchar(255) DEFAULT NULL,
  `closing_date` varchar(255) DEFAULT NULL,
  `closure_end_date` varchar(255) DEFAULT NULL,
  `closure_start_date` varchar(255) DEFAULT NULL,
  `detail_operation_code` varchar(255) DEFAULT NULL,
  `detail_operation_name` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `group_code` varchar(255) DEFAULT NULL,
  `homepage` varchar(255) DEFAULT NULL,
  `industry_name` varchar(255) DEFAULT NULL,
  `is_multiple_users` varchar(255) DEFAULT NULL,
  `last_modify_time` varchar(255) DEFAULT NULL,
  `managerment_number` varchar(255) DEFAULT NULL,
  `operation_code` varchar(255) DEFAULT NULL,
  `opertaion_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `reopening_date` varchar(255) DEFAULT NULL,
  `road_address` varchar(255) DEFAULT NULL,
  `road_zip_code` varchar(255) DEFAULT NULL,
  `service_id` varchar(255) DEFAULT NULL,
  `service_name` varchar(255) DEFAULT NULL,
  `surroud_operation_name` varchar(255) DEFAULT NULL,
  `traditioal_food` varchar(255) DEFAULT NULL,
  `tradition_number` varchar(255) DEFAULT NULL,
  `update_category` varchar(255) DEFAULT NULL,
  `update_date` varchar(255) DEFAULT NULL,
  `water_name` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`restaurant_id`)
) ENGINE=InnoDB;

```
