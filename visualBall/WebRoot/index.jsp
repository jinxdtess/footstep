<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
     <title>双色球首页</title>
     <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
     <script type="text/javascript">
           function go(){
        	  // alert(document.getElementById("num").value);
        	  location.href="/springMVC/ball/hundreds?num="+document.getElementById("num").value;
           };
           
           $(document).ready(function(){
        	   $("#showIt").click(function(){
        		   $("#content tr td").removeClass("current_date");
        		  
        		   $.post(
        			   "ball/index",
        			   $("#hello").serialize(),
        			   function(data,status){
        				    
        				   for(i=0;i<data.length;i++){
        					   
        					   var temp=data[i];
        					   $("#content tr td").eq(i).html(temp);
        				       if(temp==new Date().getDate()){
        				    	 $("#content tr td").eq(i).addClass("current_date");  
        				       }
        				   }
        			   }
        			 );
        			 
        	   });
                  
           });
           
           
           
           $(window).load(function(){
        	 var year= new Date().getFullYear();
        	 var month= new Date().getMonth();
        		   $.post(
        			   "ball/index",
        			   {"year":year,"month":month},
        			   function(data,status){
        				    
        				   for(i=0;i<data.length;i++){
        					   
        					   var temp=data[i];
        					   $("#content tr td").eq(i).html(temp);
        				       if(temp==new Date().getDate()){
        				    	 $("#content tr td").eq(i).addClass("current_date");  
        				       }
        				   }
        			   }
        			 );
        			 
        	   })
                  
         
           
           
           
           
     </script>
        <style type="text/css">
            .banner ul{overflow:hidden;}
			.banner li{float:left;height:30px;line-height:30px;text-align:center;width:80px;list-style:none;}
			.banner a{display:block;color:red;}
			.banner .current{background-color:#F00;color:white;}
			.banner a:hover{background-color:#CF3;color:#000;}
			table{text-align:center;width:168px;height:81px;}
			table th{width:15%;height:16px;background-color:#c0c0c0;}
			table td{width:15%;height:16px;background-color:#c0c0c0;}
			table td:hover{background-color:red;}
			.current_date{color:yellow;}
        </style>
     
     </head>
     <body>
     	<center>
     	     <div class="banner">
                    <ul>
                         <li><a href="#" class="current">首页</a></li>
                         <li><a href="/springMVC/ball/code">添加新号</a></li>
                         <li><a href="/springMVC/ball/newBall">新号信息</a></li>
                         <li><a href="/springMVC/ball/hoho">博览群号</a></li>
                         <li><a href="/springMVC/ball/analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="/springMVC/ball/toSelect">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
     	
     	      
     	
     	
        	<h1>双色球欢迎您</h1>
        	<form id="hello">
		        	<input type="text" name="year" />年
		           <select name="month">
		                <option></option>
		                <option value="0">一</option>
		                <option value="1">二</option>
		                <option value="2">三</option>
		                <option value="3">四</option>
		                <option value="4">五</option>
		                <option value="5">六</option>
		                <option value="6">七</option>
		                <option value="7">八</option>
		                <option value="8">九</option>
		                <option value="9">十</option>
		                <option value="10">十一</option>
		                <option value="11">十二</option>
		           </select>月
		           <input type="button" id="showIt" value="显示"/>
           </form>
        	
        	
        	
        	<table border="0">
        	   <thead>
	        	   <tr>
	                  <th>日</th>
	        	      <th>一</th>
	        	      <th>二</th>
	        	      <th>三</th>
	        	      <th>四</th>
	        	      <th>五</th>
	        	      <th>六</th>	        	   
		          </tr>
	        	 </thead>
	        	 <tbody id="content">
	        	   <tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	        	   <tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	        	   <tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	        	   <tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	        	   <tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	        	   <tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>

	        	 </tbody>
        	</table>
            <input type="text" id="num"/>
           <button id="button1" onclick="go()">提交</button>
     	</center>
       
     </body>
</html>