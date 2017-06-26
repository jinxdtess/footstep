<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
     <head>
         <title>幸运选号</title>
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
	        .tailor{padding:40px;height:50px;text-align:center;color:#666;font-size:12px;}
	     </style>
	     <script type="text/javascript" src="../js/jquery-1.4.3.js"></script>
	     <script type="text/javascript">
	         //复选框的值
	           var chk_value =[];  
	           function jqchk(){  //jquery获取复选框值    
						  chk_value=[]; 
						  $('input:checkbox:checked').each(function(){    
						   chk_value.push($(this).val());    
						  });    
						  alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value);    
						}    
	          
	           
	           function select_code(){
	        	  jqchk();
	        	  $.post(
	        		  "../ball/luck",
	        	      {//data:$("#myform").serialize(),
	        			  maxrange:$("select[name='maxrange']").val(),
	        			  partition:$("select[name='partition']").val(),
	        			  parity:$("select[name='parity']").val(),
	        			  evenno:$("select[name='evenno']").val(),
	        			  disperse:$("select[name='disperse']").val(),
	        			  number:$("input[name='number']:checked").val(),
	        			  likeCode:$("select[name='likeCode']").val(),
	        			  predrop:$("select[name='predrop']").val(),
	        			  killreds:chk_value},
	        	     function(data,status){
	        			  if(jQuery.isEmptyObject(data)){
	        				  alert("sorry，没有找到符合条件的号码！");
	        			  }
	        			  $('#luck').empty();
	        			 for(i = 0;i < data.length; i ++){
							var r = data[i];
							$('#luck').append(
							'<tr id="winner"><td id="reds">' + r.codes 
							+ '</td><td>' + r.partition 
							+ '</td><td>' + r.maxrange 
							+ '</td><td>' + r.parity 
							+ '</td><td>' + r.evenno
							+ '</td><td>' + r.disperse 
							+ '</td><td>' + r.wincounts + '</td></tr>');
						    }
	        	      },
	        	      'json'
	        	  );
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
                         <li><a href="analyze?num=100&small=11" target="_blank">数据分析</a></li>
                         <li><a href="#"  class="current">幸运选号</a></li>
                         
                 　                                   </ul>
             </div>
              <h3>填写选号指标</h3>
              <form id="myform">
                                               极差是：<select name="maxrange">
                             <option></option>                          
                             <option>14</option>                          
                             <option>15</option>                          
                             <option>16</option>                          
                             <option>17</option>                          
                             <option>18</option>                          
                             <option>19</option>                          
                             <option>20</option>                          
                             <option>21</option>                          
                             <option>22</option>                          
                             <option>23</option>                          
                             <option>24</option>                          
                             <option>25</option>                          
                             <option>26</option>                          
                             <option>27</option>                          
                             <option>28</option>                          
                             <option>29</option>                          
                             <option>30</option>                          
                             <option>31</option>                          
                             <option>32</option>                          
                      </select>
                  &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp                              
                   离散度是：<select name="disperse">
                                  <option selected></option>        
                                  <option>&lt 0.1</option>        
                                  <option>&gt 0.1</option>        
                                  <option>&lt 0.2</option>        
                                  <option>&gt 0.2</option>        
                                  <option>&lt 0.3</option>        
                                  <option>&gt 0.3</option>        
                                  <option>&lt 0.4</option>        
                                  <option>&gt 0.4</option>        
                                  <option>&lt 0.5</option>        
                                  <option>&gt 0.5</option>        
                                  <option>&lt 0.7</option>        
                                  <option>&gt 0.7</option>        
                                          
                              </select>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp 
                                                分区是：<select name="partition">
                              <option></option>                  
                              <option>'0 0 6'</option>                  
                              <option>'0 1 5'</option>                  
                              <option>'0 2 4'</option>                  
                              <option>'0 3 3'</option>                  
                              <option>'0 4 2'</option>                  
                              <option>'0 5 1'</option>                  
                              <option>'0 6 0'</option>                  
                              <option>'1 0 5'</option>                  
                              <option>'1 1 4'</option>                  
                              <option>'1 2 3'</option>                  
                              <option>'1 3 2'</option>                  
                              <option>'1 4 1'</option>                  
                              <option>'1 5 0'</option>                  
                              <option>'2 0 4'</option>                  
                              <option>'2 1 3'</option>                  
                              <option>'2 2 2'</option>                  
                              <option>'2 3 1'</option>                  
                              <option>'2 4 0'</option>                  
                              <option>'3 0 3'</option>                  
                              <option>'3 1 2'</option>                  
                              <option>'3 2 1'</option>                  
                              <option>'3 3 0'</option>                  
                              <option>'4 0 2'</option>                  
                              <option>'4 1 1'</option>                  
                              <option>'4 2 0'</option>                  
                              <option>'5 0 1'</option>                  
                              <option>'5 1 0'</option>                  
                              <option>'6 0 0'</option>                  
                        </select>                              
                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                      likeCode是：<select name="likeCode">
                                <option></option>        
                                <option>'1%'</option>        
                                <option>'2%'</option>        
                                <option>'3%'</option>        
                                <option>'4%'</option>        
                                <option>'5%'</option>        
                                <option>'6%'</option>        
                                <option>'7%'</option>        
                                <option>'8%'</option>        
                                <option>'9%'</option>        
                                <option>'10%'</option>        
                                <option>'11%'</option>        
                                <option>'12%'</option>        
                                <option>'13%'</option>        
                                <option>'14%'</option>        
                                <option>'15%'</option>        
                                <option>'16%'</option>        
                                <option>'17%'</option>        
                                <option>'18%'</option>        
                                <option>'19%'</option>        
                            </select>
                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                                               奇偶性是：<select name="parity">
                                <option></option>        
                                <option>'0:6'</option>        
                                <option>'1:5'</option>        
                                <option>'2:4'</option>        
                                <option>'3:3'</option>        
                                <option>'4:2'</option>        
                                <option>'5:1'</option>        
                                <option>'6:0'</option>        
                            </select>
                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                                            连号数是：<select name="evenno">
                                <option></option>        
                                <option>0</option>        
                                <option>1</option>        
                                <option>2</option>        
                                <option>3</option>        
                                <option>4</option>        
                                <option>5</option>        
                            </select>
                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          dropNum是：<select name="predrop">
                                <option></option>        
                                <option>0</option>        
                                <option>1</option>        
                                <option>2</option>        
                                <option>3</option>        
                                <option>4</option>        
                                <option>5</option>        
                            </select>
                            <br/>   
                 <!-- 杀号复选框 -->           
                                    红球杀号：<input type="checkbox"  value="1" />1
                 <input type="checkbox"  value="2" />2 
                 <input type="checkbox"  value="3" />3 
                 <input type="checkbox"  value="4" />4
                 <input type="checkbox"  value="5" />5 
                 <input type="checkbox"  value="6" />6
                 <input type="checkbox"  value="7" />7 
                 <input type="checkbox"  value="8" />8 
                 <input type="checkbox"  value="9" />9 
                 <input type="checkbox"  value="10" />10 
                 <input type="checkbox"  value="11" /> 11
                 <br/>
                 <input type="checkbox"  value="12" />12
                 <input type="checkbox"  value="13" />13 
                 <input type="checkbox"  value="14" />14 
                 <input type="checkbox"  value="15" /> 15
                 <input type="checkbox"  value="16" /> 16
                 <input type="checkbox"  value="17" /> 17
                 <input type="checkbox"  value="18" />18 
                 <input type="checkbox"  value="19" />19 
                 <input type="checkbox"  value="20" />20
                 <input type="checkbox"  value="21" /> 21
                 <input type="checkbox"  value="22" />22
                 <br/> 
                 <input type="checkbox"  value="23" /> 23
                 <input type="checkbox"  value="24" /> 24
                 <input type="checkbox"  value="25" /> 25
                 <input type="checkbox"  value="26" /> 26
                 <input type="checkbox"  value="27" /> 27
                 <input type="checkbox"  value="28" />28
                 <input type="checkbox"  value="29" /> 29
                 <input type="checkbox"  value="30" /> 30
                 <input type="checkbox"  value="31" /> 31
                 <input type="checkbox"  value="32" /> 32            
                 <input type="checkbox"  value="33" /> 33       
                  <br/>          
                                          
                 
                 <br/>                                 
                          <input type="radio" name="number" value='5'/>5&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          <input type="radio" name="number" value='10'/>10&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          <input type="radio" name="number" value='15'/>15&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          <input type="radio" name="number" value='20' checked/>20&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          <input type="radio" name="number" value='50'/>50&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
              <input type="reset"/> 
              <input type="button" value="选号" onclick="select_code()"/><br/>
              </form>
              
              
              
            
     
        	<h3>选号结果</h3>
        	<table border="1" cellpadding="2" cellspacing="1" >
        	   <thead>
	        	   <tr id="schame">
	        	      <th>红球号</th>
	        	      <th>区间</th>
	        	      <th>极差</th>
	        	      <th>奇偶性</th>
	        	      <th>连号</th>
	        	      <th>离散度</th>
	        	      <th>开出次数</th>
	        	   </tr>
	        	 </thead>
	        	 <tbody id="luck">
		        	  <!--<c:forEach items="${goods}" var="g" >
		        	   <tr id="winner">
		        	      <td id="reds">${g.codes }</td>
		        	      <td>${g.partition }</td>
		        	      <td>${g.maxrange }</td>
		        	      <td>${g.disperse }</td>
		        	      <td>${g.wincounts}</td>
		        	   </tr>
		        	</c:forEach>-->
	        	 </tbody>
        	</table>
            <div class="tailor">版权所有：浙ICP备13017332号&nbsp;&nbsp;联系电话：13588065649&nbsp;&nbsp;地址：中河南路74号二楼</div>
     	</center>

     </body>
</html>