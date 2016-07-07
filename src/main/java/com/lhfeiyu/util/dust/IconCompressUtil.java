package com.lhfeiyu.util.dust;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

public class IconCompressUtil {
	public String path = "";

	/**
	 * @param oldfile
	 * @param size
	 * @param newfile
	 * @return
	 * @描述 —— 将oldfile的图片文件等比例压缩为size的newfile文件
	 */
	public static File compressImg(File oldfile, int size, File newfile) {
		if (!newfile.exists())
			try {
				newfile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				System.out.println("无法创建文件！！！");
				return null;
			}
		BufferedImage bi;
		try {
			System.out.println("正在压缩:" + oldfile.getName());
			bi = ImageIO.read(new FileInputStream(oldfile));
			int width = bi.getWidth();
			int height = bi.getHeight();
			if (width > size || height > size) {
				Image image;
				if (width > height) {
					height = (int) (bi.getHeight() / (bi.getWidth() * 1d) * size);
					image = bi.getScaledInstance(size, height,
							Image.SCALE_DEFAULT);
				} else {
					width = (int) (bi.getWidth() / (bi.getHeight() * 1d) * size);
					image = bi.getScaledInstance(width, size,
							Image.SCALE_DEFAULT);
				}
				ImageIO.write(toBufferedImage(image), "png",
						new FileOutputStream(newfile));
				System.out.println("压缩完成:" + newfile.getName());
				return newfile;
			} else {
				System.out.println("无须压缩:" + oldfile.getName());
				return oldfile;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.TRANSLUCENT;
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
		}
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	/**
	 * @return
	 * @描述 —— 生成随机名字，不可能重复(用于文件的命名)
	 */
	public static String getRandomName() {
		Random r = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		StringBuffer sb = new StringBuffer();
		sb.append(r.nextInt(100));
		sb.append(r.nextInt(100));
		sb.append("_");
		sb.append(sdf.format(new Date()));
		sb.append("_");
		sb.append(r.nextInt(100));
		sb.append(r.nextInt(100));
		return sb.toString();
	}

	/**
	 * @param inputFile源文件
	 * @param outFile生成文件
	 * @param width指定宽度
	 * @param height指定高度
	 * @param proportion是否等比例操作
	 * @return
	 * @描述 —— 是否等比例缩放图片
	 */
	public static boolean compressPic(String inputFile, String outFile,
			int width, int height, boolean proportion) {
		try {
			// 获得源文件
			File file = new File(inputFile);
			if (!file.exists()) {
				return false;
			}
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return false;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) width + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) height + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) ((img.getWidth(null)) / rate);
					newHeight = (int) ((img.getHeight(null)) / rate);
				} else {
					newWidth = width; // 输出的图片宽度
					newHeight = height; // 输出的图片高度
				}

				// 如果图片小于目标图片的宽和高则不进行转换
				/*
				 * if (img.getWidth(null) < width && img.getHeight(null) <
				 * height) { newWidth = img.getWidth(null); newHeight =
				 * img.getHeight(null); }
				 */
				BufferedImage tag = new BufferedImage(newWidth,
						newHeight, BufferedImage.TYPE_INT_RGB);

				// Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的,优先级比速度高 生成的图片质量比较好 但速度慢
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(outFile);
				// JPEGImageEncoder可适用于其他图片类型的转换
				//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); -yrh comment
				//encoder.encode(tag); -yrh comment
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * @param srcFile源文件
	 * @param outFile输出文件
	 * @param x坐标
	 * @param y坐标
	 * @param width宽度
	 * @param height高度
	 * @return
	 * @描述 —— 裁剪图片
	 */
	public static boolean cutPic(String srcFile, String outFile, int x, int y,
			int width, int height) {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// 如果源图片不存在
			if (!new File(srcFile).exists()) {
				return false;
			}

			// 读取图片文件
			is = new FileInputStream(srcFile);

			// 获取文件格式
			String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);

			// ImageReader声称能够解码指定格式
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
			ImageReader reader = it.next();

			// 获取图片流
			iis = ImageIO.createImageInputStream(is);

			// 输入源中的图像将只按顺序读取
			reader.setInput(iis, true);

			// 描述如何对流进行解码
			ImageReadParam param = reader.getDefaultReadParam();

			// 图片裁剪区域
			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标
			param.setSourceRegion(rect);

			// 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			File tempOutFile = new File(outFile);
			if (!tempOutFile.exists()) {
				tempOutFile.mkdirs();
			}
			ImageIO.write(bi, ext, new File(outFile));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * @param doubleValue
	 * @return
	 * @描述 —— 将浮点型数据保留整数位转换成int型
	 */
	public static Integer getRoundIntFromDouble(Double doubleValue) {
		return Integer.parseInt(String.valueOf(Math.round(doubleValue)));
	}
	
	public IconCompressUtil(String path) {
		this.path = path;
	}
	public void change(int size) {
		compressImg(new File(path), size, null);
	}
}