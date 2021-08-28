package com.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mapper.IDepMapper;
import com.mapper.IEmpMapper;
import com.mapper.IEmpWelfareMapper;
import com.mapper.ISalaryMapper;
import com.mapper.IWelfareMapper;

@Service("DaoService")
public class DaoServiceUtil {
   @Resource(name="DepDAO")
   private IDepMapper depMapper;
   @Resource(name="WelfareDAO")
   private IWelfareMapper welfareMapper;
   @Resource(name="EmpMapper")
   private IEmpMapper empMapper;
   @Resource(name="EmpWelfareDao")
   private IEmpWelfareMapper empWelfareMapper;
   @Resource(name="SalaryDao")
   private ISalaryMapper salaryMapper;
public IDepMapper getDepMapper() {
	return depMapper;
}
public void setDepMapper(IDepMapper depMapper) {
	this.depMapper = depMapper;
}
public IWelfareMapper getWelfareMapper() {
	return welfareMapper;
}
public void setWelfareMapper(IWelfareMapper welfareMapper) {
	this.welfareMapper = welfareMapper;
}
public IEmpMapper getEmpMapper() {
	return empMapper;
}
public void setEmpMapper(IEmpMapper empMapper) {
	this.empMapper = empMapper;
}
public IEmpWelfareMapper getEmpWelfareMapper() {
	return empWelfareMapper;
}
public void setEmpWelfareMapper(IEmpWelfareMapper empWelfareMapper) {
	this.empWelfareMapper = empWelfareMapper;
}
public ISalaryMapper getSalaryMapper() {
	return salaryMapper;
}
public void setSalaryMapper(ISalaryMapper salaryMapper) {
	this.salaryMapper = salaryMapper;
}
   
}
