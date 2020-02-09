package com.scu.util;

import javax.activation.FileDataSource;
import javax.xml.soap.Text;

public interface SendEmail {

	public void sendSimpleMail(String From,String []to,String subject,String content)throws Exception;
	public void sendOneImageMail(String From,String []to,String subject,String content,FileDataSource image)throws Exception;
	public void sendImagesMail(String From,String []to,String subject,String content, FileDataSource []images)throws Exception;
	public void sendOneAttachMail(String From,String []to,String subject,String content, FileDataSource attach)throws Exception;
	public void sendAttachesMail(String From,String []to,String subject,String content, FileDataSource []attaches)throws Exception;
	public void sendMixedMail(String From,String []to,String subject,String content,FileDataSource []images, FileDataSource []attaches)throws Exception;
}
