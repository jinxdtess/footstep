package org.jinxd.service;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jinxd.admin.DealDis;
import org.junit.Test;
import org.springframework.stereotype.Service;

@Service
public class BallService {
         private final static String URL="jdbc:oracle:thin:@localhost:1521:orcl";
         private final static String NAME="scott";
		 private final static String PASSWORD="781010";
		 private Connection conn;
		 private PreparedStatement ptst;
		 private ResultSet rs;
	//处理首页日期问题
	  public  String[] dealIndex(String year,String month){
		  //int[] arr;
		  String[] s=new String[42];
		  Calendar c=Calendar.getInstance();
		  if(year==""){
			year=String.valueOf(c.get(Calendar.YEAR)); 
		  }
		  if(month==""){
			 month=String.valueOf(c.get(Calendar.MONTH)); 
		  }
		  c.set(Calendar.YEAR, Integer.parseInt(year));
		  c.set(Calendar.MONTH, Integer.parseInt(month));
		  c.setFirstDayOfWeek(Calendar.SUNDAY);
		  c.set(Calendar.DAY_OF_MONTH, 1);
		  int first=c.get(Calendar.DAY_OF_WEEK)-1;
		  System.out.println("1号是星期"+first);
		  int total=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		  //arr=new int[total];
//		  for (int i = 0; i < arr.length; i++) {
//			arr[i]=first++;
//			first%=7;
//		}
		  int day=1;
		  for (int i = 0; i < s.length; i++) {
			if(i<first || i>total+first-1){
				s[i]="";
			}else{
			s[i]=(day++)+"";
			}
		}
		  
		  System.out.println("日期已执行");
		  
		  return s;
	  }
		 
		 
		 
		 
	/*
	 * 该方法是根据给定的红、蓝球更新winner、reds两表
	 */
	  public Lottery updateTable(String reds,Integer blue){
		  try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(URL,NAME,PASSWORD);
			//从reds表中找出给定红球的技术指标
			ptst=conn.prepareStatement("select * from reds where codes=?");
			ptst.setString(1, reds);
			rs=ptst.executeQuery();
			//实例化一个Lottery对象
			Lottery lottery=new Lottery();
			int red_id=0;
			lottery.setReds(reds);
			lottery.setBlue(blue);
			//遍历结果集，将查询结果赋值给Lottery对象的成员变量
			while(rs.next()){
				red_id=rs.getInt("id");
				lottery.setDisperse(rs.getDouble("disperse"));
				lottery.setMaxrange(rs.getInt("maxrange"));
				lottery.setPartition(rs.getString("partition"));
				int count=rs.getInt("wincounts")+1;
				lottery.setWincount(count);
				lottery.setParity(rs.getString("parity"));
				lottery.setEvenno(rs.getInt("evenno"));
				
				//一、更新reds表中的wincounts值
				ptst=conn.prepareStatement("update reds set wincounts=? where id=?");
				ptst.setInt(1, count);
				ptst.setInt(2, red_id);
				ptst.executeUpdate();
			}
			
			
			//二、更新winner表，插入新的记录
			//从Winner表中查询出上一期开奖日期、期号
			ptst=conn.prepareStatement("select * from winner where id=(select max(id) from winner)");
			rs=ptst.executeQuery();
			//将查询结果处理后，赋值给Lottery对象的相应成员变量
			while(rs.next()){
				Integer k=Integer.parseInt(rs.getString("issue"))+1;
				String issue=k.toString();
				lottery.setIssue(k);
				//System.out.println(rs.getString("issue"));
				Date lastDate=rs.getDate("opendate");
				Calendar c=Calendar.getInstance();
				c.setTime(lastDate);
				int day=c.get(Calendar.DAY_OF_WEEK);
				switch(day){
					case 1:
					case 3:
						lastDate.setTime(lastDate.getTime()+3600*24*1000*2);
						break;
					case 5:
						lastDate.setTime(lastDate.getTime()+3600*24*1000*3);
						break;
					default:
						System.out.println("DAY_OF_WEEK is error");
					    break;
				}
				lottery.setOpendate(lastDate);
				//向Winner表中插入新的记录
				ptst=conn.prepareStatement("insert into winner values(winner_seq.nextval,?,?,?,?,?)");
				ptst.setString(1, reds);
				ptst.setInt(2, blue);
				ptst.setInt(3, red_id);
				ptst.setString(4, issue);
				ptst.setDate(5, lastDate);
				ptst.execute();
			}
			
			rs.close();
			ptst.close();
			conn.close();
			
			return lottery;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	  }
	 
	  
	  /*
	   * 通过查询数据库，得到一个Lottery的集合，共含有num个Lottery对象的元素
	   */
	  public List<Lottery> findLottery(int num){
		  int maxId=0;
		  try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(URL,NAME,PASSWORD);
			ptst=conn.prepareStatement("select max(id) from winner");
			rs=ptst.executeQuery();
            while(rs.next()){
            	maxId=rs.getInt(1);
            }
           
            List<Lottery> list=new ArrayList<Lottery>();
            
          
			String sql="select wr.* from" +
					  "(select w.id,w.reds,w.blue,w.opendate,w.issue,r.partition,r.maxrange,r.disperse,r.wincounts,r.parity,r.evenno " +
					  "from winner w join reds r on r.id= w.red_id) wr " +
					  "where id > "+ (maxId-num)+" order by id";
			
			ptst=conn.prepareStatement(sql);
			System.out.println(sql);
			rs=ptst.executeQuery();
		   while(rs.next()){
			   Lottery lottery=new Lottery();
			    lottery.setId(rs.getInt("id"));
			    lottery.setReds(rs.getString("reds"));
			    lottery.setBlue(rs.getInt("blue"));
			    lottery.setOpendate(rs.getDate("opendate"));
			    lottery.setIssue(Integer.parseInt(rs.getString("issue")));
			    lottery.setDisperse(rs.getDouble("disperse"));
			    lottery.setMaxrange(rs.getInt("maxrange"));
				lottery.setPartition(rs.getString("partition"));
				lottery.setWincount(rs.getInt("wincounts"));
				lottery.setParity(rs.getString("parity"));
				lottery.setEvenno(rs.getInt("evenno"));
				list.add(lottery);
		   }
		    rs.close();
			ptst.close();
			conn.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		  return null;
	  }
	  
	  
	  
	  
	  //查询新号信息
	  public Lottery findNew(){
		  
		  try {
				Class.forName("oracle.jdbc.OracleDriver");
				conn=DriverManager.getConnection(URL,NAME,PASSWORD);
				ptst=conn.prepareStatement("select w.id,w.reds,w.blue,w.opendate,w.issue,r.partition,r.maxrange,r.disperse,r.wincounts,r.parity,r.evenno " +
					  " from winner w join reds r on r.id= w.red_id where w.id=(select max(id) from winner)" );
				rs=ptst.executeQuery();
				Lottery lottery=new Lottery();
				while(rs.next()){
					    lottery.setId(rs.getInt("id"));
					    lottery.setReds(rs.getString("reds"));
					    lottery.setBlue(rs.getInt("blue"));
					    lottery.setOpendate(rs.getDate("opendate"));
					    lottery.setIssue(Integer.parseInt(rs.getString("issue")));
					    lottery.setDisperse(rs.getDouble("disperse"));
					    lottery.setMaxrange(rs.getInt("maxrange"));
						lottery.setPartition(rs.getString("partition"));
						lottery.setWincount(rs.getInt("wincounts"));
						lottery.setParity(rs.getString("parity"));
						lottery.setEvenno(rs.getInt("evenno"));
				   }
				    rs.close();
					ptst.close();
					conn.close();
				    return lottery;
			} catch (Exception e) {
				e.printStackTrace();
			}
			  return null;
		  }
	  
	  
	  
	  
	  //分页查询
	  public List<Lottery> separate(int currentPage,int lineSize){
		  try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(URL,NAME,PASSWORD);
            List<Lottery> list=new ArrayList<Lottery>();
			String sql="select * from " +
					          "(select rownum rn, wr.* from " +
					                "(select  * from " +
					                   "(select w.id,w.reds,w.blue,w.opendate,w.issue,r.partition,r.maxrange,r.disperse,r.wincounts,r.parity,r.evenno " +
									           "from winner w join reds r on r.id = w.red_id) order by id desc ) wr " +
									                 "where rownum <="+(currentPage*lineSize)+ ") temp " +
									                 		"where temp.rn>"+((currentPage-1)*lineSize);
			
			ptst=conn.prepareStatement(sql);
			System.out.println(sql);
			rs=ptst.executeQuery();
		   while(rs.next()){
			   Lottery lottery=new Lottery();
			    lottery.setId(rs.getInt("id"));
			    lottery.setReds(rs.getString("reds"));
			    lottery.setBlue(rs.getInt("blue"));
			    lottery.setOpendate(rs.getDate("opendate"));
			    lottery.setIssue(Integer.parseInt(rs.getString("issue")));
			    lottery.setDisperse(rs.getDouble("disperse"));
			    lottery.setMaxrange(rs.getInt("maxrange"));
				lottery.setPartition(rs.getString("partition"));
				lottery.setWincount(rs.getInt("wincounts"));
				lottery.setParity(rs.getString("parity"));
				lottery.setEvenno(rs.getInt("evenno"));
				list.add(lottery);
		   }
		    rs.close();
			ptst.close();
			conn.close();
			//将查询结果集倒序
			Collections.reverse(list);
			System.out.println("分页查询已完成");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		  return null;
	  }
	  
	  

	  
	  //处理蓝球数据并生成分析图表
	  public Map<Integer,int[]> dealBlue(int num){
		  List<Integer> list=new ArrayList<Integer>();
		  try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(URL,NAME,PASSWORD);
			String str="select blue from winner where id>(select count(*) from winner)-"+num+" order by id";
			//System.out.println(str);
			ptst=conn.prepareStatement(str);
			rs=ptst.executeQuery();
			
			while(rs.next()){
				list.add(rs.getInt("blue"));
			}
			rs.close();
			ptst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 //处理蓝球
		Map<Integer,int[]> blues=new TreeMap<Integer,int[]>();
		for (int i = 1; i < 17; i++) {
		    int[] data={0,0,0};
			blues.put(i, data);
		}
		//用于判断蓝球的遗漏
		boolean[] first=new boolean[16];
	
		ListIterator<Integer> li=list.listIterator(list.size());
		//统计各个蓝球的遗漏数
		int count=0;
		//倒序检索蓝球
		while(li.hasPrevious()){
			//蓝球的处理过程
			int blue=li.previous();
			switch(blue){
			case 1:if(!first[blue-1]){
						    blues.get(blue)[1]=count;
						    first[blue-1]=true;
					     }
					    blues.get(blue)[0]++;
				        break;
			case 2:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 3:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 4:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 5:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 6:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 7:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 8:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 9:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 10:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 11:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 12:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 13:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 14:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 15:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			case 16:if(!first[blue-1]){
							blues.get(blue)[1]=count;
							first[blue-1]=true;
						}
						blues.get(blue)[0]++;
						
						break;
			default:
				System.out.println("蓝球号码有误");
			}
			count++;
		}
		
		//处理blues的value中的第三个参数
		int k=1;
		List<Integer> blueList=getBlueList();
		for (int i:blueList) {
			blues.get(k)[2]=i;
			k++;
		}
			
		  return blues;
	  }
	  
	  
	  
