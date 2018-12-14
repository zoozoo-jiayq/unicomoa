package cn.com.qytx.cbb.publicDom.action;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;

public class WebSignManagerAction  extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	@Resource(name="documentExtService")
	private IDocumentExtService documentExtService;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	private File SIGNSFILE; 
	private Integer documentExtId; //
	
	/**
	 * 功能：保存附件信息
	 * @return
	 */
	public String saveFile(){
		DocumentExt documentExt =documentExtService.findOne(documentExtId);
		if(documentExt!=null){
			java.io.InputStream inStream;
			try {
				inStream = new java.io.FileInputStream(SIGNSFILE);
				byte[] bytes = new byte[(int)SIGNSFILE.length()];
				inStream.read(bytes);
				inStream.close();
				documentExt.setSignData(bytes);
				documentExt.setCompanyId(TransportUser.get().getCompanyId());
				documentExtService.saveOrUpdate(documentExt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 功能：获取所有印章数据
	 * @return
	 */
	public String getContent(){
		HttpServletResponse response = this.getResponse();
		try {
			DocumentExt documentExt =documentExtService.findOne(documentExtId);
			if(documentExt!=null&&documentExt.getSignData()!=null){
			OutputStream out = response.getOutputStream();
			out.write(documentExt.getSignData());
			out.flush();
			out.close();
			out = null;
			}else{ //没有数据
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(this.getResponse().getWriter());
			        writer.print(-1);
					writer.flush();
					writer.close();
				} catch (Exception ex) {
					ajax(-1);
					LOGGER.error(ex.getMessage());
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	
	public Integer getDocumentExtId() {
		return documentExtId;
	}
	public void setDocumentExtId(Integer documentExtId) {
		this.documentExtId = documentExtId;
	}

	public File getSIGNSFILE() {
		return SIGNSFILE;
	}

	public void setSIGNSFILE(File sIGNSFILE) {
		SIGNSFILE = sIGNSFILE;
	}

 
	
	
	
	
}
