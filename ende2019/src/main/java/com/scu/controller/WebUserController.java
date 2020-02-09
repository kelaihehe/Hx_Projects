package com.scu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paypal.api.payments.PaymentHistory;
import com.scu.pojo.Msg;
import com.scu.pojo.TabConferenceUser;
import com.scu.pojo.TabPaper;
import com.scu.pojo.TabPaymentHistory;
import com.scu.pojo.TabUser;
import com.scu.service.ConferenceService;
import com.scu.service.PaperService;
import com.scu.service.PaymentService;
import com.scu.service.WebUserService;
import com.scu.util.SendEmail;
import com.scu.util.SendEmailImpl;


@Controller
public class WebUserController {

	@Autowired
	private WebUserService webUserService;
	@Autowired
	private PaperService paperService;
	@Autowired
	private ConferenceService conferenceService;
	@Autowired
	private PaymentService paymentService;
	
		
	@RequestMapping("/register")
	@ResponseBody
	public Msg register(Model model,HttpServletResponse response,TabUser tabUser) throws Exception
	{
		System.out.println(tabUser.getName());
		Map<String,String> errors = webUserService.registerFormFormatJudge(tabUser);
		/*response.setHeader("Access-Control-Allow-Origin", "*");*/ //跨域访问
		//若注册表格填入格式错误,则再次返回到注册界面
		if(errors.size()!=0)
		{
			Msg message = Msg.fail();
			message.setMsg(errors);
			return message;
		}
		//将注册信息插入数据库中
		String code = UUID.randomUUID().toString().replace("-", "");
		tabUser.setCode(code);
		//若成功添加数据，则通过邮箱发送激活码
		if(webUserService.addUser(tabUser))
		{
			SendEmail sendEmail = new SendEmailImpl();
			//获取email配置文件
			Properties pro  = new Properties();
			pro.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
			String from = pro.getProperty("from");
			String []to = {tabUser.getEmail()};
			String subject = pro.getProperty("subject");
			String content = pro.getProperty("content");
			content = MessageFormat.format(content, code);
			sendEmail.sendSimpleMail(from, to, subject, content);
		}		
		return Msg.success();
	}
	
	@RequestMapping("/active")
	public String active(Model model,String code)
	{		
		return webUserService.activeEmail(code)?"activeSuccess":"activeError";
	}

	@RequestMapping("/login")
	@ResponseBody
	public Msg login(Model model,String email,String password,HttpSession session) throws Exception
	{
		Map<String,String> errors = webUserService.loginFormFormatjudge(email, password);
		//若注册表格填入格式错误,则再次返回到注册界面
		if(errors.size()!=0)
		{
			Msg message = Msg.fail();
			System.out.println(errors.get("email"));
			message.setMsg(errors);
			System.out.println("end...");
			return message;
		}
		TabUser user = webUserService.findUserByEmail(email).get(0);
		System.out.println(user.getName());
		session.setAttribute("user", user);
		webUserService.recordLastLoginTime(user);
		session.setAttribute("paperList",webUserService.findAllSubmitPaper(user));
		return Msg.success().addData("user", user);
	}
	
