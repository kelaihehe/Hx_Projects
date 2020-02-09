package com.web.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class Paypal implements PaymentInterface {
	
	//判断是PC还是移动端支付,pc代表pc端,wap代表移动端
	public String PayFrom = "pc";
	
	public String appid = "AdPekPGJK8CmYsktqyTvxY-4v7XT9ZL9I3byh32kFCRifRGc8jaDzN59_IOYvMRu8nAr5np-ZlhRJNWB";
	public String secret = "ENqSU6LLNfNWUjS1xE7hDPT6ia4FcGxi8fHblNXUbmMj6WVJ56QIHwEY3y4mb6TAkBm8I0iaGFxcfyyj";
	
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public String notify_url = "http://www.ende2019.com/payment/notify/paypal";

	// 同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public String return_url = "http://www.ende2019.com/payment/result/paypal";
	public APIContext apiContext;
	//订单ID
	public String orderId;
	//金额
	public String money;
	//标题
	public String subject;

	public Paypal(String from) {
		PayFrom = from;
		//apiContext = new APIContext(appid, secret, "sandbox");
		apiContext = new APIContext(appid, secret, "live");
	}
	
	/**
	 * 创建支付
	 * @return
	 * @throws PayPalRESTException
	 */
	//https://blog.csdn.net/DoveYoung8/article/details/79037443
	public Payment createPayment() throws PayPalRESTException {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		Amount amount = new Amount();
		amount.setTotal(money);
		amount.setCurrency("USD");

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(subject);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(return_url);
		redirectUrls.setReturnUrl(return_url);


		Payment payment = new Payment();
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		payment.setRedirectUrls(redirectUrls);
		payment.setIntent("sale");
		System.out.println(payment.create(apiContext).toString());
		return payment.create(apiContext);
	}
    
	/**
	 *  执行实际支付
	 * @return
	 * @throws PayPalRESTException
	 */
	public Payment executePayment() throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(orderId);
		PaymentExecution paymentExecute = new PaymentExecution();
		//paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
    }
	@Override
	public String pay(String thisOrderId, String thisAmount, String thisSubject) {
		orderId = thisOrderId;
		money = thisAmount;
		subject = thisSubject;
		Payment payment;
		String content = "<script type='text/javascript'>";
		try {
			payment = createPayment();
			//覆盖订单ID
			orderId = payment.getId();
			System.out.println("获取到的paypal支付流水号:"+orderId);
			for(Links links : payment.getLinks()){
			    if(links.getRel().equals("approval_url")){
			    	content += "window.location.href='"+links.getHref()+"'";
			    	content += "</script>";
			        return content;//客户付款登陆地址
			    }
			}
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content += "window.location.href='"+return_url+"'";
    	content += "</script>";
		return content;
	}

	@Override
	public Map<String, String> payNotify(Map<String, String[]> param) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "0");
		result.put("order_sn", "");
		result.put("pay_sn", "");
		result.put("replay", "success");
		
		return result;
	}
    
	/**
	 * 该订单ID是来之paypal回跳的
	 */
	@Override
	public Map<String, String> payInquire(String orderId) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "2");
		result.put("order_sn", "");
		result.put("pay_sn", "");
		result.put("replay", "success");
		// TODO Auto-generated method stub
		String[] info = orderId.split(",");
		 Payment payment = new Payment();
		 payment.setId(info[0]);
		 PaymentExecution paymentExecute = new PaymentExecution();
		 paymentExecute.setPayerId(info[1]);
		 try {
			 Payment thisPay = payment.execute(apiContext, paymentExecute);
			 System.out.println(thisPay);
			if(thisPay.getState().equals("approved")){
				result.put("result", "1");
			}
		} catch (PayPalRESTException e) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttr(String key) {
		// TODO Auto-generated method stub
		return orderId;
	}

}
