package org.jinxd.entity;

import java.sql.Date;

public class Winner2 {
	   private Integer id;
	    //����
	    private Reds reds;
	    //����
	    private Integer blue;
	    //��������
	    private Date opendate;
	    //������ ��
	    private Integer issue;
	   
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Reds getReds() {
			return reds;
		}
		public void setReds(Reds reds) {
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
			return "Winner2 [blue=" + blue + ", id=" + id + ", issue=" + issue
					+ ", opendate=" + opendate + ", reds=" + reds + "]";
		}
		
	    
	    
}
