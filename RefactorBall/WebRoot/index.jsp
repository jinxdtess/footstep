<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <title>用户登录</title>
	
     <link href="alone.css" rel="stylesheet" type="text/css" />
     <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
	  <script type="text/javascript">
	      $(function(){
		    	$('#valid').bind('click',function(){
		    	   $(this).attr('src','ball/createImage?rand='+new Date().getTime());
		    	});
		    	
		    	
		    	
		    	
		    	$("#username").blur(function(){
		    		
		    		if($(this).val()==null || $(this).val()==""){
		    			$(this).val("--请输入用户名--").css("color","lightgray");
		    		}
		    	});
		    	
		    	$("#username").focus(function(){
		    		$(this).val("");
		    	});
		    	$("#password").focus(function(){
		    		$(this).val("");
		    	});
		    	$("#validation").focus(function(){
		    		$(this).val("");
		    	});
		    	
		    	$("#password").blur(function(){
		    		
		    		if($(this).val()==null || $(this).val()==""){
		    			$(this).val("<span>--请输入密码--</span>").css("color","lightgray");
		    		}
		    	});
		    	
		    	
		    		
		    	
		    	
		    	$("#submitname").click(function(){
		    		
		    		$.get(
		    			"<%=request.getContextPath()%>/ball/login",
		    		{
		    		  'validation':$("#validation").val(),	
		    		  'username':$("#username").val(),
		    		  'password':$("#password").val()
		    		},
		    		function(data,status){
		    			if(status=="success"){
		    				switch(data.flag){
		    				case 1:
		    					alert("用户名错误！");
		    					break;
		    				case 2:
		    					alert("密码错误！");
		    					break;
		    				case 3:
		    					alert('验证码错误！');
		    					break;
		    				case 0:
		    					$("#msg").text("");
		    					alert('通过验证！');
		    					break;
		    				}
		    			}
		    		}
		    		
		    		);
		    	});
		    	
		    	
		    	
		    	
		    	
		    	
	    	  })
	    	  
	       
	  </script>
     </head>
     <body>
        <center>
           <div class="banner">
                    <ul>
                         <li><a href="#" class="current">首页</a></li>
                         <li><a href="/GoodLuck/ball/toAdd">添加新号</a></li>
                         <li><a href="/springMVC/ball/newBall" target="_blank">新号信息</a></li>
                         <li><a href="/GoodLuck/ball/toHundreds">博览群号</a></li>
                         <li><a href="/springMVC/ball/analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="/springMVC/ball/toSelect" target="_blank">幸运选号</a></li>
                 　                                   </ul>
           </div>
           <h1>登录页面</h1>
           <p id="msg" style="color:red"><strong><%=(session.getAttribute("msg")==null)?"":session.getAttribute("msg") %></strong></p>
	       <form method="post" action="../ball/login" onSubmit="return false">
	           
	            <table align="center">
	               <tr>
	                  <td>请输入用户名：</td>
	                  <td><input type="text" name="username" id="username"/></td>
	                  <td></td>
	               </tr>
	               <tr>
	                  <td>请输入密码：</td>
	                  <td><input type="password" name="password" id="password"/></td>
	                  <td></td>
	               </tr> 
	               <tr>
	                  <td><img  id="valid" title="点击更换" src="ball/createImage" /></td>
	                  <td><input type="text" name="validation" id="validation"/></td>
	                  <td></td>
	               </tr>
	               <tr>
	                  <td><input type="button" id="submitname" value="登录"/></td>
	                  <td><input type="reset"  value="重置"/></td>
	                  <td></td>
	               </tr>
	               <tr>
	                  <td>请注册：</td>
	                  <td><input type="button"  value="注册"/></td>
	                  <td></td>
	               </tr>
	               
	            </table>
	       </form> 
    </center>
  </body>
  
</html>
