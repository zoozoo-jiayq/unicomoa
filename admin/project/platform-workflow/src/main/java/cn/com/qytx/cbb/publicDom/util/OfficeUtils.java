package cn.com.qytx.cbb.publicDom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 功能：OFFICE工具类，提供OFFICE转PDF，OFFICE转HTML功能
 * 作者： jiayongqiang
 * 创建时间：2014年8月16日
 */
public class OfficeUtils {
	
	private DocumentConverter convert = null;
	private OpenOfficeConnection con = null;
	
	public OfficeUtils(){
		init();
	}

	/**
	 * 功能：Office转PDF
	 * 作者：jiayongqiang
	 * 参数：docFile:office源文档，targetFile:PDF文档
	 * 输出：
	 */
	public  void convertToPdf(File docFile,File targetFile){
		convert.convert(docFile, targetFile);
		con.disconnect();
	}
	
	/**
	 * 功能：Doc文档转HTML
	 * 作者：jiayongqiang
	 * 参数：DocFile:doc源文档，targetFile:HTML目标文档，imgPath:图片路径
	 * 输出：HTML字符串
	 */
	public  String convertToHtml(File docFile,String htmlFilePath,String imgPath){
		 // 创建保存html的文件
	    File htmlFile = new File(htmlFilePath + "/" + new Date().getTime()
	        + ".html");
	    if(docFile == null || (!docFile.exists())){
	    	return "";
	    }
	    if(!htmlFile.exists()){
	    	try {
				htmlFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		convert.convert(docFile, htmlFile);
		con.disconnect();
		String htmlStr = toHtmlString(htmlFile, imgPath);
		return htmlStr;
	}
	
	/**
	 * 功能：初始化OpenOffice服务
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private  void init(){
		 this.con = new SocketOpenOfficeConnection("127.0.0.1",8100);
		    try{
		    	con.connect();
			    this.convert = new OpenOfficeDocumentConverter(con);
		    }catch(ConnectException cex){
		    	cex.printStackTrace();
		    }
	}
	

    /**
     * 功能：获取HTMLfile的源码
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    private  String toHtmlString(File htmlFile,String imgPath) {
	    StringBuffer htmlSb = new StringBuffer();
	    try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	            new FileInputStream(htmlFile)));
	        while (br.ready()) {
	        htmlSb.append(br.readLine());
	        }
	        br.close();
	        // 删除临时文件
	        htmlFile.delete();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // HTML文件字符串
	    String htmlStr = htmlSb.toString();
	    // 返回经过清洁的html文本
	    return clearFormat(htmlStr, imgPath);
    }

    /**
     * 清除一些不需要的html标记
     * 
     * @param htmlStr
     *                带有复杂html标记的html语句
     * @return 去除了不需要html标记的语句
     */
    private  String clearFormat(String htmlStr, String docImgPath) {
	    // 获取body内容的正则
	    String bodyReg = "<BODY .*</BODY>";
	    Pattern bodyPattern = Pattern.compile(bodyReg);
	    Matcher bodyMatcher = bodyPattern.matcher(htmlStr);
	    if (bodyMatcher.find()) {
	        htmlStr = bodyMatcher.group().replaceFirst("<BODY", "<DIV")
	            .replaceAll("</BODY>", "</DIV>");
	    }
	    // 调整图片地址
	    htmlStr = htmlStr.replaceAll("<IMG SRC=\"", "<IMG SRC=\"" + docImgPath
	        + "/");
	    return htmlStr;
    }
	
    
	
}
