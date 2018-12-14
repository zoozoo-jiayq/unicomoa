package cn.com.qytx.cbb.publicDom.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能：公文附件表工具类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:28:13 
 * 修改日期：上午9:28:13 
 * 修改列表：
 */
public class DocumentExtUtil {

	/**
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 功能：创建公文
	 * @param
	 * @return
	 * @throws   
	 */
	public static int generateDocumentExt(String instanceId,String moduleName) throws IOException{
		IAttachment attachmentService = (IAttachment) SpringUtil.getBean("attachmentService");
		IDocumentExtService documentExtService = (IDocumentExtService) SpringUtil.getBean("documentExtService");
	    FilePathConfig filePathConfig = (FilePathConfig) SpringUtil.getBean("filePathConfig");
		DocumentExt ext = new DocumentExt();
		ext.setProcessInstanceId(instanceId);
		String uploadFilePath = filePathConfig.getFileUploadPath()+"\\upload\\";
		File f = new File(uploadFilePath+moduleName);
		if(!f.exists()){
			f.mkdirs();
		}
		String str = ".doc";
		InputStream is = DocumentExtUtil.class.getResourceAsStream("/cn/com/qytx/cbb/publicDom/util/office_blank.doc");
		if(is == null){
		    is = DocumentExtUtil.class.getResourceAsStream("/cn/com/qytx/cbb/publicDom/util/office_blank.docx");
		    str = ".docx";
		}
		String destpath = moduleName+"/"+instanceId+str;
	    File dest = new File(uploadFilePath+destpath);
	    FileOutputStream fos = new FileOutputStream(dest);
	    byte[] bs = new byte[1024];
	    int l = is.read(bs);
	    while(l!=-1){
	    	fos.write(bs, 0, l);
	    	fos.flush();
	    	bs = new byte[1024];
	    	l = is.read(bs);
	    }
	    
	    fos.close();
	    is.close();
	    Attachment attach = new Attachment();
	    attach.setAttachFile(destpath);
	    attach.setAttachName(instanceId+str);
	    attach.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	    attach.setIsDelete(0);
	    attach.setCreateUserId(0);
	    attach.setCompanyId(TransportUser.get().getCompanyId());
	    attachmentService.saveOrUpdate(attach);
	    ext.setAttachId(attach.getId());
	    ext.setCompanyId(TransportUser.get().getCompanyId());
	    documentExtService.saveOrUpdate(ext);
	    return ext.getDocumentExtId();
	}
}
