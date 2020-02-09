
package com.scu.mysqlTest;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scu.mapper.TabPaymentHistoryMapper;
import com.scu.pojo.TabPaymentHistory;
import com.scu.pojo.TabPaymentHistoryExample;
import com.scu.service.PaymentService;
import com.scu.service.impl.PaymentServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/applicationContext-dao.xml","classpath:spring/applicationContext-service.xml"})
public class TabPaymentHistoryTest {
	

	@Autowired
	private TabPaymentHistoryMapper mapper;
	
	@Autowired
	private PaymentService service;
	
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
	public void test()
	{
		PaymentServiceImpl paymentServiceImpl = new PaymentServiceImpl();
		//System.out.println(paymentServiceImpl.find("123456"));
	}
	
	@Test
	public void insertTest()
	{
	    Long amount = (long)(Math.random()*100);
		String description = getRandomString(20);
	    String payMethod = "2";
	    String payAccount = getRandomString(10);
	    String payTrader = getRandomString(10);
	    Long userId = (long)5;
	    Long conferencuserId = (long)5;
	    Date createTime = new Date();
	    Date responseTime = new Date();
	    Byte isSuccess = 1;
	    
	    TabPaymentHistory paymentHistory = new TabPaymentHistory();
	    //paymentHistory.setAmount(amount);
	    paymentHistory.setDescription(description);
	    paymentHistory.setPayAccount(payAccount);
	    paymentHistory.setPayTrader(payTrader);
	    paymentHistory.setUserId(userId);
	    //paymentHistory.setConferencUserId(Long.toString(conferencuserId));
	    paymentHistory.setCreateTime(createTime);
	    paymentHistory.setResponseTime(responseTime);
	    paymentHistory.setIsSuccess(isSuccess);
	    paymentHistory.setPrintPoster((byte)1);
	    paymentHistory.setOrderNumber("123456");
	    int result = mapper.insert(paymentHistory);
	    System.out.println(result);
	}
	
	@Test
	public void updateTest()
	{
		TabPaymentHistory paymentHistory = new TabPaymentHistory();
		paymentHistory.setId((long)1);
		paymentHistory.setDescription("已付款");
		int result = mapper.updateByPrimaryKeySelective(paymentHistory);
		System.out.println(result);
	}
	
	@Test
	public void serviceTest()
	{
		//service.update("123456");
	}
	
}

