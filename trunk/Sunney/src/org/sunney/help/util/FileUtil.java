package org.sunney.help.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author fisher
 * @description 文件工具类
 */

public class FileUtil {
	private static final Log Debug = LogFactory.getLog(FileUtil.class);

	// 获取从classpath根目录开始读取文件注意转化成中文
	public static String getCPFile(String path) {
		URL url = FileUtil.class.getClassLoader().getResource(path);
		String filepath = url.getFile();
		File file = new File(filepath);
		byte[] retBuffer = new byte[(int) file.length()];
		try {
			FileInputStream fis = new FileInputStream(filepath);
			fis.read(retBuffer);
			fis.close();
			return new String(retBuffer, "GBK");
		} catch (IOException e) {
			Debug.error("FileUtils.getCPFile读取文件异常：" + e.toString());
			return null;
		}
	}

	/**
	 * 获取系统中class文件的物理绝对根路径,比如: E:\eshop\eshop\classes
	 */
	public static String CLASS_PATH;
	static {
		try {
			URL url = FileUtil.class.getResource("/");
			CLASS_PATH = URLDecoder.decode(url.toString(), "GBK");
			if (CLASS_PATH.toUpperCase().endsWith("CLASSES/")
					|| CLASS_PATH.toUpperCase().endsWith("CLASSES\\")) {
				int i = CLASS_PATH.toUpperCase().lastIndexOf("CLASSES\\");
				if (i == -1) {
					i = CLASS_PATH.toUpperCase().lastIndexOf("CLASSES/");
				}
				CLASS_PATH = CLASS_PATH.substring(0, i);
			}
			if (CLASS_PATH.toUpperCase().startsWith("FILE:")) {
				CLASS_PATH = CLASS_PATH.substring(CLASS_PATH.toUpperCase()
						.indexOf("FILE:") + 6, CLASS_PATH.length());
			}
		} catch (Exception ex) {
			throw new RuntimeException("获取系统中class文件的物理绝对根路径异常："
					+ ex.toString());
		}
	}

	/**
	 * 利用java本地拷贝文件及文件夹,如何实现文件夹对文件夹的拷贝呢?如果文件夹里还有文件夹怎么办呢?
	 * 
	 * @param objDir
	 *            目标文件夹
	 * @param srcDir
	 *            源的文件夹
	 * @throws IOException
	 */
	public static void copyDirectiory(String objDir, String srcDir)
			throws IOException {
		(new File(objDir)).mkdirs();
		File[] file = (new File(srcDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				FileInputStream input = new FileInputStream(file[i]);
				FileOutputStream output = new FileOutputStream(objDir + "/"
						+ file[i].getName());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if (file[i].isDirectory()) {
				copyDirectiory(objDir + "/" + file[i].getName(), srcDir + "/"
						+ file[i].getName());
			}
		}
	}

	/**
	 * 将一个文件inName拷贝到另外一个文件outName中
	 * 
	 * @param inName
	 *            源文件路径
	 * @param outName
	 *            目标文件路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void copyFile(String inName, String outName)
			throws FileNotFoundException, IOException {
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(
				inName));
		BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(outName));
		copyFile(is, os, true);
	}

	/**
	 * Copy a file from an opened InputStream to opened OutputStream
	 * 
	 * @param is
	 *            source InputStream
	 * @param os
	 *            target OutputStream
	 * @param close
	 *            写入之后是否需要关闭OutputStream
	 * @throws IOException
	 */
	public static void copyFile(InputStream is, OutputStream os, boolean close)
			throws IOException {
		int b;
		while ((b = is.read()) != -1) {
			os.write(b);
		}
		is.close();
		if (close)
			os.close();
	}

	public static void copyFile(Reader is, Writer os, boolean close)
			throws IOException {
		int b;
		while ((b = is.read()) != -1) {
			os.write(b);
		}
		is.close();
		if (close)
			os.close();
	}

	public static void copyFile(String inName, PrintWriter pw, boolean close)
			throws FileNotFoundException, IOException {
		BufferedReader is = new BufferedReader(new FileReader(inName));
		copyFile(is, pw, close);
	}

	/**
	 * 从文件inName中读取第一行的内容
	 * 
	 * @param inName
	 *            源文件路径
	 * @return 第一行的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readLine(String inName) throws FileNotFoundException,
			IOException {
		BufferedReader is = new BufferedReader(new FileReader(inName));
		String line = null;
		line = is.readLine();
		is.close();
		return line;
	}

	/**
	 * default buffer size
	 */
	private static final int BLKSIZ = 8192;

	public static void copyFileBuffered(String inName, String outName)
			throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(inName);
		OutputStream os = new FileOutputStream(outName);
		int count = 0;
		byte b[] = new byte[BLKSIZ];
		while ((count = is.read(b)) != -1) {
			os.write(b, 0, count);
		}
		is.close();
		os.close();
	}

