/*
package com.scu.mysqlTest;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scu.mapper.TabUserMapper;
import com.scu.pojo.TabUser;
import com.scu.pojo.TabUserExample;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/applicationContext-dao.xml"})
public class TabUserTest {

	@Autowired
	private TabUserMapper tabUserMapper;
	
	public String getRandomString(int length) {
	    //随机字符串的随机字符库
	    String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuffer sb = new StringBuffer();
	    int len = KeyString.length();
	    for (int i = 0; i < length; i++) {
	       sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
	    }
	    return sb.toString();
	}


	@Test
	public void insertTest()
	{
		//插入user
		TabUser tabUser = new TabUser();
		//String name = getRandomString(10);
		String name = "胡翔";
		String email = getRandomString(10)+"@qq.com";
		String password = getRandomString(10);
		String title = Math.round(Math.random()*4)+"";
		String nationality = getRandomString(5);
		String affiliation = getRandomString(10)+" University";
		int roleId =  1;
		Date createTime = new Date();
		Date lastLoginTime = new Date();
		String code = UUID.randomUUID().toString().replace("-", "");
		
		tabUser.setName(name);
		tabUser.setEmail(email);
		tabUser.setPassword(password);
		tabUser.setTitle(title);
		tabUser.setNationality(nationality);
		tabUser.setAffiliation(affiliation);
		tabUser.setRoleId(roleId);
		tabUser.setCreateTime(createTime);
		tabUser.setLastLoginTime(lastLoginTime);
		tabUser.setCode(code);
		int result = tabUserMapper.insertSelective(tabUser);
		System.out.println(result);
	}
	
	@Test
	public void updateTestByExampleSelective()
	{
		//根据email对应的记录修改部分字段
		TabUserExample example = new TabUserExample();
		TabUserExample.Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo("1791964653@qq.com") ;
		
		TabUser tabUser = new TabUser();
		tabUser.setTitle("2");
		int result = tabUserMapper.updateByExampleSelective(tabUser, example);
		System.out.println(result);
	}
	
	@Test
	public void deleteTest()
	{
		int result = tabUserMapper.deleteByPrimaryKey((long)1);
		System.out.println(result);
	}
	
}
*/