	 //分析号码的方法
	  public Map<String,Object> think(int num){
		  List<Lottery> list=findLottery(num);
		  //储存TreeMap的Map
		  Map<String,Object> maps=new HashMap<String,Object>();
		  
		//得到有序的map
			Map<String,Integer> maxs=new TreeMap<String,Integer>();
			for(int i=14;i<33;i++){
				maxs.put(i+"", 0);
			}
			
			Map<String,Integer> evens=new TreeMap<String,Integer>();
			for(int i=0;i<6;i++){
				evens.put(i+"", 0);
			}
			Map<String,Integer> paris=new TreeMap<String,Integer>();
			paris.put("0:6", 0);
			paris.put("1:5", 0);
			paris.put("2:4", 0);
			paris.put("3:3", 0);
			paris.put("4:2", 0);
			paris.put("5:1", 0);
			paris.put("6:0", 0);
			
			
			Map<String,Integer> parts=toPartition();
			
			
			Map<String,Integer> dis=new TreeMap<String,Integer>();
			dis.put("0~0.1", 0);
			dis.put("0.10~0.4", 0);
			dis.put("0.40~0.7", 0);
			dis.put("0.70~1", 0);
			Set<Entry<String,Integer>> s=maxs.entrySet();
			Set<Entry<String,Integer>> s1=parts.entrySet();
			Set<Entry<String,Integer>> s2=evens.entrySet();
			Set<Entry<String,Integer>> s3=paris.entrySet();
			
			//获取数据
			for(Lottery lot:list){
				//处理极差
				for(Entry<String,Integer> entry:s){
					if(entry.getKey().equals(lot.getMaxrange()+"")){
						int m=entry.getValue();
					    m++;
					   maxs.put(entry.getKey(), m);
					   break;
					}
				}
			//处理连号
				for(Entry<String,Integer> entry:s2){
					if(entry.getKey().equals(lot.getEvenno()+"")){
						int m=entry.getValue();
					    m++;
					   evens.put(entry.getKey(), m);
					   break;
					}
				}
				
				//处理分区
				for(Entry<String,Integer> entry:s1){
					if(entry.getKey().equals(lot.getPartition())){
						int m=entry.getValue();
						m++;
						parts.put(entry.getKey(), m);
						break;
					}
				}
				//处理奇偶性
				for(Entry<String,Integer> entry:s3){
					if(entry.getKey().equals(lot.getParity())){
						int m=entry.getValue();
					    m++;
					   paris.put(entry.getKey(), m);
					   break;
					}
				}
				//处理离散度
				int x=0;
				double d=lot.getDisperse();
				if(d<0.1){
					x=dis.get("0~0.1");
					x++;
					dis.put("0~0.1", x);
				}else if(d<0.4){
					x=dis.get("0.10~0.4");
					x++;
					dis.put("0.10~0.4", x);
				}else if(d<0.7){
					x=dis.get("0.40~0.7");
					x++;
					dis.put("0.40~0.7", x);
				}else{
					x=dis.get("0.70~1");
					x++;
					dis.put("0.70~1", x);
				}
				
			}
		//Set<Entry<String,Integer>> s2=dis.entrySet();
		    
		 maps.put("maxrange", maxs);
		 maps.put("partition", parts);
		 maps.put("disperse", dis);
		 maps.put("evenno", evens);
		 maps.put("parity", paris);
		 
		 //生成dis.jpg报表图
		 new DealDis().countDis(dis);
		 
		 return maps;
	  }
	  
	  
	  //读取xml文件，计算各分析指标的正常出现次数
	  public Map<String,Object> readXml(int num){
		  Map<String,Object> reMap=new HashMap<String,Object>();
		  //创建SAXReader对象
		  SAXReader reader=new SAXReader();
			try {
				//File file=new File("src/resource/columns.xml");
				//也可以通过BallService.class.getClassLoader.getResource("").getPath()获得同样的路径
				Document doc=reader.read(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"res/columns.xml"));
				Element root=doc.getRootElement();
				Iterator it=root.elementIterator();
				while(it.hasNext()){
					//用于保存解析后的键值对的map
					Map<String,Long> map=new TreeMap<String,Long>();
					Element colName=(Element) it.next();
					//System.out.println(colName.getName());
					if("partition".equals(colName.getName())){
						Iterator itt=colName.elementIterator();
						while(itt.hasNext()){
							Element e=(Element) itt.next();
							String s=e.getName().substring(4);
							String str=s.charAt(0)+" "+s.charAt(1)+" "+s.charAt(2);
							map.put(str,Math.round(Double.parseDouble(e.getStringValue())*num));
						}
					}else if("parity".equals(colName.getName())){
						Iterator itt=colName.elementIterator();
						while(itt.hasNext()){
							Element e=(Element) itt.next();
							String s=e.getName().substring(4);
							String str=s.charAt(0)+":"+s.charAt(1);
							map.put(str,Math.round(Double.parseDouble(e.getStringValue())*num));
						}
					}else{
						Iterator itt=colName.elementIterator();
						while(itt.hasNext()){
							Element e=(Element) itt.next();
							map.put(e.getName().substring(4),Math.round(Double.parseDouble(e.getStringValue())*num));
						  }
					}
				   reMap.put(colName.getName(), map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		  return reMap;
	  }
	  
	  
	  
	  
	  
	  //分区
	  private Map<String,Integer> toPartition(){
		  Map<String,Integer> parts=new TreeMap<String,Integer>();
		  for(int k=0;k<=6;k++){
			  for(int m=0;m<=6;m++){
				  for(int n=0;n<=6;n++){
					  if(k+m+n==6){
						 parts.put(k+" "+m+" "+n, 0);  
					  }
				  }
			  }
		  }
		  System.out.println("分析执行完毕");
		  return parts;
	  }
	  
	 
	  
	  //处理红球下落的方法
	  public  Map<String,Integer> redDrop(int small){
		  List<String> list=new ArrayList<String>();
		  //List<Integer> drops=new ArrayList<Integer>();
		  Map<String,Integer> drops=new TreeMap<String,Integer>();
    	  try {
  			Class.forName("oracle.jdbc.OracleDriver");
  			conn=DriverManager.getConnection(URL,NAME,PASSWORD);
  			int maxId=0;
  			ptst=conn.prepareStatement("select max(id) from winner");
			rs=ptst.executeQuery();
            while(rs.next()){
            	maxId=rs.getInt(1);
            }
  			
  			ptst=conn.prepareStatement("select issue,reds from winner where id> "+( maxId-small) +" order by id");
  			rs=ptst.executeQuery();
  			
              while(rs.next()){
            	list.add(rs.getString("reds")); 
            	drops.put(rs.getString("issue"), 0);
              }
              
              
             for (int i = list.size()-1; i >0 ; i--) {
            	 int count=0;
            	 
				String[] nexts=list.get(i).split(" ");
				String[] pres=list.get(i-1).split(" ");
				  for(int j=0;j<nexts.length;j++){
					  for(int k=0;k<pres.length;k++){
						  if(nexts[j].equals(pres[k])){
							  count++;
						  }
					  }
				  }
				 
				 drops.put((String) drops.keySet().toArray()[i], count);
				  
			}

  		    rs.close();
  			ptst.close();
  			conn.close();
  			drops.remove(drops.keySet().toArray()[0]);
  			return drops;
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
		  return null;
	  }

	  
	  
	  
    //选号的方法
      public List<Reds> selectCode(String maxrange,String disperse,String partition,
    		     String likeCode,String parity,String evenno,int number,Integer predrop,
    		     int[] killreds){
    	  
    	//shangqihongqiuhaoma
			Lottery l=findNew();
	    	String preWinter=l.getReds();
	    	String[] strs=preWinter.split("\\s");
	  		for(int i=0;i<strs.length;i++){
	  			if(strs[i].length()==1){
	  				strs[i]="\\b"+strs[i]+"\\b";
	  			}
	  		}
    	 
	      List<Reds> redCodes=new ArrayList<Reds>();
    	  List<Reds> lucks=new ArrayList<Reds>();
    	  int id=0;
    	 //Integer id=0;
    	  try {
				Class.forName("oracle.jdbc.OracleDriver");
				conn=DriverManager.getConnection(URL,NAME,PASSWORD);
				

				String sql="select * from reds where ";
				boolean flag=false;
				if(maxrange!=""){
                     sql+="maxrange = "+maxrange; 
                     flag=true;
				}
				if(disperse!=""){
                     sql+=flag?(" and disperse "+disperse):(" disperse "+disperse);
                     flag=true;
				}
				if(partition!=""){
                     sql+=flag?(" and partition="+ partition):(" partition="+ partition);
                     flag=true;
				}
				if(likeCode!=""){
                     sql+=flag?(" and codes like "+likeCode):("codes like "+likeCode);
                     flag=true;
				}
				if(parity!=""){
					sql+=flag?(" and parity like "+parity):("parity like "+parity);
					flag=true;
				}
				if(evenno!=""){
					sql+=flag?(" and evenno = "+evenno):("evenno = "+evenno);
					flag=true;
				}
				if(!flag){
					sql="select * from reds"; 
				}
				
				sql+=" order by id";
				System.out.println(sql);
				ptst=conn.prepareStatement(sql);
				rs=ptst.executeQuery();
				
				
				while(rs.next()){
					//执行红球杀号
					String redStr=rs.getString("codes").trim();
					String[] code=redStr.split("\\s");
					boolean noKill=true;
					for (int i = 0; i < code.length; i++) {
						for (int k = 0; k < killreds.length; k++) {
							if(Integer.parseInt(code[i])==killreds[k]){
								noKill=false;
								break;
							}
						}
					}
					
					//如果该号没被杀
	            	if(noKill){
						Reds r=new Reds();
						id=opDrop(redCodes,r, strs,predrop,rs,redStr,id);
	            	}
	            }

				rs.close();
				ptst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//随机选取中意的红号
			Random rand=new Random();
			for (int i = 0; i < number; i++) {
				//如果没有中意的红号，即id=0时，则结束循环
				if(id==0){
					break;
				}
				int k=rand.nextInt(id);
				for(Reds r:redCodes){
					if(k==r.getId()){
						lucks.add(r);
						break;
					}
				}
			}
			System.out.println("选号执行完毕");
//			for(Reds r:lucks){
//				System.out.println(r.getCodes());
//			}
    	  return lucks;
      }
     
      
    //判断是否有上期的红球下落，以及下落的个数
      public int opDrop(List<Reds> redCodes,Reds r, String[] strs,
    		            Integer predrop,ResultSet rs,String redStr,int id) throws SQLException{
    	  
    	  if(predrop==null){
    		r.setId(id++);
    		r.setCodes(redStr);
          	r.setDisperse(rs.getDouble("disperse"));
          	r.setMaxrange(rs.getInt("maxrange"));
          	r.setPartition(rs.getString("partition"));
          	r.setWincounts(rs.getInt("wincounts"));
          	r.setParity(rs.getString("parity"));
			r.setEvenno(rs.getInt("evenno"));
    		  redCodes.add(r);
    	  }else{
		  		String target=rs.getString("codes");
		  		Pattern p;
		  		Matcher m;
		  		int count=0;
		  		for(String regex:strs){
		  			p=Pattern.compile(regex);
		  			m=p.matcher(target);
		  			if(m.find()){
		  				count++;
		  			}
		  		 }
	    	  if(count==predrop){
	    		    r.setId(id++);
	  				r.setCodes(target);
	  	          	r.setDisperse(rs.getDouble("disperse"));
	  	          	r.setMaxrange(rs.getInt("maxrange"));
	  	          	r.setPartition(rs.getString("partition"));
	  	          	r.setWincounts(rs.getInt("wincounts"));
	  	          	r.setParity(rs.getString("parity"));
	  				r.setEvenno(rs.getInt("evenno"));
	    		  redCodes.add(r);
	    	  }
    	 }
    	  return id;
      }
      
      
      //处理蓝球历史总数的方法
      public List<Integer> getBlueList(){
    	  List<Integer> list=new ArrayList<Integer>();
    	  try {
  			Class.forName("oracle.jdbc.OracleDriver");
  			conn=DriverManager.getConnection(URL,NAME,PASSWORD);
  			ptst=conn.prepareStatement("select  blue,count(blue) as bs from winner group by blue order by blue");
  			rs=ptst.executeQuery();
              while(rs.next()){
            	list.add(rs.getInt("bs"));  
              }
  		    rs.close();
  			ptst.close();
  			conn.close();
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		  return list;
  	  }
      
      
      
      
      //@Test
      public void test(){
//    	  Map<String,Object> map=think(100);
//    	  Set<Entry<Object,Object>> set=(Set<Entry<Object, Object>>) map.get("maxrange");
//    	  for(Entry<Object,Object> s:set){
//    		  System.out.println(s.getKey()+":"+s.getValue());
//    	  }
    	  Calendar c=Calendar.getInstance();
    	  Date lastDate=new Date(24*3600*1000*3);
			c.setTime(lastDate);
			int day=c.get(Calendar.DAY_OF_WEEK);
			System.out.println(day);
			switch(day){
				case 1:
					System.out.println("日");
					break;
				case 2:
					System.out.println("一");
					break;
				case 3:
					System.out.println("二");
					break;
				case 4:
					System.out.println("三");
					break;
				case 5:
					System.out.println("四");
					break;
				case 6:
					System.out.println("五");
					break;
				case 7:
					System.out.println("六");
					break;
				default:
					System.out.println("DAY_OF_WEEK is error");
				    break;
			}
    	  
    	  System.out.println(lastDate);
      }
      
      
      //实验集合的遍历
     // @Test
      public void test2(){
    	 Map<String,Integer>map=new TreeMap<String,Integer>();
    	 map.put("9-1", 101);
    	 map.put("9-2", 102);
    	 map.put("9-3", 103);
    	 map.put("9-4", 104);
    	 map.put("9-5", 105);
    	 System.out.println(map.toString());
    	 map.remove(map.keySet().toArray()[0]);
    	 System.out.println(map.toString());
      }
     
      //测试dealIndex方法
     //@Test
//      public void test3(){
//    	 int[] arr= dealIndex("2015","9");
//    	 System.out.println( arr.length);
//    	 for (int i = 0; i < arr.length; i++) {
//    		 System.out.println(arr[i]);
//		}
//    	 
//      }
      
     //@Test
      public void test5(){
    	  Map<String,Object> map=readXml(100);
    	  Map<String,Long>parts= (Map<String, Long>) map.get("parity");
    	  for(Entry<String, Long> entry:parts.entrySet()){
    		  System.out.println(entry.getKey()+":"+entry.getValue());
    	  }
      }
}
