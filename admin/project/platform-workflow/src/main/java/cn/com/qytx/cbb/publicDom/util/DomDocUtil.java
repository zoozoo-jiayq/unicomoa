package cn.com.qytx.cbb.publicDom.util;

import java.io.File;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能：doc文档转pdf文件工具类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:28:56 
 * 修改日期：上午10:28:56 
 * 修改列表：
 */
public class DomDocUtil {

		public static void toPdf(String instanceId){
			Thread t = new Thread(new Office2Pdf(instanceId));
			t.start();
		}
		
		/**
		 * 功能：根据附件ID获取文件路径
		 * @param
		 * @return
		 * @throws   
		 */
		public static  String getInputFileByAttachId(int attachId){
			IAttachment attachmentService = (IAttachment) SpringUtil.getBean("attachmentService");
			FilePathConfig filePathConfig = (FilePathConfig) SpringUtil.getBean("filePathConfig");
			Attachment ach = attachmentService.getAttachment(attachId);
			if(ach!=null){
				String refPath = ach.getAttachFile();
				
				String catalinaHome = filePathConfig.getFileUploadPath();
		        String inputFile = catalinaHome+"/upload/"+refPath;
		        return inputFile;
			}
			return "";
		}
		
}

class Office2Pdf implements Runnable{

	private String instanceId;
	
	public Office2Pdf(String instanceId) {
		super();
		this.instanceId = instanceId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		toPdf(instanceId);
	}
	
	/**
	 * 功能：doc转pdf
	 * @param
	 * @return
	 * @throws   
	 */
	private  void toPdf(String instanceId){
		IDocumentExtService documentExtService = (IDocumentExtService) SpringUtil.getBean("documentExtService");
		DocumentExt documentExt= documentExtService.findByProcessInstanceId(instanceId);
		if(documentExt!=null){
			Integer attachId = documentExt.getAttachId();
			if(attachId!=null){
				String inputFile = DomDocUtil.getInputFileByAttachId(attachId);
				if(inputFile!=null && !inputFile.equals("")){
					docToPdf(new File(inputFile), new File(inputFile+".pdf"));
				}
			}
		}
	}
	
	private   void docToPdf(File inputFile, File outputFile){
	    OfficeUtils utils = new OfficeUtils();
	    utils.convertToPdf(inputFile, outputFile);
	}
	
	
	
}
