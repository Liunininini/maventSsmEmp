package com.mapper;

import org.springframework.stereotype.Service;


import com.po.Salary;

@Service("SalaryDao")
public interface ISalaryMapper {
	//н�����
	public int save(Salary salary);
	//н��ɾ��
	public int delById(Integer eid);
	//��ѯ����
	public Salary findById(Integer eid);
	//�޸�
	public int update(Salary salary);
}
