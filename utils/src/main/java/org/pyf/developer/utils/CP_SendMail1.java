/*******************************************************************************
* COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
********************************************************************************
* 源文件名: xxxxx.java														       
* 功能: （描述文件功能）													   
* 版本:	@version 1.0	                                                                   
* 编制日期: 2009-5-9							    						   
* 说明: （描述使用文件功能时的制约条件）                                       
* 修改历史: (主要历史变动原因及说明)					      
* 2009-5-9 下午04:12:14 | @author hanqunfeng  |  Change Description 
*
*******************************************************************************/
package org.pyf.developer.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

//import com.me.util.*;

public class CP_SendMail1 implements CP_ISendMail {

	final int topCount =10;
	static int sendCount;
	
	private MimeMessage mimeMsg; // MIME邮件对象

	public MimeMessage getMimeMsg() {
		return mimeMsg;
	}

	public void setMimeMsg(MimeMessage mimeMsg) {
		this.mimeMsg = mimeMsg;
	}

	private Session session; // 邮件会话对象

	private Properties props; // 系统属性

	//private boolean needAuth = false; // smtp是否需要认证

	private String username = ""; // smtp认证用户名和密码

	private String password = "";

	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	// private syslog slog; //系统日志功能
	Logger logger = LoggerFactory.getLogger(CP_SendMail1.class);

	/**
	 * 
	 */
	public CP_SendMail1() {
		setSmtpHost("smtp.netqin.com");// 如果没有指定邮件服务器,就从getConfig类中获取
		createMimeMessage();
		
		setNeedAuth(true);
		setNamePass("nqcoopService3@netqin.com", "9VSK75ubnvqEXYpsB6");
		setFrom("noreply@nqservice.com");
	}

	public CP_SendMail1(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
		
		setNeedAuth(true);
		setNamePass("nqcoopService3@netqin.com", "9VSK75ubnvqEXYpsB6");
		setFrom("noreply@nqservice.com");
	}

