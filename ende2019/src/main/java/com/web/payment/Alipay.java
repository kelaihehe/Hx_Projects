package com.web.payment;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

public class Alipay implements PaymentInterface {
	
	//判断是PC还是移动端支付,pc代表pc端,wap代表移动端
	public String PayFrom = "pc";
	
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public String app_id = "2018111262156055";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCHwSmip7y8RoGr6J14NJ+7/ChdDHj5gxByatIP59HweprL8Wup90xrVqSwzViCui1vXixbJvc8vNVAU3AXOsmMaUlf4Ocg58jONkRPawzV7zDoax8U8OrZBT8UStxvdTw8R3jT2EVq6sohmcqxlY5ZfaDXrz6U0uMgq1DfKbeAuvgnTdfxI+9zxWEuAil6XX5wPsxanzKJ+yTYU6Lnfn2kQDxXm5iob+eleXtvFjOH7Lsmt7vg/mlN6Lk4rnDWoEF2ggyQ2svNZQWzLUPIPe9nmfG9252xcr2Z8uIGir44bz3eimtmijsXMx/N2iCKI5WAJgqLA4Pjk36prSESD1glAgMBAAECggEAT/xZ4cJfC7enM3jNP0QSUZqS1bBBnxHAIQobVs/J/JzglAVc4DobRqs4QgvbeLrlhlbAX7yns5u3Mj13L9itZBOs7db/l3257PZAbWKvn2VdNbPYDmiclt3merhZBTrYENQztu4YiJRqID4TFWr/Am9o02wnvVw8AsWt0q8rtaaYSKiR8HB+P65fDB7GIYabu9p0c1s5wezmy86h1nQJ+4CuH01fhWoi8iWPz+4ANzTaeDEA2+8dy1Xcrm51KuXBcmC/aEHFsvT+nt+X84xciWPVSPIxG9sxUuyZFpHHeMCIcQ/FOGtCNnhXyNubMIvLgW1vivfQ275Texk9rmd/AQKBgQC/lFgGxVWGVLWBky0p7bKXaBqawNnoPjwb3bvGj3fTIog2QZC+6945SGHgvMLE14vLhyae6Gt3e4zqC7uyFnQZiWzoKHskFxf03gJlIhxjOuNrs5h8PVIpBhoH3y+ae/moMkT14//fMkbx+YuQhXS+cuuYWCaiUC15DFcebcY3oQKBgQC1Z0PD7ncbHWnp2eHAjUkvcEQ5/sG3Tq96Go1f9o6E9YJWuunh0dODB6iEWDPmhRD1DYEdhYxsOthlFbkwRN3o48y4eB44V1vdt+psYoF+B0raO3wXxh8j+kN1Pd1jBRuVve52Q7Nn74Nf7cTgUQLTMsXWDYdK6WQygGmQFSECBQKBgGS8v1DIIQGit1JsLnp2p8sfjaXENJD/cKIChUOSmyJlbRSCg3cxFcgvPHBtMUlKQhMCfRdgBWptiBplkwvA7MmkzqSA7Md8cTdsKfw95Il1JLK0XEEwCWzS7YsoG6Ly/8Mms8ZMXnZhvJZubqnO0yV1zlZsO9xrpo2ZJJI2jN0hAoGACUL2cMByEE5WI/xdzEjqXaOruBJLraI0z1UftrCN4jfuqaLtYfwmgmAXuHoQXEqa7Qf9eZZh7vyqI68SP2aktck7oIdxc4ryQ8WqEFKPLfINXkr/MO+Kk3mpjAjWjsGqAOErlZorRLkzyegHzzQTotjKxPz31VlJJlLsENEjWEkCgYA4akDlquZ/HPvs9xyIBIxxIt8ByupsljK6wIspZMXGPJT5Wpn6MH2jzPoODfNuachzoprC3q1ZHOQf7y4wjz8hfPR4E/JIfdSCTGYfkvcraRkN3351NlI/KkpxpR4lM06gGT2G/W4DzT9GC2+34BEqw+0D2lqBfRzO9e+8LydKVw==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiAsAdTp2lMCL4Cc98Lx770QwErFDmBMmEbWKaZSio1OIJAlyvHO7HvSPGk0Hk2SYw1spycMwF+IVmQR7xuBDlrrvTTJBoQd2Z8F8tMNol+Fus7s+zgfbxHsCjfx5hbUuIcRYhbaGMTqRUXP5tir9nxk9uWWRPmB2zJCI1W0bAqRxs9nhHdFsV+Dfxr8tVi+JU0S3Z8WeNJ2wp76HcQ4EeaZ5zxpB6nEDisgrjXRywJcI2st5ZRrzz1vMQF4aE2K7LYriD9H0kTVmefYboXQFNGMG2I6+m08tcZMcWcma7LZEw4WT5EbZIzhwPJG2EAIGtyx4pH9SYSJ/kAuPl2/uXwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public String notify_url = "http://www.ende2019.com/payment/notify/alipay";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public String return_url = "http://www.ende2019.com/payment/result/alipay";

