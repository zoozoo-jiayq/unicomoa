package cn.com.qytx.fileserver.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SysUtil {
	public final static String[] SAFE_EXTS = {"exe","dll","bat"}; 
	public final static String[] VIDEO_EXTS = {"mp4","avi","rmvb","wmv"}; 
	
	public static void  toClient(Object ret,HttpServletResponse response){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8"); 
			out = response.getWriter();
			out.println(ret);
		} catch (IOException e) {
			e.printStackTrace();

		}finally{
			if(out != null){
				out.flush();
				out.close();
				out = null;
			}
		}
	}
	
	/**将obj转换成字符串
	 * @param obj obj为基本数据类型
	 * @return
	 */
	public static String  getStringFromObject(Object obj){
		if (obj == null) {
			return "";
		} else if(StringUtils.isBlank(obj.toString())){
			return "";
		}else{
			return obj.toString();
		}		
	}
	
	
	/**去的application应用
	 * @param request
	 * @return
	 */
	public static ApplicationContext  getApplicationContext(HttpServletRequest request){
		ApplicationContext application = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		return application;
	}
	
	/**
     * 得到文件的扩展名,得不到返回空
     */
    public static String getExtension(String fileName)
    {
        if ((fileName != null) && (fileName.length() > 0))
        {
            int i = fileName.lastIndexOf('.');

            if ((i > -1) && (i < fileName.length() - 1))
            {
                return fileName.substring(i + 1);
            }
        }
        return "";
    }

    /**
     * 功能：判断文件类型是否是安全文件
     * @return
     */
    public static boolean isSaveFile(String ext){
    	if(Arrays.asList(SAFE_EXTS).contains(ext.toLowerCase())){
    		return false;
    	}
    	return true;
    }
	
    
    /**
     * 功能：判断文件类型是否是视频
     * @return
     */
    public static boolean isVideoFile(String ext){
    	if(Arrays.asList(VIDEO_EXTS).contains(ext.toLowerCase())){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 功能： 获取附件后缀
     * @return
     */
    public static String getAttacthSuffix(String attacthName){
    	String attacthSuffix = "file";
    	if(attacthName!=null){
    		attacthName = attacthName.toLowerCase();
    		
    		if(attacthName.trim().equals("")){
    			attacthSuffix = "file";
    		}else{
    			String[] atts = attacthName.split("\\.");
    			if(atts.length>1){
    				String att = atts[atts.length-1];
    				if(att.endsWith("doc")||att.endsWith("docx")){
    					attacthSuffix = "fileWord";
    				}else if(att.endsWith("xls")||att.endsWith("xlsx")){
    					attacthSuffix = "fileExcel";
    				}else if(att.endsWith("ppt")||att.endsWith("pptx")){
    					attacthSuffix = "filePPT";
    				}else if(att.endsWith("jpg")||att.endsWith("gif")||att.endsWith("png")||att.endsWith("jpeg")||att.endsWith("JPG")||att.endsWith("GIF")||att.endsWith("PNG")||att.endsWith("JPEG")){
    					attacthSuffix = "filePicture";
    				}else if(att.endsWith("rar")||att.endsWith("zip")||att.endsWith("7z")){
    					attacthSuffix = "fileRar";
    				}else if(att.endsWith("mp4")||att.endsWith("rmvb")||att.endsWith("avi")||att.endsWith("wmv")){
    					attacthSuffix = "fileVideo";
    				}else if(att.endsWith("mp3")){
    					attacthSuffix = "fileVoice";
    				}
    			}else{
    				attacthSuffix = "file";
    			}
    		}
    	}
		return attacthSuffix;
    }
}
