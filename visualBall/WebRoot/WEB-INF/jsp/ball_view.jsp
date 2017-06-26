<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
        <title>新号信息</title>
        <style type="text/css">
            .banner ul{overflow:hidden;}
			.banner li{float:left;height:30px;line-height:30px;text-align:center;width:80px;list-style:none;}
			.banner a{display:block;color:red;}
			.banner .current{background-color:#F00;color:white;}
			.banner a:hover{background-color:#CF3;color:#000;}
        </style>
        <script type="text/javascript" src="../js/jquery-1.4.3.js"></script>
	     <script type="text/javascript">
	           $(document).ready(function(){
	        	   
	  				$('#button1').click(function(){
	  					location.href="hundreds?num="+$("#num").val();
	  				});
	           });
	     
	     </script>
     </head>
     <body>
        <center>
            <div class="banner">
                    <ul>
                         <li><a href="/springMVC/index.jsp" target="_blank">首页</a></li>
                         <li><a href="code">添加新号</a></li>
                         <li><a href="#" class="current">新号信息</a></li>
                         <li><a href="hoho" target="_blank">博览群号</a></li>
                         <li><a href="analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="toSelect" target="_blank">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
            <h1>最新一期的中奖号之详情</h1>
            <table style="border:1px solid #ff0;">
                <tr>
                   <td>红球</td>
                   <td>${lottery.reds}</td>
                </tr>
                <tr>
                   <td>蓝球</td>
                   <td>${lottery.blue}</td>
                </tr>
                <tr>
                   <td>开出次数</td>
                   <td>${lottery.wincount}</td>
                </tr>
                <tr>
                   <td>分区</td>
                   <td>${lottery.partition}</td>
                </tr>
                <tr>
                   <td>离散度</td>
                   <td>${lottery.disperse}</td>
                </tr>
                <tr>
                   <td>极差</td>
                   <td>${lottery.maxrange}</td>
                </tr>
                <tr>
                   <td>奇偶性</td>
                   <td>${lottery.parity}</td>
                </tr>
                <tr>
                   <td>连号</td>
                   <td>${lottery.evenno}</td>
                </tr>
                <tr>
                   <td>开奖日期</td>
                   <td>${lottery.opendate}</td>
                </tr>
                <tr>
                   <td>开奖期号</td>
                   <td>${lottery.issue}</td>
                </tr>
            
            </table>             
            <input type="text" id="num"/>
            <button id="button1">提交</button>
        </center>

     </body>
</html>