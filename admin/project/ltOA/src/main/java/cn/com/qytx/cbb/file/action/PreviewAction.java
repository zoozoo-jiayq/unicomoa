package cn.com.qytx.cbb.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.util.OfficeUtils;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能: doc文档预览接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2016年3月9日
 * 修改日期: 2016年3月9日
 * 修改列表:
 */
public class PreviewAction extends BaseActionSupport {

	@Autowired
	private IAttachment attachmentService;
	
    @Resource(name="filePathConfig")
    private FilePathConfig filePathConfig;
	
	private Integer attachmentId;
	
	private String previewUrl;
	
	public String previewFile(){
		HttpServletResponse response = this.getResponse();
	    HttpServletRequest request = this.getRequest();
        response.reset();
        java.io.OutputStream outp = null;
        java.io.FileInputStream in = null;
		try {
			Attachment attachment = attachmentService.findOne(attachmentId);
			if(attachment!=null){
				String name = attachment.getAttachName();
				if(name.toLowerCase().endsWith("doc")){
					String attachName = attachment.getAttachFile().substring(attachment.getAttachFile().lastIndexOf("/")+1,attachment.getAttachFile().lastIndexOf("."));
					String attachPath = attachment.getAttachFile().substring(0,attachment.getAttachFile().lastIndexOf("/")+1);
					String catalinaHome = filePathConfig.getFileUploadPath();
		            String targetDirectory = catalinaHome + "/upload/";
		            String filedownload = targetDirectory + attachment.getAttachFile();
		            File f = new File(filedownload);
		            if(f.exists()){
		            	//如果doc文档存在，判断预览html是否存在
		            	String htmlPath =  targetDirectory+(attachment.getAttachFile().substring(0,attachment.getAttachFile().lastIndexOf(".")))+".html";
		            	File hf = new File(htmlPath);
		            	if(hf.exists()){
		            		//预览页面存在
		            		previewUrl = htmlPath;
		            	}else{
		            		//预览页面不存在则新增预览页面
		            		OfficeUtils office = new OfficeUtils();
							String newHTMLName = office.convertToHtml(f, targetDirectory+attachPath, "",attachName);
							if(!newHTMLName.equals("error")){
								previewUrl = htmlPath;
							}
		            	}
		            }
				}
			}
			if(StringUtils.isNotEmpty(previewUrl)){
				File tempFile = new File(previewUrl);
				response.addHeader("Content-Length", ""+tempFile.length());
				outp = response.getOutputStream();
				in = new FileInputStream(previewUrl);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0)
				{
					outp.write(b, 0, i);
				}
				outp.flush();
			}else{
				ajax("该文件不支持预览");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    LOGGER.error("downFile error.", e);
                }
                in = null;
            }
        }
		return null;
	}

	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
}
