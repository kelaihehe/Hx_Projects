package com.web.payment;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.wxpay.payment.WXConfig;

import com.wxpay.payment.WXPay;

import com.wxpay.payment.WXPayConfig;
import com.wxpay.payment.WXPayConstants;
import com.wxpay.payment.WXPayUtil;

import net.sf.json.JSONObject;

public class WechatPay implements PaymentInterface {
	
	//判断是PC还是移动端支付,pc代表pc端,wap代表移动端
	public String PayFrom = "pc";
	
	public String openid="";
	
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public String notify_url = "http://www.ende2019.com/payment/notify/weixin";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public String return_url = "http://www.ende2019.com/payment/result/weixin";
	
	public String orderId;
	
	public WechatPay(String from) {
		PayFrom = from;
	}
	
	public void setOpenid(String thisOpenid) {
		openid = thisOpenid;
	}
	
	@Override
	public String pay(String orderId, String amount, String subject) {
		if(PayFrom.equals("pc")) {
			return pcpay(orderId,amount,subject);
		} else {
			return mpay(orderId,amount,subject);
		}
	}
	
	/**
	    * 公众号支付
	 * @param orderId
	 * @param amount
	 * @param subject
	 * @return
	 */
	public String mpay(String orderId, String amount, String subject) {
		   Float thisAmount = Float.parseFloat(amount);
		   thisAmount = thisAmount*100;
		   int intValue = thisAmount.intValue();
		   System.out.println("转化金额"+String.valueOf(intValue));
		   WXConfig config;
			try {
				config = new WXConfig();
				 WXPay wxpay = new WXPay(config);//正式
				//WXPay wxpay = new WXPay(config,"http://yayihouse.com",false,true);//沙箱
				 Map<String, String> data = new HashMap<String, String>();

			       data.put("body", subject);//商品描述

			       data.put("out_trade_no", orderId);//商户订单号

			       data.put("device_info", "WEB");//设备号，PC网页或公众号内支付可以传"WEB"

			       data.put("fee_type", "CNY");//标价币种，默认人民币：CNY

			       data.put("total_fee", String.valueOf(intValue));//标价金额，单位为分，

			       //data.put("spbill_create_ip", "123.12.12.123");//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP

			       data.put("notify_url", notify_url);//异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。

			       data.put("trade_type", "JSAPI");  //交易类型   JSAPI：公众号支付，NATIVE：扫码支付，APP：APP支付 此处指定为扫码支付
			       data.put("openid", openid);
			       Map<String, String> resp = wxpay.unifiedOrder(data);
                //判定，如果未succss, 则返回支付二维码图片
			       System.out.println(resp.get("result_code").toString().equals("SUCCESS"));
			       System.out.println(resp.get("return_code").toString().equals("SUCCESS"));
		           System.out.println(resp);
		           //拉去二维码地址成功
		           if(resp.get("result_code").toString().equals("SUCCESS") 
		        	 && resp.get("return_code").toString().equals("SUCCESS")) {
		        	  
		        	   SortedMap<String,String> params = new TreeMap<String,String>();
		               params.put("appId", config.getAppID());
		               params.put("timeStamp", getSecondTimestampTwo(new Date()));
		               params.put("nonceStr", WXPayUtil.generateNonceStr());
		               params.put("package", "prepay_id="+resp.get("prepay_id"));
		               params.put("signType", WXPayConstants.HMACSHA256);
		               //注意paySign签名
		               String paySign =  WXPayUtil.generateSignature(params, config.getKey(), WXPayConstants.SignType.HMACSHA256);

		               //封装参数，前端调用微信支付
		               JSONObject json =new JSONObject();
		               json.put("appId", config.getAppID());
		               json.put("timeStamp", params.get("timeStamp").toString());
		               json.put("nonceStr", params.get("nonceStr").toString());
		               json.put("package", "prepay_id="+resp.get("prepay_id"));
		               json.put("signType",  WXPayConstants.HMACSHA256);
		               json.put("paySign", paySign); //paySign的生成规则和Sign的生成规则一致
		               json.put("sendUrl",return_url); //付款成功后，点击完成跳转的页面
		               return json.toString(); //这个data格式是要在前端调用的哦！大家可以注意一下
		           } else {
		        	   return "0";
		           }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return "0";
	}
	
	/**
	 * 时间戳转时间(10位时间戳)
	 * @param time
	 * @return
	 */
	public String getSecondTimestampTwo(Date date){
        if (null == date) {
            return "0";
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return String.valueOf(timestamp);
    }

	
	public String pcpay(String orderId, String amount, String subject) {
		   Float thisAmount = Float.parseFloat(amount);
		   thisAmount = thisAmount*100;
		   int intValue = thisAmount.intValue();
		   System.out.println("转化金额"+String.valueOf(intValue));
		   WXConfig config;
			try {
				config = new WXConfig();
				 WXPay wxpay = new WXPay(config);//正式
				//WXPay wxpay = new WXPay(config,"http://yayihouse.com",false,true);//沙箱
				 Map<String, String> data = new HashMap<String, String>();

			       data.put("body", subject);//商品描述

			       data.put("out_trade_no", orderId);//商户订单号

			       data.put("device_info", "WEB");//设备号，PC网页或公众号内支付可以传"WEB"

			       data.put("fee_type", "CNY");//标价币种，默认人民币：CNY

			       data.put("total_fee", String.valueOf(intValue));//标价金额，单位为分，

			       //data.put("spbill_create_ip", "123.12.12.123");//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP

			       data.put("notify_url", notify_url);//异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。

			       data.put("trade_type", "NATIVE");  //交易类型   JSAPI：公众号支付，NATIVE：扫码支付，APP：APP支付 此处指定为扫码支付

			       data.put("product_id", "12");//trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
			       Map<String, String> resp = wxpay.unifiedOrder(data);
                   //判定，如果未succss, 则返回支付二维码图片
			       System.out.println(resp.get("result_code").toString().equals("SUCCESS"));
			       System.out.println(resp.get("return_code").toString().equals("SUCCESS"));
		           System.out.println(resp);
		           //拉去二维码地址成功
		           if(resp.get("result_code").toString().equals("SUCCESS") 
		        	 && resp.get("return_code").toString().equals("SUCCESS")) {
		        	   //生成二维码
		        	   return resp.get("code_url").toString();
		           } else {
		        	   return "0";
		           }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return "0";
	}
    
	/*
	 * 微信暂时用不上该方法
	 */
	@Override
	public Map<String, String> payNotify(Map<String, String[]> param) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "0");
		result.put("order_sn", "");
		result.put("pay_sn", "");
		result.put("replay", "success");
		return result;
	}
    
	/*
	 * 支付查询，用于同步通知
	 */
	@Override
	public Map<String, String> payInquire(String orderId) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "0");
		result.put("order_sn", "");
		result.put("pay_sn", "");
		result.put("replay", "success");
		 WXConfig config;
			try {
				config = new WXConfig();
				 WXPay wxpay = new WXPay(config);//正式
				//WXPay wxpay = new WXPay(config,"http://yayihouse.com",false,true);//沙箱
				 Map<String, String> data = new HashMap<String, String>();
			     data.put("out_trade_no", orderId);//商户订单号
			    Map<String, String> resp = wxpay.orderQuery(data);
                //判定，如果未succss, 则返回支付二维码图片
			      
		           System.out.println(resp);
		           //拉去二维码地址成功
		           if(resp.get("result_code").toString().equals("SUCCESS") 
		        	 && resp.get("trade_state").toString().equals("SUCCESS")) {
		        	   //生成二维码
		        	   result.put("result", "1");
						result.put("order_sn", resp.get("out_trade_no"));
						result.put("pay_sn", resp.get("transaction_id"));
		           } else {
		        	   result.put("result", "2");
		           }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return result;
	}

