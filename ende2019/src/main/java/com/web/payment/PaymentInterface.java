package com.web.payment;

import java.util.Map;

/**
 * 接口类，定义支付接口
 * @author ljl
 *
 */
public interface PaymentInterface {
	
	/**
	 * 获取属性
	 */
	public String getAttr(String key);
    
	/**
	 * 支付方法
	 */
	public String pay(String orderId, String amount, String subject);
	/**
	 * 支付异步通知解析
	 */
	public Map<String,String> payNotify(Map<String,String[]> param);
	/**
	 * 支付查询方法
	 * @return 
	 */
	public Map<String, String>  payInquire(String orderId);
	/**
	 * 退款
	 */
	public String refund();
	/**
	 * 退款异步通知
	 */
	public String refundNotify();
	/**
	 * 退款查询方法
	 * @return 
	 */
	public Map[][]  refundInquire(String refundId);
	public Map<String, String> payNotify(String content);
	public String replay(Map<String,String> payResult);
}
