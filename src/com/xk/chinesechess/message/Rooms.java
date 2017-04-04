package com.xk.chinesechess.message;

import java.util.ArrayList;
import java.util.List;

public class Rooms {
	private String creator;//创建者
	private String name;
	private Integer type = 1;
	private String id;
	private List<String> members = new ArrayList<String>();//加入者
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
		return this.getName()+"    创建者:"+this.creator;
	}

	
	public static String asString(Rooms[] list){
		if(null==list){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		for(Rooms room:list){
			sb.append(room.getName());
			sb.append("创建者:");
			sb.append(room.creator);
		}
		return sb.toString();
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
