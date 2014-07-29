package com.renren.ntc.video.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestMail {

	public class Email_Autherticator extends Authenticator {
		
		String username = "userFrom";

		String password = "pwd";

		public Email_Autherticator() {
			super();
		}

		public Email_Autherticator(String user, String pwd) {
			super();
			username = user;
			password = pwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	private String host = "192.168.0.177";
	//private String host = "smtp.126.com"; 
	
	private String mail_head_name = "this is head of this mail";

	private String mail_head_value = "this is head of this mail";

	private String mail_to = "303970912@test.com";

	private String mail_from = "userFrom@test.com";

	private String mail_subject = "this is the subject of this test mail";

	private String mail_body = "this is mail_body of this test mail";

	private String personalName = "personalName";

	public void sendMail() throws SendFailedException {
		try {
			Properties props = new Properties();// 获取系统环境
			Authenticator auth = new Email_Autherticator();// 进行邮件服务用户认证
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			System.out.println(props);
			Session session = Session.getDefaultInstance(props, auth);
			// 设置session,和邮件服务器进行通讯
			MimeMessage message = new MimeMessage(session);
			message.setContent("Hello", "text/plain");// 设置邮件格式
			message.setSubject(mail_subject);// 设置邮件主题
			message.setText(mail_body);// 设置邮件内容
			message.setHeader(mail_head_name, mail_head_value);// 设置邮件标题
			message.setSentDate(new Date());// 设置邮件发送时期
			Address address = new InternetAddress(mail_from, personalName);
			message.setFrom(address);// 设置邮件发送者的地址
			Address toaddress = new InternetAddress(mail_to);// 设置邮件接收者的地址
			message.addRecipient(Message.RecipientType.TO, toaddress);
			System.out.println(message);
			Transport.send(message);
			System.out.println("Send Mail Ok!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return flag;
	}
	
	public static void main(String[] args) throws SendFailedException{
		TestMail m = new TestMail();
		m.sendMail();
	}
}
