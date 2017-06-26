<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
     </head>
     <body>
          <center>
              <h3>文件上传</h3>
             <form method="post" action="/ball/upload" enctype="multipart/form-data">
                <input type="file" name="uploadFile"/>"
                <input type="submit" value="上传"/>
             </form>
          
          </center>

     </body>
</html>