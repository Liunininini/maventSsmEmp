package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.EmpWelfare;
import com.po.Welfare;

@Service("EmpWelfareDao")
public interface IEmpWelfareMapper {
	//福利添加
	public int save(EmpWelfare empWelfare);
	//福利删除
	public int delById(Integer eid);
	//福利查询
	public List<Welfare> findById(Integer eid);

}
