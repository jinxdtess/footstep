<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.Map" %>
<html>
     <head>
           <title>蓝球分析</title>
           <style type="text/css">
            .banner ul{overflow:hidden;}
			.banner li{float:left;height:30px;line-height:30px;text-align:center;width:80px;list-style:none;}
			.banner a{display:block;color:red;}
			.banner .current{background-color:#F00;color:white;}
			.banner a:hover{background-color:#CF3;color:#000;}
			.tailor{padding:40px;height:50px;text-align:center;color:#666;font-size:12px;}
	        #parts{font-size:12px;text-align:center;}
	        table{border-color:green}
           </style>
     </head>
     <body>
         <center>
         
          <div class="banner">
                    <ul>
                         <li><a href="/springMVC/index.jsp">首页</a></li>
                         <li><a href="#" class="current">添加新号</a></li>
                         <li><a href="newBall">新号信息</a></li>
                         <li><a href="hoho">博览群号</a></li>
                         <li><a href="analyze?num=100&small=11">数据分析</a></li>
                         <li><a href="toSelect">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
              <h1>蓝球数据分析</h1>
            
              <table border="1" cellpadding="2" cellspacing="1" >
		        	   <tr>
		        	      <td>蓝球</td>
		        	      <c:forEach items="${blues}" var="b">
		        	      <td style="color:blue;">${b.key}</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>历史总计</td>
		        	      <c:forEach items="${blues}" var="b">
		        	      <td>${b.value[2] }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>近期统计</td>
		        	      <c:forEach items="${blues}" var="b">
		        	      <td>${b.value[0] }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>最近遗漏</td>
		        	      <c:forEach items="${blues}" var="b">
		        	      <td>${b.value[1] }</td>
		        	      </c:forEach>
		        	   </tr>
        	</table>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/历史总计.jpg"/>
        	<hr/>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/近期统计.jpg"/>
        	<hr/>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/最近遗漏.jpg"/>
        	<hr/>
        	
        	 <div class="tailor">版权所有：浙ICP备13017332号&nbsp;&nbsp;联系电话：13588065649&nbsp;&nbsp;地址：中河南路74号二楼</div>
         </center>
     </body>
</html>