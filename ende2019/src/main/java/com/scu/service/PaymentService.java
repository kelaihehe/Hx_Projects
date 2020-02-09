package com.scu.service;

import java.util.List;

import com.scu.pojo.TabPaymentHistory;

public interface PaymentService {

	public boolean update(String orderNumber,byte is_success,String SerialNumber);
	
	public List<TabPaymentHistory> findByorderNumber(String orderNumber);
	
	public boolean addPaymentHistory (TabPaymentHistory paymentHistory);
	
	public boolean updateByexample(TabPaymentHistory paymentHistory,String example);
	
	public boolean updateById(TabPaymentHistory paymentHistory,Long id);
	
	public String findorderNumberByserialNumber(String serialNumber);
	
	public TabPaymentHistory findorderByserialNumber(String serialNumber);
	
	public TabPaymentHistory findPaymentInfoByordernumber(String orderNumber);
}
