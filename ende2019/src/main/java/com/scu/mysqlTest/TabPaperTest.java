
package com.scu.mysqlTest;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scu.mapper.TabPaperMapper;
import com.scu.mapper.TabUserMapper;
import com.scu.pojo.TabPaper;
import com.scu.pojo.TabPaperExample;
import com.scu.pojo.TabUser;
import com.scu.pojo.TabUserExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml"})
public class TabPaperTest {

	@Autowired
	private TabPaperMapper tabPaperMapper;
	
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
				TabPaper  paper = new TabPaper();
				String name = getRandomString(10);
				String email = getRandomString(10)+"@qq.com";
				String nationality = getRandomString(5);
				String affiliation = getRandomString(10)+" University";
				String title = getRandomString(30);
				String type = "1";
				String paperPath = "F://"+getRandomString(5)+".pdf";
				Date  createTime = new Date();
				Long commitUserId = (long) 6;
				
				paper.setName(name);
				paper.setEmail(email);
				paper.setNationality(nationality);
				paper.setAffiliation(affiliation);
				paper.setTitle(title);
				paper.setType(type);
				paper.setPaperPath(paperPath);
				paper.setCreateTime(createTime);
				paper.setCommitUserId(commitUserId);
				
				int result = tabPaperMapper.insert(paper);
				System.out.println(result);
	}
	
	@Test
	public void updateTestByExampleSelective()
	{
		//根据主键对应的记录修改部分字段	
		TabPaper  paper = new TabPaper();
		paper.setId((long) 1);
		paper.setNationality("China");
		int result = tabPaperMapper.updateByPrimaryKeySelective(paper);
		System.out.println(result);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

