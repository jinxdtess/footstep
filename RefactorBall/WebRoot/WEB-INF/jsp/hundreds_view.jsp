<%@page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
        <title>群号博览</title>
         <link href="../alone.css" rel="stylesheet" type="text/css" />
     <style>
        
        #schame{font-weight:bold;background-color:#CCC;}
        #winner:hover{background-color:#FC3;}
        td{font-size:14px;text-align:center;}
        #reds{color:#F00}
        #blue{color:#00F}
        .tailor{padding:40px;height:50px;text-align:center;color:#666;font-size:12px;}
        tbody tr:nth-of-type(4n){background-color:#fcc;}
        #boss{margin-top:20px;}
        #pages{width:600px;height:30px;text-align:center;margin-top:20px;display:inline}
        #pages a,#first,#last{text-decoration:none;border:1px solid #000;padding:0 10px;color:#ccf}
        .current{background-color:yellow;}
        .modify_winner {color:green;}
        
        
     </style>
     
     <script type="text/javascript" src="../js/jquery-1.4.3.js"></script>
     <script type="text/javascript">
              $(function(){
            	   //获取指定名称的cookie的值
		    /* function getcookie(objname){
				var arrstr = document.cookie.split("; ");
				for(var i = 0;i < arrstr.length;i ++){
					    var temp = arrstr[i].split("=");
						if(temp[0] == objname) return unescape(temp[1]);
				}
			};
            
            	     //读取cookie
                 var index=getcookie("pageByModify");
                 if(index!=null){
		            	 $("#pages>a:eq("+(index-1)+")").addClass("current"); 
		             
            	}  */
            	
            	
            	
            	 //修改操作函数
            	 function modify(pageByModify){
			           $(".modify_winner").click(function(){
			        	   var obj="{"
			        		   +"'id':"+$(this).parent().siblings()[0].innerText
			        		   +",'reds':"+$(this).parent().siblings()[3].innerText
			        		   +",'blue':"+$(this).parent().siblings()[4].innerText
			        		   +",'opendate':"+$(this).parent().siblings()[2].innerText
			        		   +",'issue':"+$(this).parent().siblings()[1].innerText
			        	   +"}";
			        	  location.href="toModify?obj="+obj+"&pageByModify="+
			        	                         (pageByModify===null?1:pageByModify);
			           });
            	};
            	modify(1);
            
            	  //ajax请求
            	  $("#pages>a").click(function(event){
            		var start=$("#pages").children()[0].text;
	        	   //event.target也可以用$(this)替代
	        	    var current=event.target;
	        	    
	        	    var currentPage=middle=current.text;
            		 
            		  $.post(
	        		    "../ball/paging",
	        		    
	        		    //此处key应有引号，磨人的发现
	        		    {'currentPage':currentPage},
	        		     function(map){
	        		    	$('#content').empty();
	        		    	//注意这里的技巧，对于返回的map中含有的list要如此处理
	        		    	var d=eval("(" +map.data + ")");
	        		    	
	        		    	for(i=0;i<d.length;i++){
	        		    		var r=d[i];
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
							+ '</td><td>' + r.wincount 
							+ '</td><td>'
		        	        + '<input type="button"  class="modify_winner" value="修改" />'
		        	        + ' </td></tr>');
	        		    		}
	        		    	 //修改操作
	        	              modify(currentPage);
	        		    	},
	        		    	'json'
	        		       );
            	
	        	  //操作页码
	        	    $("#pages>a").removeClass("current");
	        	    //alert(start);
	        	    var offset=middle-start;
	        	    var pages=$("#pages").children();
	        	    if(start>1&&offset<2){
	        	    	$.each(pages,function(i){
	        	    		//或许可以转成jquery对象，$(page[i]).text()-=1
	        	    		pages[i].text=parseInt(pages[i].text)-1;
	        	    	});
	        	    	
	        	    	$(current).next().addClass("current");
	        	    }else   if(offset>7){
	        	    	$.each(pages,function(i){
	        	    		pages[i].text=parseInt(pages[i].text)+1;
	        	    	});
	        	    	
	        	    	$(current).prev().addClass("current");
	        	    }else{
	        	    	$(current).addClass("current");
	        	    }
	        	    
	        	    //此处click结束
            	  });
            	
            	  
            	  
            	  
            	  
            	 $("#first").click(function(){
            		 //location.href="hundreds";
            		 $.get(
            			 "paging",
            			function(map){
	        		    	$('#content').empty();
	        		    	//注意这里的技巧，对于返回的map中含有的list要如此处理
	        		    	var d=eval("(" +map.data + ")");
	        		    	
	        		    	for(i=0;i<d.length;i++){
	        		    		var r=d[i];
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
							+ '</td><td>' + r.wincount 
							+ '</td><td>'
		        	        + '<input type="button"  class="modify_winner" value="修改" />'
		        	        + ' </td></tr>');
	        		    		}
	        		    	
	        		    	//处理页码
	        		    	$("#pages>a").removeClass("current");
	        		    	$("#pages>a:eq(0)").addClass("current");
	        		    	var pages=$("#pages").children();
	        		    	$.each(pages,function(i){
	        	    		    pages[i].text=map.currentPage+i;
	        	    	    });
	        		    	//修改操作
	        		    	   modify(1);
	        		    }
            		 );
            	 }); 
            	 
            	 
            	 $("#last").click(function(){
            		  //location.href="lastPage";
            		   $.get(
            			 "lastPage",
            			 function(map){
	        		    	$('#content').empty();
	        		    	//注意这里的技巧，对于返回的map中含有的list要如此处理
	        		    	var d=eval("(" +map.data + ")");
	        		    	
	        		    	for(i=0;i<d.length;i++){
	        		    		var r=d[i];
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
							+ '</td><td>' + r.wincount 
							+ '</td><td>'
		        	        + '<input type="button"  class="modify_winner" value="修改" />'
		        	        + ' </td></tr>');
	        		    		}
	        		    	
	        		    	//处理页码
	        		    	$("#pages>a").removeClass("current");
	        		    	$("#pages>a:eq(9)").addClass("current");
	        		    	var pages=$("#pages").children();
	        		    	$.each(pages,function(i){
	        	    		    pages[i].text=map.currentPage-9+i;
	        	    	    });
	        		    	//修改操作
	        		    	   modify(map.currentPage);
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
                         <li><a href="/GoodLuck/index.jsp">首页</a></li>
                         <li><a href="toAdd" target="_blank">添加新号</a></li>
                         <li><a href="/springMVC/ball/newBall" target="_blank">新号信息</a></li>
                         <li><a href="#" class="current">博览群号</a></li>
                         <li><a href="/springMVC/ball/analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="/springMVC/ball/toSelect" target="_blank">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
           
        	<h1>最近${qs }期的中奖号码</h1>
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
	        	      <th>操作</th>
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
		        	      <td>
		        	          <input type="button"  class="modify_winner" value="修改" />
		        	      </td>
		        	   </tr>
		        	</c:forEach> 
	        	 </tbody>
        	</table>
        	
                          
        	<diV id="boss">
        	    <a href="javascript:void(0);" id="first">|&lt</a>
	        	<div id="pages">
	        	     <a href="javascript:void(0);" >${currentPage}</a>
	        	     <a href="javascript:void(0);" >${currentPage+1}</a>
	        	     <a href="javascript:void(0);" >${currentPage+2}</a>
	        	     <a href="javascript:void(0);" >${currentPage+3}</a>
	        	     <a href="javascript:void(0);" >${currentPage+4}</a>
	        	     <a href="javascript:void(0);" >${currentPage+5}</a>
	        	     <a href="javascript:void(0);" >${currentPage+6}</a>
	        	     <a href="javascript:void(0);" >${currentPage+7}</a>
	        	     <a href="javascript:void(0);" >${currentPage+8}</a>
	        	     <a href="javascript:void(0);" >${currentPage+9}</a>
	        	</div> 
	        	<a href="javascript:void(0);" id="last">>|</a>
        	</diV> 
        	 
        	 
            <div class="tailor">版权所有：浙ICP备13017332号&nbsp;&nbsp;联系电话：13588065649&nbsp;&nbsp;地址：中河南路74号二楼</div>
     	</center>
      
     </body>
</html>