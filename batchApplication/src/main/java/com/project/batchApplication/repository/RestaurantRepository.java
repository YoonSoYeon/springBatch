package com.project.batchApplication.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.type.SqlTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.batchApplication.model.Restaurant;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {

	private final JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Transactional
	public void saveAll(List<? extends Restaurant> list) {
		String sql = "INSERT INTO restaurant (restaurant_id, address, approval_date, area,"
				+ " building_owner_name, business_name, business_type, cancel_date, closing_date, "
				+ " closure_end_date, closure_start_date, deposit, detail_operation_code, detail_operation_name, "
				+ " facotry_office_employees, facotry_sales_employess, factory_production_employees, "
				+ " female, grade, group_code, head_office_employees, homepage, industry_name, is_multiple_users, "
				+ " last_modify_time, male, managerment_number, operation_code, opertaion_name, phone_number, "
				+ " positionx, positiony, rent, reopening_date, road_address, road_zip_code, "
				+ " service_id, service_name, surroud_operation_name, total_employees, total_size, "
				+ " traditioal_food, tradition_number, update_category, update_date, water_name, zip_code) "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, (List<Restaurant>) list, list.size(), (PreparedStatement ps, Restaurant rs) -> {
			ps.setLong(1, rs.getRestaurantId());
			ps.setString(2, rs.getAddress());
			ps.setString(3, rs.getApprovalDate());
			ps.setObject(4, rs.getArea(), SqlTypes.DOUBLE);
			ps.setString(5, rs.getBuildingOwnerName());
			ps.setString(6, rs.getBusinessName());
			ps.setString(7, rs.getBusinessType());
			ps.setString(8, rs.getCancelDate());
			ps.setString(9, rs.getClosingDate());
			ps.setString(10, rs.getClosureEndDate());
			ps.setString(11, rs.getClosureStartDate());
			ps.setObject(12, rs.getDeposit(), SqlTypes.DOUBLE);
			ps.setString(13, rs.getDetailOperationCode());
			ps.setString(14, rs.getDetailOperationName());
			ps.setObject(15, rs.getFacotryOfficeEmployees(), SqlTypes.INTEGER);
			ps.setObject(16, rs.getFacotrySalesEmployess(), SqlTypes.INTEGER);
			ps.setObject(17, rs.getFactoryProductionEmployees(), SqlTypes.INTEGER);
			ps.setObject(18, rs.getFemale(), SqlTypes.INTEGER);
			ps.setString(19, rs.getGrade());
			ps.setString(20, rs.getGroupCode());
			ps.setObject(21, rs.getHeadOfficeEmployees(), SqlTypes.INTEGER);
			ps.setString(22, rs.getHomepage());
			ps.setString(23, rs.getIndustryName());
			ps.setString(24, rs.getIsMultipleUsers());
			ps.setString(25, rs.getLastModifyTime());
			ps.setObject(26, rs.getMale(), SqlTypes.INTEGER);
			ps.setString(27, rs.getManagermentNumber());
			ps.setString(28, rs.getOperationCode());
			ps.setString(29, rs.getOpertaionName());
			ps.setString(30, rs.getPhoneNumber());
			ps.setObject(31, rs.getPositionX(), SqlTypes.DOUBLE);
			ps.setObject(32, rs.getPositionY(), SqlTypes.DOUBLE);
			ps.setObject(33, rs.getRent(), SqlTypes.DOUBLE);
			ps.setString(34, rs.getReopeningDate());
			ps.setString(35, rs.getRoadAddress());
			ps.setString(36, rs.getRoadZipCode());
			ps.setString(37, rs.getServiceId());
			ps.setString(38, rs.getServiceName());
			ps.setString(39, rs.getSurroudOperationName());
			ps.setObject(40, rs.getTotalEmployees(), SqlTypes.INTEGER);
			ps.setObject(41, rs.getTotalSize(), SqlTypes.DOUBLE);
			ps.setString(42, rs.getTraditioalFood());
			ps.setString(43, rs.getTraditionNumber());
			ps.setString(44, rs.getUpdateCategory());
			ps.setString(45, rs.getUpdateDate());
			ps.setString(46, rs.getWaterName());
			ps.setString(47, rs.getZipCode());
		});
	}
}
