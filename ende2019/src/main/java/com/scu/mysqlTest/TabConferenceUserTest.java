/*
package com.scu.mysqlTest;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scu.mapper.TabConferenceUserMapper;
import com.scu.mapper.TabPaperMapper;
import com.scu.pojo.TabConferenceUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/applicationContext-dao.xml"})
public class TabConferenceUserTest {

	@Autowired
	private TabConferenceUserMapper mapper;
	
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
		 String name = getRandomString(10);
	     String email = getRandomString(10)+"@qq.com";
	     Long carryPaperId = (long)1;
	     String position = "1";
	     String gender = "1";
	     String nationality = getRandomString(5);
	     String affiliation = getRandomString(10)+" University";
	     Byte isStudent = 1;
	     Date createTime = new Date();
	     Long registUserId = (long)3;
	     
	     TabConferenceUser conferenceUser  = new TabConferenceUser();
	     conferenceUser.setName(name);
	     conferenceUser.setEmail(email);
	     conferenceUser.setCarryPaperId(carryPaperId);
	     conferenceUser.setPosition(position);
	     conferenceUser.setGender(gender);
	     conferenceUser.setNationality(nationality);
	     conferenceUser.setAffiliation(affiliation);
	     conferenceUser.setIsStudent(isStudent);
	     conferenceUser.setCreateTime(createTime);
	     conferenceUser.setRegistUserId(registUserId);
	     
	     int result = mapper.insert(conferenceUser);
	     System.out.println(result);
	}
	
	
	@Test
	public void updateTest()
	{
		TabConferenceUser conferenceUser  = new TabConferenceUser();
		conferenceUser.setId((long)3);
		conferenceUser.setIsStudent((byte)0);
		int result = mapper.updateByPrimaryKeySelective(conferenceUser);
		System.out.println(result);
	}
	
	
	
	
	
	
	
	
	
	
	
}
*/
