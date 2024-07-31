package com.project.batchApplication.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Restaurant {

	@Id
	private long restaurantId;
	private String serviceName;
	private String serviceId;
	private String groupCode;
	private String managermentNumber;
	private String approvalDate;
	private String cancelDate;
	private String operationCode;
	private String opertaionName;
	private String detailOperationCode;
	private String detailOperationName;
	private String closingDate;
	private String closureStartDate;
	private String closureEndDate;
	private String reopeningDate;
	private String phoneNumber;
	private Double area;
	private String zipCode;
	private String address;
	private String roadAddress;
	private String roadZipCode;
	private String businessName;
	private String lastModifyTime;
	private String updateCategory;
	private String updateDate;
	private String businessType;
	private Double positionX;
	private Double positionY;
	private String industryName;
	private Integer male;
	private Integer female;
	private String surroudOperationName;
	private String grade;
	private String waterName;
	private Integer totalEmployees;
	private Integer headOfficeEmployees;
	private Integer facotryOfficeEmployees;
	private Integer facotrySalesEmployess;
	private Integer factoryProductionEmployees;
	private String buildingOwnerName;
	private Double deposit;
	private Double rent;
	private String isMultipleUsers;
	private Double totalSize;
	private String traditionNumber;
	private String traditioalFood;
	private String homepage;

	public static List<String> getFieldNames() {
		Field[] declaredFields = Restaurant.class.getDeclaredFields();
		List<String> result = new ArrayList<>();
		for (Field declaredField : declaredFields) {
			result.add(declaredField.getName());
		}

		return result;
	}

}
