<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
     <head>
         <title>数据分析</title>
     <style>
        .banner ul{overflow:hidden;}
		.banner li{float:left;height:30px;line-height:30px;text-align:center;width:80px;list-style:none;}
		.banner a{display:block;color:red;}
		.banner .current{background-color:#F00;color:white;}
		.banner a:hover{background-color:#CF3;color:#000;}
        .tailor{padding:40px;height:50px;text-align:center;color:#666;font-size:12px;}
        #parts{font-size:12px;text-align:center;}
        table{border-color:green}
     </style>
     <script type="text/javascript">
	           function go(){
	        	  location.href="/springMVC/ball/toBlue?num="+${param.num };
	           }
     </script>
     </head>
    
     <body>
          <center>
          
            <div class="banner">
                    <ul>
                         <li><a href="/springMVC/index.jsp" target="_blank">首页</a></li>
                         <li><a href="code" target="_blank">添加新号</a></li>
                         <li><a href="newBall" target="_blank">新号信息</a></li>
                         <li><a href="hoho" target="_blank">博览群号</a></li>
                         <li><a href="#" class="current">数据分析</a></li>
                         <li><a href="toSelect" target="_blank">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
             <h1>最新${param.num }期分析结果</h1>
        	<table border="1" cellpadding="2" cellspacing="1" >
		        	   <tr>
		        	      <td>极差</td>
		        	      <c:forEach items="${maxs}" var="m">
		        	      <td>${m.key }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>统计</td>
		        	      <c:forEach items="${maxs}" var="m">
		        	      <td>${m.value }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>正常</td>
		        	      <c:forEach items="${maxranges}" var="m">
		        	      <td>${m.value }</td>
		        	      </c:forEach>
		        	   </tr>
        	</table>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/极差.jpg"/>
        	<hr/>
        	
        	<table border="1" cellpadding="0" cellspacing="2"  id="parts">
		        	   <tr>
		        	      <td>分区</td>
		        	      <c:forEach items="${parts}" var="p">
		        	      <td>${p.key }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>统计</td>
		        	      <c:forEach items="${parts}" var="p">
		        	      <td>${p.value }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>正常</td>
		        	      <c:forEach items="${partitions}" var="p">
		        	      <td>${p.value }</td>
		        	      </c:forEach>
		        	   </tr>
        	</table>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/分区.jpg"/>
        	<hr/>
        	
        	<table border="1" cellpadding="2" cellspacing="1" class="lit">
		        	   <tr>
		        	      <td>离散度</td>
		        	      <c:forEach items="${dis}" var="d">
		        	      <td>${d.key }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>统计</td>
		        	      <c:forEach items="${dis}" var="d">
		        	      <td>${d.value }</td>
		        	      </c:forEach>
		        	   </tr>
        	</table>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/disperse.jpg" style="text-align:center;"/>
        	<img src="<%=request.getContextPath()%>/resource/dis.jpg"/>
        	<hr/>
        	
        	
        	<table border="1" cellpadding="2" cellspacing="1" class="lit">
		        	   <tr>
		        	      <td>奇偶性</td>
		        	      <c:forEach items="${paris}" var="d">
		        	      <td>${d.key }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>统计</td>
		        	      <c:forEach items="${paris}" var="d">
		        	      <td>${d.value }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>正常</td>
		        	      <c:forEach items="${paritys}" var="d">
		        	      <td>${d.value }</td>
		        	      </c:forEach>
		        	   </tr>
        	</table>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/奇偶性.jpg"/>
        	<hr/>
        	
        	
        	<table border="1" cellpadding="2" cellspacing="1" class="lit">
		        	   <tr>
		        	      <td>连号</td>
		        	      <c:forEach items="${evens}" var="d">
		        	      <td>${d.key }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>统计</td>
		        	      <c:forEach items="${evens}" var="d">
		        	      <td>${d.value }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>正常</td>
		        	      <c:forEach items="${evennos}" var="d">
		        	      <td>${d.value }</td>
		        	      </c:forEach>
		        	   </tr>
        	</table>
        	<br/>
        	<img src="<%=request.getContextPath()%>/resource/连号.jpg"/>
        	<hr/>
        	
        	<table border="1" cellpadding="2" cellspacing="1" >
        	      <c:forEach var="row" begin="1" end="${drops.size()/10}">
		        	   <tr>
		        	      <td>开奖期号</td>
		        	      <c:forEach items="${drops}" var="d"  begin="${0+(row-1)*10}" end="${9+(row-1)*10}">
		        	      <td>${d.key.substring(4) }</td>
		        	      </c:forEach>
		        	   </tr>
		        	   <tr>
		        	      <td>下落统计</td>
		        	      <c:forEach items="${drops}" var="d"  begin="${0+(row-1)*10}" end="${9+(row-1)*10}">
		        	      <td>${d.value }</td>
		        	      </c:forEach>
		        	   </tr>
		        </c:forEach>
        	</table>
        	<br/>
        	
			<button id="button1" onclick="go()">去分析蓝球</button>
            <div class="tailor">版权所有：浙ICP备13017332号&nbsp;&nbsp;联系电话：13588065649&nbsp;&nbsp;地址：中河南路74号二楼</div>
         </center>
     </body>
</html>