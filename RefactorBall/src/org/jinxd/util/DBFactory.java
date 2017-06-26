package org.jinxd.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBFactory {
       private static SqlSessionFactory sqlSessionFactory=null;
       private static SqlSession sqlSession=null;
       //在静态代码块中创建
       static{
	    	   String resource="SqlMapConfig.xml";
	    	   try {
				InputStream in=Resources.getResourceAsStream(resource);
				sqlSessionFactory=new SqlSessionFactoryBuilder().build(in,"development");
				sqlSession=sqlSessionFactory.openSession();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
       
       public static SqlSession openSession(){
    	  return sqlSession;
       }
       
}