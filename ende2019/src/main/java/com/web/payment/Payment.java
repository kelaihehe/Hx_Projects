package com.web.payment;

import java.util.Map;

/**
 * 对外提供统一的支付方法
 * @author ljl
 *
 */
public class Payment {
	//定义支付方式编码
    public String[] payCode = {
        "paypal",
        "weixin",
        "alipay"
    };
    //定义支付类型
    public PaymentInterface payObj;
    //定义异步通知地址
    public String notify;
    //定义同步回跳地址
    public String redirect;
    
    public Payment( String code, String from ) {
    	switch(code) {
    	    case "paypal":
    	    	payObj = new Paypal(from);
    	    	break;
    	    case "weixin":
    	    	payObj = new WechatPay(from);
    	    	break;
    	    case "alipay":
    	    	payObj = new Alipay(from);
    	    	break;
    	}
    }
    
    /**
     * 支付方法
     */
    public String pay(String orderId, String amount, String title) {
    	if(payObj == null) {
    		return "错误的支付方式选择";
    	}
        String result = payObj.pay(orderId, amount, title);
    	return result;
    }
    
    /**
                  * 支付异步通知
     * @param map
     * map=>数据结构为 result 0未知的结果,,1为成功,2为支付失败
     * map=>order_sn为我方支付单号
     * map=>pay_sn为支付平台单号
     * @return
     */
	public Map<String, String> payNotify(Map<String, String[]> map) {
		return payObj.payNotify(map);
	}
    
	/**
	     * 支付异步通知--主要用于微信
	* @param map
	* map=>数据结构为 result 0未知的结果,,1为成功,2为支付失败
	* map=>order_sn为我方支付单号
	* map=>pay_sn为支付平台单号
	* @return
	*/
	public Map<String, String> payNotify(String content) {
		return payObj.payNotify(content);
	}
	
	public Map<String, String> payInquire(String orderId) {
		return payObj.payInquire(orderId);
	}
	
	/**
               * 支付异步通知后返回给支付平台结果
	* @return
	*/
	public String replay(Map<String, String> map) {
		return payObj.replay(map);
	}
	
	/**
	 * 设置微信的openId
	 */
	public void setOpenid(String openid) {
		if(payObj instanceof WechatPay) {
			((WechatPay)payObj).openid = openid;
		}
	}
	
	/**
	 * 获取订单ID
	 */
	public String getOrderId() {
		return payObj.getAttr("orderId");
	}
}
