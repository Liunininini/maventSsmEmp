package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Emp;
import com.po.EmpWelfare;
import com.po.Salary;
import com.po.Welfare;
import com.service.IEmpService;
import com.util.DaoServiceUtil;
@Service("empBiz")
@Transactional
public class EmpServiceImpl implements IEmpService {
	@Resource(name="DaoService")
	private DaoServiceUtil daoservice;
	public DaoServiceUtil getDaoservice() {
		return daoservice;
	}
	public void setDaoservice(DaoServiceUtil daoservice) {
		this.daoservice = daoservice;
	}
	@Override
	public boolean save(Emp emp) {
		/**员工添加 **/
		int code=daoservice.getEmpMapper().save(emp);
		if(code>0){
			/**获取 员工的 eid**/
			int maxeid=daoservice.getEmpMapper().findMaxEid();
			/**薪资添加**/
			Salary salary=new Salary(maxeid,emp.getEmoney());
			daoservice.getSalaryMapper().save(salary);
			/**福利添加**/
			String [] wids=emp.getWids();
			for(int i=0;i<wids.length;i++){
				Integer wid=new Integer(wids[i]);
				EmpWelfare empWelfare=new EmpWelfare(maxeid,wid);
				daoservice.getEmpWelfareMapper().save(empWelfare);
			}
			return true;
		}
		
		return false;
	}
	@Override
	public List<Emp> findAll(int page, int rows) {
		// TODO Auto-generated method stub
		return daoservice.getEmpMapper().findAll(page, rows);
	}
	@Override
	public int findMaxRows() {
		// TODO Auto-generated method stub
		return daoservice.getEmpMapper().findMaxRows();
	}
	@Override
	public boolean delById(Integer eid) {
		//先删除从表(薪资,福利,)
		daoservice.getSalaryMapper().delById(eid);
		daoservice.getEmpWelfareMapper().delById(eid);
		//删除主表
		int code= daoservice.getEmpMapper().delById(eid);
		if(code>0){
			return true;
		}
		return false;
	}
	@Override
	public Emp findById(Integer eid) {
		Emp oldemp=daoservice.getEmpMapper().findById(eid);
		//System.out.println("aaaaaaaaaaaaa"+oldemp.toString());
		Salary oldsa=daoservice.getSalaryMapper().findById(eid);
		//System.out.println("bbbbbbbbbbbbb"+oldsa.toString());
		List<Welfare> oldlswf=daoservice.getEmpWelfareMapper().findById(eid);
		//System.out.println("ccccccccccccc"+oldlswf.toString());
		oldemp.setEmoney(oldsa.getEmoney());				
		
		String []wids=new String[oldlswf.size()];
		for(int i=0;i<wids.length;i++){
			Welfare wf=oldlswf.get(i);
			wids[i]=wf.getWid().toString();
		}
		
		oldemp.setLswf(oldlswf);
		
		
		return oldemp;
	}
	@Override
	public boolean update(Emp emp) {
		//修改员工
		int code=daoservice.getEmpMapper().update(emp);
		System.out.println("code:"+code);
		if(code>0){
			
			if(emp.getEmoney()!=null&&!emp.getEmoney().equals("")){
				//修改工资
				Salary salary=daoservice.getSalaryMapper().findById(emp.getEid());
				salary.setEmoney(emp.getEmoney());
				daoservice.getSalaryMapper().update(salary);			
			}
				//修改福利			
					//删除福利
				daoservice.getEmpWelfareMapper().delById(emp.getEid());
					//添加福利
				String [] wids=emp.getWids();
				for(int i=0;i<wids.length;i++){
					Integer wid=new Integer(wids[i]);
					EmpWelfare empWelfare=new EmpWelfare(emp.getEid(),wid);
					daoservice.getEmpWelfareMapper().save(empWelfare);
				}
					
			return true;
		}
		return false;
	}

}
