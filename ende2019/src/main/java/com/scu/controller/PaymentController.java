package com.scu.controller;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.payment.HttpRequestor;
import com.web.payment.Payment;
import com.wxpay.payment.WXConfig;

import net.sf.json.JSONObject;

import com.scu.service.WebUserService;
import com.scu.util.QRCodeUtil;
import com.scu.util.SendEmail;
import com.scu.util.SendEmailImpl;
import com.scu.mapper.TabPaymentHistoryMapper;
import com.scu.pojo.Msg;
import com.scu.pojo.TabPaymentHistory;
import com.scu.pojo.TabUser;
import com.scu.service.PaymentService;

@Controller
public class PaymentController {
	
	@Autowired
	private PaymentService PaymentService;
	
	
	/**
	 * 微信二维码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/payment/code", produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public void code(String code_url, HttpServletResponse response) throws Exception
	{
		ServletOutputStream sos = response.getOutputStream();
		QRCodeUtil.encode(code_url, sos);
	}
	
	/**
	 * 支付方法
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/payment/pay", produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//return "xxx";
		//支付简码，alipay代表阿里支付,weixin代表微信,paypal代表贝宝
		String code = new String((request.getParameter("code")).getBytes("ISO-8859-1"),"UTF-8");
		//支付方式,是来自PC还是mobile移动端
		String from = new String((request.getParameter("from")).getBytes("ISO-8859-1"),"UTF-8");
		//前端传来的单号
		String orderId = new String((request.getParameter("orderId")).getBytes("ISO-8859-1"),"UTF-8");
		Payment thisPay = new Payment(code, from);
		if(code.equals("weixin") && from.equals("mobile")) {
			thisPay.setOpenid(request.getParameter("openid"));
		}
		//TODO传入订单ID，支付金额, 商品简略信息
		List<TabPaymentHistory> orderList = PaymentService.findByorderNumber(orderId);
		if(orderList.isEmpty()) {
			return ( code.equals("weixin")? "0":"未知的订单信息" );
		}
		//判断订单是否已经支付
		if(orderList.get(0).getIsSuccess().intValue() == 1) {
			return ( code.equals("weixin")? "0":"订单已经支付过了");
		}
		BigDecimal amounts = new BigDecimal("0.00");
		BigDecimal amount = new BigDecimal("0.00");
		if(!code.equals("paypal")) {
			for(TabPaymentHistory order : orderList) {//其内部实质上还是调用了迭代器遍历方式，这种循环方式还有其他限制，不建议使用。
				amounts = amounts.add(order.getAmount()); 
			}
			System.out.println("订单:"+orderId+"计算支付金额:"+String.valueOf(amounts));
		}else {
			for(TabPaymentHistory order : orderList) {//其内部实质上还是调用了迭代器遍历方式，这种循环方式还有其他限制，不建议使用。
				amount = order.getAmount();
				if(amount.compareTo(BigDecimal.valueOf(3500))==0) {
					amount = BigDecimal.valueOf(500);
					//amount = BigDecimal.valueOf(0.5);
				}else if(amount.compareTo(BigDecimal.valueOf(2500))==0) {
					amount = BigDecimal.valueOf(350);
					//amount = BigDecimal.valueOf(0.350);
				}else if(amount.compareTo(BigDecimal.valueOf(2600))==0) {
					amount = BigDecimal.valueOf(365);
					//amount = BigDecimal.valueOf(0.365);
				}else if(amount.compareTo(BigDecimal.valueOf(1200))==0) {
					amount = BigDecimal.valueOf(150);
					//amount = BigDecimal.valueOf(0.150);
				}else if(amount.compareTo(BigDecimal.valueOf(350))==0)  {
					amount = BigDecimal.valueOf(50);
					//amount = BigDecimal.valueOf(0.05);
				}
				order.setAmount(amount);
				PaymentService.updateById(order,order.getId()); //由于是paypal支付方式，更新金额
				amounts = amounts.add(amount);
			}
			System.out.println("Paypal:"+orderId+"计算支付金额:"+String.valueOf(amounts));
		}
		
		String content = thisPay.pay(orderId, String.valueOf(amounts), "ENDE注册支付");
		//如果是贝宝支付，则只能先获取流水号，通过流水号来操作
		TabPaymentHistory paymentHistory = new TabPaymentHistory();
		paymentHistory.setOrderNumber(orderId);
		if(code.equals("paypal")) {
			String paysn = thisPay.getOrderId();
			System.out.println("修改支付流水号:"+paysn);
			//通过订单号来修改支付流水号
			paymentHistory.setSerialNumber(paysn);
		}
		//所有订单编辑支付方式
		paymentHistory.setPayMethod(code);
		PaymentService.updateByexample(paymentHistory, "order_number");
        return content;
	}
	
	
	/**
	 * 支付成功后的异步通知地址
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@RequestMapping(value="/payment/notify/{code}", produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public void notify(@PathVariable(value="code")String code,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 主要用于微信支付和阿里云支付
		PrintWriter out = response.getWriter();
		Payment thisPay = new Payment(code, "pc");
		if( code.equals("weixin")) {
			BufferedReader br = request.getReader();
			String str=""; 
			String content = "";
			while((str = br.readLine()) != null){
				content += str;
			}
			//最终获取到的xml
			content = content.replaceAll(" ", "");
			System.out.print("微信异步回调:"+content);
			Map<String, String> payResult = thisPay.payNotify(content);
			System.out.print("微信异步回调支付结果解析:"+payResult);
			if(payResult.get("result").equals("1")) {
				boolean result = PaymentService.update(payResult.get("order_sn"), (byte)1, payResult.get("pay_sn"));
				if(!result) {
					System.out.println("weixin异步订单修改失败:"+payResult.get("order_sn"));
				} else {
					System.out.println("weixin异步订单修改支付成功:"+payResult.get("order_sn"));
				}
			} else {
				System.out.print("微信异步回调判定支付失败:"+payResult);
			}
			out.println(thisPay.replay(payResult));
		} else {
			//贝宝特殊，异步通知再主动查询支付结果就好，比较方便
			//todo
			Map<String,String[]> map = (Map<String,String[]>)request.getParameterMap();
			//设置页面的文档类型和字符集，页面中的字符所采用的字符集
			//response.setContentType("text/html; charset=UTF-8");
			System.out.print("pay code:"+code);
			//设置页面的编码方式，即以什么样的编码方式来保存页面
			response.setCharacterEncoding("utf-8");
			
			String fileName = "temp.txt";//文件的相对路径
			String filePath = request.getServletContext().getRealPath(fileName);//文件的绝对路径
			//使用文件的绝对路径打开文件
			File file = new File(filePath);
			//使用打开的文件对象，创建FileWriter类的实例
			FileWriter writer = new FileWriter(file);
			//使用打开文件对应的writer对象，创建BufferderWriter类的实例
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			String content = getMapToString(map);
			bufferWriter.write(content);
			bufferWriter.flush();
			bufferWriter.close();
			writer.close();
			System.out.print("支付宝异步回调:"+content);
			//初始化支付对象,不用管来自哪里,进行二次校验
			Map<String, String> payResult = thisPay.payNotify(map);
			System.out.print("支付宝异步回调解析结果:"+payResult);
			if(payResult.get("result").equals("1")) {
				//支付成功。修改订单状态
				boolean result = PaymentService.update(payResult.get("order_sn"), (byte)1, payResult.get("pay_sn"));
				if(!result) {
					System.out.println("支付宝异步订单修改失败:"+payResult.get("order_sn"));
				} else {
					System.out.println("支付宝异步订单修改支付成功:"+payResult.get("order_sn"));
				}
			} else {
				System.out.print("支付宝异步回调判定支付失败:"+payResult);
			}
			out.println(thisPay.replay(payResult));
		}
	}
	
	/**
	 *
	 * map转str
	 * @param map
	 * @return
	 */
	private String getMapToString(Map<String,String[]> map){
		Set<String> keySet = map.keySet();
		//将set集合转换为数组
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		//给数组排序(升序)
		Arrays.sort(keyArray);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyArray.length; i++) {
			// 参数值为空，则不参与签名 这个方法trim()是去空格
			if (map.get(keyArray[i]).length > 0) {
				String unit = map.get(keyArray[i])[0];
				sb.append(keyArray[i]+"="+unit);
			}
			if(i != keyArray.length-1){
				sb.append("&");
			}
		}
		return sb.toString();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@RequestMapping(value="/payment/result/{code}",produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public void result(@PathVariable(value="code")String code,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("pay result:"+code);
		//主要用于贝宝和支付宝PC支付，微信是二维码扫码支付
		String thisOrderId = "";
		if(code.equals("paypal")) {
			//判断，待支付，则主动查询
			String paysn = request.getParameter("paymentId");
			String payerId = request.getParameter("PayerID");
			//订单查询
			TabPaymentHistory order = PaymentService.findorderByserialNumber(paysn);
			thisOrderId = order.getOrderNumber();
			System.out.println(order.getOrderNumber());
			Payment thisPay = new Payment(code, "pc");
			Map<String, String> payResult = thisPay.payInquire(paysn+","+payerId);
			System.out.println(payResult);
			if(payResult.get("result").equals("1")) {
//				TabPaymentHistory paymentHistory = new TabPaymentHistory();
//				paymentHistory.setOrderNumber(order.getOrderNumber());
//				paymentHistory.setIsSuccess((byte)1);
//				paymentHistory.setResponseTime(new Date());
//				//支付成功。修改订单状态
//				boolean result = PaymentService.updateByexample(paymentHistory, "order_number");
				boolean result = PaymentService.update(order.getOrderNumber(), (byte)1, "");
				if(!result) {
					System.out.println("paypal同步订单修改失败:"+order.getOrderNumber());
				} else {
					System.out.println("paypal同步订单支付成功:"+order.getOrderNumber());
				}
			}
		} else if( code.equals("alipay") ) {
			//判断订单状态，如果未支付，则去支付宝那边查询状态
			//我们的订单号
			String orderId = request.getParameter("out_trade_no");
			//返回的支付流水号
			String paysn = request.getParameter("trade_no");
			thisOrderId = orderId;
			List<TabPaymentHistory> orderList = PaymentService.findByorderNumber(orderId);
			//查询到又订单
			if(!orderList.isEmpty()) {
				TabPaymentHistory order = orderList.get(0);
				//如果已经支付成功
				if(order.getIsSuccess().intValue() == 1) {
					
				} else {
					//如果没有支付成功，则查询并修改订单状态
					Payment thisPay = new Payment(code, "pc");
					Map<String, String> payResult = thisPay.payInquire(orderId);
					System.out.println(payResult);
					if(payResult.get("result").equals("1")) {
						boolean result = PaymentService.update(orderId, (byte)1, payResult.get("pay_sn"));
						if(!result) {
							System.out.println("alipay同步订单修改失败:"+orderId);
						} else {
							System.out.println("alipay同步订单支付成功:"+orderId);
						}
					}
				}
			}
		}
//		Boolean result = PaymentService.update("EN201901231213560001");
//		System.out.println(result);
		//TODO,根据支付方式查询支付结果
		String url = request.getScheme()+"://"+ request.getServerName()+"/paySuccess.html?orderId="+thisOrderId;
        response.sendRedirect(url);
	}
	
	
	/**
	 * 用以前端轮询查询订单状态，向微信请求
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/payment/weixin", produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public String weixin(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String message = "0";
		String orderId = request.getParameter("orderId");
		List<TabPaymentHistory> orderList = PaymentService.findByorderNumber(orderId);
		if(orderList.isEmpty()) {
			return message;
		}
		if(orderList.get(0).getIsSuccess().intValue() == 1) {
			message = "1";
			return message;
		} else {
			Payment thisPay = new Payment("weixin", "pc");
			Map<String, String> payResult = thisPay.payInquire(orderId);
			if(payResult.get("result").equals("1")) {
				boolean result = PaymentService.update(orderId, (byte)1, payResult.get("pay_sn"));
				if(!result) {
					message = "1";
					System.out.println("alipay同步订单修改失败:"+orderId);
				} else {
					System.out.println("alipay同步订单支付成功:"+orderId);
				}
			}
		}
		return message;
	}
	
	/**
	 * 用以处理微信，获取openid
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/payment/openid", produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public void openid(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WXConfig config = new WXConfig();
        String appid = config.getAppID();
        String returnUrl = request.getScheme()+"://"+ request.getServerName()+"/payment/wx";
        returnUrl = URLEncoder.encode(returnUrl,   "utf-8");
        String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_base&state=ende#wechat_redirect";
        response.sendRedirect(requestUrl);
        //return returnUrl;
	}
	
	
	/**
	 * 用以处理微信，获取openid
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/payment/wx", produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public void wx(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String code = request.getParameter("code");
		WXConfig config = new WXConfig();
        String appid = config.getAppID();
        String secret = config.getAppSecret();
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        //第一次请求 获取access_token 和 openid
        String  oppid = new HttpRequestor().doGet(requestUrl);
        JSONObject oppidObj =JSONObject.fromObject(oppid);
        String access_token = (String) oppidObj.get("access_token");
        String openid = (String) oppidObj.get("openid");
        //静默授权不用获取用户信息
//        String requestUrl2 = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
//        String userInfoStr = new HttpRequestor().doGet(requestUrl2);
//        JSONObject wxUserInfo =JSONObject.fromObject(userInfoStr); 
        //拿到openID写入session,进行回跳
        String url = request.getScheme()+"://"+ request.getServerName()+"/order_m.html"+"?openid="+openid;
        response.sendRedirect(url);
	}
	
	/**
	 * 订单支付结果查询，用于订单支付成功页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payment/order", produces={"text/html;charset=UTF-8;"})
	@ResponseBody
	public String order(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String orderId = request.getParameter("orderId");
		List<TabPaymentHistory> orderList = PaymentService.findByorderNumber(orderId);
		if(orderList.isEmpty()) {
			//订单不存在
			return "-1";
		}
		//支付成功
		if(orderList.get(0).getIsSuccess().intValue() == 1) {
			return "1";
		} else {
			//支付失败
			return "0";
		}
	}
	
	
}
