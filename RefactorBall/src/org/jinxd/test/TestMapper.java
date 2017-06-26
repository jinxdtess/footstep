package org.jinxd.test;

import java.sql.Date;

import org.apache.ibatis.session.SqlSession;
import org.jinxd.entity.Winner;
import org.jinxd.util.DBFactory;
import org.junit.Test;
/**
 * �����ǻ���mybatis�Ĳ���
 * @author jinxd
 *
 */
public class TestMapper {
	public SqlSession session=null;
	//@Test
     public void queryWinner(){
    	 session=DBFactory.openSession();
    	 //����ĵڶ�������������winner.xml��id="findWinnerById"��parameterType="int"һ��
    	 Winner win=session.selectOne("org.jinxd.dao.WinnerDao.findWinnerById",1900);
    	 System.out.println(win);
     }
	
	@Test
	public void saveWinner(){
		 session=DBFactory.openSession();
    	 //����ĵڶ�������������winner.xml��id="findWinnerById"��parameterType="int"һ��
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
