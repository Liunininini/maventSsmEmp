package com.service;

import java.util.List;

import com.po.Emp;

public interface IEmpService {
	
	//Ա�����
	public boolean save(Emp emp);
	//Ա����ҳ��ѯ
	public List<Emp> findAll(int page, int rows);
	//����¼��
	public int findMaxRows();
	//ɾ��Ա��
	public boolean delById(Integer eid);
	//��ѯ����
	public Emp findById(Integer eid);
	//�޸�Ա��
	public boolean update(Emp emp);
}
