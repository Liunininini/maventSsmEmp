package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.po.Emp;

@Service("EmpMapper")
public interface IEmpMapper {
	//员工添加
	public int save(Emp emp);
	//员工最大编号
	public int findMaxEid();
	//展示分页信息
	public List<Emp> findAll(@Param(value = "page") Integer page, @Param(value = "rows") Integer rows);
	//员工最大记录数
	public int findMaxRows();
	//员工删除
	public int delById(Integer eid);
	//查询单个
	public Emp findById(Integer eid);
	//修改
	public int update(Emp emp);
	
	
}
