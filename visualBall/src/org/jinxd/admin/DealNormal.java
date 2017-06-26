package org.jinxd.admin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DealNormal {
	private String url="jdbc:oracle:thin:@localhost:1521:orcl";
	private String username="scott";
	private String password="781010";
	private Connection conn=null;
	private Statement stmt=null;
	private ResultSet rs=null;
	@Test
      public void deal(){
		String[] columns={"maxrange","partition","evenno","parity"};
    	  try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(url,username,password);
			
			for (int i = 0; i < columns.length; i++) {
				Map<String,Double> map=new TreeMap<String,Double>();
				String sql="select "+columns[i]+", round(count("+columns[i]+")/1107568, 5)  from reds group by "+ columns[i] +" order by "+columns[i];
				stmt=conn.createStatement();
				rs=stmt.executeQuery(sql);
				while(rs.next()){
					map.put(("find"+rs.getObject(1)).replaceAll("\\s*", ""), rs.getDouble(2));
				}
				createXML(columns[i],map);
			}
			stmt.close();
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
      }
	
	public void createXML(String colName,Map<String,Double> map){
		DocumentBuilder db=getDocumentBuilder();
		Document doc=db.newDocument();
		doc.setXmlStandalone(true);
		//添加根节点
		Element columns=doc.createElement("columns");
		Element son=doc.createElement(colName);
		
		for(Entry<String, Double> entry : map.entrySet()){
			String str=entry.getKey();
		   System.out.println(str);
		   Element grandson=doc.createElement(str);
		   grandson.setTextContent(entry.getValue()+"");
		   son.appendChild(grandson);
		}
		columns.appendChild(son);
		doc.appendChild(columns);
		
		TransformerFactory tff=TransformerFactory.newInstance();
		try {
			Transformer tf=tff.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(new DOMSource(doc), new StreamResult(new File(colName+".xml")));
			} catch (TransformerConfigurationException e) {
					e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		
	}
	
	private DocumentBuilder getDocumentBuilder(){
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=null;
		try {
			db=dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return db;
	}
	
}