package cn.com.qytx.cbb.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.file.service.IConvert;
import cn.com.qytx.cbb.file.service.impl.hslf.converter.PPTConverter;
import cn.com.qytx.cbb.file.service.impl.hslf.converter.PPTXConverter;
import cn.com.qytx.cbb.file.service.impl.hssf.converter.XlsConverter;
import cn.com.qytx.cbb.file.service.impl.hwpf.converter.DocConverter;
import cn.com.qytx.cbb.file.service.impl.txt.converter.ImgContverter;
import cn.com.qytx.cbb.file.service.impl.txt.converter.TxtContverter;
import cn.com.qytx.cbb.file.service.impl.util.HtmlFile;
import cn.com.qytx.cbb.file.service.impl.xssf.converter.XlsxConverter;
import cn.com.qytx.cbb.file.service.impl.xwpf.converter.DocxConverter;
import cn.com.qytx.platform.base.action.BaseActionSupport;

public class FilePreviewAction extends BaseActionSupport {
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	IAttachment attachmentService;
	@Resource(name="filePathConfig")
	private FilePathConfig filePathConfig;
	
	/**
	 * 附件id
	 */
	private Integer attachId;
	private Integer attachmentId;
	/**
	 * 物理路径
	 */
	private String attachFile;
	/**
	 * 文件名
	 */
	private String attachName = "附件";
	
	private String filePath;
	
	
	// 附件存储的物理路径
	private String attachPath;
	

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getAttachId() {
		return attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}

