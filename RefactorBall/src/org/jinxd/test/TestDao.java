package org.jinxd.test;



import java.sql.Date;

import org.jinxd.dao.LotteryDao;
import org.jinxd.dao.WinnerDao;
import org.jinxd.entity.Lottery;
import org.jinxd.entity.Winner;
import org.jinxd.entity.Winner2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDao {
	 String conf="applicationContext.xml";
     ApplicationContext context=new ClassPathXmlApplicationContext(conf);
	     //查找
        //@Test
	     public void findWinner(){
	    	 WinnerDao winnerDao=(WinnerDao) context.getBean("winnerDao");
	    	 Winner winner=winnerDao.findWinnerById(1920);
	    	 System.out.println(winner);
	     }
        
        //查找
        //@Test
        public void findLottery(){
        	LotteryDao dao=context.getBean(LotteryDao.class);
        	Lottery lottery=dao.findByMaxId();
        	System.out.println(lottery);
        }
        
        //增加
        //@Test
        public void saveLottery(){
        	
        	LotteryDao dao=context.getBean(LotteryDao.class);
        	String reds="8 12 14 15 26 33";
        	int red_id=dao.findIdByReds(reds);
        	Winner winner=new Winner();
        	winner.setBlue(8);
        	winner.setIssue(2016030);
        	winner.setRed_id(red_id);
        	winner.setReds(reds);
        	winner.setOpendate(Date.valueOf("2016-3-25"));
        	
        	dao.save(winner);
        	System.out.println("中奖号保存完成！！");
        }
        //删除
        @Test
        public void delwinner(){
        	LotteryDao dao=context.getBean(LotteryDao.class);
        	int id=1950;
        	dao.delWinner(id);
        	System.out.println("删除成功！！");
        }
        
        //更新
       //@Test
        public void updateWinner(){
        	LotteryDao dao=context.getBean(LotteryDao.class);
        	Winner win=dao.findWinnerIdById(1947);
        	win.setBlue(99);
        	dao.update(win);
        }
        
        //@Test
        public void findRandLottery(){
        	LotteryDao dao=context.getBean(LotteryDao.class);
        	Winner2 lottery=dao.findById(1000);
        	System.out.println(lottery);
        }
        
     }
     