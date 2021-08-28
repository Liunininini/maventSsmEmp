package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.po.Emp;

@Service("EmpMapper")
public interface IEmpMapper {
	//Ա�����
	public int save(Emp emp);
	//Ա�������
	public int findMaxEid();
	//չʾ��ҳ��Ϣ
	public List<Emp> findAll(@Param(value = "page") Integer page, @Param(value = "rows") Integer rows);
	//Ա������¼��
	public int findMaxRows();
	//Ա��ɾ��
	public int delById(Integer eid);
	//��ѯ����
	public Emp findById(Integer eid);
	//�޸�
	public int update(Emp emp);
	
	
}
