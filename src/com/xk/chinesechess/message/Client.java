package com.xk.chinesechess.message;

public class Client {
	private String cid = null;//玩家id
	private String cname="asdfsd";//玩家名
	private String roomid="sdf";//所在房间id
	private Integer computer=2;//电脑对战局数
	private Integer duizhan=2;//玩家对战局数
	private Integer cwin=1;//电脑对战赢了局数
	private Integer dwin=1;//玩家对战赢了局数
	
	public Client() {
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public Integer getComputer() {
		return computer;
	}
	public void setComputer(Integer computer) {
		this.computer = computer;
	}
	public Integer getDuizhan() {
		return duizhan;
	}
	public void setDuizhan(Integer duizhan) {
		this.duizhan = duizhan;
	}
	public Integer getCwin() {
		return cwin;
	}
	public void setCwin(Integer cwin) {
		this.cwin = cwin;
	}
	public Integer getDwin() {
		return dwin;
	}
	public void setDwin(Integer dwin) {
		this.dwin = dwin;
	}
	
}
