package com.mapper;

import org.springframework.stereotype.Service;


import com.po.Salary;

@Service("SalaryDao")
public interface ISalaryMapper {
	//薪资添加
	public int save(Salary salary);
	//薪资删除
	public int delById(Integer eid);
	//查询单个
	public Salary findById(Integer eid);
	//修改
	public int update(Salary salary);
}
