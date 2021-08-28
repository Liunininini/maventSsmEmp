package com.service;

import java.util.List;

import com.po.Emp;

public interface IEmpService {
	
	//员工添加
	public boolean save(Emp emp);
	//员工分页查询
	public List<Emp> findAll(int page, int rows);
	//最大记录数
	public int findMaxRows();
	//删除员工
	public boolean delById(Integer eid);
	//查询单个
	public Emp findById(Integer eid);
	//修改员工
	public boolean update(Emp emp);
}
