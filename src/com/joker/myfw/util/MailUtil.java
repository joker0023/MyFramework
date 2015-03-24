package com.joker.myfw.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * 邮件工具类
 * @author Joker
 *
 */
public class MailUtil {

	private final static Logger logger = Logger.getLogger(MailUtil.class);
	

	/**
	 *  mailServer : 邮件服务器地址
	 *  username : 发送人邮箱用户名
	 *  password : 发送人邮箱密码
	 *  from :     发送人的邮箱全名
	 *  nick :     昵称
	 */
    private String mailServer, username, password,from;
    private Session mailSession;
    private Properties prop;
    private Message message;
    private String nick;
    
    /**
     * 设置邮件服务器相关
     * @param mailServer:
     * @param username:
     * @param password:
     */
    public MailUtil(String mailServer, String from, String username, String password, String nick) {
    	 this.mailServer = mailServer;
         this.from = from;
         this.username = username;
         this.password = password;
         this.nick = nick;
    }
    
	/**
    *
    * @param to :
    * @param mailSubject:
    * @param mailContent
    */
   public void send(String to, String mailSubject, String mailContent) {
	   EmailAuthenticator mailauth = new EmailAuthenticator(username, password);
       // 设置邮件服务器
       prop = System.getProperties();
       prop.put("mail.smtp.port", "25");
       prop.put("mail.smtp.auth", "true");
       prop.put("mail.smtp.host", mailServer);
       prop.put("mail.transport.protocol", "smtp");
       prop.put("mail.store.protocol", "pop3");
       // 产生新的Session服务
       mailSession = Session.getDefaultInstance(prop, mailauth);
       message = new MimeMessage(mailSession);

       try {
       	 // 设置发件人
       	if(null != nick && !"".equals(nick)){
       		try {  
   	            nick=javax.mail.internet.MimeUtility.encodeText(nick);  
   	        } catch (UnsupportedEncodingException e) {  
   	            e.printStackTrace();  
   	        }   
   			message.setFrom(new InternetAddress(nick+" <"+from+">"));
       	}else{
       		message.setFrom(new InternetAddress(from));
       	}
           
           message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));// 设置收件人
           message.setSubject(mailSubject);// 设置主题
           message.setContent(mailContent, "text/html;charset=utf8");
            
           // 设置日期
           message.setSentDate(new Date());
           Transport tran = mailSession.getTransport("smtp");
           tran.connect(mailServer, username, password);
           Transport.send(message, message.getAllRecipients());
           tran.close();
           
           System.out.println("SendMail Process Over!");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   /**
    * 批量发送  上限多少个还没有测试过 
    * @param toAddrs
    * @param mailSubject
    * @param mailContent
    */
   public void batchSend(List<String> toAddrs, String mailSubject, String mailContent) {
       EmailAuthenticator mailauth = new EmailAuthenticator(username, password);
       // 设置邮件服务器
       prop = System.getProperties();
       prop.put("mail.smtp.auth", "true");
       prop.put("mail.smtp.host", mailServer);
       // 产生新的Session服务
       mailSession = Session.getDefaultInstance(prop, mailauth);
       message = new MimeMessage(mailSession);

       try {
       	 // 设置发件人
       	if(null != nick && !"".equals(nick)){
       		try {  
   	            nick=javax.mail.internet.MimeUtility.encodeText(nick);  
   	        } catch (UnsupportedEncodingException e) {  
   	            e.printStackTrace();  
   	        }   
   			message.setFrom(new InternetAddress(nick+" <"+from+">"));
       	}else{
       		message.setFrom(new InternetAddress(from));
       	}
           
       	// 设置收件人
       	InternetAddress[] address = new InternetAddress[toAddrs.size()];
       	for(int i = 0;i < toAddrs.size();i++){
       		address[i] = new InternetAddress(toAddrs.get(i));
       	}
       	message.addRecipients(Message.RecipientType.TO, address);
       	message.setReplyTo(address);
           message.setSubject(mailSubject);// 设置主题
           message.setContent(mailContent, "text/html;charset=utf8");
            
           // 设置日期
           message.setSentDate(new Date());
           Transport tran = mailSession.getTransport("smtp");
           tran.connect(mailServer, username, password);
           Transport.send(message, message.getAllRecipients());
           tran.close();
           
//           System.out.println("SendMail Process Over!");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   /**
    * 接收邮件
    */
	public void getMail() {
		EmailAuthenticator mailauth = new EmailAuthenticator(username, password);
		// 创建Properties 对象
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "pop3");       // 协议  
        props.setProperty("mail.pop3.port", "110");             // 端口  

		// 创建邮件会话
		Session session = Session.getDefaultInstance(props,mailauth);

		try {
			// 获取邮箱的pop3存储
			Store store = session.getStore("pop3");
			store.connect(mailServer,110,username, password);

			// 获取inbox文件
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY); // 打开，打开后才能读取邮件信息

			// 获取邮件消息
			Message message[] = folder.getMessages();

			for (int i = 0, n = message.length; i < n; i++) {
				System.out.println(i + ": " + message[i].getFrom()[0] + "/t"
						+ message[i].getSubject());
				try {
					message[i].writeTo(System.out);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			// 关闭资源
			folder.close(false);
			store.close();

		} catch (MessagingException e) {
			logger.error("发送邮件失败\n" + e.getMessage());
		}
//		System.out.println("GetMail Process Over!");
	}
	
	
	
	//main函数(测试)
	public static void main(String[] args) {
		
		String mailServer = "220.181.12.17";
		String from = "ttth23@163.com";
		String username = "ttth23";
		String password = "shisi0023";
		String nick = "测试账号";
		
		//初始化
		MailUtil mail = new MailUtil(mailServer, from, username, password, nick);
		
		//设置发送参数
		String to = "379572904@qq.com";
		String mailSubject = "邮件系统,测试";
		String mailContent = "此为测试..<br>";
		mailContent += "<br>请点击<br><a href=\"http://www.lunjar.com\">http://www.lunjar.com</a><br>以完成注册!";
		
		//发送邮件
		mail.send(to, mailSubject, mailContent);
		
//		List<String> list = new ArrayList<String>();
//		list.add("qiaohe11@163.com");
//		list.add("15017564850@139.com");
//		list.add("15017564850@189.cn");
//		
//		mail.batchSend(list, mailSubject, mailContent);
		
	}
	
}

class EmailAuthenticator extends Authenticator {  
    private String m_username = null;  
   
    private String m_userpass = null;  
   
    void setUsername(String username) {  
        m_username = username;  
    }  
   
    void setUserpass(String userpass) {  
        m_userpass = userpass;  
    }  
   
    public EmailAuthenticator(String username, String userpass) {  
        super();  
        setUsername(username);  
        setUserpass(userpass);  
    }  
   
    @Override
	public PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(m_username, m_userpass);  
    }  
}


