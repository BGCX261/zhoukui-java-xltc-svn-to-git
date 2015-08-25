package org.sunney.help.util;

import java.io.*;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import org.apache.log4j.Logger;

public class ImageUtils {
	private static final Logger logger = Logger.getLogger(ImageUtils.class);

	private BufferedImage image = null;

	public void load(File imageFile) throws IOException {
		image = ImageIO.read(imageFile);
	}

	public int getImageWidth() {
		return image.getWidth();
	}

	public int getImageHeight() {
		return image.getHeight();
	}

	public void cutTo(int tarWidth, int tarHeight) throws FileNotFoundException {
		if (image == null) {
			throw new FileNotFoundException("image file not be load.please execute 'load' function agin.");
		}

		int iSrcWidth = getImageWidth(); // 得到源图宽
		int iSrcHeight = getImageHeight(); // 得到源图长

		// 如果源图片的宽度或高度小于目标图片的宽度或高度，则直接返回出错
		if (iSrcWidth < tarWidth || iSrcHeight < tarHeight) {
			logger.error("source image size too small.");
			throw new RuntimeException("source image size too small.");
		}

		// 先求源图和目标图的尺寸比例
		double dSrcScale = iSrcWidth * 1.0 / iSrcHeight;
		double dDstScale = tarWidth * 1.0 / tarHeight;

		// 先确定剪裁尺寸
		int iDstLeft, iDstTop, iDstWidth, iDstHeight;
		if (dDstScale > dSrcScale) { // 目标图片宽
			iDstWidth = iSrcWidth;
			iDstHeight = (int) (iDstWidth * 1.0 / dDstScale);
		} else { // 目标图片高
			iDstHeight = iSrcHeight;
			iDstWidth = (int) (iDstHeight * dDstScale);
		}
		iDstLeft = (iSrcWidth - iDstWidth) / 2;
		iDstTop = (iSrcHeight - iDstHeight) / 2;

		// 剪裁
		this.image = image.getSubimage(iDstLeft, iDstTop, iDstWidth, iDstHeight);

	}

	public void zoomTo(int tarWidth, int tarHeight) {
		BufferedImage tagImage = new BufferedImage(tarWidth, tarHeight, BufferedImage.TYPE_INT_RGB); // 缩放图像
		Image image = this.image.getScaledInstance(tarWidth, tarHeight, Image.SCALE_SMOOTH);
		Graphics g = tagImage.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制目标图
		g.dispose();
		this.image = tagImage;

	}

	public void save(String fileName, String formatName) throws IOException {
		// 写文件
		FileOutputStream out = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(this.image, formatName, bos);// 输出到bos
			out = new FileOutputStream(fileName);
			out.write(bos.toByteArray()); // 写文件
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				logger.warn(e);
			}
		}
	}

	public static boolean zoomImage(String srcFile, String dstFile, int width, int height, String formatName) {
		try {
			ImageUtils zoom = new ImageUtils();
			zoom.load(new File(srcFile));
			zoom.zoomTo(width, height);
			zoom.save(dstFile, formatName);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static ImageUtils fromImageFile(File file) throws IOException {
		ImageUtils utils = new ImageUtils();
		utils.load(file);
		return utils;
	}

	public static ImageUtils load(String fileName) throws IOException {
		File file = new File(fileName);
		return fromImageFile(file);
	}

	public static void main(String[] args) throws IOException {
		ImageUtils img = load("D:/temp/imgutil.jpg");
		int heigth = img.getImageHeight();
		int width = img.getImageWidth();
		System.out.println("height=" + heigth+"---width=" + width);
	}
}
