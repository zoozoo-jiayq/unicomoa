package cn.com.qytx.cbb.file.service.impl.txt.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import cn.com.qytx.cbb.file.service.IConvert;
import cn.com.qytx.cbb.file.service.impl.util.HtmlFile;
/**
 * 
 * <br/>功能: txt转html
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2013-7-10
 * <br/>修改日期: 2013-7-10
 * <br/>修改列表:
 */
public class TxtContverter implements IConvert {

	public void convert(String fileName, String outPutFile,String imagesPhysicalPath, String imageUrl,String title) throws Exception {
		try {
			String encoding = "GBK";
			File file = new File(fileName);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				StringBuffer sb = new StringBuffer();
				sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
				sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
				sb.append("<head>\n");
				sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />\n");
				sb.append("<title>"+title+"</title>\n");
				sb.append("</head>\n");
				sb.append("<body>\n");
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt+"<br/>");
				}
				sb.append("</body>\n");
				sb.append("</html>\n");
				bufferedReader.close();
				read.close();
				HtmlFile.writeFile(sb.toString(), outPutFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
