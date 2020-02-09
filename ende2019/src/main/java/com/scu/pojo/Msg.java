package com.scu.pojo;

import java.util.HashMap;
import java.util.Map;

public class Msg {

	private String code;//状态码：成功-200，失败404
	
	private Map<String,String> msg = new HashMap<String,String>();//提示信息
	
	private Map<String,Object> data = new HashMap<String,Object>();//返回数据
	
	public static Msg success()
	{
		Msg result = new Msg();
		result.setCode("200");
		return result;
	}
	
	public static Msg fail()
	{
		Msg result = new Msg();
		result.setCode("404");
		return result;
	}
	
	public Msg addData(String key,Object value)
	{
		this.getData().put(key, value);		
		return this;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getMsg() {
		return msg;
	}

	public void setMsg(Map<String, String> msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}	
}
