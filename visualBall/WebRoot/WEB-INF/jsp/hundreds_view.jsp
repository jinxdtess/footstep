<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
        <title>群号博览</title>
     <style>
        .banner ul{overflow:hidden;}
		.banner li{float:left;height:30px;line-height:30px;text-align:center;width:80px;list-style:none;}
		.banner a{display:block;color:red;}
		.banner .current{background-color:#F00;color:white;}
		.banner a:hover{background-color:#CF3;color:#000;}
        #schame{font-weight:bold;background-color:#CCC;}
        #winner:hover{background-color:#FC3;}
        td{font-size:14px;text-align:center;}
        #reds{color:#F00}
        #blue{color:#00F}
        .tailor{padding:40px;height:50px;text-align:center;color:#666;font-size:12px;}
        tbody tr:nth-of-type(4n){background-color:#fcc;}
        #pages{width:600px;height:30px;text-align:center;margin-top:20px;}
        #pages a{text-decoration:none;border:1px solid #000;padding:0 10px;color:#ccf}
        .current{background-color:yellow;}
        
        
     </style>
     
     <script type="text/javascript" src="../js/jquery-1.4.3.js"></script>
     <script type="text/javascript">
	           function go(){
	        	  // alert(document.getElementById("num").value);
	        	  location.href="analyze?num="+document.getElementById("num").value
	        	                                        +"&small="+document.getElementById("small").value;
	           }
	           
	           function turn(currentPage){
	        	  $("#qs").html(20*currentPage);
	        	  $("#pages").find("a").removeClass("current");
	        	  $("#pages").find("a").eq(currentPage-1).addClass("current");
	        	  $.post(
	        		    "../ball/paging",
	        		    //此处key应有引号，磨人的发现
	        		    {'currentPage':currentPage},
	        		     function(data){
	        		    	$('#content').empty();
	        		    	for(i=0;i<data.length;i++){
	        		    		var r=data[i];
	        		    		$('#content').append(
	        		    	'<tr id="winner">'
	        		    	+ '<td>'+r.id
	        		    	+ '</td><td>'+r.issue
	        		    	+ '</td><td>'+r.opendate
	        		    	+ '</td><td id="reds">' + r.reds 
	        		    	+ '</td><td id="blue">' + r.blue 
							+ '</td><td>' + r.partition 
							+ '</td><td>' + r.maxrange 
							+ '</td><td>' + r.parity 
							+ '</td><td>' + r.evenno
							+ '</td><td>' + r.disperse 
							+ '</td><td>' + r.wincount + '</td></tr>');
	        		    		}
	        		    	},
	        		    	'json'
	        		       );}
	           
	                      
	           
            	
            	/*var $table=$('table');
            	var currentPage=0;
            	var pageSize=20;
            	$table.bind('paging',function(){
            		$table.find('tbody tr').hide()
            		     .slice(currentPage*pageSize,(currentPage+1)*pageSize).show();
            	});
            	var sumRows=$table.find('tbody tr').length;//记录总行数
            	var sumPages=Math.ceil(sumRows/pageSize);//总页数
            	var $pager=$('<div class="page"></div>');
            	
            	for(var index=0;index<sumPages;index++){
            		$('<a href="#"><span>'+(index+1)+'</span></a>').bind("click",
            			{"newPage":index},function(event){
            				currentPage=event.data["newPage"];
            				$table.trigger("paging");
            			}).appendTo($pager);
            		$pager.append(" ");
            	}
            	
            	$pager.insertAfter($table);
            	$table.trigger("paging");*/
              
            
     </script>
     </head>
     <body>
     	<center>
     	    <div class="banner">
                    <ul>
                         <li><a href="/springMVC/index.jsp">首页</a></li>
                         <li><a href="/springMVC/ball/code" target="_blank">添加新号</a></li>
                         <li><a href="newBall" target="_blank">新号信息</a></li>
                         <li><a href="#" class="current">博览群号</a></li>
                         <li><a href="/springMVC/ball/analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="/springMVC/ball/toSelect" target="_blank">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
        	<h1>最近<span id="qs">${qs }</span>期的中奖号码</h1>
        	<table border="1" cellpadding="2" cellspacing="1" >
        	   <thead>
	        	   <tr id="schame">
	        	      <th>id</th>
	        	      <th>开奖期数</th>
	        	      <th>开奖日期</th>
	        	      <th>红球号</th>
	        	      <th>蓝球号</th>
	        	      <th>区间</th>
	        	      <th>极差</th>
	        	      <th>奇偶性</th>
	        	      <th>连号</th>
	        	      <th>离散度</th>
	        	      <th>开出次数</th>
	        	   </tr>
	        	 </thead>
	        	 <tbody id="content">
		        	  <c:forEach items="${cps}" var="cp">
		        	   <tr id="winner">
		        	      <td>${cp.id }</td>
		        	      <td>${cp.issue }</td>
		        	      <td>${cp.opendate }</td>
		        	      <td id="reds">${cp.reds }</td>
		        	      <td id="blue">${cp.blue }</td>
		        	      <td>${cp.partition }</td>
		        	      <td>${cp.maxrange }</td>
		        	      <td>${cp.parity }</td>
		        	      <td>${cp.evenno}</td>
		        	      <td>${cp.disperse }</td>
		        	      <td>${cp.wincount}</td>
		        	      
		        	   </tr>
		        	</c:forEach>
	        	 </tbody>
        	</table>
        	 请填分析期数<input type="text" id="num"/>
        	 请选红球下落期数 <select id="small">
        	                      <option value="11">10</option>
        	                      <option value="21">20</option>
        	                      <option value="31">30</option>
        	                      <option value="41">40</option>
        	                      <option value="51">50</option>
        	                      <option value="101">100</option>
        	             </select>
                          
        	 <button id="button1" onclick="go()">去分析页面</button>
        	 
        	<div id="pages">
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>1</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>2</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>3</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>4</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>5</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>6</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>7</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>8</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>9</a>
        	     <a href="javascript:void(0);" onclick=turn(this.innerHTML)>10</a>
        	     
        	</div> 
        	 
        	 
        	 
            <div class="tailor">版权所有：浙ICP备13017332号&nbsp;&nbsp;联系电话：13588065649&nbsp;&nbsp;地址：中河南路74号二楼</div>
     	</center>
      
     </body>
</html>