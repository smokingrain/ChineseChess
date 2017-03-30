package com.xk.chinesechess.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class StringUtil {
	
	public static String getTimeStr(long time){
		StringBuffer sb=new StringBuffer();
		if(time<60){//������
			sb.append(time).append("��");
		}else if(time<3600){//�����
			sb.append(time/60).append("��").append(time%60).append("��");
		}else{//����Сʱ
			sb.append(time/3600).append("Сʱ").append(time%3600/60).append("��").append(time%60).append("��");
		}
		return sb.toString();
	}
	
	public static String asString(String[] strs){
		if(null==strs){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		for(String str:strs){
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static String noRepeat(String base){
		if(null==base){
			return "";
		}
		Set<String> chars=new HashSet<String>();
		for(int i=0;i<base.length();i++){
			chars.add(base.charAt(i)+"");
		}
		return asString(chars.toArray(new String[]{}));
	}
	
	public static boolean isBlank(String str){
		return "".equals(str)||null==str;
	}
}
