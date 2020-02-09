package com.scu.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.paypal.api.payments.PaymentHistory;
import com.scu.mapper.TabPaymentHistoryMapper;
import com.scu.pojo.TabPaymentHistory;
import com.scu.pojo.TabPaymentHistoryExample;
import com.scu.service.ConferenceService;
import com.scu.service.PaymentService;

public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private TabPaymentHistoryMapper mapper;
	
	@Autowired
	private ConferenceService conferenceService;
	
	@Override
	public boolean update(String orderNumber,byte is_success,String SerialNumber) {  
		//修改支付成功的两个字段,paymentHistory表的is_success字段和修改conferenceUser表的is_pay字段；1代表成功
		TabPaymentHistoryExample paymentHistoryExample = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria = paymentHistoryExample.createCriteria();
		criteria.andOrderNumberEqualTo(orderNumber);
		TabPaymentHistory paymentHistory = new TabPaymentHistory();
		paymentHistory.setIsSuccess(is_success);
		//加入流水号字段
		if(SerialNumber!=null) {
			paymentHistory.setSerialNumber(SerialNumber);
		}
		paymentHistory.setIsSuccess(is_success);
		paymentHistory.setResponseTime(new Date());
		mapper.updateByExampleSelective(paymentHistory,paymentHistoryExample);//修改paymentHistory表的is_success字段
		/*在修改支付成功时还要记得修改tab_conference_user中的is_pay字段*/
		TabPaymentHistoryExample paymentHistoryExample1 = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria1 = paymentHistoryExample1.createCriteria();
		criteria1.andOrderNumberEqualTo(orderNumber);
		List<TabPaymentHistory> list = mapper.selectByExample(paymentHistoryExample1);
		List<String>  conferenceUserIdList = new ArrayList<String>();
		for(int i=0 ; i<list.size();i++)
		{
			conferenceUserIdList.add(Long.toString(list.get(i).getConferencUserId()));
		}
		boolean result = conferenceService.updateIsPaybyconferenceUserArray(conferenceUserIdList,is_success);//修改is_pay字段
		return result;
	}
	
	@Override
	public boolean updateByexample(TabPaymentHistory paymentHistory,String example) {
		TabPaymentHistoryExample paymentHistoryExample = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria = paymentHistoryExample.createCriteria();
		
		switch (example) {
		case "order_number":
			criteria.andOrderNumberEqualTo(paymentHistory.getOrderNumber());
			break;
		case "serial_number":
			criteria.andSerialNumberEqualTo(paymentHistory.getSerialNumber());
			break;
		case "create_time":
			criteria.andCreateTimeEqualTo(paymentHistory.getCreateTime());
			break;
		case "response_time":
			criteria.andResponseTimeEqualTo(paymentHistory.getResponseTime());
			break;
		case "pay_account":
			criteria.andPayAccountEqualTo(paymentHistory.getPayAccount());
			break;
		case "pay_method":
			criteria.andPayMethodEqualTo(paymentHistory.getPayMethod());
			break;
		default:
			break;
		}		
		int result = mapper.updateByExampleSelective(paymentHistory,paymentHistoryExample);
		return result == 0?false:true;
	}
	
	public boolean updateById(TabPaymentHistory paymentHistory,Long id)
	{
		TabPaymentHistoryExample paymentHistoryExample = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria = paymentHistoryExample.createCriteria();
		
		criteria.andIdEqualTo(id);
		int result = mapper.updateByExampleSelective(paymentHistory,paymentHistoryExample);
		return result == 0?false:true;
	}
	
	@Override
	public boolean addPaymentHistory(TabPaymentHistory paymentHistory) {
		/*paymentHistory.setCreateTime(new Date());
		paymentHistory.setDescription("下单成功");
		paymentHistory.setSerialNumber("000000");
		paymentHistory.setPayAccount("支付账号111111");
		paymentHistory.setPayTrader("商家账号222222");*/
		int result = mapper.insertSelective(paymentHistory);
		return result==1?true:false;
	}

	@Override
	public List<TabPaymentHistory> findByorderNumber(String orderNumber) {
		TabPaymentHistoryExample paymentHistoryExample = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria = paymentHistoryExample.createCriteria();
		criteria.andOrderNumberEqualTo(orderNumber);
		List<TabPaymentHistory> list = mapper.selectByExample(paymentHistoryExample);
		return list;
	}
	
	@Override
	public String findorderNumberByserialNumber(String serialNumber) {
		TabPaymentHistoryExample paymentHistoryExample = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria = paymentHistoryExample.createCriteria();
		criteria.andSerialNumberEqualTo(serialNumber);
		List<TabPaymentHistory> list = mapper.selectByExample(paymentHistoryExample);
		return list.get(0).getOrderNumber();
	}

	@Override
	public TabPaymentHistory findorderByserialNumber(String serialNumber) {
		TabPaymentHistoryExample paymentHistoryExample = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria = paymentHistoryExample.createCriteria();
		criteria.andSerialNumberEqualTo(serialNumber);
		List<TabPaymentHistory> list = mapper.selectByExample(paymentHistoryExample);
		return list.get(0);
	}

	@Override
	public TabPaymentHistory findPaymentInfoByordernumber(String orderNumber) {
		TabPaymentHistoryExample paymentHistoryExample = new TabPaymentHistoryExample();
		TabPaymentHistoryExample.Criteria criteria = paymentHistoryExample.createCriteria();
		criteria.andOrderNumberEqualTo(orderNumber);
		List<TabPaymentHistory> list = mapper.selectByExample(paymentHistoryExample);
		TabPaymentHistory PaymentInfo = new TabPaymentHistory();
		PaymentInfo.setOrderNumber(orderNumber);//本次支付的订单号
		BigDecimal amount =new BigDecimal("0.00");
		if(list.size()!=0) {
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getIsSuccess()==1){
					amount = amount.add(list.get(i).getAmount()); 
					PaymentInfo.setPayMethod(list.get(i).getPayMethod());//本次的支付方式
					PaymentInfo.setResponseTime(list.get(i).getResponseTime());//本次的支付时间
				}
			}
			PaymentInfo.setAmount(amount);//本次的支付的总金额
		}
		return PaymentInfo;
	}
}
