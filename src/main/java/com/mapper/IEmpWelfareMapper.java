package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.EmpWelfare;
import com.po.Welfare;

@Service("EmpWelfareDao")
public interface IEmpWelfareMapper {
	//�������
	public int save(EmpWelfare empWelfare);
	//����ɾ��
	public int delById(Integer eid);
	//������ѯ
	public List<Welfare> findById(Integer eid);

}
