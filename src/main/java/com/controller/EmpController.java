package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.po.Welfare;
import com.util.AjAxUtils;
import com.util.BizServiceUtil;



@Controller
public class EmpController {
	@Resource(name="BizService")
    private BizServiceUtil bizService;

	public BizServiceUtil getBizService() {
		return bizService;
	}

	public void setBizService(BizServiceUtil bizService) {
		this.bizService = bizService;
	}
	
	/**���� �Ͳ��Ų�ѯ**/
@RequestMapping(value="doinit_Emp.do")
 public String doinit(HttpServletRequest request,HttpServletResponse response){
	Map<String, Object> map=new HashMap<String,Object>();
	List<Dep> lsdep=bizService.getDepService().findAll();
	List<Welfare> lswf=bizService.getWelfareService().findAll();
	map.put("lsdep", lsdep);
	map.put("lswf", lswf);
	   PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
	  //����ѯ�������ת��Ϊjson�ַ���
	  	String jsonstr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
	  	System.out.println("json:"+jsonstr);
	  	//��������ִ�н����json�ַ������ص�ǰ̨
	  	AjAxUtils.printString(response, jsonstr);
	return null;
 }

	/**Ա�����**/
@RequestMapping(value="save_Emp.do")
public String save(HttpServletRequest request,HttpServletResponse response ,Emp emp){
	System.out.println("�ɹ�");
	
	
	System.out.println("��ӷ���...."+emp.toString());  
	//��վ��·��
	String realpath=request.getRealPath("/");
	System.out.println("��վ��·��:"+realpath);
	//*****************�ļ��ϴ�begin*******************//
	//��ȡ�ϴ���Ƭ����
	MultipartFile multipartFile=emp.getPic();
	if(multipartFile!=null&&!multipartFile.isEmpty()){
		//��ȡ�ϴ��ļ�����
		String fname=multipartFile.getOriginalFilename();
		//����
		if(fname.lastIndexOf(".")!=-1){
			String ext=fname.substring(fname.lastIndexOf("."));
			if(ext.equalsIgnoreCase(".jpg")){
				String newfname=new Date().getTime()+ext;
				//�����ļ�����ָ���ļ�·��
				File dostFile=new File(realpath+"/uppic/"+newfname);
				try {
					//�ϴ�(�����󴫵ݵ��ļ����ݸ���һ�ݵ��ղ����ɵ��ļ���)
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),dostFile);
					emp.setPhoto(newfname);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//*****************�ļ��ϴ�end*******************//
	boolean flag=bizService.getEmpService().save(emp);
	  if(flag){
		  System.out.println("��ӳɹ�");
		  return "redirect:stulist.jsp";
	  }else{
		  System.out.println("���ʧ��");
	  }
	return null;
}


		/**Ա��չʾ**/
@RequestMapping(value="findAll_Emp.do")
public String findAll(HttpServletRequest request,HttpServletResponse response ,Integer page,Integer rows){
	System.out.println("Page:"+page);
	System.out.println("Rows:"+rows);
	PageBean pb=new PageBean();
	page=page==null||page<1?pb.getPage():page;
	rows=rows==null||rows<1?pb.getRows():rows;
	if(rows>20)rows=20;
	int maxRows=bizService.getEmpService().findMaxRows();
	pb.setPage(page);
	pb.setRows(rows);
	
	
	List<Emp> lsemp= bizService.getEmpService().findAll(page, rows);
	Map<String,Object> map=new HashMap<>();
	map.put("page", page);
	map.put("rows", lsemp);
	map.put("total", maxRows);
	PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
	//����ѯ�������ת��Ϊjson�ַ���
	String jsonstr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
	System.out.println("json:"+jsonstr);
		  	//��������ִ�н����json�ַ������ص�ǰ̨
	AjAxUtils.printString(response, jsonstr);
		
	return null;
}

	/**Ա��ɾ��**/
@RequestMapping(value="delById_Emp.do")
public String delById(HttpServletRequest request,HttpServletResponse response ,Integer eid){
	System.out.println("ɾ������");
	boolean flag=bizService.getEmpService().delById(eid);
	
	
	
	/**��Ƭɾ��**/
/*	String oldphoto=bizService.getEmpService().findById(eid).getPhoto();
	String realpath=request.getRealPath("/");
	if(flag){
		File oldfile=new File(realpath+"/uppic/"+oldphoto);
		if(oldfile!=null&&!oldphoto.equals("default.jpg")){
			System.out.println("��ͼƬ");
			oldfile.delete();
		}
	}	*/
	/**��Ƭɾ��end**/	
	  if(flag){
		  System.out.println("ɾ���ɹ�");
		  AjAxUtils.printString(response, 1+"");
	  }else{
		  System.out.println("ɾ��ʧ��");
		  AjAxUtils.printString(response, 0+"");
	  }
	
	return null;
	
}
	/**������ѯ**/
@RequestMapping(value="findByid_Emp.do")
public String findByid(HttpServletRequest request,HttpServletResponse response ,Integer eid){
	System.out.println("��ѯ��������");
	Emp oldemp=bizService.getEmpService().findById(eid);
	System.out.println(oldemp.toString());
	PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
	//����ѯ�������ת��Ϊjson�ַ���
	String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
	System.out.println("json:"+jsonstr);
			//��������ִ�н����json�ַ������ص�ǰ̨
	AjAxUtils.printString(response, jsonstr);
		
	return null;

}
	/**�޸ĵ���**/
@RequestMapping(value="update_Emp.do")
public String update(HttpServletRequest request,HttpServletResponse response ,Emp emp){
	System.out.println("�޸ĵ���");
	System.out.println(emp.toString());
	System.out.println("ͼƬ:" +emp.getPhoto());
	//��վ��·��
	String realpath=request.getRealPath("/");
	System.out.println("��վ��·��:"+realpath);
	//*****************�ļ��ϴ�begin*******************//
	//��ȡ�ϴ���Ƭ����
	MultipartFile multipartFile=emp.getPic();
	if(multipartFile!=null&&!multipartFile.isEmpty()){
		System.out.println("��ͼƬ");
		//��ȡ�ϴ��ļ�����
		String fname=multipartFile.getOriginalFilename();
		//����
		if(fname.lastIndexOf(".")!=-1){
			String ext=fname.substring(fname.lastIndexOf("."));
			if(ext.equalsIgnoreCase(".jpg")){
				String newfname=new Date().getTime()+ext;
				//�����ļ�����ָ���ļ�·��
				File dostFile=new File(realpath+"/uppic/"+newfname);
				try {
					//�ϴ�(�����󴫵ݵ��ļ����ݸ���һ�ݵ��ղ����ɵ��ļ���)
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),dostFile);
					emp.setPhoto(newfname);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ɾ��ԭ����Ƭ����
				
			}
		}
	}		
	//*****************�ļ��ϴ�end*******************//
	boolean flag=bizService.getEmpService().update(emp);
	  if(flag){
	  	//��������ִ�н����json�ַ������ص�ǰ̨
	  	AjAxUtils.printString(response, 1+"");
	  }else{
		AjAxUtils.printString(response, 0+""); 
	  }
		
	return null;	
}




/**������ѯ**/
@RequestMapping(value="findDatail_Emp.do")
public String findDatail(HttpServletRequest request,HttpServletResponse response ,Integer eid){
System.out.println("��ѯ��������");
Emp oldemp=bizService.getEmpService().findById(eid);
PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
//����ѯ�������ת��Ϊjson�ַ���
	String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
	System.out.println("json:"+jsonstr);
	//��������ִ�н����json�ַ������ص�ǰ̨
	AjAxUtils.printString(response, jsonstr);
return null;

}


/**�������**/
@RequestMapping(value="save_Dep.do")
public String findDatail(HttpServletRequest request,HttpServletResponse response ,Dep dep){
	System.out.println("�ɹ�");
	
	
	System.out.println("��ӷ���...."+dep.toString());  
	boolean flag=bizService.getDepService().save(dep);
	  if(flag){
		 	AjAxUtils.printString(response, 1+"");
	  }else{
		AjAxUtils.printString(response, 0+""); 
	  }
	  
	return null;	
}

}