	@RequestMapping("contribute")
	@ResponseBody
	public Msg contribute(Model model,TabPaper paper,MultipartFile file,HttpServletRequest request) throws UnsupportedEncodingException
	{
		Cookie[] cookies = request.getCookies();
		String userid = null;
		if(cookies != null && cookies.length>0)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals("userid"))
				{
					userid = URLDecoder.decode(c.getValue(),"utf-8");					
				}
			}
		}
		String originalFilename=file.getOriginalFilename();
     
        //插入数据库
        //paper.setPaperPath(pic_path+newFileName);
        paper.setCommitUserId(Long.valueOf(userid));
        long paperId = paperService.addPaper(paper);
        //存储的物理路径
        //String pic_path="E:\\Test\\conferencePaper\\";
		String pic_path="/mnt/ENDE2019/";
        //文件名称
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_");
        String strID = ""+paperId;  //设置编号
        String number = "0000"+strID;
        number = number.substring(strID.length(),number.length());
        String newFileName=sdf.format(new Date())+number+"_"+paper.getTitle()+originalFilename.substring(originalFilename.lastIndexOf("."));
        
        File newFile=new File(pic_path+newFileName);
        //将获取的文件写入磁盘
        try {
        	file.transferTo(newFile);
		} catch (Exception e){
			e.printStackTrace();
		}
        
        //更新文件名
        TabPaper paper2 = new TabPaper();
        paper2.setId(paperId);
        paper2.setPaperPath(pic_path+newFileName);
        paperService.updatePaperByPaperId(paper2);
		return Msg.success().addData("strNumber", number); //传入编号
	}
	
	@RequestMapping("/SendEmailAfterContribute")
	@ResponseBody
	public Msg sendEmailAfterContribute(Model model,String email,String strNumber) throws Exception
	{
		System.out.println("email:"+email);
		SendEmail sendEmail = new SendEmailImpl();
		//获取email配置文件
		Properties pro  = new Properties();
		pro.load(this.getClass().getClassLoader().getResourceAsStream("uploadEmail.properties"));
		String from = pro.getProperty("from");
		String []to = {email};
		String subject = pro.getProperty("subject");
		String content = pro.getProperty("content");
		content = MessageFormat.format(content, "A"+strNumber,"A"+strNumber);
		sendEmail.sendSimpleMail(from, to, subject, content);
		return Msg.success();
	}
	
	@RequestMapping("/queryPapers")
	@ResponseBody
	public Msg queryPapers( @RequestParam(value="page",defaultValue="1") Integer pn,HttpServletRequest request) throws UnsupportedEncodingException
	{
		String userid = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length>0)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals("userid"))
				{
					userid = URLDecoder.decode(c.getValue(),"utf-8");					
				}
			}
		}
		//先引入分页插件
		//在查询之前只需要调用startPage，传入页码和每页的记录数量
		
		List<TabPaper> paperList = new ArrayList<TabPaper>();
		if(webUserService.getRoleAuthority(userid)==2)//如果是编辑的权限
		{
			PageHelper.startPage(pn, 6);
			paperList = paperService.findAllPapers();
		}
		else if(webUserService.getRoleAuthority(userid)==1)
		{
			PageHelper.startPage(pn, 6);
			paperList = paperService.findPapersByuserId(Long.valueOf(userid));
		}
		
		PageInfo pageInfo = new PageInfo(paperList,5);
		
		return Msg.success().addData("PageInfo", pageInfo);
	}
		
	@RequestMapping("/queryPaperById")
	@ResponseBody
	public Msg queryPaperById(String paperId,HttpServletRequest request)
	{
		TabPaper paper = paperService.findPaperByPaperId(paperId);
		return Msg.success().addData("Paper", paper);
	}
	
	@RequestMapping("/queryPapersByUser")
	@ResponseBody
	public Msg queryPapersByUser(HttpServletRequest request) throws UnsupportedEncodingException
	{
		String userid = null;
		List<TabPaper> paperList = new ArrayList<TabPaper>();
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length>0)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals("userid"))
				{
					userid = URLDecoder.decode(c.getValue(),"utf-8");					
				}
			}
		}
		System.out.println("userId:"+userid);
		paperList = paperService.findPapersByuserId(Long.valueOf(userid));
		return Msg.success().addData("PaperList", paperList);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatePaper/{id}",method=RequestMethod.PUT)
	public Msg updatePaper(TabPaper paper)
	{
		if(paperService.updatePaperByPaperId(paper))
		{
			return Msg.success();
		}
		else
		{
			return Msg.fail();
		}
		
	}
	
	@RequestMapping("/deletePaperById")
	@ResponseBody
	public Msg deletePaperById(String paperId,HttpServletRequest request)
	{
		if(paperService.deletePaperByPaperId(paperId))
			return Msg.success();
		else
			return Msg.fail();
	}
	
	@RequestMapping("/showPaper")
	public String showPaper(String paperPath,HttpServletResponse response) throws Exception
	{
		
		/**
		 * 使用腾讯云服务器时，不需要加下面这句，而且需要配置server.xml中的connect标签，添加URIEncoding="UTF-8"
		 * String filePath = new String(paperPath.getBytes("ISO8859-1"), "UTF-8");
		 * 再本地测试时，加上这句
		 */
		String filePath = paperPath;
		System.out.println("real paperPath:"+filePath);
		int fileNameBegin = filePath.lastIndexOf("_");
		String fileName = filePath.substring(fileNameBegin+1);
		System.out.println(fileName);
		System.out.println("filename="+URLEncoder.encode(fileName,"UTF-8"));
		File file = new File(filePath);
		InputStream in = null;
		OutputStream os = null;
		try {
		response.setContentType("application/pdf"); // 设置返回内容格式
//		response.setHeader("Content-Disposition","attachment;filename='mypdf.pdf'");
		response.setHeader("Content-Disposition","filename="+URLEncoder.encode(fileName,"UTF-8"));
		in = new FileInputStream(file);//用该文件创建一个输入流
		os = response.getOutputStream();//创建输出流
		byte[] b = new byte[1024];
		while (in.read(b) != -1) {
			os.write(b);
		}
		in.close();
		os.flush();
		os.close();
		} catch (Exception e) {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if (null != os) {
					os.close();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
		
	@ResponseBody
	@RequestMapping("ConferenceRegister")
	public Msg ConferenceRegister(TabConferenceUser conferenceUser,HttpServletRequest request) throws UnsupportedEncodingException
	{
		String userid = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length>0)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals("userid"))
				{
					userid = URLDecoder.decode(c.getValue(),"utf-8");					
				}
			}
		}
		conferenceUser.setRegistUserId(Long.valueOf(userid));
		conferenceService.AddConference(conferenceUser);
		return Msg.success();
	}
	
	
	@ResponseBody
	@RequestMapping("Generate_Order")
	public Msg GenerateOrder(@Param("orders") String orders)
	{
		JSONObject json_orders = new JSONObject(orders);
		JSONArray orderArray =  json_orders.getJSONArray("orderuserArray");
		long conferenceUserId;
		String printPoster;
		long userId;
		String orderNumber ="ENDE";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String Date = sdf.format(new Date());
			orderNumber += Date;
			Random random = new Random();
			for (int j = 0; j < 4; j++) {
				orderNumber += random.nextInt(10);
			}
		Date createDate = new Date();
		for(int i=0;i<orderArray.length();i++){
			JSONObject jsonTemp = orderArray.getJSONObject(i);
			conferenceUserId = jsonTemp.getLong("conferenceUserId");
			printPoster = jsonTemp.getString("printPoster");
			userId = jsonTemp.getLong("userId");
			
			TabPaymentHistory history = genPaymenthistory(conferenceUserId,printPoster,userId,createDate,orderNumber);
			paymentService.addPaymentHistory(history);
		}
		
		System.out.println(orders);	
		
		return Msg.success().addData("orderNumber", orderNumber).addData("creationTime", sdf.format(createDate));
	}
	
	/**
	 * 
	 * @Title: genPaymenthistory
	 * @Description: 按照规则构造一个paymenthistory
	 * @throws
	 */
	public TabPaymentHistory genPaymenthistory(long conferenceUserId,String printPoster,long userId,Date createDate,String orderNumber)
	{
		TabPaymentHistory history = new TabPaymentHistory();
		history.setConferencUserId(conferenceUserId);
		history.setUserId(userId);
		history.setPrintPoster(Byte.valueOf(printPoster));
		TabConferenceUser conferenceUser = conferenceService.findConferenceUserById(conferenceUserId);
		//正式上线
		long printFee;
		if(printPoster=="0") {
			printFee = 0;
		}
		else
			printFee = 100;
		int isStudent = conferenceUser.getIsStudent();
		switch(isStudent) {
			case 0: 
				history.setAmount((BigDecimal.valueOf(3500)));
				break;
			case 1: 
				history.setAmount((BigDecimal.valueOf(2500+printFee)));
				break;
			case 2: 
				history.setAmount((BigDecimal.valueOf(1200)));	
				break;
			case 3: 
				history.setAmount((BigDecimal.valueOf(350)));	
				break;
			default:
				break;	
		}
		//PayTest
		/*double printFee;
		if(printPoster=="0") {
			printFee = 0;
		}
		else
			printFee = 0.1;
		int isStudent = conferenceUser.getIsStudent();
		switch(isStudent) {
			case 0: 
				history.setAmount((BigDecimal.valueOf(3.5)));
				break;
			case 1: 
				history.setAmount((BigDecimal.valueOf(2.5+printFee)));
				break;
			case 2: 
				history.setAmount((BigDecimal.valueOf(1.2)));	
				break;
			case 3: 
				history.setAmount((BigDecimal.valueOf(0.35)));	
				break;
			default:
				break;	
		}*/
		history.setOrderNumber(orderNumber);
		history.setCreateTime(createDate);
		return history;
	}
	
	@RequestMapping("/queryPaymentInfoByOrder")
	@ResponseBody
	public Msg queryPaymentInfoByOrder(String orderId,HttpServletRequest request) 
	{
		TabPaymentHistory paymentHistory = paymentService.findPaymentInfoByordernumber(orderId);
		return Msg.success().addData("paymentHistory", paymentHistory);
	}
	
	
	@RequestMapping("/queryPaymentdetails")
	@ResponseBody
	public Msg queryPaymentdetails(String orderId,HttpServletRequest request) 
	{
		//订单号   是否打印海报 支付金额 支付方式 支付时间
		//注册人姓名 注册类型
		long id;
		String name;
		String registerType = ""; //注册类型
		List<TabPaymentHistory> list = paymentService.findByorderNumber(orderId);
		if(list.size()!=0) {
			for(int i=0;i<list.size();i++) {
				id = list.get(i).getConferencUserId();
				name = conferenceService.findConferenceUserById(id).getName();
				byte isStudent = conferenceService.findConferenceUserById(id).getIsStudent();
				
				switch(isStudent) {
				case 0: 
					registerType = "Regular";
					break;
				case 1: 
					registerType = "Student";
					break;
				case 2: 
					registerType = "Accompanying person";	
					break;
				case 3: 
					registerType = "Banquet ticket only";	
					break;
				default:
					break;	
				}
				list.get(i).setDescription(name);  //将注册人姓名存放在Description字段
				list.get(i).setPayAccount(registerType);  //将注册类型存放在PayAccount字段
			}
		}
		return Msg.success().addData("paymentHistoryList", list);
	}
	
	@RequestMapping("/SendEmailAfterPaySuccess")
	@ResponseBody
	public Msg SendEmailAfterPaySuccess(Model model,String orderNumber) throws Exception
	{
		String ConferenceUserName;  //会议注册用户账号
		String responseTime;        //支付时间
		String payMethod;			//支付方式
		String amount;				//支付金额
		String serialNumber;        //账单号
		
		TabPaymentHistory history;
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
		SendEmail sendEmail = new SendEmailImpl();
		//获取email配置文件
				Properties pro  = new Properties();
				pro.load(this.getClass().getClassLoader().getResourceAsStream("paySuccessEmail.properties"));
				String from = pro.getProperty("from");
				String subject = pro.getProperty("subject");
				String content = pro.getProperty("content");
		List<TabPaymentHistory> list = paymentService.findByorderNumber(orderNumber);
		if(list.size()!=0) {
			for(int i=0;i<list.size();i++) {
				history = list.get(i);
				TabConferenceUser conferenceUser = conferenceService.findConferenceUserById(history.getConferencUserId());
				ConferenceUserName = conferenceUser.getName();
				responseTime = sdf.format(history.getResponseTime());
				payMethod = history.getPayMethod();
				if(payMethod.equals("weixin")) {
					System.out.println(payMethod);
					payMethod = "weChat";
				}
		        amount = String.valueOf(history.getAmount());
		        serialNumber = orderNumber;
		        content = MessageFormat.format(content, ConferenceUserName,responseTime,payMethod,amount,serialNumber);
		        String []to = {conferenceUser.getEmail()};
		        sendEmail.sendSimpleMail(from, to, subject, content);
			}
		}else {
			System.out.println("发送支付完成邮件时，未获取订单所未对应的用户列表为空");
		}
		
		return Msg.success();
	}
}
