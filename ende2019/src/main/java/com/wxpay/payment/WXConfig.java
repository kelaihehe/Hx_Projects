


package com.wxpay.payment;

import com.wxpay.payment.IWXPayDomain;
import com.wxpay.payment.WXPayConfig;
import java.io.*;

//http://yayihouse.com/yayishuwu/chapter/1448
public class WXConfig extends WXPayConfig{
    private byte[] certData;
	public WXConfig() throws Exception {
       //不是沙箱环境要要下载证书，开出来
       String certPath = "/data/apiclient_cert.p12";

       File file = new File(certPath);

       InputStream certStream = new FileInputStream(file);

       this.certData = new byte[(int) file.length()];

       certStream.read(this.certData);

       certStream.close();
	 }

	 public String getAppID() {

	      return "wx78353a039a08aee1";

	 }
	 
	 public String getAppSecret() {

	      return "7cd36228509b9cddcd9223b667e44bf3";

	 }



	   public String getMchID() {

	       return "1518767521";

	   }



	   public String getKey() {

	       return "e8ef6f81d22429305d23c2c40f8efb33";

	   }



	   public InputStream getCertStream() {

	       ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);

	       return certBis;

	   }



	   public int getHttpConnectTimeoutMs() {

	       return 8000;

	   }



	   public int getHttpReadTimeoutMs() {

	       return 10000;

	   }



	@Override

	IWXPayDomain getWXPayDomain() {
	// TODO Auto-generated method stub
	    return WXPayDomainSimpleImpl.instance();

	}
}
