<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
           <title>添加新号</title>
           
           <link href="../alone.css" rel="stylesheet" type="text/css" />
           <style type="text/css">
            .execColor{color:lightgray;}
           </style>
           <script type="text/javascript" src="../js/jquery-1.4.3.js"></script>
	       <script type="text/javascript">
	      //全局变量
	       var  json="";
	           $(function(){
	        	   $("input").keyup(function(e){
	        		   if(e.which=='13'){
	        			   $("#submit").click;
	        		   }
	        	   });
	        	   
	        	   
	        //批量添加中奖号码
	           
	           $("#submit").click(function(){
	        	   //校验
	        	   var reds=$("#reds").val();
	        	   var blue=$("#blue").val();
	        	   
	        	  if((reds==""|| reds==null)||(blue==""|| blue==null)){
	        		  alert("号码不能为空");
	        		  return false;
	        	  }
	        	   
	        	   if(!((/^[\d\s]+$/).test(reds)&&(/^[\d]+$/).test(blue))){
	        		    alert("不能含有非数字符号(红球中空格除外)");
	        		    return false;
	        	   }
	        	   
	        	   
	        	   if(!((reds.match(/\b\d{1,2}/g).length==6)&&((/\d{1,2}/).test(blue)))){
	        		   alert("号码有误");
	        		   $("#reds").val("");
	                   $("#blue").val("");
	        		   return false;
	        	   }
	        	   var strings=reds.split(" ");
	        	  /* $.each(strings,function(i){
	        		   if(strings[i]<1||strings[i]>33){
	        			   $("#reds").val("");
	        			   alert("红球号码取值范围有误");
	        			   return false;
	        		   }
	        	   });*/
	        	    var redbool=true;
	        	   $.each(strings,function(i){
	        		   
	        		   if(strings[i]<1||strings[i]>33){
	        			   alert("红球号码取值范围有误");
	        			   redbool=false;
	        			  // return false;
	        		   }
	        	   });
	        	   if(redbool===false){
	        		   return redbool;
	        	   }
	        	   
	        	   
	        	   if(blue<1||blue>16){
	                   $("#blue").val("");
	                   alert("蓝球号码取值范围有误");
	        		   return false;
	        	   }
	        	   //校验结束
	        	   
	        	  json+='{\"reds\":'+reds+',\"blue\":'+blue+'},';
	              $("#reds").val("");
	              $("#blue").val("");
	              
	              
	              $("#exec").removeAttr("disabled").removeClass("execColor");
	           });
	        	   
	         //撤销
	         $("#destroy").click(function(){
	        	 $("#exec").attr({"disabled":"disabled"}).addClass("execColor");
	        	 json=null;
	         });
	           
	           
	         //执行
	            $("#exec").click(function(event){
	        	   var reds=$("#reds").val();
	        	   var blue=$("#blue").val();
	            	
	            	if(json==="undifined"||json===""){
	            		if((reds==""|| reds==null)||(blue==""|| blue==null)){
	            			alert("号码为空");
	            			return false;
	            				}else{
	            		 //校验
	        	   if(!((reds.match(/\b\d{1,2}/g).length==6)&&((/\d{1,2}/).test(blue)))){
	        		   $("#reds").val("");
	                   $("#blue").val("");
	                   alert("号码有误");
	        		   return false;
	        	   }
	        	   var strings=reds.split(" ");
	        	   /*$.each(strings,function(i){
	        		   if(strings[i]<1||strings[i]>33){
	        			   //$("#reds").val("");
	        			   alert("红球号码取值范围有误");
	        			   return false;
	        		   }
	        	   });*/
	        	    var redbool=true;
	        	   $.each(strings,function(i){
	        		   
	        		   if(strings[i]<1||strings[i]>33){
	        			   alert("红球号码取值范围有误");
	        			   redbool=false;
	        			  // return false;
	        		   }
	        	   });
	        	   if(redbool===false){
	        		   return redbool;
	        	   }
	        	   
	        	   
	        	   
	        	   
	        	   
	        	   
	        	   
	        	   if(blue<1||blue>16){
	                   $("#blue").val("");
	                   alert("蓝球号码取值范围有误");
	        		   return false;
	        	   }
	        	   //校验结束
	            		json='[{\"reds\":'+reds+',\"blue\":'+blue+'}]';
	            		$.post(
			        		  "../ball/addWinners",
			        		  {'json':json}
		        	      );
	            	}
	            	}else{
	            		if((reds==""|| reds==null)||(blue==""|| blue==null)){
	            			   //去掉最后的逗号
	                            json=json.substr(0,json.length-1);
	            			    json="["+json+"]";
		            			$.post(
				        		  "../ball/addWinners",
				        		  {'json':json}
			        	      );
	            				}else{
	            		 //校验
	        	   if(!((reds.match(/\b\d{1,2}/g).length==6)&&((/\d{1,2}/).test(blue)))){
	        		   $("#reds").val("");
	                   $("#blue").val("");
	                   alert("号码有误");
	        		   return false;
	        	   }
	            		 
	            		 
	        	   var strings=reds.split(" ");
	        	   var redbool=true;
	        	   $.each(strings,function(i){
	        		   
	        		   if(strings[i]<1||strings[i]>33){
	        			   alert("红球号码取值范围有误");
	        			   redbool=false;
	        			  // return false;
	        		   }
	        	   });
	        	   if(redbool===false){
	        		   return redbool;
	        	   }
	        	  
	        	   if(blue<1||blue>16){
	                   $("#blue").val("");
	                   alert("蓝球号码取值范围有误");
	        		   return false;
	        	   }
	        	   //校验结束
	            		json+='{\"reds\":'+reds+',\"blue\":'+blue+'}';
	            		json="["+json+"]";
	            		
	            		$.post(
			        		  "../ball/addWinners",
			        		  {'json':json}
		        	      );
	            	   }
	            	};
	            		//呈现json,最后检查
	            		confirm(json);
	           });//执行结束	
	         	       
	         	 //从文件中提取数据，批量插入数据库winner表
	         	 $("#fromfile").click(function(){
	         		 alert("good");
	         		$.post( "../ball/addWinnersFromExcelFile",
	         			
	         			function(msg){
	         			alert(msg);
	         			
	         			});
	         	   });   
	         	       
	           });
	           
	           
	          
	     
	       </script>
     </head>
     <body>
         <center>
         
          <div class="banner">
                    <ul>
                         <li><a href="/GoodLuck/index.jsp">首页</a></li>
                         <li><a href="#" class="current">添加新号</a></li>
                         <li><a href="/springMVC/ball/newBall" target="_blank">新号信息</a></li>
                         <li><a href="hundreds">博览群号</a></li>
                         <li><a href="/springMVC/ball/analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="/springMVC/ball/toSelect" target="_blank">幸运选号</a></li>
                 　                                   </ul>
             </div>
              <h1>最新一期的号码是什么呢</h1>
              <form method="post" name="myform">
                                              红球号码是：<input type="text" id="reds" name="reds"><br/>
                                              <br/>
                                              蓝球号码是：<input type="text" id="blue" name="blue"><br/><br/>
                          <input type="button" id="submit" value="提交">
                          <input type="button" id="destroy" value="撤销">
                          <input type="button" id="exec" value="执行"><br/></br>
                          <input type="button" id="fromfile" value="从服务器Excel文件中提取">
              </form>
         </center>
     </body>
</html>