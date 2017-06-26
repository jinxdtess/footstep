package org.jinxd.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;


public class DealDatabase {
	private String url="jdbc:oracle:thin:@localhost:1521:orcl";
	private String username="scott";
	private String password="781010";
	private Connection conn=null;
	private PreparedStatement ptst=null;
	private PreparedStatement ptst2=null;
	private ResultSet rs=null;
	@Test
      public void deal(){
    	  try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(url,username,password);
			String[] strs;
			for(int k=1;k<=1107568;k++){
				ptst=conn.prepareStatement("select codes from reds where id ="+k);
				rs=ptst.executeQuery();
				//conn.setAutoCommit(false);
			    
				ptst2=conn.prepareStatement("update reds set parity=?,evenno=? where id=?");
				while(rs.next()){
					int odd=0;
					int even=0;
					int index=0;
					int count=0;//连号数
					strs=rs.getString("codes").split(" ");
					int arr[]=new int[strs.length];
					for(String s:strs){
						int temp=Integer.parseInt(s);
						arr[index++]=temp;
						//处理奇偶性
						if(temp%2==1){
							odd++;
						}else{
							even++;
						}
					}
						//处理连号
						for(int i=1;i<arr.length;i++){
							if(arr[i]-arr[i-1]==1){
								count++;
							}
						}
				
					ptst2.setString(1,odd+":"+even );
					System.out.println(count);
					ptst2.setInt(2,count );
					ptst2.setInt(3,k);
					//ptst.addBatch();
					ptst2.executeUpdate();
				}
				ptst.close();
				ptst2.close();
			}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
      }
}
  