	@Override
	public String refund() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String refundNotify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map[][] refundInquire(String refundId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> payNotify(String content) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "0");
		result.put("order_sn", "");
		result.put("pay_sn", "");
		result.put("replay", "success");
		WXConfig config;
		try {
			config = new WXConfig();
			WXPay wxpay = new WXPay(config);//正式
			Map<String, String> resp = wxpay.processResponseXml(content);
			//判断结果
			if(resp.get("result_code").toString().equals("SUCCESS") 
        	 && resp.get("return_code").toString().equals("SUCCESS")) {
        	   //支付成功
				result.put("result", "1");
				result.put("order_sn", resp.get("out_trade_no"));
				result.put("pay_sn", resp.get("transaction_id"));
           } else {
        	   result.put("result", "2");
        	   result.put("order_sn", resp.get("out_trade_no"));
			   result.put("pay_sn", resp.get("transaction_id"));
           }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	@Override
	public String replay(Map<String, String> payResult) {
		String content = "<xml>\n" +
                "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
		//0是未知的结果
		if(payResult.get("result").equals("0")) {
			content = "<xml>\n" +
	                "<return_code><![CDATA[FAIL]]></return_code>\n" +
	                "<return_msg><![CDATA[]]></return_msg>\n" +
	                "</xml>";
		}
		return content;
	}

	@Override
	public String getAttr(String key) {
		// TODO Auto-generated method stub
		return orderId;
	}

}
