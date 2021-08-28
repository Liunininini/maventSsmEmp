<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加页面</title>
<!--easyui支持引入  -->
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css"/>
<script type="text/javascript" src="easyui/jquery-1.9.1.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	
	var eid=$("#eid").val();
	//alert(eid);
	if(eid==null){
		$("#btupdate").hide();
	}
	
	//获取后台传递的页面初始化数据
	$.getJSON("doinit_Emp.do",function(map){
		var lswf=map.lswf;
		var lsdep=map.lsdep;
		//处理复选框
		for(var i=0;i<lswf.length;i++){
			var wf=lswf[i];
			$("#wf").append("<input type='checkbox' name='wids' value='"+wf.wid+"'/>"+wf.wname)
		}
		//处理下拉列表
		$('#cc').combobox({    
		    data:lsdep,    
		    valueField:'depid',    
		    textField:'depname',
		    value:1,
		    panelHeight:88
		});  

	});
	
	//$("#btupdate").attr('disabled',true);
});

/*********保存begin**************/
/*  $(function(){
	$("#btsave").click(function(){
		$.messager.progress();	// 显示进度条
		$('#ffemp').form('submit', {
			url:'save_Emp.do',
			onSubmit: function(){
				var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
				}
				return isValid;	// 返回false终止表单提交
			},
			//回调函数
			success: function(code){
				if(code=='1'){
					$.messager.alert('提示','保存成功!!!');
				}else{
					$.messager.alert('提示','保存失败!!!');
				}
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
			}
		});


	});
});  */
/*********保存end**************/
</script>
</head>
<body>
<p align="center">员工列表</p>
<hr />
<form action="save_Emp.do" name="ffemp" id="ffemp" method="post" enctype="multipart/form-data">
  <table border="1px" width="550px" align="center">
    <tr bgcolor="#FFFFCC" align="center">
     <td colspan="3">员工管理</td>
    </tr>
     <tr>
     <td>姓名</td>
     <td>
     <input type="text" id="ename" name="ename" class="easyui-validatebox" data-options="required:true">
     </td>
     <td rowspan="7">
      <a href="uppic/default.jpg">
      <img alt="图片不存在" src="uppic/default.jpg" width="240px" height="260px">
      </a>
     </td>
    </tr>
     <tr>
     <td>性别</td>
     <td>
      <input type="radio" id="sex" name="sex" value="男" checked="checked">男
      <input type="radio" id="sex" name="sex" value="女" >女
     </td>
    </tr>
     <tr>
     <td>地址</td>
     <td>
     <input type="text" id="address" name="address">
     </td>
     
    </tr>
     <tr>
     <td>生日</td>
     <td>
     <input type="date" id="sdate" name="sdate">
     </td>
     
    </tr>
     <tr>
     <td>照片</td>
     <td>
     <input type="file" id="pic" name="pic">
     </td>
     
    </tr>
     <tr>
     <td>部门</td>
     <td>
     <input type="text" id="cc" name="depid">
     </td>
    
    </tr>
     <tr>
     <td>薪资</td>
     <td>
     <input type="text" id="emoney" name="emoney" value="3000">
     </td>
    </tr>
     <tr>
     <td>福利</td>
     <td colspan="2">
     <span id="wf"></span>
     </td>
    </tr>
    <tr bgcolor="#FFFFCC" align="center" >
     <td colspan="3">
     <input type="submit" id="btsave" name="btsave" value="保存" >
    <input type="button" id="btupdate" name="btupdate" value="修改">
     <input type="reset" id="btrest" name="btrest" value="取消">
     </td>
    </tr>
  </table>
</form>
</body>
</html>