	public String getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(String attachFile) {
		this.attachFile = attachFile;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	
	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * 
	 * 功能：文件预览
	 * 
	 * @return
	 */
	public String htmlPreview() {
		try {
			String previewFilePath="";
			 // TOMCAT根目录,上传文件路径
			String uploadFilePath = filePathConfig.getFileUploadPath()+"/upload/";
			String realPath=this.getRequest().getRealPath("//");
			String path = this.getRequest().getContextPath();
			String basePath = this.getRequest().getScheme() + "://"
					+ this.getRequest().getServerName() + ":"
					+ this.getRequest().getServerPort() + path + "/";	
			//	预览文件实际路径
			String realPathPreview=realPath+"/preview/";
			File filetemp = new File(realPathPreview);
			if (!filetemp.exists()) {
				// 建立文件夹
			    boolean result = filetemp.mkdir();
			    if (!result){
			        return ERROR;
			    }
			}
			String basePathPreview=basePath+"preview/";
			// 预览文件名
			String previewName ="";
			// 得到登录用户
				if (attachId != null) {
					Attachment attachment = attachmentService.getAttachment(attachId);
					attachFile = attachment.getAttachFile();
					attachName = attachment.getAttachName();
				}
				//判断文件是否存在
				File file =new File(uploadFilePath+attachFile);
				if(file.exists()){
					if (StringUtils.isNotEmpty(attachFile) && StringUtils.isNotEmpty(attachName)) {
						IConvert convert = null;
						String fileName= HtmlFile.getFileName(attachName,".");
						String fileType = HtmlFile.getFileType(attachName,".");
						//需要修改,不能每次生成一个
						previewName=HtmlFile.getFileName(HtmlFile.getFileType(attachFile,"/"),".")+".html";
						if (fileType.equalsIgnoreCase("xls")) {
							
							convert = new XlsConverter(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview);
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("xlsx")) {
							
							convert = new XlsxConverter(uploadFilePath+attachFile, realPathPreview+previewName);
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("pdf")) {
							
							//convert = new PdfConverter();
							//convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							//previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("doc")) {
							
							convert = new DocConverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("docx")) {
							
							convert = new DocxConverter(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview);
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("ppt")) {
							
							convert = new PPTConverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("pptx")) {
							
							convert = new PPTXConverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("txt")) {
							
							convert = new TxtContverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("jpg")||fileType.equalsIgnoreCase("png")
						        ||fileType.equalsIgnoreCase("gif") || fileType.equalsIgnoreCase("bmp")
						        ||fileType.equalsIgnoreCase("jpeg")) {
						    convert = new ImgContverter();
						    convert.convert(basePath+"filemanager/downview.action?_clientType=wap&attachPath="+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
                            previewFilePath=basePathPreview+previewName;
//                            return "img";
                        }
					}
				}
			this.getRequest().setAttribute("previewFilePath", previewFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			this.getRequest().setAttribute("previewFilePath", "");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 
	 * 功能：文件预览 手机端
	 * 
	 * @return
	 */
	public String htmlPreviewWap() {
		try {
			String previewFilePath="";
			 // TOMCAT根目录,上传文件路径
			String uploadFilePath = filePathConfig.getFileUploadPath()+"/upload/";
			
			String realPath=this.getRequest().getRealPath("//");
			String path = this.getRequest().getContextPath();
			String basePath = this.getRequest().getScheme() + "://"
					+ this.getRequest().getServerName() + ":"
					+ this.getRequest().getServerPort() + path + "/";	
			//	预览文件实际路径
			String realPathPreview=realPath+"/preview/";
			File filetemp = new File(realPathPreview);
			if (!filetemp.exists()) {
				// 建立文件夹
				boolean result = filetemp.mkdir();
				if (!result)
				{
				    return ERROR;
				}
			}
			String basePathPreview=basePath+"preview/";
			// 预览文件名
			String previewName ="";
			// 得到登录用户
			 
				if (attachId != null) {
					Attachment attachment = attachmentService.getAttachment(attachId);
					attachFile = attachment.getAttachFile();
					attachName = attachment.getAttachName();
				}
				//判断文件是否存在
				File file =new File(uploadFilePath+attachFile);
				if(file.exists()){
					if (StringUtils.isNotEmpty(attachFile) && StringUtils.isNotEmpty(attachName)) {
						IConvert convert = null;
						String fileName= HtmlFile.getFileName(attachName,".");
						String fileType = HtmlFile.getFileType(attachName,".");
						//需要修改,不能每次生成一个
						previewName=HtmlFile.getFileName(HtmlFile.getFileType(attachFile,"/"),".")+".html";
						if (fileType.equalsIgnoreCase("xls")) {
							
							convert = new XlsConverter(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview);
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("xlsx")) {
							
							convert = new XlsxConverter(uploadFilePath+attachFile, realPathPreview+previewName);
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("pdf")) {
							
							//convert = new PdfConverter();
							//convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							//previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("doc")) {
							
							convert = new DocConverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("docx")) {
							
							convert = new DocxConverter(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview);
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("ppt")) {
							
							convert = new PPTConverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("pptx")) {
							
							convert = new PPTXConverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equalsIgnoreCase("txt")) {
							
							convert = new TxtContverter();
							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						}else if (fileType.equalsIgnoreCase("jpg")||fileType.equalsIgnoreCase("png")
						        ||fileType.equalsIgnoreCase("gif") || fileType.equalsIgnoreCase("bmp")
						        ||fileType.equalsIgnoreCase("jpeg")) {
						    convert = new ImgContverter();
						    convert.convert(basePath+"filemanager/downview.action?_clientType=wap&attachPath="+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
                            previewFilePath=basePathPreview+previewName;
//                            return "img";
                        }
					}
				}
			this.getRequest().setAttribute("previewFilePath", previewFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			this.getRequest().setAttribute("previewFilePath", "");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	
	public String htmlPreviewByPath(){
		try {
			String previewFilePath="";
			 // TOMCAT根目录,上传文件路径
			String uploadFilePath = filePathConfig.getFileUploadPath()+"/upload/";
			
			String realPath=this.getRequest().getRealPath("//");
			String path = this.getRequest().getContextPath();
			String basePath = this.getRequest().getScheme() + "://"
					+ this.getRequest().getServerName() + ":"
					+ this.getRequest().getServerPort() + path + "/";	
			//	预览文件实际路径
			String realPathPreview=realPath+"/preview/";
			File filetemp = new File(realPathPreview);
			if (!filetemp.exists()) {
				// 建立文件夹
				boolean result = filetemp.mkdir();
				if (!result)
				{
				    return ERROR;
				}
			}
			String basePathPreview=basePath+"preview/";
			// 预览文件名
			String previewName ="";
			// 得到登录用户
				//判断文件是否存在
				File file =new File(uploadFilePath+filePath);
				if(file.exists()){
					if (StringUtils.isNotEmpty(filePath) && StringUtils.isNotEmpty(attachName)) {
						IConvert convert = null;
						String fileName= HtmlFile.getFileName(attachName,".");
						String fileType = HtmlFile.getFileType(attachName,".");
						//需要修改,不能每次生成一个
						previewName=HtmlFile.getFileName(HtmlFile.getFileType(filePath,"/"),".")+".html";
						if (fileType.equals("xls")) {
							
							convert = new XlsConverter(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview);
							convert.convert(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equals("xlsx")) {
							
							convert = new XlsxConverter(uploadFilePath+filePath, realPathPreview+previewName);
							convert.convert(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equals("pdf")) {
							
//							convert = new PdfConverter();
//							convert.convert(uploadFilePath+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
//							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equals("doc")) {
							
							convert = new DocConverter();
							convert.convert(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equals("docx")) {
							
							convert = new DocxConverter(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview);
							convert.convert(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equals("ppt")) {
							
							convert = new PPTConverter();
							convert.convert(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equals("pptx")) {
							
							convert = new PPTXConverter();
							convert.convert(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						} else if (fileType.equals("txt")) {
							
							convert = new TxtContverter();
							convert.convert(uploadFilePath+filePath, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
							previewFilePath=basePathPreview+previewName;
							
						}else if (fileType.equalsIgnoreCase("jpg")||fileType.equalsIgnoreCase("png")
						        ||fileType.equalsIgnoreCase("gif") || fileType.equalsIgnoreCase("bmp")
						        ||fileType.equalsIgnoreCase("jpeg")) {
						    convert = new ImgContverter();
						    convert.convert(basePath+"filemanager/downview.action?_clientType=wap&attachPath="+attachFile, realPathPreview+previewName, realPathPreview, basePathPreview,fileName);
                            previewFilePath=basePathPreview+previewName;
//                            return "img";
                        }
					}
				}
			this.getRequest().setAttribute("previewFilePath", previewFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			this.getRequest().setAttribute("previewFilePath", "");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	 /**
     * 下载文件
     */
    public void downFile()
    {
        // 文件下载时采用文件流输出的方式处理：
        if (this.getRequest().getParameter("attachmentId") != null)
        {
            String attachmentIdStr = this.getRequest().getParameter("attachmentId").toString();
            attachmentId = Integer.parseInt(attachmentIdStr);
        }
        HttpServletResponse response = this.getResponse();
        response.reset();
//        response.setContentType("application/x-download");
        java.io.OutputStream outp = null;
        java.io.FileInputStream in = null;
        try
        {
            // 获取文件保存路径
            String catalinaHome = filePathConfig.getFileUploadPath();
            String targetDirectory = null;
            String filedownload =null;
            String filedisplay = null;
            if (attachmentId != null){
                Attachment attachment = attachmentService.getAttachment(attachmentId);
                targetDirectory = catalinaHome + "/upload/";
                filedownload  = targetDirectory + attachment.getAttachFile();
                filedisplay = attachment.getAttachName();
            } else {
                filedownload = catalinaHome + "/upload/" + attachPath;
                filedisplay = attachName;
            }
            
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "inline;filename=" + filedisplay);
            outp = response.getOutputStream();
            in = new FileInputStream(filedownload);
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0)
            {
                outp.write(b, 0, i);
            }
            outp.flush();
        }
        catch (Exception e)
        {
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
    }
	
	
}
