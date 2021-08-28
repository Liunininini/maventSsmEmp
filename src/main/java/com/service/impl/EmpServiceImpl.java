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
		/**Ա����� **/
		int code=daoservice.getEmpMapper().save(emp);
		if(code>0){
			/**��ȡ Ա���� eid**/
			int maxeid=daoservice.getEmpMapper().findMaxEid();
			/**н�����**/
			Salary salary=new Salary(maxeid,emp.getEmoney());
			daoservice.getSalaryMapper().save(salary);
			/**�������**/
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
		//��ɾ���ӱ�(н��,����,)
		daoservice.getSalaryMapper().delById(eid);
		daoservice.getEmpWelfareMapper().delById(eid);
		//ɾ������
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
		//�޸�Ա��
		int code=daoservice.getEmpMapper().update(emp);
		System.out.println("code:"+code);
		if(code>0){
			
			if(emp.getEmoney()!=null&&!emp.getEmoney().equals("")){
				//�޸Ĺ���
				Salary salary=daoservice.getSalaryMapper().findById(emp.getEid());
				salary.setEmoney(emp.getEmoney());
				daoservice.getSalaryMapper().update(salary);			
			}
				//�޸ĸ���			
					//ɾ������
				daoservice.getEmpWelfareMapper().delById(emp.getEid());
					//��Ӹ���
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
