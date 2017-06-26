package org.jinxd.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.jinxd.dao.LotteryDao;
import org.jinxd.entity.Lottery;
import org.jinxd.entity.Winner;
import org.jinxd.format.DateJsonValueProcessor;
import org.springframework.stereotype.Service;

@Service
public class BallService {
	@Resource
    private LotteryDao dao;
    //插入中奖记录
	/*public void addWinner(String reds,Integer blue){
		Winner winner=new Winner();
		winner.setReds(reds);
		winner.setBlue(blue);
		Lottery lottery=dao.findByMaxId();
		//设置开奖期号
		winner.setIssue(lottery.getIssue()+1);
		//处理开奖日期
		Date lastDate=lottery.getOpendate();
		Calendar c=Calendar.getInstance();
		c.setTime(lastDate);
		int day =c.get(Calendar.DAY_OF_WEEK);
		switch(day){
		case 1://周日
		case 3://周二
			lastDate.setTime(lastDate.getTime()+24*3600*1000*2);
			break;
		case 5://周四
			lastDate.setTime(lastDate.getTime()+3600*24*1000*3);
			break;
		default:
			System.out.println("DAY_OF_WEEK is error");
		    break;
		}
		winner.setOpendate(lastDate);
		int reds_id=dao.findIdByReds(reds);
		winner.setRed_id(reds_id);
		dao.save(winner);
		dao.upWincounts(reds);
	}*/
	
	public int calLastPage(){
		Lottery lot=dao.findByMaxId();
		int lastPage=(int) Math.ceil(lot.getId()/20.0);
		return lastPage;
	}
	
	
	
	
	public void addWinners(String json){
		
		JSONArray jsons=JSONArray.fromObject(json);
		List list=(List) JSONArray.toCollection(jsons, Winner.class);
		OpOtherField(list);
		
	}
	
	//从Excel文件提取数据，批量插入中奖记录
	public void addWinnersFromExcelFile() throws Exception{
		
			Workbook book=Workbook.getWorkbook(new File("d:/ssq.xls"));
			Sheet sheet=book.getSheet(0);
			//保存Winner的list集合
			List<Winner> list=new ArrayList<Winner>();
			Lottery lottery=dao.findByMaxId();
			int issue=lottery.getIssue();
			int row=0;
			//判断开奖期数，以便从数据表没有记录的那一期开始追加
			while(Integer.parseInt(sheet.getCell(0,row).getContents())<=issue){
				//System.out.println(sheet.getCell(0,row).getContents());
				row++;
			}
				
			//从Excel表获取数据
			int totalRows=sheet.getRows();
			System.out.println(totalRows);
			for(int i=row;i<totalRows;i++){//从第row行开始
				Winner win=new Winner();
				//设置issue信息
				win.setIssue(Integer.parseInt(sheet.getCell(0,i).getContents()));
				//设置红球信息
				String[] tempReds=sheet.getCell(1,i).getContents().trim().split(" ");
			    String reds=" ";
			    for (int j = 0; j < tempReds.length; j++) {
			    	 reds+=Integer.parseInt(tempReds[j])+" ";
				}
			   reds=reds.trim();
			   //System.out.println(reds);
				win.setReds(reds);
				//设置蓝球信息
				win.setBlue(Integer.parseInt(sheet.getCell(2,i).getContents()));
				list.add(win);
			}
			if(list!=null){
				OpOtherField(list);
			}else{
				System.out.println("list is null!!!");
			}
		    
	}
	
	//根据红、蓝球信息处理Winner其他字段，并将信息存入数据库winner表
	public void OpOtherField(List<Winner> list){
		//List<Winner> ws=new ArrayList<Winner>();
		Lottery lottery=dao.findByMaxId();
		int issue=lottery.getIssue();
		Date lastDate=lottery.getOpendate();
		for(int i=0;i<list.size();i++){
			Winner winner=(Winner) list.get(i);
		    String reds=winner.getReds();
			//System.out.println(reds);
			//设置开奖期号
		    if(null==winner.getIssue() || winner.getIssue()==0){
		    	winner.setIssue(++issue);
		    }
			
			//处理开奖日期
			Calendar c=Calendar.getInstance();
		
			c.setTime(lastDate);
			
			int day =c.get(Calendar.DAY_OF_WEEK);
			switch(day){
			case 1://周日
			case 3://周二
				lastDate.setTime(lastDate.getTime()+24*3600*1000*2);
				break;
			case 5://周四
				lastDate.setTime(lastDate.getTime()+3600*24*1000*3);
				break;
			default:
				System.out.println("DAY_OF_WEEK is error");
				break;
			}
			//System.out.println(i+": "+lastDate);
			winner.setOpendate((Date)lastDate.clone());
			
			int reds_id=dao.findIdByReds(reds);
			winner.setRed_id(reds_id);
			//ws.add(winner);
			
			dao.upWincounts(reds);
			dao.save(winner);
		}
		//dao.saves(ws);
		
	}
	
	
	//生成验证码
	public Map<String,BufferedImage> createImage(){
		Map<String,BufferedImage> map=new HashMap<String,BufferedImage>();
		int width=80;
		int height=20;
		int charSize=5;
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g=image.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);
		String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String str="";
		for(int i=0;i<charSize;i++){
			str+=chars.charAt((int)(Math.random()*(chars.length())));
			}
		g.setColor(getRandomColor());
		g.setFont(new Font("宋体",Font.BOLD,16));
		g.drawString(str, 10, 18);
		map.put(str, image);
		return map;
	}
	private Color getRandomColor(){
		Random rand=new Random();
		Color color=new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
		return color;
	}
	
	//分页查找中奖记录及其形态
	public String findLottery(int currentpage){
		List<Lottery>  lotterys=new ArrayList<Lottery>();
		//lotterys.add(new Lottery());
		//分页查询
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("currentpage", currentpage);
	    //map.put("lotterys", lotterys);
	    
		dao.findLotteryByPage(map);
		lotterys=(List<Lottery>) map.get("lotterys");
		
		//System.out.println("查询的结果集:"+lotterys);
		
		JsonValueProcessor jsonProcessor=new DateJsonValueProcessor();
		JsonConfig jsonConfig=new JsonConfig();
		//注册JsonValue处理器
		jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
		JSONArray arr=new JSONArray();
		for(Lottery lot:lotterys){
			String json=JSONSerializer.toJSON(lot,jsonConfig).toString();
			arr.add(json);
		}
		String content=arr.toString();
		return content;
	}
	
	
	
	
	//修改中奖记录
	public void modify(Winner winner,String oldReds){
		if(!(winner.getReds().trim().equals(oldReds))){
			
			int redId=dao.findIdByReds(winner.getReds());
			winner.setRed_id(redId);
			dao.upWincounts(winner.getReds());
			dao.downWincounts(oldReds);
		}
		dao.update(winner);
	}
	
	//删除中奖记录
	
	//查找中奖记录
	public Lottery findWinner(){
		Lottery lot=dao.findByMaxId();
		return lot;
	}
	
	
    //红球数据分析
	
	
	//蓝球数据分析
	
	
	
	
	//幸运选号
	
}
