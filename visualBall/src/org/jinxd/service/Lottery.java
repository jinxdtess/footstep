package org.jinxd.service;

import java.sql.Date;

public class Lottery {
    private Integer id;
    //����
    private String reds;
    //����
    private Integer blue;
    //�н��ſ�������
    private Integer wincount;
    //����
    private String partition;
    //��ɢ��
    private Double disperse;
    //����
    private Integer maxrange;
    //��������
    private Date opendate;
    //�����ں�
    private Integer issue;
    //��ż��
    private String parity;
    //����
    private Integer evenno;
    
	public String getParity() {
		return parity;
	}
	public void setParity(String parity) {
		this.parity = parity;
	}
	public Integer getEvenno() {
		return evenno;
	}
	public void setEvenno(Integer evenno) {
		this.evenno = evenno;
	}
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
	public Integer getWincount() {
		return wincount;
	}
	public void setWincount(Integer wincount) {
		this.wincount = wincount;
	}
	public String getPartition() {
		return partition;
	}
	public void setPartition(String partition) {
		this.partition = partition;
	}
	public Double getDisperse() {
		return disperse;
	}
	public void setDisperse(Double disperse) {
		this.disperse = disperse;
	}
	public Integer getMaxrange() {
		return maxrange;
	}
	public void setMaxrange(Integer maxrange) {
		this.maxrange = maxrange;
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
    
}
