package com.lhfeiyu.util;

import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;



public class SendMail {
	 private StringBuffer bodytext = new StringBuffer();
	 private static MimeMessage mimeMessage = null;  
	 
	 public SendMail(MimeMessage mimeMessage) {    
	        SendMail.mimeMessage = mimeMessage;    
	    }  
	public static String SendMailUser(String host,String auth,String user,String password,String SendFrom,String SendTo,String Title,String Content){
		String errorCode= "";
		try{
			Properties props = new Properties();
	         props.setProperty("mail.host", host);
	         props.setProperty("mail.transport.protocol", "smtp");
	         props.setProperty("mail.smtp.auth", auth);
	         props.put("mail.smtp.ssl.enable", true);
	         //使用JavaMail发送邮件的5个步骤
	        //1、创建session
	         Session session = Session.getInstance(props);
	         //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
	         session.setDebug(true);
	         //2、通过session得到transport对象
	         Transport ts = session.getTransport();
	         //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
	         ts.connect(host, user, password);
	         //4、创建邮件
	         Message message = SendMailToUser(session,SendFrom,SendTo,Title,Content);
	         //5、发送邮件
	         ts.sendMessage(message, message.getAllRecipients());
	         ts.close();
		}catch(Exception e){
			e.printStackTrace();
			errorCode = "邮件服务器设置错误";
		}
		return errorCode;
	} 
	
	
	
	 public static  MimeMessage SendMailToUser(Session session, String SendFrom,String SendTo,String Title,String Content){
		 try{
		 	  //创建邮件对象
			 mimeMessage = new MimeMessage(session);
	          //指明邮件的发件人
			 mimeMessage.setFrom(new InternetAddress(SendFrom));
	          new InternetAddress();
			//指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
			 InternetAddress[] toList = InternetAddress.parse(SendTo);//用,隔开
			 mimeMessage.setRecipients(Message.RecipientType.TO, toList);
	          //邮件的标题
			 mimeMessage.setSubject(Title);
	          //邮件的文本内容
			 mimeMessage.setContent(Content, "text/html;charset=UTF-8");
	          //返回创建好的邮件对象
	         }catch(Exception e){
	        	 e.printStackTrace();
	         }
	          return mimeMessage;
	      }
	 
	   
	 public  String getBodyText() {     
	        return bodytext.toString();     
	    }   
	 
	 public static  void getMailContent(Part part) throws Exception {     
	        String contenttype = part.getContentType();     
	        int nameindex = contenttype.indexOf("name");     
	        boolean conname = false;     
	        if (nameindex != -1)     
	            conname = true;     
	        System.out.println("类型: " + contenttype); 
	        if (part.isMimeType("text/plain") && !conname) {     
	        	 System.out.println("内容: " +part.getContent());    
	        } else if (part.isMimeType("text/html") && !conname) {     
	        	 System.out.println("内容: " +part.getContent());        
	        } /*else if (part.isMimeType("multipart/*")) {     
	            Multipart multipart = (Multipart) part.getContent();     
	            int counts = multipart.getCount();     
	            for (int i = 0; i < counts; i++) {     
	                getMailContent(multipart.getBodyPart(i));     
	            }     
	        }*/ else if (part.isMimeType("message/rfc822")) {     
	            getMailContent((Part) part.getContent());     
	        } else {}
			   
	    }     
	 
	public static void ReceiveMail(String host,String user,String password){
		 try{
			 Store store = null;
			 Folder folder = null;
			 Properties props = new Properties();
		     props.put("mail.pop3.host", host);
		     props.put("mail.pop3.ssl.enable", true);
		     props.put("mail.pop3.port", "995");
		     props.put("mail.smtp.auth", "true");
			 Session session = Session.getDefaultInstance(props,null);
			 session.setDebug(true);
			 store = session.getStore("pop3");
			 store.connect(host, user, password);
			 folder  = store.getFolder("INBOX");
			 folder.open(Folder.READ_ONLY);
			 Message message[] = folder.getMessages();
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			 //SendMail sendMail = null;
			 for(int i=0;i<message.length;i++){
				 MimeMessage mimeMessage = (MimeMessage) message[i];
				 InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom(); 
				 System.out.println("发信人地址:"+address[0].getAddress());
				 System.out.println("发信人:"+address[0].getPersonal());
				 System.out.println("标题:"+MimeUtility.decodeText(mimeMessage.getSubject()));
				 System.out.println("日期:"+format.format(mimeMessage.getSentDate()));
				 getMailContent(message[i]);
				 //sendMail.getMailContent(message[i]);
			     //System.out.println("内容:"+sendMail.getBodyText());
				 System.out.println("邮件ID:"+mimeMessage.getMessageID());
			}
			folder.close(false);
			 store.close();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	 
}
