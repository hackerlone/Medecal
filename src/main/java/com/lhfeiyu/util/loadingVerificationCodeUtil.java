package com.lhfeiyu.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;


public class loadingVerificationCodeUtil {

	public static String strCode = null;
	/*public static final char[] CHARS = {'0','1','2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','I','J', 'K', 'L', 'M',
			'N', 'O','P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ,'a',
			'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s',
			't','u','v','w','x','y','z'};
	*/
	public static final char[] CHARS = {'0','1','2', '3', '4', '5', '6', '7', '8','9'};//纯数字
	
	public static Random random = new Random();
	 //获取四位随机数 
	public static String getRandomString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		strCode = buffer.toString();
		System.out.println("4位随机数：" + strCode);
		return strCode;
	}
	//获取随机颜色 
	public static Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random
				.nextInt(255));
	}
	//返回某颜色的反色 
	public static Color getReverseColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c
				.getBlue());
	}
	//创建图片
	public static BufferedImage createImage() {
		String randomString = getRandomString();//获取随机字符串 

		int width = 77;//设置图片的宽度 
		int height = 26;//高度 

		Color color = getRandomColor();//获取随机颜色，作为背景色 
//		Color reverse = getReverseColor(color);//获取反色，用于前景色 
		//创建一个彩色图片 
		BufferedImage image = new BufferedImage(width, height, 1);
		Graphics2D g = image.createGraphics();
		g.fillRect(0, 0, width, height);//绘制背景 
		g.setFont(new Font("SansSerif", 1, 21));//设置字体 
		g.setBackground(Color.WHITE);//设置背景色
//		g.setPaint(Color.blue);//设置画笔,设置Paint属性
//		g.setPaint(color);//设置画笔,设置Paint属性   随机字体颜色
		g.setColor(color);//设置颜色 

		
//		g.setColor(reverse);//设置颜色 
		g.drawString(randomString, 5, 23);
		//最多绘制100个噪音点 
		int i = 0;
		for (int n = random.nextInt(30); i < n; i++) {
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
		}

		return image;
	}
	
}
