package com.scu.niagaramodule.pojo;

import java.util.HashMap;
import java.util.Map;

public class Msg {

	private String code;//状态码：成功-200，失败404
	
	private String msg;//提示信息
	
	private Map<String,Object> data = new HashMap<>();//返回数据

	public Msg(String msg) {
		this.msg = msg;
	}

	public static Msg success(String msg)
	{
		Msg result = new Msg(msg);
		result.setCode("200");
		return result;
	}
	
	public static Msg fail(String msg)
	{
		Msg result = new Msg(msg);
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}	
}
