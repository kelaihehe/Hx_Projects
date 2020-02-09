package com.scu.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.scu.pojo.TabPaper;
import com.scu.pojo.TabUser;

public interface WebUserService {

	public TabUser findUserById(long id);//根据主键ID查询user

	public List<TabUser> findUserByEmail(String email);//根据Email查询user
	
	public boolean activeEmail(String code);//根据激活码查询user
	
	public Map<String,String> registerFormFormatJudge(TabUser form);//注册表单格式判断
	
	public Map<String,String> loginFormFormatjudge(String email,String password);//登录表单判断

	public boolean recordLastLoginTime(TabUser user);//记录上次登录时间
	
	public List<TabPaper> findAllSubmitPaper(TabUser user)throws Exception; //查询该网站用户所提交的所有稿件
	
	public boolean addUser(TabUser user);//添加user

	public int getRoleAuthority(String userId);  //查询user的权限
}
