package cn.com.qytx.cbb.file.service.impl.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class HtmlFile {
	/**
	 * 
	 * 功能： 写文件
	 * @param content
	 * @param path
	 */
	public static void writeFile(String content, String path) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}
	/**
	 * 功能： 得到文件的扩展名,得不到返回空
	 * 
	 * @return 文件扩展名
	 */
	public static String getFileName(String fileName,String sign) {
		if ((fileName != null) && (fileName.length() > 0)&&sign!=null) {
			int i = fileName.lastIndexOf(sign);

			if ((i > -1) && (i < fileName.length() - 1)) {
				return fileName.substring(0, i);
			}
		}
		return "";
	}
	/**
	 * 功能： 得到文件的扩展名,得不到返回空
	 * 
	 * @return 文件扩展名
	 */
	public static String getFileType(String fileName,String sign) {
		if ((fileName != null) && (fileName.length() > 0)&&sign!=null) {
			int i = fileName.lastIndexOf(sign);

			if ((i > -1) && (i < fileName.length() - 1)) {
				return fileName.substring(i + 1);
			}
		}
		return "";
	}
}
