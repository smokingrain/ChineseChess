package com.xk.chinesechess.message;

public class Rooms {
	private Client creater;//创建者
	private String name;
	private String id;
	private Client client;//加入者
	private String createTime;
	
	public Rooms(){
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		return this.getName()+"    创建者:"+this.getCreater();
	}

	
	public static String asString(Rooms[] list){
		if(null==list){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		for(Rooms room:list){
			sb.append(room.getName());
			sb.append("创建者:");
			sb.append(room.getCreater().getCname());
		}
		return sb.toString();
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getCreater() {
		return creater;
	}

	public void setCreater(Client creater) {
		this.creater = creater;
	}

}
