package org.jinxd.controller;


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jinxd.format.DateJsonValueProcessor;
import org.jinxd.service.BallService;
import org.jinxd.service.Lottery;
import org.jinxd.service.Reds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/ball")
public class BallController {
    private BallService ballService;
	@Autowired
	public void setBallService(BallService ballService) {
		this.ballService = ballService;
	}
	
	@RequestMapping("code")
	public String toAdd(){
		return "add_code";
	}
	
    @RequestMapping("add")
	public String ball(@RequestParam("reds")String reds,@RequestParam("blue")int blue){
    	 Lottery lottery= ballService.updateTable(reds, blue);
    	// model.addAttribute(lottery);
		 return "redirect:newBall";
	}
    
   
    @RequestMapping("newBall")
    public String newBall(Model model){
    	Lottery lottery= ballService.findNew();
    	model.addAttribute(lottery);
    	return "ball_view";
    }
    
    @RequestMapping("hundreds")
    public String hundredBall(@RequestParam(value="num",defaultValue="20")int num,Model model){
    	List<Lottery> lots=ballService.findLottery(num);
    	model.addAttribute("cps", lots);
    	//查询的期数
    	model.addAttribute("qs", num);
    	return "hundreds_view";
    }
    
    
   //分页查看
    @RequestMapping("paging")
    @ResponseBody
    public String pages(@RequestParam(value="currentPage",defaultValue="1")int currentPage){
    	final int lineSize=20;
    	List<Lottery> lots=ballService.separate(currentPage,lineSize);
        JSONArray arr=new JSONArray();
    	for(Lottery lot:lots){
    	JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
        //注册值处理器
        jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
        String json=JSONSerializer.toJSON(lot , jsonConfig).toString();
        arr.add(json);
    	}
    	
    	String content=arr.toString();
    	return content;
    }


    @RequestMapping("hoho")
    public String hoho(){
    	return "redirect:hundreds";
    }
    
    
    //分析蓝球
    @RequestMapping("toBlue")
    public String opBlue(@RequestParam("num")int num,Model model){
    	Map<Integer,int[]> blues= ballService.dealBlue(num);
    	Set<Integer> keys=blues.keySet();
    	String[] titles={"近期统计","最近遗漏","历史总计"};
    	
    	for(int n=0;n<titles.length;n++){
    		int i=0;
    		int[] newarr =new int[blues.values().size()];
    		for(int[] arr:blues.values()){
	    		newarr[i++]=arr[n];
	    	}
    		getBlueJPG(keys,newarr,titles[n]);
    	}
	    	
    	model.addAttribute("blues",blues);
    	return "blue_analyze";
    }
    
