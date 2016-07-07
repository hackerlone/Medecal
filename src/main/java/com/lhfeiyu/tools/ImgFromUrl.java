package com.lhfeiyu.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lhfeiyu.thirdparty.wx.business.AuthAccess;

/**
 * @说明 从网络获取图片到本地
 * @version 1.0
 * @since
 */
public class ImgFromUrl {
	
	public static String fileType = "jpg";
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://www.baidu.com/img/baidu_sylogo1.gif";
		byte[] btImg = getImageFromNetByUrl(url);
		if (null != btImg && btImg.length > 0) {
			System.out.println("读取到：" + btImg.length + " 字节");
			String fileName = "百度.gif";
			String filePath = writeImageToDisk(btImg, fileName);
			System.out.println(filePath);
		} else {
			System.out.println("没有从该连接获得内容");
		}
	}
	
	public static String saveImgFromUrl(String mediaId, String url){
		String path = "";
		if(Check.isNotNull(mediaId)){
			String access_token = AuthAccess.getWxDataFromProperty("access_token");//从Property文件中获取access_token,如果文件中没有，则会远程获取
			url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+access_token+"&media_id="+mediaId;
		}else if(!Check.isNotNull(url)){return "";}
		byte[] btImg = getImageFromNetByUrl(url);
		if (null != btImg && btImg.length > 0) {
			//System.out.println("读取到：" + btImg.length + " 字节");
			String fileName = CommonGenerator.getSerialByDate();
			fileName += "."+fileType;
			path = writeImageToDisk(btImg, fileName);
		} else {
			System.out.println("没有从该连接获得内容");
		}
		return path;
	}

	/**
	 * 将图片写入到磁盘
	 * @param img 图片数据流
	 * @param fileName   文件保存时的名称
	 */
	public static String writeImageToDisk(byte[] img, String fileName) {
		String path = "";
		try {
			//path = "D:/file/default/" + fileName;
			path = "E:/3_project/Medical/file/wx/" + fileName;
			File file = new File(path);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			System.out.println("图片已经保存");
		} catch (Exception e) {
			e.printStackTrace();
		}
		path = "/file/wx/" + fileName;
		return path;
	}

	/**
	 * 根据地址获得数据的字节流
	 * 
	 * @param strUrl  网络连接地址
	 * @return
	 */
	public static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			//System.out.println(conn.getHeaderField("Content-Type"));
			//System.out.println(conn.getHeaderField("Content-disposition"));
			//String name = conn.getHeaderField("filename");
			String contentType = conn.getHeaderField("Content-Type");
			//System.out.println(name);
			if(Check.isNotNull(contentType)){
				//attachment; filename=
				fileType = contentType.replace("image/", "");
			}
			/*if(Check.isNotNull(name)){
				fileType = name.replace("attachment; filename=", "");
			}*/
			
			byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从输入流中获取数据
	 * @param inStream  输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}
