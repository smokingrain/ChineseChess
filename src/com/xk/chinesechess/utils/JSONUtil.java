package com.xk.chinesechess.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {

	private static ObjectMapper  mapper=new ObjectMapper();
	
	/**
	 * 
	 * ��;����ȡ��������
	 * @date 2016��9��29��
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    } 
	
	public static JavaType getType(Class clazz){
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz); 
		return javaType;
	}
	
	public static Map<String,Object> fromJson(String json){
		if(null==json){
			return Collections.EMPTY_MAP;
		}
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
		try {
			Map<String,Object> jsonMap = mapper.readValue(json,Map.class);
			return jsonMap;
		} catch (Exception e) {
			return Collections.EMPTY_MAP;
		} //ת��ΪHashMap����
	}
	
	public static String toJosn(Object obj){
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
		try {
			String result=mapper.writeValueAsString(obj);
			return result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static <T> T toBean(String json,JavaType javatype){
		if(null==json){
			return null;
		}
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
		try {
			return mapper.readValue(json, javatype);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static <T> T toBean(String json,Class<T> clazz) {
		if(null==json){
			return null;
		}
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
		//������֧�ֽ���������
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
		try {
			return (T) mapper.readValue(json,clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
