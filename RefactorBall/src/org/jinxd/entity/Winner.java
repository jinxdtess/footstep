package org.jinxd.entity;

import java.sql.Date;

public class Winner {
	    private Integer id;
	    //ºìÇò
	    private String reds;
	    //À¶Çò
	    private Integer blue;
	   //ºìÇòID
	    private Integer red_id;
	    //¿ª½±ÈÕÆÚ
	    private Date opendate;
	    //¿ª½±ÆÚºÅ
	    private Integer issue;
	    
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getReds() {
			return reds;
		}
		public void setReds(String reds) {
			this.reds = reds;
		}
		public Integer getBlue() {
			return blue;
		}
		public void setBlue(Integer blue) {
			this.blue = blue;
		}
		
		public Date getOpendate() {
			return opendate;
		}
		public void setOpendate(Date opendate) {
			this.opendate = opendate;
		}
		public Integer getIssue() {
			return issue;
		}
		public void setIssue(Integer issue) {
			this.issue = issue;
		}
		@Override
		public String toString() {
			return "Winner [blue=" + blue + ", id=" + id + ", issue=" + issue
					+ ", opendate=" + opendate + ", reds=" + reds + "]";
		}
		public void setRed_id(Integer red_id) {
			this.red_id = red_id;
		}
		public Integer getRed_id() {
			return red_id;
		}
	   
	    
}
