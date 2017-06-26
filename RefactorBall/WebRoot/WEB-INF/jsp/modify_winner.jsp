<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
        <title>修改号码</title>
        <link href="../alone.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
           
			.readonly {color:lightgray}
        </style>
        <script type="text/javascript" src="../js/jquery-1.4.3.js"></script>
        <script type="text/javascript">
        $(function(){
        	$("#modify").click(function(event){
            	 
            	// event.preventDefault();
            	  //校验
	        	   var reds=$("#reds").val();
	        	   var blue=$("#blue").val();
	        	   var opendate=$("#opendate").val();
	        	   var issue=$("#issue").val();
	        	   
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
	        	   $.each(strings,function(i){
	        		   if(strings[i]<1||strings[i]>33){
	        			   event.preventDefault();
	        			  
	        			   alert("红球号码取值范围(1-33)有误");
	        			   return false;
	        		   }
	        	   });
	        	   if(blue<1||blue>16){
	                   $("#blue").val("");
	                   alert("蓝球号码取值范围(1-16)有误");
	        		   return false;
	        	   }
	        	   if(!(/^\d{4}-\d{2}-\d{2}$/.test(opendate))){
	        		   alert("日期格式有误,应为xxxx-xx-xx");
	        		   return false;
	        	   }
	        	   if(!(/^20\d{5}$/.test(issue))){
	        		   alert("期号格式有误,应为20xxxxx");
	        		   return false;
	        	   }
	        	   //校验结束
	        	   return true;
             });
	      
        })
             
        </script>
        
     </head>
     <body>
        <center>
            <div class="banner">
                    <ul>
                         <li><a href="/GoodLuck/index.jsp">首页</a></li>
                         <li><a href="toAdd" target="_blank">添加新号</a></li>
                         <li><a href="/springMVC/ball/newBall" target="_blank">新号信息</a></li>
                         <li><a href="#" class="current">修改号码</a></li>
                         <li><a href="/springMVC/ball/analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="/springMVC/ball/toSelect" target="_blank">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
            <h1>需要修改的中奖号码</h1>
            <form method="post" action="../ball/modify" >
            <table style="border:1px solid #ff0;">
                <tr>
                   <td>ID</td>
                   <td><input type="text" value="${lottery.id}" name="id" readonly class="readonly"/></td>
                </tr>
                <tr>
                   <td>红球</td>
                   <td><input type="text" value="${lottery.reds}" id="reds" name="reds"/></td>
                </tr>
                <tr>
                   <td>蓝球</td>
                   <td><input type="text" value="${lottery.blue}" id="blue" name="blue"/></td>
                </tr>
                <tr>
                   <td>开奖日期</td>
                   <td><input type="text" value="${lottery.opendate}" id="opendate" name="opendate"/></td>
                </tr>
                <tr>
                   <td>开奖期号</td>
                   <td> <input type="text" value="${lottery.issue}" id="issue" name="issue"/></td>
                </tr>
            
            </table>             
             <input type='hidden' value="${lottery.reds}" name="oldReds"/>
           
            <input type="submit" id="modify" value="修改"/>
            </form>
        </center>

     </body>
</html>