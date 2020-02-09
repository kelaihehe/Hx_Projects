package com.scu.util;

import java.io.FileOutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.soap.Text;

public class SendEmailImpl implements SendEmail {
	
	public Session CreateSession()
	{
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", "smtp.sina.com");
		prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		prop.setProperty("mail.smtp.socketFactory.fallback", "false");
		prop.setProperty("mail.smtp.port", "465");
		prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.setProperty("mail.smtp.auth", "true");
		
		//Session session = Session.getInstance(prop);
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ende2019@sina.com","ende2019");
            }
        });
		session.setDebug(true);
		return session;
	}
	
	/*
	 * 发送简单文本的邮件
	 */
	/*@Override
	public void sendSimpleMail(String from, String []to, String subject, String content) throws Exception {
		Session session = CreateSession();
		Transport ts = session.getTransport();
		ts.connect("smtp.qq.com","1791964653@qq.com","vykfdhaglrwvejic");
		Message message = createSimpleMail(session,from,to,subject,content);
		ts.sendMessage(message, message.getAllRecipients());
	}
	
	public static MimeMessage createSimpleMail(Session session,String From, String []to, String subject, String content) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		//指定邮件的发件人
		message.setFrom(new InternetAddress(From));
		//指定一个邮件的收件人
		if(to.length==0)
		{
			return null;
		}
		for(String recipient:to)
		{
			message.addRecipients(Message.RecipientType.BCC, recipient);
		}
		
		message.setSubject(subject);
		message.setContent(content,"text/html;charset=UTF-8");
		return message;		
	}*/
	
	@Override
	public void sendSimpleMail(String from, String []to, String subject, String content) throws Exception {
		Session session = CreateSession();
		/*Transport ts = session.getTransport();
		ts.connect("smtp.qq.com","1791964653@qq.com","vykfdhaglrwvejic");*/
		Message message = createSimpleMail(session,from,to,subject,content);
		//T.sendMessage(message, message.getAllRecipients());
		Transport.send(message,message.getAllRecipients());
	}
	
	public static MimeMessage createSimpleMail(Session session,String From, String []to, String subject, String content) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		//指定邮件的发件人
		message.setFrom(new InternetAddress(From));
		//指定一个邮件的收件人
		if(to.length==0)
		{
			return null;
		}
		for(String recipient:to)
		{
			message.addRecipients(Message.RecipientType.BCC, recipient);
		}
		
		message.setSubject(subject);
		message.setContent(content,"text/html;charset=UTF-8");
		return message;		
	}
	
	/*
	 * 发送一张内嵌图片的邮件
	 */
	@Override
	public void sendOneImageMail(String From, String []to, String subject, String content, FileDataSource image) throws Exception {
		Session session = CreateSession();
		Transport ts = session.getTransport();
		ts.connect("smtp.qq.com","1791964653@qq.com","vykfdhaglrwvejic");
		Message message = createOneImageMail(session,From,to,subject,content,image);
		ts.sendMessage(message, message.getAllRecipients());		
	}
	
	public static MimeMessage createOneImageMail(Session session,String From, String []to, String subject, String content, FileDataSource image) throws Exception, MessagingException
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(From));
		message.setSubject(subject);
		if(to.length==0)
		{
			return null;
		}
		for(String recipient:to)
		{
			message.addRecipients(Message.RecipientType.BCC, recipient);
		}
		//准备邮件正文内容
		MimeBodyPart text = new MimeBodyPart();
		//img标签中src值用cid来指定图片
		text.setContent(content+"<br/>"+String.format("<img src='cid:%s' width=\"100\" height=\"100\">", image.getName()),"text/html;charset=UTF-8");
		//准备图片内容
		MimeBodyPart imagepart = new MimeBodyPart();
		DataHandler dataHandler = new DataHandler(image);
		imagepart.setDataHandler(dataHandler);
		imagepart.setContentID(image.getName());  //设置邮件内容id
		//描述数据关系
		MimeMultipart mimeMultipart = new MimeMultipart();
		mimeMultipart.addBodyPart(text);
		mimeMultipart.addBodyPart(imagepart);
		mimeMultipart.setSubType("related");//related表示知道内容为内嵌资源
		//填充message
		message.setContent(mimeMultipart);
		message.saveChanges();
		message.writeTo(new FileOutputStream(String.format("E:\\Test\\send%s.eml",image.getName() )));
		return message;
	}
	
	/*
	 * 发送若干张内嵌图片的邮件
	 */
	@Override
	public void sendImagesMail(String From, String []to, String subject, String content, FileDataSource[] images) throws Exception {
		Session session = CreateSession();
		Transport ts = session.getTransport();
		ts.connect("smtp.qq.com","1791964653@qq.com","vykfdhaglrwvejic");
		Message message = createImagesMail(session,From,to,subject,content,images);
		ts.sendMessage(message, message.getAllRecipients());	
	}
	
	public static MimeMessage createImagesMail(Session session,String From, String []to, String subject, String content, FileDataSource []images) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(From));
		message.setSubject(subject);
		if(to.length==0)
		{
			return null;
		}
		for(String recipient:to)
		{
			message.addRecipients(Message.RecipientType.BCC, recipient);
		}
		//准备邮件正文内容
		MimeBodyPart text = new MimeBodyPart();
		StringBuilder contentAndImage = new StringBuilder();
		contentAndImage.append(content);
		for(FileDataSource image : images )
		{
			contentAndImage.append("<br/>");
			contentAndImage.append(String.format("<img src='cid:%s' width='100' height='100'>", image.getName()));
		}
		text.setContent(contentAndImage.toString(),"text/html;charset=UTF-8");
		//准备图片内容		
		MimeMultipart mimeMultipart = new MimeMultipart();		
		for(FileDataSource image : images)
		{
			MimeBodyPart imageBodypart = new MimeBodyPart();
			DataHandler dataHandler = new DataHandler(image);
			imageBodypart.setDataHandler(dataHandler);
			imageBodypart.setContentID(image.getName());  //设置邮件内容id
			mimeMultipart.addBodyPart(imageBodypart);
		}
		//描述数据关系		
		mimeMultipart.addBodyPart(text);
		mimeMultipart.setSubType("related");//related表示知道内容为内嵌资源
		//填充message
		message.setContent(mimeMultipart);
		message.saveChanges();
		message.writeTo(new FileOutputStream(String.format("E:\\Test\\send%s.eml",images[0].getName() )));
		return message;
	}
	
	/*
	 *发送文本加一个附件的邮件
	 */
	@Override
	public void sendOneAttachMail(String From, String []to, String subject, String content, FileDataSource attach) throws Exception {
		Session session = CreateSession();
		Transport ts = session.getTransport();
		ts.connect("smtp.qq.com","1791964653@qq.com","vykfdhaglrwvejic");
		Message message = createOneAttachMail(session,From,to,subject,content,attach);
		ts.sendMessage(message, message.getAllRecipients());		
	}
	public static MimeMessage createOneAttachMail(Session session,String From, String []to, String subject, String content, FileDataSource attach) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(From));
		if(to.length==0)
		{
			return null;
		}
		for(String recipient:to)
		{
			message.addRecipients(Message.RecipientType.BCC, recipient);
		}
		//设置邮件主题
		message.setSubject(subject);
		//准备邮件正文内容
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(content,"text/html;charset=UTF-8");
		//创建附件
		MimeBodyPart attachBodypart = new MimeBodyPart();
		DataHandler dataHandler = new DataHandler(attach);
		attachBodypart.setDataHandler(dataHandler);
		attachBodypart.setFileName(dataHandler.getName());
		//描述数据关系
		MimeMultipart mimeMultipart = new MimeMultipart();
		mimeMultipart.addBodyPart(text);
		mimeMultipart.addBodyPart(attachBodypart);
		mimeMultipart.setSubType("mixed");//mixed表示指定以附件形式
		
		//填充message
		message.setContent(mimeMultipart);
		message.saveChanges();
		message.writeTo(new FileOutputStream("E:\\Test\\attachMail.eml"));
		return message;
	}
	
	/*
	 *发送文本加多个附件的邮件
	 */
	@Override
	public void sendAttachesMail(String From, String []to, String subject, String content, FileDataSource[] attaches) throws Exception {
		Session session = CreateSession();
		Transport ts = session.getTransport();
		ts.connect("smtp.qq.com","1791964653@qq.com","vykfdhaglrwvejic");
		Message message = createAttachesMail(session,From,to,subject,content,attaches);
		ts.sendMessage(message, message.getAllRecipients());		
	}
	
	public static MimeMessage createAttachesMail(Session session,String From, String []to, String subject, String content, FileDataSource[] attaches) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(From));
		if(to.length==0)
		{
			return null;
		}
		for(String recipient:to)
		{
			message.addRecipients(Message.RecipientType.BCC, recipient);
		}
		//设置邮件主题
		message.setSubject(subject);
		//准备邮件正文内容
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(content,"text/html;charset=UTF-8");
		MimeMultipart mimeMultipart = new MimeMultipart();
		//创建附件
		for(FileDataSource attach:attaches)
		{
			MimeBodyPart attachBodypart = new MimeBodyPart();
			DataHandler dataHandler = new DataHandler(attach);
			attachBodypart.setDataHandler(dataHandler);
			attachBodypart.setFileName(dataHandler.getName());
			mimeMultipart.addBodyPart(attachBodypart);
		}		
		//描述数据关系		
		mimeMultipart.addBodyPart(text);
		
		mimeMultipart.setSubType("mixed");//mixed表示指定以附件形式
		
		//填充message
		message.setContent(mimeMultipart);
		message.saveChanges();
		message.writeTo(new FileOutputStream("E:\\Test\\attachMail.eml"));
		return message;
	}
	
	/*
	 *发送文本内嵌加附件(数量不限)的邮件
	 */
	@Override
	public void sendMixedMail(String From, String []to, String subject, String content, FileDataSource[] images,
			FileDataSource[] attaches) throws Exception {
		Session session = CreateSession();
		Transport ts = session.getTransport();
		ts.connect("smtp.qq.com","1791964653@qq.com","vykfdhaglrwvejic");
		Message message = createMixedMail(session,From,to,subject,content,images,attaches);
		ts.sendMessage(message, message.getAllRecipients());		
	}

	public static MimeMessage createMixedMail(Session session,String From, String []to, String subject, String content, FileDataSource[] images,
			FileDataSource[] attaches) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(From));
		//指定多个邮件的收件人，并将分别密抄给所有人
		if(to.length==0)
		{
			return null;
		}
		for(String recipient:to)
		{
			message.addRecipients(Message.RecipientType.BCC, recipient);
		}
		//设置邮件主题
		message.setSubject(subject);
		//准备邮件正文内容
		MimeBodyPart text = new MimeBodyPart();
		StringBuilder contentAndImage = new StringBuilder();
		contentAndImage.append(content);
		for(FileDataSource image : images )
		{
			contentAndImage.append("<br/>");
			contentAndImage.append(String.format("<img src='cid:%s' width='100' height='100'>", image.getName()));
		}
		text.setContent(contentAndImage.toString(),"text/html;charset=UTF-8");
		//准备图片内容		
		MimeMultipart mimeMultipart1 = new MimeMultipart();		
		for(FileDataSource image : images)
		{
			MimeBodyPart imageBodypart = new MimeBodyPart();
			DataHandler dataHandler = new DataHandler(image);
			imageBodypart.setDataHandler(dataHandler);
			imageBodypart.setContentID(image.getName());  //设置邮件内容id
			mimeMultipart1.addBodyPart(imageBodypart);
		}
		//描述正文中的数据关系		
		mimeMultipart1.addBodyPart(text);
		mimeMultipart1.setSubType("related");//related表示知道内容为内嵌资源
		//准备附件部分
		MimeMultipart mimeMultipart = new MimeMultipart();
		for(FileDataSource attach:attaches)
		{
			MimeBodyPart attachBodyPart = new MimeBodyPart();
			DataHandler dataHandler = new DataHandler(attach);
			attachBodyPart.setDataHandler(dataHandler);
			attachBodyPart.setFileName(dataHandler.getName());
			mimeMultipart.addBodyPart(attachBodyPart);
		}				
		//描述数据关系(正文与附件之间使用内嵌)		
		MimeBodyPart contentbodyPart = new MimeBodyPart();
		contentbodyPart.setContent(mimeMultipart1);//将正文部分包含进去
		mimeMultipart.addBodyPart(contentbodyPart);
		mimeMultipart.setSubType("mixed");
		
		//填充message
		message.setContent(mimeMultipart);
		message.saveChanges();
		message.writeTo(new FileOutputStream("E:\\Test\\mixedMail.eml"));
		return message;
	}
	

}
