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

## 주요 기능
CSV TO DB 프로젝트의 주요 기능은 다음과 같다.

## DB ERD
![image](https://github.com/user-attachments/assets/a975108b-3d87-4632-87d2-c5e3a186d12a)


