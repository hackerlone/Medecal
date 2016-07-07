package com.lhfeiyu.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：HTTP <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2016年4月12日14:30:35 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 http://lhfeiyu.com <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 * <strong> 备&nbsp;&nbsp;&nbsp;&nbsp;注：</strong>  <p>
 */
@Service("HttpService")
public class HttpService {
	
	
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url); // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();// 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
           /* conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Host", "app.cloopen.com:8883");
            conn.setRequestProperty("Content-Length", "252");
            conn.setRequestProperty("Authorization", authorization);*/
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
            connection.setRequestProperty("contentType", "utf-8");  
            connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();// 建立实际的连接
           /* Map<String, List<String>> map = connection.getHeaderFields();// 获取所有响应头字段
            for (String key : map.keySet()) {// 遍历所有的响应头字段
                System.out.println(key + "--->" + map.get(key));
            }*/
            in = new BufferedReader(new InputStreamReader(// 定义 BufferedReader输入流来读取URL的响应
                    connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            //result = new String(result.getBytes(), "utf-8");
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

    public static String xml2JSON(String xml) {  
        JSONObject obj = new JSONObject();  
        try {  
        	//System.err.println(xml);
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));//ISO-8859-1
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(is);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
    /** 
     * 一个迭代方法 
     * @param element : org.jdom.Element 
     * @return java.util.Map 实例 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    private static Map iterateElement(Element element) {  
        List jiedian = element.getChildren();  
        Element et = null;  
        Map obj = new HashMap();  
        List list = null;  
        for (int i = 0; i < jiedian.size(); i++) {  
            list = new LinkedList();  
            et = (Element) jiedian.get(i);  
            if (et.getTextTrim().equals("")) {  
                if (et.getChildren().size() == 0)  
                    continue;  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(iterateElement(et));  
                obj.put(et.getName(), list);  
            } else {  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(et.getTextTrim());  
                obj.put(et.getName(), list);  
            }  
        }  
        return obj;  
    }  
  
}