    //生成蓝球的jsp分析图
    public void getBlueJPG(Set<Integer> keys,int[] arr,String title){
         DefaultKeyedValues values=new DefaultKeyedValues();
         int i=0;
         for(Integer key:keys){
              values.addValue(key, arr[i++]);
         }
         CategoryDataset dataset=DatasetUtilities.createCategoryDataset("blue_analyze", values);
         JFreeChart chart=ChartFactory.createBarChart(title+"分析图", "蓝球号", title, dataset);
         //更改bar条颜色
         if("最近遗漏".equals(title))
               chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.decode("#F8D661"));
         //3、解决中文乱码问题
     	//设置X轴字体
 	     chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("宋体",Font.BOLD,12));
 	     //设置y周字体
 	   	 chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("宋体",Font.BOLD,12));
 	   	 TextTitle tt=chart.getTitle();
 	   	 //设置标题字体
 	   	 tt.setFont(new Font("宋体",Font.BOLD,14)); 
 	   	 //设置图例字体
 	   	 chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,12));
     //4、生成JPG图片
 	  try{
 		    String path=getWebRootPath()+"/resource/"+title+".jpg";
 			FileOutputStream fos=new FileOutputStream(new File(path));
 			ChartUtilities.writeChartAsJPEG(fos,chart, 800, 400);
 			System.out.println("蓝球统计图片生成完毕！！");
 			fos.close();
 			} catch (FileNotFoundException e) {
 				e.printStackTrace();
 			}catch (IOException e){
 				e.printStackTrace();
 			}
    }
    
    
  @RequestMapping("toSelect")
 	public String go(){
		return "good_luck";
	}
    
    
    
    //分析
    @RequestMapping("analyze")
    public String think(@RequestParam("num")int num,@RequestParam("small")int small,RedirectAttributes attr){
    	Map<String,Object> maps=ballService.think(num);
    	Map<String,Integer> parts= (Map<String, Integer>) maps.get("partition");
    	Map<String,Integer> dis= (Map<String, Integer>) maps.get("disperse");
    	Map<String,Integer> maxs= (Map<String, Integer>) maps.get("maxrange");
    	Map<String,Integer> paris= (Map<String, Integer>) maps.get("parity");
    	Map<String,Integer> evens= (Map<String, Integer>) maps.get("evenno");
    	//System.out.println(evens);
    	
    	Map<String,Integer> drops=ballService.redDrop(small);
    	attr.addFlashAttribute("drops",drops);
		attr.addFlashAttribute("maxs",maxs);
		attr.addFlashAttribute("dis",dis);
		attr.addFlashAttribute("parts",parts);
		attr.addFlashAttribute("evens",evens);
		attr.addFlashAttribute("paris",paris);
		attr.addAttribute("num",num);
		
    	return "redirect:nextAnalyze";
    }
    
    
    
    
    //正常出现次数
    @RequestMapping("nextAnalyze")
    public String normal(@RequestParam("num")int num,
    		@ModelAttribute("evens")Map<String,Integer> evens,
    		@ModelAttribute("maxs")Map<String,Integer> maxs,
    		@ModelAttribute("parts")Map<String,Integer> parts,
    		@ModelAttribute("paris")Map<String,Integer> paris,
    		Model model){
    	//System.out.println(evens);
    	Map<String,Object> maps=ballService.readXml(num);
    	Map<String,Long> partitions= (Map<String, Long>) maps.get("partition");
    	Map<String,Long> maxranges= (Map<String, Long>) maps.get("maxrange");
    	Map<String,Long> paritys= (Map<String, Long>) maps.get("parity");
    	Map<String,Long> evennos= (Map<String, Long>) maps.get("evenno");
    	
    	model.addAttribute("maxranges",maxranges);
    	model.addAttribute("partitions",partitions);
    	model.addAttribute("evennos",evennos);
    	model.addAttribute("paritys",paritys);
    	//生成JFreeChart图片
    	getJPG(partitions,parts,"分区");
    	getJPG(maxranges,maxs,"极差");
    	getJPG(evennos,evens,"连号");
    	getJPG(paritys,paris,"奇偶性");
    
    	return "analyze_result";
    	
    }
  //生成图表的方法
    public void getJPG(Map<String,Long> map1,Map<String,Integer> map2,String title){
    	 final String s1="正常";
    	 final String s2="统计";
    	 //1、获取CategoryDataset
    	DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    	Iterator it= map1.keySet().iterator();
	    while(it.hasNext()){
	   		 String str=(String) it.next();
	   		 dataset.addValue(map1.get(str), s1, str);
		   	}
		Iterator it2= map2.keySet().iterator();
		while(it2.hasNext()){
		   		String str=(String)it2.next();
		   		dataset.addValue(map2.get(str), s2, str);
		   	}
    	 //2、生成JFreeChart
    	JFreeChart chart=
   		 ChartFactory.createBarChart3D(title+"统计图",
   				 						title,//
   				 						"统计",//
   				 						dataset,
   				 						PlotOrientation.VERTICAL,
		 									true,
		 									false,
		 									false);
    //3、解决中文乱码问题
    	//设置X轴字体
	     chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("宋体",Font.PLAIN,12));
	     //设置y周字体
	   	 chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("宋体",Font.PLAIN,12));
	   	 TextTitle tt=chart.getTitle();
	   	 //设置标题字体
	   	 tt.setFont(new Font("宋体",Font.BOLD,14)); 
	   	 //设置图例字体
	   	 chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,12));
    //4、生成JPG图片
	  try{
		    String path=getWebRootPath()+"/resource/"+title+".jpg";
			FileOutputStream fos=new FileOutputStream(new File(path));
			ChartUtilities.writeChartAsJPEG(fos,chart, 800, 400);
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
    
    
    //选号
    /*
     * 
     */
    @RequestMapping("luck")
    @ResponseBody
    public String haveLuck(Model model,@RequestParam("maxrange")String maxrange,@RequestParam("disperse")String disperse,
	         @RequestParam("partition")String partition,@RequestParam("likeCode")String likeCode,
	         @RequestParam("parity")String parity,@RequestParam("evenno")String evenno,
	         @RequestParam("number")int number,@RequestParam("predrop")Integer predrop,
    		         @RequestParam("killreds[]")int[] killreds){
    	
    	//System.out.println(killreds[0]);
    	
    	List<Reds> good=ballService.selectCode(maxrange, disperse, partition, likeCode,parity,evenno,number,predrop,killreds);
    	JSONArray ja=JSONArray.fromObject(good);
    	String goods=ja.toString();
    	model.addAttribute("goods",goods);
     	return goods;
//    	System.out.println(goods.isEmpty());
    	
    	//return "good_luck";
    }
    
    
    //首页动态时期日期处理
    @RequestMapping("index")
    @ResponseBody
    public Object index(@RequestParam("year")String year,@RequestParam("month")String month){
    	String[] s=ballService.dealIndex(year,month);
    	JSONArray ja=new JSONArray();
    	for (int i = 0; i < s.length; i++) {
    		ja.add(s[i]);
		}
    	return ja;
    	
    }
    
    
    
    
    
    
    
    /*以下内容与双色球无关，纯为学习知识所用*/
    //文件上传的练习
	@RequestMapping(value="upload",method=RequestMethod.GET)
	public String showUpload(){
		return "study/upload";
	}
	
	@RequestMapping(value="doUpload",method=RequestMethod.POST)
	public String doUpload(@RequestParam("file")MultipartFile file) throws IOException{
		if(!file.isEmpty()){
			
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("D:\\temp\\",
					System.currentTimeMillis()+file.getOriginalFilename()));
		}
		return "success";
	}
	
	
	//在spring中将Model数据以json的格式向页面传递
	@RequestMapping(value="json1/{num}",method=RequestMethod.GET)//url直接带上参数
	public @ResponseBody List<Lottery> getLotteryInJson(@PathVariable Integer num){
		return ballService.findLottery(num);
	}
	
//	@RequestMapping(value="json2/{num}",method=RequestMethod.GET)
//	public @ResponseEntity List<Lottery> getLotteryInJson2(@PathVariable Integer num){
//		List<Lottery> list=ballService.findLottery(num);
//		return new ResponseEntity<Lottery> (list,HttpStatus.OK);
//	}
	
	
	
	
	
	
	
}
