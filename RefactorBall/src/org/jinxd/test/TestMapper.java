package org.jinxd.test;

import java.sql.Date;

import org.apache.ibatis.session.SqlSession;
import org.jinxd.entity.Winner;
import org.jinxd.util.DBFactory;
import org.junit.Test;
/**
 * 这里是基于mybatis的测试
 * @author jinxd
 *
 */
public class TestMapper {
	public SqlSession session=null;
	//@Test
     public void queryWinner(){
    	 session=DBFactory.openSession();
    	 //这里的第二个参数必须与winner.xml中id="findWinnerById"的parameterType="int"一致
    	 Winner win=session.selectOne("org.jinxd.dao.WinnerDao.findWinnerById",1900);
    	 System.out.println(win);
     }
	
	@Test
	public void saveWinner(){
		 session=DBFactory.openSession();
    	 //这里的第二个参数必须与winner.xml中id="findWinnerById"的parameterType="int"一致
		 String reds="2 9 14 15 26 30";
     	int red_id=session.selectOne("org.jinxd.dao.LotteryDao.findIdByReds",reds);
     	Winner winner=new Winner();
     	winner.setBlue(8);
     	//winner.setIssue(2016030);
     	winner.setRed_id(red_id);
     	winner.setReds(reds);
     	winner.setOpendate(Date.valueOf("2016-3-20"));
    	 session.insert("org.jinxd.dao.LotteryDao.save",winner);
    	 session.commit();
	}
	
}
