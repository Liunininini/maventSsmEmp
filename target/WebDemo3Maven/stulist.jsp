<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>员工展示页面</title>
<!--easyui支持引入  -->
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css"/>
<script type="text/javascript" src="easyui/jquery-1.9.1.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
/**********展示列表**********/
$(function(){
	//alert('提示')	
	
	$("#ffemp").hide();
	$('#win').window('close');
	$("#ffdep").hide();
	$("#fdep").hide();
	
	
	
	$('#dg').datagrid({  		
	    url:'findAll_Emp.do', 
	    pagination:true,
	    singleSelect:true,
	    pageSize:5,
	    pageList:[5,10,15,20],
	    columns:[[    
	        {field:'eid',title:'员工编号',width:50},    
	        {field:'ename',title:'姓名',width:100},    
	        {field:'sex',title:'性别',width:100},    
	        {field:'address',title:'地址',width:100},    
	        {field:'sdate',title:'生日',width:100},    
	        {field:'photo',title:'照片',width:100,
	        	formatter: function(value,row,index){
	        		return '<a  href=uppic/'+row.photo+'><img   src=uppic/'+row.photo+'  width=100 height=100></a>'	        		
				
	       		}
	        },
	        {field:'depname',title:'部门',width:100,align:'right'},
	        {field:'opt',title:'操作',width:150,
	        	formatter: function(value,row,index){
	        		var bt1="<input type='button' onclick=delById("+row.eid+") value='删除'/>";
	        		var bt2="<input type='button' onclick=findById("+row.eid+") value='编辑'/>"
	        		var bt3="<input type='button' onclick=editById("+row.eid+") value='详情'/>"
	        		return bt1+bt2+bt3;	        		
	        		
	       		}
	        }
	    ]]    
	});  
	
});
/**展示部门和下拉列表**/
$(function(){
	//获取后台传递的页面初始化数据
	$.getJSON("doinit_Emp.do",function(map){
		var lswf=map.lswf;
		var lsdep=map.lsdep;
		//处理复选框
		for(var i=0;i<lswf.length;i++){
			var wf=lswf[i];
			$("#wf").append("<input type='checkbox' id='wids' name='wids' value='"+wf.wid+"'/>"+wf.wname)
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
});

/**********展示end**********/

/**********删除*************/

function delById(eid){
	   
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.getJSON("delById_Emp.do?eid="+eid,function(code){
	    		if(code=='1'){
					$.messager.alert('提示','删除成功!!!');
					$('#dg').datagrid('reload');    // 重新载入当前页面数据  
				}else{
					$.messager.alert('提示','删除失败!!!');
				}
	    	})
	          
	    }    
	}); 		
}
/**********删除end**********/
/**********修改展示*************/
 
function findById(eid){ 
	
	alert('修改')	
	 $.getJSON("findByid_Emp.do?eid="+eid,function(oldemp){
		//先删除福利复选矿中的所有选中
			$(":checkbox[name='wids']").each(function(){
				$(this).prop("checked",false);
			});
			$('#ffemp').form('load',{
				'eid':oldemp.eid,
				'ename':oldemp.ename,
				'sex':oldemp.sex,
				'address':oldemp.address,
				'sdate':oldemp.sdate,
				'depid':oldemp.depid,
				'emoney':oldemp.emoney,
			});
			 //处理图片
            $("#myphoto").attr('src','uppic/'+oldemp.photo)
            $("#myhtml").attr('href','uppic/'+oldemp.photo)
            //处理复选框
            var wids=oldemp.wids;
            $(":checkbox[name='wids']").each(function(){
				for(var i=0;i<wids.length;i++){
					if($(this).val()==wids[i]){
						$(this).prop("checked",true);	
					}
				}
			});
		 		 		 
	 })
	 $("#ffemp").show();
	 
 }
 
/**********修改展示end**********/
/**********修改*************/
 $(function () {
	
	 
	 $("#btupdate").click(function(){
			$.messager.progress();	// 显示进度条
			$('#ffemp').form('submit', {
				url:'update_Emp.do',
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
						$.messager.alert('提示','修改成功!!!');
						$('#dg').datagrid('reload');    // 重新载入当前页面数据  
						
						$("#ffemp").hide();
					}else{
						$.messager.alert('提示','修改失败!!!');
						$('#dg').datagrid('reload');
					}
					$.messager.progress('close');	// 如果提交成功则隐藏进度条
				}
			});
	})
 })
/**********修改end**********/
/**********详细**********/
 function editById(eid){
	
	 $.getJSON("findDatail_Emp.do?eid="+eid,function(emp){
		 //赋值
		 $("#enametxt").html(emp.ename);
		 $("#sextxt").html(emp.sex);
		 $("#addresstxt").html(emp.address);
		 $("#sdatetxt").html(emp.sdate);
		 $("#phototxt").html(emp.photo);
		 $("#deptxt").html(emp.depname);
		 $("#emoneytxt").html(emp.emoney);
		 $("#eidtxt").html(emp.eid);
		 //获取福利
		  var lswf=emp.lswf;
		 var wnames=[];//福利名称数组
		 for(var i=0;i<lswf.length;i++){
			 var wf=lswf[i];
			 wnames.push(wf.wname);
		 }
		 var strwname=wnames.join(',');
		 $("#wftxt").html(strwname); 
		 $("#domyphoto").attr('src','uppic/'+emp.photo); 
		 $('#win').window('open');  // open a window 
	 });	
	}
