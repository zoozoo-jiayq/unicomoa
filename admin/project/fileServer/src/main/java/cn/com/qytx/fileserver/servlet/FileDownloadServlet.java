package cn.com.qytx.fileserver.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.fileserver.service.FileserverService;
import cn.com.qytx.fileserver.utils.SysUtil;

/**
 * 功能: 文件下载servlet 
 * 版本: 1.0 
 * 开发人员: zyf 
 * 创建日期: 2015年4月24日 
 * 修改日期: 2015年4月24日 
 * 修改列表:
 */
public class FileDownloadServlet extends HttpServlet {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 6480285422999357538L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("filePath") == null && request.getParameter("fileId") == null) {// 相对路径必填
			SysUtil.toClient("参数不正确", response);
			return;
		}

		String filePath = SysUtil.getStringFromObject(request.getParameter("filePath"));
		String dataBaseFileName = "";
		if(filePath.equals("")){
			if(request.getParameter("fileId") == null){
				SysUtil.toClient("参数不正确", response);
				return;
			}else{//获得数据库中文件信息
				int fileId = Integer.valueOf(request.getParameter("fileId").toString());
				FileserverService fileService = (FileserverService) SysUtil.getApplicationContext(request).getBean("fileserverService");
				Map<String, Object> map = fileService.getAttachment(fileId);
				if(map!=null){
					filePath = SysUtil.getStringFromObject(map.get("attach_file"));
					dataBaseFileName = SysUtil.getStringFromObject(map.get("attach_name"));
				}else{
					SysUtil.toClient("服务器错误", response);
					return;
				}
			}
		}
		
		String fileName = request.getParameter("fileName") == null ? ""
				: request.getParameter("fileName").toString();

		response.reset();
		// 文件下载时采用文件流输出的方式处理
		response.setContentType("application/x-download");
		java.io.OutputStream outp = null;
		java.io.FileInputStream in = null;
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream(
					"/application.properties"));// 获取配置文件
			String uploadPath = prop.getProperty("uploadPath").toString();// 获取文件保存路径
			// 文件路径
			String fileRealPath = uploadPath + "/" + filePath;
			if (StringUtils.isEmpty(fileName)) {
				if(StringUtils.isEmpty(dataBaseFileName)){
					fileName = UUID.randomUUID().toString();
				}else{
					fileName = dataBaseFileName.substring(0,dataBaseFileName.indexOf("."));
				}
			}
			// 下载名
			String fileDisplayName = fileName
					+ filePath.substring(filePath.lastIndexOf("."));

			String osName=System.getProperty("os.name");
			String agent = (String) request.getHeader("USER-AGENT");
			if(osName.toLowerCase().indexOf("windows")>=0 ){
				if (agent != null && agent.indexOf("Firefox") >= 0) {// FF 火狐
					fileDisplayName = "=?UTF-8?B?"
							+ (new String(Base64.encodeBase64(fileDisplayName
									.getBytes("UTF-8")))) + "?=";
				} else {
					fileDisplayName = URLEncoder.encode(fileDisplayName, "UTF-8");
				}
			}
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileDisplayName);

			outp = response.getOutputStream();
			File tempFile = new File(fileRealPath);
			in = new FileInputStream(fileRealPath);
			long p = 0;
			long l = tempFile.length();
			if (request.getHeader("Range") != null) // 客户端请求的下载的文件块的开始字节
			{
				// 如果是下载文件的范围而不是全部,向客户端声明支持并开始文件块下载
				// 要设置状态
				// 响应的格式是:
				// HTTP/1.1 206 Partial Content
				response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);// 206

				// 从请求中得到开始的字节
				// 请求的格式是:
				// Range: bytes=[文件块的开始字节]-
				p = Long.parseLong(request.getHeader("Range")
						.replaceAll("bytes=", "").replaceAll("-", ""));
			}
			if (p != 0) {
				// 不是从最开始下载,
				// 响应的格式是:
				// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
				response.setHeader("Content-Range",
						"bytes " + new Long(p).toString() + "-"
								+ new Long(l - 1).toString() + "/"
								+ new Long(l).toString());
			}

			in.skip(p);
			response.addHeader("Content-Length", new Long(l - p).toString());
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
				in = null;
			}
		}
	}

}