	/**
	 * @param need
	 * boolean
	 */
	public void setNeedAuth(boolean need) {
		// System.out.println("设置smtp身份认证：mail.smtp.auth = "+need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * @param hostName
	 *            String
	 */
	public void setSmtpHost(String hostName) {
		// System.out.println("设置系统属性：mail.smtp.host = "+hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象

		props.put("mail.smtp.host", hostName); // 设置SMTP主机
	}

	/**
	 * @return boolean
	 */
	public boolean createMimeMessage() {
		try {
			// System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			// System.err.println("获取邮件会话对象时发生错误！"+e);
			logger.error("获取邮件会话对象时发生错误！" + e);
			return false;
		}

		// System.out.println("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			// System.err.println("创建MIME邮件对象失败！"+e);
			logger.error("创建MIME邮件对象失败！" + e);
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#setSubject(java.lang.String)
	 */
	@Override
	public boolean setSubject(String mailSubject) {
		// System.out.println("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			// System.err.println("设置邮件主题发生错误！");
			logger.error("设置邮件主题发生错误！");
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#setBody(java.lang.String)
	 */
	@Override
	public boolean setBody(String mailBody) {
		try {
			mp = new MimeMultipart(); // 初始化邮件多媒体对象
			BodyPart bp = new MimeBodyPart();
			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=utf-8>"
							+ mailBody, "text/html;charset=utf-8");
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			// System.err.println("设置邮件正文时发生错误！"+e);
			logger.error("设置邮件正文时发生错误！" + e);
			return false;
		}
	}

	/**
	 * @param filename
	 *            String
	 *
	 */
	public boolean addFileAffix(String filename) {
		// System.out.println("增加邮件附件："+filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			// System.err.println("增加邮件附件："+filename+"发生错误！"+e);
			logger.error("增加邮件附件：" + filename + "发生错误！" + e);
			return false;
		}
	}

	public static String getISOFileName(Part body) {
		// 设置一个标志，判断文件名从Content-Disposition中获取还是从Content-Type中获取
		boolean flag = true;
		if (body == null) {
			return null;
		}
		String[] cdis;
		try {
			cdis = body.getHeader("Content-Disposition");
		} catch (Exception e) {
			return null;
		}
		if (cdis == null) {
			flag = false;
		}
		if (!flag) {
			try {
				cdis = body.getHeader("Content-Type");
			} catch (Exception e) {
				return null;
			}
		}
		if (cdis == null) {
			return null;
		}
		if (cdis[0] == null) {
			return null;
		}
		// 从Content-Disposition中获取文件名
		if (flag) {
			int pos = cdis[0].indexOf("filename=");
			if (pos < 0) {
				return null;
			}
			// 如果文件名带引号
			if (cdis[0].charAt(cdis[0].length() - 1) == '"') {
				return cdis[0].substring(pos + 10, cdis[0].length() - 1);
			}
			return cdis[0].substring(pos + 9, cdis[0].length());
		} else {
			int pos = cdis[0].indexOf("name=");
			if (pos < 0) {
				return null;
			}
			// 如果文件名带引号
			if (cdis[0].charAt(cdis[0].length() - 1) == '"') {
				return cdis[0].substring(pos + 6, cdis[0].length() - 1);
			}
			return cdis[0].substring(pos + 5, cdis[0].length());
		}
	}

	/**
	 * 对字符串进行解码处理
	 * 
	 * @param s
	 *            要转换的字符串
	 * @return 返回转换后的字符串
	 */
	public static String decodeWord_s(String s) {
		System.out.println("s========decode=" + s);
		if (s == null || s.equals("")) {
			return "";
		}
		if (!s.startsWith("=?")) {
			return s; // 如果没有编码就进行内码转换,调用str类里getstr()
		}
		if (s.indexOf("=?") != -1) {
			int i = 2;
			int j;
			if ((j = s.indexOf(63, i)) == -1)
				return s;
			//String s1 = (s.substring(i, j));
			i = j + 1;
			if ((j = s.indexOf(63, i)) == -1)
				return s;
			String s2 = s.substring(i, j);
			i = j + 1;
			if ((j = s.indexOf("?=", i)) == -1)
				return s;
			String s3 = s.substring(i, j);
			try {
				ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(
						s3.getBytes());
				Object obj;
				if (s2.equalsIgnoreCase("B"))
					obj = new com.sun.mail.util.BASE64DecoderStream(
							bytearrayinputstream);
				else if (s2.equalsIgnoreCase("Q"))
					obj = new com.sun.mail.util.QDecoderStream(
							bytearrayinputstream);
				else
					return s;
				int k = bytearrayinputstream.available();
				byte abyte0[] = new byte[k];
				k = ((InputStream) (obj)).read(abyte0, 0, k);
				return new String(abyte0, 0, k);
			}

			catch (Exception e) {
				return s;
			}
		}
		return s;
	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#setFrom(java.lang.String)
	 */
	@Override
	public boolean setFrom(String from) {
		// System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#setTo(java.lang.String)
	 */
	@Override
	public boolean setTo(String to) {
		if (to == null)
			return false;

		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#setTo(java.lang.String[])
	 */
	@Override
	public boolean setTo(String[] to) {
		if (to == null || to.length < 1)
			return false;

		try {
			InternetAddress[] toAddress = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			mimeMsg.setRecipients(Message.RecipientType.TO, toAddress);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#setCopyTo(java.lang.String)
	 */
	@Override
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			logger.error("设置CopyTo抄送出错！ " + e.toString());
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#setCopyTo(java.lang.String[])
	 */
	@Override
	public boolean setCopyTo(String copyto[]) {
		if (copyto == null || copyto.length < 1)
			return false;

		try {
			InternetAddress[] toAddress = new InternetAddress[copyto.length];
			for (int i = 0; i < copyto.length; i++) {
				toAddress[i] = new InternetAddress(copyto[i]);
			}
			mimeMsg.setRecipients(Message.RecipientType.CC, toAddress);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#sendout()
	 */
	@Override
	public boolean sendout() {
		logger.info("sendMail1:sendCount->begin");
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			transport.close();
			 ++sendCount;
			logger.info("sendMail1:sendCount:"+sendCount);
			return true;
		} catch (Exception e) {
			logger.error("sendMail1:邮件发送失败！" + e.toString());
			CP_SendMail2 s = new CP_SendMail2();
			return s.sendout(mimeMsg);
		}
	}
	/* (non-Javadoc)
	 * @see com.netqin.common.util.ISendMail1#sendout()
	 */
	@Override
	public boolean sendout(MimeMessage mimeMsg ) {
		logger.info("sendMail1:sendCountByMimeMessage->begin");
		try {
			
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			transport.close();
			++sendCount;
			logger.info("sendMail1:sendCount:"+sendCount);
			return true;
		} catch (Exception e) {
			logger.error("sendMail1:邮件发送失败！" + e.toString());
			return false;
		}
	}

	

	@Override
	public boolean shouldChange() {
		boolean retult = sendCount >= topCount;
		if(sendCount>=topCount){
			sendCount=0;
		}
		return retult;
	}

//	public static void main(String[] args) {
//		CP_SendMail1 sm = new CP_SendMail1();
//		//sm.setFrom("hanqunfeng@netqin.com");
//
//		sm.setSubject("主题123");
//		sm.setBody("内容123,邱思湲");
//		String[] mails = {"hanqunfeng@nq.com","songge@nq.com"};
//		sm.setTo(mails);
//		//String[] mails2 = {"sunchengqi@nq.com"};
//		//sm.setCopyTo(mails2);
//
//		try {
//			if (sm.sendout()) {
//				System.out.print("已将内容发送");
//			} else {
////				System.out.print("内容无法发送"+StringUtils.arrayToCommaDelimitedString(mails));
////				String[] mails3 = {"sunchengqi@nq.com"};
////				sm.setTo(mails3);
////				if (sm.sendout()) {
////					System.out.print("已将内容发送3");
////				} else {
////					System.out.print("内容无法发送3"+StringUtils.arrayToCommaDelimitedString(mails3));
////
////				}
//			}
//		} catch (Exception ex) {
//			System.out.print("SendMail1 " + ex.toString());
//		}
//	}
}