/**********详细end**********/
/**********部门添加列表展示**********/
 $(function () {
	 $("#addDep").click(function(){
		 alert('部门添加')	;		 
		 $("#ffdep").show();
	 })
 })
 /**********部门添加列表展示end**********/
 
 
 /**********部门添加**********/
 $(function () {
	 $("#butaddDep").click(function(){		 
		$.messager.progress();	// 显示进度条
		$('#ffdep').form('submit', {
			url:'save_Dep.do',
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
			    $('#dg').datagrid('reload');    // 重新载入当前页面数据  
			}else{
				$.messager.alert('提示','保存失败!!!');
			}
			$.messager.progress('close');	// 如果提交成功则隐藏进度条			
			}
		
		 })
		 $("#ffdep").hide();
	 })
 })
 /**********部门添加end**********/

 

 
 /**********部门列表展示**********/
 $(function () {
	 $("#findAllDep").click(function(){
		 $.getJSON("doinit_Emp.do",function(map){
			  var lsdep=map.lsdep;
			 var head="<form id='bb' align='center'><table border='1px' width='550px' align='center'><tr><td colspan='2' align='center'>部门展示</td></tr>"
			 			+"<tr><td>部门编号</td><td>部门名称</td></tr>";		 
			 
			 var body="";
		 	 for(var i=0;i<lsdep.length;i++){
				  var dep=lsdep[i];
				 body+="<tr><td>"+dep.depid+"</td><td>"+dep.depname+"</td></tr>"
				
			 } 								
			 var tail="<tr><td colspan='2'><input type='button' id='aa'  value='确定' /></td></tr></table></form>"
			 $("#fdep").html(head+body+tail); 
			 alert('显示');
			 $("#fdep").show();
		
			
		 })
	 })
 })
 /**********部门列表展示end**********/
  /**********部门列表展示隐藏**********/
 $(function(){
	 $("#aa").click(function(){		
		 alert('隐藏'); 
	 })
  
})  
  /**********部门列表展示隐藏end**********/
</script>
</head>
<body>
<p align="center">员工列表</p>
<hr />
<table id="dg"></table>  
<hr>

<form action="update_Emp.do" name="ffemp" id="ffemp" method="post" enctype="multipart/form-data">
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
      <a  id="myhtml" href="uppic/default.jpg">
      <img id="myphoto"  alt="图片不存在" src="uppic/default.jpg" width="240px" height="260px">
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
     <input type="hidden" id="eid" name="eid" >
     
     <input type="button" id="btupdate" name="btupdate" value="修改">
     <input type="reset" id="btrest" name="btrest" value="取消">
     </td>
    </tr>
  </table>
</form>

<!--详情窗口  -->
<div id="win" class="easyui-window" title="员工详情" style="width:600px;height:400px"   
        data-options="iconCls:'icon-save',modal:true">   
       <table border="1px" width="550px" align="center">
     <tr>
     <td width="100px">姓名</td>
     <td width="200px">
     <span id="enametxt"></span>
     </td>
     <td rowspan="7">
      <img id="domyphoto" alt="图片不存在" src="uppic/default.jpg" width="240px" height="260px">
     </td>
    </tr>
     <tr>
     <td>性别</td>
     <td>
      <span id="sextxt"></span>
     </td>
    </tr>
     <tr>
     <td>地址</td>
     <td>
     <span id="addresstxt"></span>
     </td>
     
    </tr>
     <tr>
     <td>生日</td>
     <td>
     <span id="sdatetxt"></span>
     </td>
     
    </tr>
     <tr>
     <td>照片</td>
     <td>
     <span id="phototxt"></span>
     </td>
     
    </tr>
     <tr>
     <td>部门</td>
     <td>
     <span id="deptxt"></span>
     </td>
    
    </tr>
     <tr>
     <td>薪资</td>
     <td>
     <span id="emoneytxt"></span>
     </td>
    </tr>
     <tr>
     <td>福利</td>
     <td colspan="2">
     <span id="wftxt"></span>
     </td>
    </tr>
    <tr >
     <td>编号</td>
     <td colspan="2">
     <span id="eidtxt"></span>
     </td>
    </tr>
  </table>
</div>  

<a  id="addDep" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">部门添加</a> 
<a id="findAllDep" href="###" class="easyui-linkbutton" data-options="iconCls:'icon-blank'">部门查看</a>

<form action="save_Dep.do" name="ffdep" id="ffdep" method="post">
<table > 
<tr border="1px" width="550px" align="center" bordercolor="FFFFcc">
	<td colspan="2">部门添加</td>
</tr>
<tr>
<td>部门名称:</td>
<td><input type="text" id="depname" name="depname"></td>
</tr>
<tr border="1px" width="550px" align="center" bordercolor="FFFFcc">
<td colspan="2"> 
<input type="button" id="butaddDep" name="butaddDep" value="确定">
<input type="reset" value="取消">

</td>
</tr>
</table>

</form>


<div id="fdep"></div>

</body>
</html>