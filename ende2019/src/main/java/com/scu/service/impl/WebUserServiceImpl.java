package com.scu.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.scu.mapper.TabPaperMapper;
import com.scu.mapper.TabUserMapper;
import com.scu.pojo.TabPaper;
import com.scu.pojo.TabPaperExample;
import com.scu.pojo.TabUser;
import com.scu.pojo.TabUserExample;
import com.scu.service.WebUserService;

public class WebUserServiceImpl implements WebUserService {

	@Autowired
	private TabUserMapper mapper;
	
	@Autowired
	private TabPaperMapper paperMapper;
	
	@Override
	public TabUser findUserById(long id) {
		TabUser user = mapper.selectByPrimaryKey(id);
		return user;
	}

	@Override
	public List<TabUser> findUserByEmail(String email) {
		TabUserExample example = new TabUserExample();
		TabUserExample.Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		
		List<TabUser> tabUserList  = mapper.selectByExample(example);
		return tabUserList;
	}
	
	@Override
	public Map<String, String> registerFormFormatJudge(TabUser form) {
		Map<String,String> errors = new HashMap<String,String>();
						
		//用户名表项验证
		String username = form.getName();
		int userNameLenth = 0;
		String chinese = "[\u4e00-\u9fa5]";//中文匹配
		for (int i = 0; i < username.length(); i++) 
		{
		  String temp = username.substring(i, i + 1);
		  if (temp.matches(chinese)) 
		  {
			  userNameLenth += 2;
		  } 
		  else 
		  {
			   userNameLenth += 1;
		  }
		}
		if(username==null||username.isEmpty())
		{
			errors.put("username", "用户名不能为空");
		}
		else if(userNameLenth<2||userNameLenth>10)
		{
			errors.put("username", "用户名长度必须在2-10之间");
		}
		//用户密码表项验证
		String password = form.getPassword();
		if(password==null||password.isEmpty())
		{
			errors.put("password", "密码不能为空");
		}
		else if(password.length()<3||password.length()>10)
		{
			errors.put("password", "密码长度必须在3-10之间");
		}
		//邮箱表项验证
		String email = form.getEmail();
		List<TabUser> userList;
		if(email==null||email.isEmpty())
		{
			errors.put("email", "Email不能为空");
		}
		else if(!email.matches("\\w+@\\w+\\.\\w+"))
		{
			errors.put("email", "Email格式错误");
		}
		else if(( userList=findUserByEmail(email)).size()!=0)
		{
			if(userList.get(0).getActiveFlag()==1)
				errors.put("email", "邮箱已注册");  //如果用户邮箱已激活，不能再用同一邮箱注册
			else
			{
				mapper.deleteByPrimaryKey(userList.get(0).getId());//如果用户未激活，则将未激活的记录删除
			}
		}		
		return errors;
	}
	
	@Override
	public boolean addUser(TabUser user) {
		user.setCreateTime(new Date());
		user.setLastLoginTime(new Date());
		int result = mapper.insertSelective(user);
		return result==1 ? true:false;
	}
	
	@Override
	public boolean activeEmail(String code) {
		TabUserExample example = new TabUserExample();
		TabUserExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(code);
		
		System.out.println(code);
		List<TabUser> tabUserList  = mapper.selectByExample(example);
		TabUser user = tabUserList.get(0);
		if(user!=null)
		{
			user.setActiveFlag((byte)1);//设置为已激活标志
			mapper.updateByPrimaryKeySelective(user);
			return true;
		}
		else
		{
			return false;
		}				
	}
	
	@Override
	public Map<String, String> loginFormFormatjudge(String email, String password) {
		Map<String,String> errors = new HashMap<String,String>();		
		
		//1、邮箱表项验证
		List<TabUser> userList;
		if(email==null||email.isEmpty())
		{
			errors.put("email", "Email不能为空");
		}
		else if(!email.matches("\\w+@\\w+\\.\\w+"))
		{
			errors.put("email", "Email格式错误");
		}
		else if(( userList=findUserByEmail(email)).size()!=0)  
		{
			if(userList.get(0).getActiveFlag()==0)
				errors.put("email", "邮箱未激活，请先进行邮箱激活或重新注册");  
			else
			{
				//2、邮箱已注册激活，则下一步进行用户密码表项验证
				String psd = userList.get(0).getPassword();
				if(password==null||password.isEmpty())
				{
					errors.put("password", "密码不能为空");
				}
				else if(password.length()<3||password.length()>10)
				{
					errors.put("password", "密码长度必须在3-10之间");
				}
				else if(!psd.equals(password))
				{
					errors.put("password", "密码错误");
				}
			}
		}
		else if(( userList=findUserByEmail(email)).size()==0)
		{
			errors.put("email","邮箱未注册,请先进行邮箱注册并激活");
		}				
		return errors;
	}
	
	@Override
	public boolean recordLastLoginTime(TabUser user) {
		user.setLastLoginTime(new Date());
		int result = mapper.updateByPrimaryKeySelective(user);
		return result==1?true:false;
	}
	
	@Override
	public List<TabPaper> findAllSubmitPaper(TabUser user) throws Exception {
		TabPaperExample example = new TabPaperExample();
		TabPaperExample.Criteria criteria = example.createCriteria();
		criteria.andCommitUserIdEqualTo(user.getId());
		
		List<TabPaper> paperList = paperMapper.selectByExample(example);
		return paperList;
	}

	@Override
	public int getRoleAuthority(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
