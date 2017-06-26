package org.jinxd.admin;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jinxd.controller.BallController;

public class DealDis {
	 private final static String URL="jdbc:oracle:thin:@localhost:1521:orcl";
     private final static String NAME="scott";
	 private final static String PASSWORD="781010";
	 private Connection conn;
	 private PreparedStatement ptst;
	 private ResultSet rs;
	 public  void countDis(){
		 try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(URL,NAME,PASSWORD);
			ptst=conn.prepareStatement("select disperse from reds");
			rs=ptst.executeQuery();
			ArrayList<Double> list=new ArrayList<Double>();
			while(rs.next()){
				list.add(rs.getDouble("disperse"));
			}
			
			int a=0,b=0,c=0,d=0;
			for(double doub :list){
					if(doub<0.1){
						a++;
					}else if(doub<0.4){
						b++;
					}else if(doub<0.7){
						c++;
					}else{
						d++;
					}
			  }
			DefaultPieDataset dataset=new DefaultPieDataset();
			
			dataset.setValue("0.10~0.4", b);
			dataset.setValue("0.40~0.7", c);
			dataset.setValue("0.70~1", d);
			dataset.setValue("0~0.1", a);
			
			JFreeChart chart=ChartFactory.createPieChart("离散度总体分布", dataset, false, false, false);
			//设置标题字体
			TextTitle tt=chart.getTitle();
			tt.setFont(new Font("宋体",Font.BOLD,14));
			//设置片区颜色
//			PiePlot plot=(PiePlot)chart.getPlot();
//			plot.setSectionPaint("0~0.1", Color.yellow);
//			plot.setSectionPaint("0.10~0.4", Color.red);
//			plot.setSectionPaint("0.40~0.7", Color.blue);
//			plot.setSectionPaint("0.70~1", Color.green);
			 try{
				    String path=getWebRootPath()+"/resource/disperse.jpg";
					FileOutputStream fos=new FileOutputStream(new File(path));
					ChartUtilities.writeChartAsJPEG(fos,chart, 400, 400);
					System.out.println("图片生成完毕！！");
					fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}catch (IOException e){
						e.printStackTrace();
					}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	 }
	 //main
	 public static void main(String[] args) {
		DealDis dd=new DealDis();
		dd.countDis();
	}
	 
	 
	 
	 
	 
	 public  void countDis(Map<String,Integer> dis){
		
			DefaultPieDataset dataset=new DefaultPieDataset();
			Set<Entry<String, Integer>> set=dis.entrySet();
			for(Entry<String,Integer> entry:set){
					dataset.setValue(entry.getKey(),entry.getValue() );
			}
			JFreeChart chart=ChartFactory.createPieChart("离散度近期分布", dataset, false, false, false);
			//设置标题字体
			TextTitle tt=chart.getTitle();
			tt.setFont(new Font("宋体",Font.BOLD,14));
			try{
				    String path=getWebRootPath()+"/resource/dis.jpg";
					FileOutputStream fos=new FileOutputStream(new File(path));
					ChartUtilities.writeChartAsJPEG(fos,chart, 400, 400);
					System.out.println("图片生成完毕！！");
					fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}catch (IOException e){
						e.printStackTrace();
					}
	    }
	 
	 //获取webroot的路径
	 private String getWebRootPath()  
	    {  
	        URL urlpath=BallController.class.getResource("");  
	        String path=urlpath.toString();  
	        if(path.startsWith("file"))  
	        {  
	            path=path.substring(6);  
	        }  
	        if(path.indexOf("WEB-INF")>0)  
	        {  
	            path=path.substring(0,path.indexOf("WEB-INF")-1);  
	        }  
	        path.replace("/", File.separator);  
	        return path;  
	    }
	
}