	// 签名方式
	public String sign_type = "RSA2";
	
	// 字符编码格式
	public String charset = "utf-8";
	
	// 支付宝网关
	public String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
	//订单ID
	public String orderId;
	
	
	public Alipay(String from) {
		PayFrom = from;
	}

	@Override
	public String pay(String orderId, String amount, String subject) {
		String result = "";
		if(PayFrom.equals("pc")) {
			result = pcpay(orderId, amount,subject);
		} else {
			result = mpay(orderId, amount,subject);
		}
		return result;
	}
	
	public String pcpay(String orderId, String amount, String subject) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json",charset, alipay_public_key, sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(return_url);
		alipayRequest.setNotifyUrl(notify_url);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = orderId;
		//付款金额，必填
		String total_amount = amount;
		//订单名称，必填
		//商品描述，可空
		String body = subject;
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//请求
		String result = "";
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return result;
	}
	
	
	public String mpay(String orderId, String amount, String subject) {
		// 商户订单号，商户网站订单系统中唯一订单号，必填
	    String out_trade_no = orderId;
		System.out.println(subject);
	    // 付款金额，必填
	    String total_amount=amount;
	    // 商品描述，可空
	    String body = subject;
	    // 超时时间 可空
	   String timeout_express="2m";
	    // 销售产品码 必填
	    String product_code="QUICK_WAP_WAY";
	    /**********************/
	    // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
	    //调用RSA签名方式
	    AlipayClient client = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json",charset, alipay_public_key, sign_type);
	    AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
	    
	    // 封装请求支付信息
	    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
	    model.setOutTradeNo(out_trade_no);
	    model.setSubject(subject);
	    model.setTotalAmount(total_amount);
	    model.setBody(body);
	    model.setTimeoutExpress(timeout_express);
	    model.setProductCode(product_code);
	    alipay_request.setBizModel(model);
	    // 设置异步通知地址
	    alipay_request.setNotifyUrl(notify_url);
	    // 设置同步地址
	    alipay_request.setReturnUrl(return_url);   
	    
	    // form表单生产
	    String form = "";
		try {
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return form;
	}
    
	/**
	 * 支付宝异步通知，返回Map[
	 */
	@Override
	public Map<String, String> payNotify(Map<String,String[]> param) {
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = param;
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
//			try {
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			params.put(name, valueStr);
		}
		System.out.println("支付宝签名验证的参数:-----");
		System.out.println(params);
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, alipay_public_key, charset, sign_type);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "0");
		result.put("order_sn", "");
		result.put("pay_sn", "");
		result.put("replay", "success");
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = requestParams.get("out_trade_no")[0];
		
			//支付宝交易号
			String trade_no = requestParams.get("trade_no")[0];
		
			//交易状态
			String trade_status = requestParams.get("trade_status")[0];
			
			result.put("order_sn", out_trade_no);
			result.put("pay_sn", trade_no);
			if(trade_status.equals("TRADE_FINISHED")){
				result.put("result", "1");
			}else if (trade_status.equals("TRADE_SUCCESS")){
				result.put("result", "1");
			} else {
				result.put("result", "2");
			}
			
		}else {//验证失败
			result.put("replay", "fail");
		
		}
		return result;
	}

	@Override
	public Map<String, String> payInquire(String orderId) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "0");
		result.put("order_sn", "");
		result.put("pay_sn", "");
		result.put("replay", "success");
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type);
		
		//设置请求参数
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
		
		//商户订单号，商户网站订单系统中唯一订单号
		String out_trade_no = orderId;
		//支付宝交易号
		String trade_no = "";
		//请二选一设置
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","+"\"trade_no\":\""+ trade_no +"\"}");
		
		//请求
		try {
			AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayRequest);
			result.put("pay_sn", alipayTradeQueryResponse.getTradeNo());
			//字符串专json
			if(alipayTradeQueryResponse.getTradeStatus().equals("TRADE_SUCCESS")
			|| alipayTradeQueryResponse.getTradeStatus().equals("TRADE_FINISHED")) {
				result.put("result", "1");
			} else {
				result.put("result", "2");
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String replay(Map<String, String> payResult) {
		String content = "success";
		//0是未知的结果
		if(payResult.get("result").equals("0")) {
			content = "fail";
		}
		return content;
	}

	@Override
	public String getAttr(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
