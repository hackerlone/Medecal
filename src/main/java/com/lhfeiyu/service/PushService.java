package com.lhfeiyu.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lhfeiyu.config.ConstField;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：业务-推送 <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class PushService {
	
	public static String doPush(String param,Date date){
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		String pushUrl = ConstField.getPushMsgUrl(timeStamp);
		String authorization = ConstField.getAuthorization(timeStamp);
		System.out.println("pushUrl:"+pushUrl);
		System.out.println("authorization:"+authorization);
		System.out.println("param:"+param);
		return sendPost(pushUrl,param,authorization);
	}
	
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,String authorization) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url); // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();// 设置通用的请求属性
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Host", "app.cloopen.com:8883");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("Content-Length", "252");
            conn.setRequestProperty("Authorization", authorization);
            //conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);// 发送POST请求必须设置如下两行
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream()); // 获取URLConnection对象对应的输出流
            out.print(param);// 发送请求参数
            out.flush(); // flush输出流的缓冲
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));// 定义BufferedReader输入流来读取URL的响应
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){ out.close(); }
                if(in!=null){in.close();}
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString); // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();// 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();// 建立实际的连接
            Map<String, List<String>> map = connection.getHeaderFields();// 获取所有响应头字段
            for (String key : map.keySet()) {// 遍历所有的响应头字段
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(// 定义 BufferedReader输入流来读取URL的响应
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {// 使用finally块来关闭输入流
            try {
                if (in != null) {in.close();}
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果  http://www.yuntongxun.com/
	 * @param url
	 * @param pushType
	 * @param receiver
	 * @param msgType
	 * @param sender
	 * @param map
	 *  http://docs.yuntongxun.com/index.php/5.0_%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%AB%AF%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E
	 *  pushType	int		必选	推送类型，1：个人，2：群组，默认为1
		appId		String	必选	应用Id
		sender		String	必选	发送者帐号
		receiver	String	必选	接收者账号，如果是个人，最大上限100人/次，如果是群组，仅支持1个。
		msgType		int		必选	消息类型，1：文本消息，2：语音消息，3：视频消息，4：图片消息，5：位置消息，6：文件
		msgContent	String	可选	文本内容，最大长度2048字节，文本和附件二选一，不能都为空
		msgDomain	String	可选	扩展字段
		msgFileName	String	可选	文件名，最大长度128字节
		msgFileUrl	String	可选	文件绝对路径
	 */
    
    /* 先取消注释：httpclient:<artifactId>httpclient</artifactId>
     * public static void doPost(String url,String pushType,String receiver,String msgType,String authorization,String sender,Map<String,String> map) {  
        
        CloseableHttpClient httpclient = HttpClients.createDefault();  // 创建默认的httpClient实例.    
        
        //HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");  // 创建httppost
        HttpPost httppost = new HttpPost(url);  // 创建httppost    
        
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  // 创建参数队列   
        //formparams.add(new BasicNameValuePair("type", "house"));
        formparams.add(new BasicNameValuePair("appId", "8a48b551505b4af001505f21aca8091b"));//修改为实际APPID 
        formparams.add(new BasicNameValuePair("sender", "8001977500000001"));//修改为实际官方账号
        formparams.add(new BasicNameValuePair("pushType", pushType));
        formparams.add(new BasicNameValuePair("receiver", receiver));
        formparams.add(new BasicNameValuePair("msgType", msgType));
        
        JSONObject json = new JSONObject();
        json.put("appId", "8a48b551505b4af001505f21aca8091b");
        json.put("sender", "8001977500000001");
        json.put("pushType", pushType);
        json.put("receiver", receiver);
        json.put("msgType", msgType);
        
        formparams.add(new BasicNameValuePair("Accept", "application/json"));
        formparams.add(new BasicNameValuePair("Content-Type", "application/json;charset=utf-8"));
        formparams.add(new BasicNameValuePair("Authorization", authorization));
        formparams.add(new BasicNameValuePair("Authorization", authorization));
        
        if(null != map){
        	Iterator<String> it = map.keySet().iterator();
            String key = "";
            while(it.hasNext()){
            	key = it.next();
            	formparams.add(new BasicNameValuePair(key, map.get(key)));  
            }
        }
        UrlEncodedFormEntity uefEntity;  
        try {  
            //httppost.setHeader("Content-Length", "256");
            httppost.addHeader("Accept", "application/json");
           // httppost.addHeader("Content-Type", "application/json;charset=utf-8");
            httppost.addHeader("Authorization", authorization);
            httppost.addHeader("Host", "app.cloopen.com:8883");
            //uefEntity.setContentType("application/json;charset=utf-8");
            //httppost.addHeader("Content-type", "application/x-www-form-urlencoded");  
            // StringEntity se = new StringEntity(json.toJSONString(), "UTF-8");
             //uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            //httppost.setEntity(uefEntity);  
           // httppost.setEntity(se);  
            
            StringEntity s = new StringEntity(json.toString(),"UTF-8");
            //s.setContentEncoding("UTF-8");
            //s.setContentType("application/json");//发送json数据需要设置contentType
            httppost.setEntity(s);
            ////
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out.println("--------------------------------------");  
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                    System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  */
    
	
}

