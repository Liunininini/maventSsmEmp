package com.util;

import com.alibaba.fastjson.serializer.PropertyFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AjAxUtils {
	/**
	 * ������Ӧ���ͻ���
	 * */
	public static void printString(HttpServletResponse response,String s){
		response.setCharacterEncoding("utf-8");
		PrintWriter out=null;
		
		try {
			out=response.getWriter();
			out.print(s);//�����Ӧ
			
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * ��������
	 * */
	public static PropertyFilter filterProperts(final String...propNames){
		PropertyFilter propertyFilter=new PropertyFilter() {
			
			public boolean apply(Object arg0, String propertyName, Object arg2) {
				for (String pname : propNames) {
					if(propertyName.equals(pname)){
						return false;//����
					}
				}
				return true;
			}
		};
		
		return propertyFilter;
	}
	
	
	
	
	
	
}