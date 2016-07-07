package com.lhfeiyu.util.dust;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CircleCut {
	
	String srcFile;
	String newFile;

	public static boolean ImgCircleCut(String srcFile, String newFile) {
		try {
			BufferedImage bi1 = ImageIO.read(new File(srcFile));// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
			BufferedImage bi2 = new BufferedImage(bi1.getWidth(),bi1.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(),bi1.getHeight());
			Graphics2D g2 = bi2.createGraphics();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
			g2.setComposite(ac);
			g2.setBackground(new Color(22, 2, 2, 0));
			g2.fill3DRect(-1000000, 200, 180, 80, false);// -1000000：在编写这个类时测试发现将此值设为极小值则可切出圆形
			g2.setClip(shape);// g2.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
			g2.drawImage(bi1, 0, 0, null);// 使用 setRenderingHint 设置抗锯齿
			g2.dispose();
			ImageIO.write(bi2, "png", new File(newFile));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getSrcFile() {
		return srcFile;
	}
	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}
	public String getNewFile() {
		return newFile;
	}
	public void setNewFile(String newFile) {
		this.newFile = newFile;
	}
}