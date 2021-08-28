package com.service.impl;

import com.po.Dep;
import com.service.IDepService;
import com.util.DaoServiceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("DepBiz")
@Transactional
public class DepServiceImpl implements IDepService {
    @Resource(name="DaoService")
	private DaoServiceUtil daoservice;
	public DaoServiceUtil getDaoservice() {
		return daoservice;
	}
	public void setDaoservice(DaoServiceUtil daoservice) {
		this.daoservice = daoservice;
	}

	@Override
	public List<Dep> findAll() {
		// TODO Auto-generated method stub
		return daoservice.getDepMapper().findAll();
	}
	@Override
	public boolean save(Dep dep) {
		int flag=daoservice.getDepMapper().save(dep);
		if(flag>0){
			return true;
		}
		return false;
	}

}
