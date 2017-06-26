<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
           <title>添加新号</title>
           <style type="text/css">
            .banner ul{overflow:hidden;}
			.banner li{float:left;height:30px;line-height:30px;text-align:center;width:80px;list-style:none;}
			.banner a{display:block;color:red;}
			.banner .current{background-color:#F00;color:white;}
			.banner a:hover{background-color:#CF3;color:#000;}
           </style>
           <!-- jquery实现Ajax -->
           <script type="text/javascript" src="../js/jquery-1.4.3.js"></script>
           <script type="text/javascript">
                $(function(){
                	$("#recent").click(function(){
                		
                		$.getJSON('../ball/findWinner',function(data){
                			$("#recentWinner").empty();
                			$("#recentWinner").val(data.reds+":"+data.blue);
                		});
                	});
                })
           
           </script>
     </head>
     <body>
         <center>
          <div class="banner">
                    <ul>
                         <li><a href="/springMVC/index.jsp">首页</a></li>
                         <li><a href="#">添加新号</a></li>
                         <li><a href="newBall">新号信息</a></li>
                         <li><a href="#" class="current">开奖号码</a></li>
                         <li><a href="hoho">博览群号</a></li>
                         <li><a href="analyze?num=100&small=11">数据分析</a></li>
                         <li><a href="toSelect">幸运选号</a></li>
                 　                                   </ul>
             </div>
              <h1>最新一期的号码是什么呢</h1>
             
                                              查看的中奖id是：<input type="text" id="recentWinner">
                             <input type="button" id="recent" value="查询"><br/>
              
              <table border="1">
                   <thead>
                      <tr>
                          <th>id</th>
                          <th>红球</th>
                          <th>蓝球</th>
                          <th>开奖日期</th>
                          <th>开奖期号</th>
                          <th>修改</th>
                          <th>删除</th>
                      </tr>
                   </thead>
                   <tbody>
                      <tr>
                      	<td>${winner.id}</td>
                      	<td>${winner.reds}</td>
                      	<td>${winner.blue}</td>
                      	<td>${winner.opendate}</td>
                      	<td>${winner.issue}</td>
                      	<td><input type="button" value="修改" /></td>
                      	<td><input type="button" value="删除" /></td>
                      </tr>
                   </tbody>
              </table>
         </center>
     </body>
</html>