package org.jinxd.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.read.biff.BiffException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jinxd.entity.Lottery;
import org.jinxd.entity.Winner;
import org.jinxd.service.BallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ball")
public class WinnerController {
	
 private BallService ballService;
 @Autowired
	public void setBallService(BallService ballService) {
		this.ballService = ballService;
	}
		
	
	//登录时，用户名与密码验证
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Integer> dealLogin(HttpSession session,@RequestParam("validation")String validation,
			@RequestParam("username")String username,@RequestParam("password")String password){
		String code=(String) session.getAttribute("code");
		System.out.println("code:"+code);
		Map<String,Integer> result=new HashMap<String,Integer>();
		if(!(validation.equalsIgnoreCase(code))){
			result.put("flag", 3);
			return result;
		}else if(!("jinxd".equalsIgnoreCase(username))){
			result.put("flag", 1);
			return result;
		}else if(!("781010".equals(password))){
			result.put("flag", 2);
			return result;
		}
		session.setAttribute("user", username);
		result.put("flag", 0);
		return result;
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "add_code";
	}
	@RequestMapping("/toHundreds")
	public String toRecentWinner(){
		return "redirect:hundreds";
	}
	
	
	//反馈验证码
	@RequestMapping("/createImage")
	public void toValid(HttpServletResponse response,HttpSession session) throws IOException{
		Map<String,BufferedImage> map=ballService.createImage();
		String key=map.keySet().iterator().next();
		System.out.println(key);
		//在jsp页面，session中的值与验证码图片不同步 
		session.setAttribute("code", key);
		
		response.setContentType("image/jpeg");
	
		ServletOutputStream output=response.getOutputStream();
		ImageIO.write(map.get(key),"jpeg", output);
		output.close();
	}
	
	
	 //插入中奖记录
//	@RequestMapping("/addWinner")
//	public String addWinner(@RequestParam("reds")String reds,@RequestParam("blue")int blue){
//		ballService.addWinner(reds, blue);
//		return "redirect:toAdd";
//	}
	
	//批量插入中奖记录
	@RequestMapping("/addWinners")
	@ResponseBody
	public void addWinners(@RequestParam("json")String json){
		ballService.addWinners(json);
	
	}
	
	//从Excel文件提取数据，批量插入中奖记录
	@RequestMapping("/addWinnersFromExcelFile")
	@ResponseBody
	public String addWinnersFromExcel(){
		String msg="fail";
		try {
			ballService.addWinnersFromExcelFile();
			msg="success";
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		    return msg;
		}
	}
	
	
	//查找中奖彩票及其形态
	//分页查看
    @RequestMapping("/paging")
    @ResponseBody
    public Map<String,Object> pages(@RequestParam(value="currentPage",defaultValue="1")int currentPage){
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("currentPage", currentPage);
    	map.put("data", ballService.findLottery(currentPage));
    	return map;
    }
    //最后一页
    @RequestMapping("/lastPage")
    public String lastPage(){
    	int last= ballService.calLastPage();
    	return "redirect:paging?currentPage="+last;
    }
    
    
    @RequestMapping("/hundreds")
    public String hundredBall(Model model,
    		@RequestParam(value="currentPage",defaultValue="1")int currentPage) throws ParseException{
    	String lotStr=ballService.findLottery(currentPage);
    	
//    	把JSON字符串转换为JAVA 对象数组
    	JSONArray json=JSONArray.fromObject(lotStr);
    	List<Lottery> cps=new ArrayList<Lottery>();
    	List<JSONObject> jsons=(List<JSONObject>) JSONArray.toCollection(json,JSONObject.class);
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");  
		for(JSONObject j:jsons){
			//处理日期类型的数据
			//String[] dateFormats = new String[] {"yyyy/MM/dd","yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"};
			//JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
			Lottery lot=new Lottery();
			lot.setBlue(j.getInt("blue"));
			lot.setDisperse(j.getDouble("disperse"));
			lot.setEvenno(j.getInt("evenno"));
			lot.setId(j.getInt("id"));
			lot.setIssue(j.getInt("issue"));
		    lot.setMaxrange(j.getInt("maxrange"));
		    lot.setOpendate(new Date(format.parse(j.getString("opendate")).getTime()));
		    lot.setParity(j.getString("parity"));
		    lot.setPartition(j.getString("partition"));
		    lot.setReds(j.getString("reds"));
		    lot.setWincount(j.getInt("wincount"));
		    cps.add(lot);
		   }
        model.addAttribute("cps", cps);
        model.addAttribute("currentPage", currentPage);
    	return "hundreds_view";
    }
    
    
    //修改号码
    @RequestMapping("/toModify")
    public String modifyWinner(@RequestParam("obj")Object json,@RequestParam int pageByModify,
    		HttpSession session,Model model){
    	JSONObject jsonObject=JSONObject.fromObject(json);
    	model.addAttribute("lottery", jsonObject);
    	session.setAttribute("pageByModify", pageByModify);
    	return "modify_winner";
    }
	
    
    @RequestMapping("/modify")
    public String updateBall(@RequestParam("id")Integer id,
    		                 @RequestParam("reds")String reds,
    		                 @RequestParam("blue")Integer blue,
    		                 @RequestParam("opendate")String opendate,
    		                 @RequestParam("issue")Integer issue,
    		                 @RequestParam("oldReds")String oldReds
    		                 ,HttpSession session
    		                 ,HttpServletResponse response){
    	DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Date date=null;
		try {
			date =new Date(sdf.parse(opendate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	Winner winner=new Winner();
    	winner.setReds(reds);
    	winner.setBlue(blue);
    	winner.setId(id);
    	winner.setIssue(issue);
    	winner.setOpendate(date);
    	ballService.modify(winner,oldReds);
    	//设置cookie
    	Cookie pageByModify=new Cookie("pageByModify",session.getAttribute("pageByModify").toString());
    	response.addCookie(pageByModify);
    	return "redirect:hundreds?currentPage="+session.getAttribute("pageByModify");
    }
	
}