	/**
	 * 将String变成文本文件
	 * 
	 * @param text
	 *            源String
	 * @param fileName
	 *            目标文件路径
	 * @throws IOException
	 */
	public static void stringToFile(String text, String fileName)
			throws IOException {
		BufferedWriter os = new BufferedWriter(new FileWriter(fileName));
		os.write(text);
		os.flush();
		os.close();
	}

	/**
	 * 打开文件获得BufferedReader
	 * 
	 * @param fileName
	 *            目标文件路径
	 * @return BufferedReader
	 * @throws IOException
	 */
	public static BufferedReader openFile(String fileName) throws IOException {
		return new BufferedReader(new FileReader(fileName));
	}

	/**
	 * 获取文件filePath的字节编码byte[]
	 * 
	 * @param filePath
	 *            文件全路径
	 * @return 文件内容的字节编码
	 * @roseuid 3FBE26DE027D
	 */
	public static byte[] fileToBytes(String filePath) {
		if (filePath == null) {
			Debug.info("路径为空：");
			return null;
		}

		File tmpFile = new File(filePath);

		byte[] retBuffer = new byte[(int) tmpFile.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			fis.read(retBuffer);
			fis.close();
			return retBuffer;
		} catch (IOException e) {
			Debug.error("读取文件异常：" + e.toString());
			return null;
		}
	}

	/**
	 * 将byte[]转化成文件fullFilePath
	 * 
	 * @param fullFilePath
	 *            文件全路径
	 * @param content
	 *            源byte[]
	 */
	public static void bytesToFile(String fullFilePath, byte[] content) {
		if (fullFilePath == null || content == null) {
			return;
		}

		// 创建相应的目录
		File f = new File(getDir(fullFilePath));
		if (f == null || !f.exists()) {
			f.mkdirs();
		}

		try {
			FileOutputStream fos = new FileOutputStream(fullFilePath);
			fos.write(content);
			fos.close();
		} catch (Exception e) {
			Debug.error("写入文件异常:" + e.toString());
		}
	}

	/**
	 * 根据传入的文件全路径，返回文件所在路径
	 * 
	 * @param fullPath
	 *            文件全路径
	 * @return 文件所在路径
	 */
	public static String getDir(String fullPath) {
		int iPos1 = fullPath.lastIndexOf("/");
		int iPos2 = fullPath.lastIndexOf("\\");
		iPos1 = (iPos1 > iPos2 ? iPos1 : iPos2);
		return fullPath.substring(0, iPos1 + 1);
	}

	/**
	 * 根据传入的文件全路径，返回文件全名（包括后缀名）
	 * 
	 * @param fullPath
	 *            文件全路径
	 * @return 文件全名（包括后缀名）
	 */
	public static String getFileName(String fullPath) {
		int iPos1 = fullPath.lastIndexOf("/");
		int iPos2 = fullPath.lastIndexOf("\\");
		iPos1 = (iPos1 > iPos2 ? iPos1 : iPos2);
		return fullPath.substring(iPos1 + 1);
	}

	/**
	 * 获得文件名fileName中的后缀名
	 * 
	 * @param fileName
	 *            源文件名
	 * @return String 后缀名
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
	}

	/**
	 * 根据传入的文件全名（包括后缀名）或者文件全路径返回文件名（没有后缀名）
	 * 
	 * @param fullPath
	 *            文件全名（包括后缀名）或者文件全路径
	 * @return 文件名（没有后缀名）
	 */
	public static String getPureFileName(String fullPath) {
		String fileFullName = getFileName(fullPath);
		return fileFullName.substring(0, fileFullName.lastIndexOf("."));
	}

	/**
	 * 转换文件路径中的\\为/
	 * 
	 * @param filePath
	 *            要转换的文件路径
	 * @return String
	 */
	public static String wrapFilePath(String filePath) {
		filePath.replace('\\', '/');
		if (filePath.charAt(filePath.length() - 1) != '/') {
			filePath += "/";
		}
		return filePath;
	}

	/**
	 * 删除整个目录path,包括该目录下所有的子目录和文件
	 * 
	 * @param path
	 */
	public static void deleteDirs(String path) {
		File rootFile = new File(path);
		File[] files = rootFile.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				deleteDirs(file.getPath());
			} else {
				file.delete();
			}
		}
		rootFile.delete();
	}

}
