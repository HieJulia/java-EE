package com.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.Entity.AreaEntity;


public interface AreaDaoImpl {
	//查找所有地区
	@Select("select * from area")
	List<AreaEntity> getAllArea()throws SQLException;
	
	//根据ID查找地区
	@Select("select * from area where id=#{para}")
	AreaEntity getAreaByID(int id)throws SQLException;
	
	//根据市查找地区
	@Select("select * from area where state=#{para}")
	List<AreaEntity> getAreaByState(String state)throws SQLException;
	
	//根据地区名称查地区
	@Select("select * from area where areaName=#{para}")
	AreaEntity getAreaByName(String name);

	// interface : selection from where 
}
