package org.jinxd.entity;

import java.sql.Date;

public class Lottery {
    private Integer id;
    //红球
    private String reds;
    //蓝球
    private Integer blue;
    //中奖号开出次数
    private Integer wincounts;
    //分区
    private String partition;
    //离散度
    private Double disperse;
    //极差
    private Integer maxrange;
    //开奖日期
    private Date opendate;
    //开奖期号
    private Integer issue;
    //奇偶性
    private String parity;
    //连号
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
		return wincounts;
	}
	public void setWincount(Integer wincounts) {
		this.wincounts = wincounts;
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
	@Override
	public String toString() {
		return "Lottery [blue=" + blue + ", disperse=" + disperse + ", evenno="
				+ evenno + ", id=" + id + ", issue=" + issue + ", maxrange="
				+ maxrange + ", opendate=" + opendate + ", parity=" + parity
				+ ", partition=" + partition + ", reds=" + reds + ", wincounts="
				+ wincounts + "]";
	}
    
}
