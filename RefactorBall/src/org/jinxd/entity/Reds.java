package org.jinxd.entity;


public class Reds{
     
	 private Integer id;
     private String  codes;
     //�н�����
     private Integer wincounts;
     //�������
     private String partition;
     //��ɢ��
     private Double disperse;
     //����
     private Integer maxrange;
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
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
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
	public void setMaxrange(Integer maxrange) {
		this.maxrange = maxrange;
	}
	public Integer getMaxrange() {
		return maxrange;
	}
	
	public void setWincounts(Integer wincounts) {
		this.wincounts = wincounts;
	}
	public Integer getWincounts() {
		return wincounts;
	}
	@Override
	public String toString() {
		return "Reds [codes=" + codes + ", disperse=" + disperse + ", evenno="
				+ evenno + ", id=" + id + ", maxrange=" + maxrange
				+ ", parity=" + parity + ", partition=" + partition
				+ ", wincounts=" + wincounts + "]";
	}
	
	
     
}
