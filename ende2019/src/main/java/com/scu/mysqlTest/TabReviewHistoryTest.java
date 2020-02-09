/*
package com.scu.mysqlTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scu.mapper.TabReviewHistoryMapper;
import com.scu.mapper.TabUserMapper;
import com.scu.pojo.TabReviewHistory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/applicationContext-dao.xml"})
public class TabReviewHistoryTest {

	@Autowired
	private TabReviewHistoryMapper mapper;
	
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
	public void insertTest() throws ParseException
	{
		 Long reviewPaperId = (long)1;
	     String email = getRandomString(10)+"@qq.com";;
	     String subject = getRandomString(20);
	     Long editorId = (long)1;	       
	     Date createTime = new Date();
	     	String dateString = "2018-12-30 "; 	     
	     	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
	     Date endTime = sdf.parse(dateString);
	     Date finishTime = new Date();
	     Byte isComplete = 1;
	     
	     TabReviewHistory history = new TabReviewHistory();
	     history.setReviewPaperId(reviewPaperId);
	     history.setEmail(email);
	     history.setSubject(subject);
	     history.setEditorId(editorId);
	     history.setCreateTime(createTime);
	     history.setEndTime(endTime);
	     history.setFinishTime(finishTime);
	     history.setIsComplete(isComplete);
	     int result = mapper.insert(history);
	     System.out.println(result);
	}
	
	@Test
	public void updateTest()
	{
		TabReviewHistory history = new TabReviewHistory();
		history.setId((long)1);
		history.setSubject("subject"+getRandomString(20));
		int result = mapper.updateByPrimaryKeySelective(history);
		System.out.println(result);
	}
}
*/
