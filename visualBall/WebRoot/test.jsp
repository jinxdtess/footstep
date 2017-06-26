<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
        
       <style type="text/css">
         .head{background-color:red;width:300px}
         .content{width:300px;height:300px;border:1px solid black;display:none;}
       </style>
      <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
	  <script type="text/javascript">
	      $(function(){
	    	  $("#panel h5.head").bind("click",function(){
	    		  $(this).next().show();
	    	  });
	      })
	  </script>
     </head>
     <body>
          <div id="panel">
              <h5 class="head">什么是jquery</h5>
              <div class="content">
              jquery是一个优秀的javascrip库。极大的简化了JavaScript开发人员遍历html文档，
                  操作DOM，处理事件，执行动画，开发Ajax工作。
              </div>
          </div>
     </body>
      
</html>