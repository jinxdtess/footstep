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
           <script type="text/javascript">
               function vali(myform){
            	   var msg=confirm("警告：同一记录只能插入一次！！");
            	   alert(msg);
            	   }
               }
           
           </script>
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
              <h1>最新一期的号码是什么呢</h1>
              <form method="post" action="../ball/add" name="myform" onsubmit="return vali(this)">
                                              红球号码是：<input type="text" name="reds"><br/>
                                              <br/>
                                              蓝球号码是：<input type="text" name="blue"><br/>
                          <input type="submit" id="button" value="提交"><br/>
              </form>
         </center>
     </body>
</html>