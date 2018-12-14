package cn.com.qytx.cbb.file.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.file.dao.AttachmentDao;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("attachmentService")
@Transactional
public class AttachmentImpl extends BaseServiceImpl<Attachment> implements IAttachment{
	
	@Resource(name = "attachmentDao")
	private AttachmentDao attachmentDao;
	
	@Transactional(readOnly=true)
	public Attachment getAttachment(int attachmentId) {
		return attachmentDao.findOne(attachmentId);
	}
	
	@Transactional(readOnly=true)
	public List<Attachment> getAttachmentsByIds(String ids){
		return attachmentDao.getAttachmentsByIds(ids);
	}

	/**
	 * 获取附件显示的 编辑页面的html 预览和 删除
	 * @param ids
	 * @return
	 */
	public String getAttachmentEditHtml(String idsStr) {
		String ids="";
    	StringBuffer attachmentList = new StringBuffer("");
    	String[] attmentIds = idsStr.split(",");
    	if(attmentIds!=null&&attmentIds.length>0){
    		for(String str:attmentIds){
    			if(str!=null&&!"".equals(str)){
    				ids+=str+",";
    			}
    		}
    	}
    	if(ids.endsWith(",")){
    		ids = ids.substring(0,ids.length()-1);
    	}
        if(ids!=null&&!ids.equals("")){
        	String lineString = "";
        	int count= 0;
            List<Attachment> attachList = this.getAttachmentsByIds(ids);
            for(int j=0;j<attachList.size();j++){
            	Attachment ac =attachList.get(j);
        		String attachName = ac.getAttachName();
        		String attacthSuffix = getAttacthSuffix(attachName);
        		   lineString +="<li>";
				   if(attacthSuffix.equals("fileWord")){
					   lineString+="<div class=\"icon\"><em class=\"doc\"></em></div>";
				   }else  if(attacthSuffix.equals("fileExcel")){
					   lineString+="<div class=\"icon\"><em class=\"excel\"></em></div>";
				   }else  if(attacthSuffix.equals("filePPT")){
					   lineString+="<div class=\"icon\"><em class=\"ppt\"></em></div>";
				   }else  if(attacthSuffix.equals("fileRar")){
					   lineString+="<div class=\"icon\"><em class=\"rar\"></em></div>";
				   }else  if(attacthSuffix.equals("filePicture")){
					   lineString+="<div class=\"icon\"><em class=\"img\"></em></div>";
				   }else  if(attacthSuffix.equals("fileTxt")){
					   lineString+="<div class=\"icon\"><em class=\"txt\"></em></div>";
				   }
		        lineString +="<div class=\"txt\"><p title=\""+attachName+"\">";
		        lineString +=attachName+"</p>";
		        lineString +="<p><a style=\"cursor:pointer;\"  onclick=\"viewfileOpen("+ac.getId()+");\">预览</a><a  style=\"cursor:pointer;\"   onclick=\"delAttachById("+ac.getId()+",this);\">删除</a></p>";
		        lineString +="</li>";
		        count++;
            }
            attachmentList.append(lineString);
        }
		return attachmentList.toString();
	}
	
	/**
	 * 获取附件显示的 查看页面的html 预览和下载
	 * @param ids
	 * @return
	 */
	public String getAttachmentViewHtml(String data) {
		String idsStr=data;
		if(idsStr==null){
			idsStr ="";
		}
		String ids="";
    	StringBuffer attachmentList = new StringBuffer("");
    	String[] attmentIds = idsStr.split(",");
    	if(attmentIds!=null&&attmentIds.length>0){
    		for(String str:attmentIds){
    			if(str!=null&&!"".equals(str)){
    				ids+=str+",";
    			}
    		}
    	}
    	if(ids.endsWith(",")){
    		ids = ids.substring(0,ids.length()-1);
    	}
        if(ids!=null&&!ids.equals("")){
        	String lineString = "";
        	int count= 0;
            List<Attachment> attachList = this.getAttachmentsByIds(ids);
            for(int j=0;j<attachList.size();j++){
            	Attachment ac =attachList.get(j);
        		String attachName = ac.getAttachName();
        		String attacthSuffix = getAttacthSuffix(attachName);
        		   lineString +="<li>";
				   if(attacthSuffix.equals("fileWord")){
					   lineString+="<div class=\"icon\"><em class=\"doc\"></em></div>";
				   }else  if(attacthSuffix.equals("fileExcel")){
					   lineString+="<div class=\"icon\"><em class=\"excel\"></em></div>";
				   }else  if(attacthSuffix.equals("filePPT")){
					   lineString+="<div class=\"icon\"><em class=\"ppt\"></em></div>";
				   }else  if(attacthSuffix.equals("fileRar")){
					   lineString+="<div class=\"icon\"><em class=\"rar\"></em></div>";
				   }else  if(attacthSuffix.equals("filePicture")){
					   lineString+="<div class=\"icon\"><em class=\"img\"></em></div>";
				   }else  if(attacthSuffix.equals("fileTxt")){
					   lineString+="<div class=\"icon\"><em class=\"txt\"></em></div>";
				   }
		        lineString +="<div class=\"txt\"><p  title=\""+attachName+"\">";
		        lineString +=attachName+"</p>";
		        lineString +="<p><a style=\"cursor:pointer;\"   onclick=\"viewfileOpen("+ac.getId()+");\">预览</a><a  style=\"cursor:pointer;\"  onclick=\"downLoadAttachment("+ac.getId()+",this);\">下载</a></p>";
		        lineString +="</li>";
		        count++;
            }
            attachmentList.append(lineString);
        }
		return attachmentList.toString();
	}
	
	
	/**
	 * 获取附件显示的 查看页面的html 预览和下载
	 * @param ids
	 * @return
	 */
	public String getAttachmentViewDownHtml(String data) {
		String idsStr=data;
		if(idsStr==null){
			idsStr ="";
		}
		String ids="";
    	StringBuffer attachmentList = new StringBuffer("");
    	String[] attmentIds = idsStr.split(",");
    	if(attmentIds!=null&&attmentIds.length>0){
    		for(String str:attmentIds){
    			if(str!=null&&!"".equals(str)){
    				ids+=str+",";
    			}
    		}
    	}
    	if(ids.endsWith(",")){
    		ids = ids.substring(0,ids.length()-1);
    	}
        if(ids!=null&&!ids.equals("")){
        	String lineString = "";
        	int count= 0;
            List<Attachment> attachList = this.getAttachmentsByIds(ids);
            for(int j=0;j<attachList.size();j++){
            	Attachment ac =attachList.get(j);
        		String attachName = ac.getAttachName();
        		String attacthSuffix = getAttacthSuffix(attachName);
        		   lineString +="<li>";
				   if(attacthSuffix.equals("fileWord")){
					   lineString+="<div class=\"icon\"><em class=\"doc\"></em></div>";
				   }else  if(attacthSuffix.equals("fileExcel")){
					   lineString+="<div class=\"icon\"><em class=\"excel\"></em></div>";
				   }else  if(attacthSuffix.equals("filePPT")){
					   lineString+="<div class=\"icon\"><em class=\"ppt\"></em></div>";
				   }else  if(attacthSuffix.equals("fileRar")){
					   lineString+="<div class=\"icon\"><em class=\"rar\"></em></div>";
				   }else  if(attacthSuffix.equals("filePicture")){
					   lineString+="<div class=\"icon\"><em class=\"img\"></em></div>";
				   }else  if(attacthSuffix.equals("fileTxt")){
					   lineString+="<div class=\"icon\"><em class=\"txt\"></em></div>";
				   }
		        lineString +="<div class=\"txt\"><p  title=\""+attachName+"\">";
		        lineString +=attachName+"</p>";
		        lineString +="<p> <a  style=\"cursor:pointer;\"  onclick=\"downLoadAttachment("+ac.getId()+",this);\">下载</a></p>";
		        lineString +="</li>";
		        count++;
            }
            attachmentList.append(lineString);
        }
		return attachmentList.toString();
	}
	
	 /**
     * 功能： 获取附件后缀
     * @return
     */
    public String getAttacthSuffix(String attacthName){
    	String attacthSuffix = "fileTxt";

    	if(attacthName.trim().equals("")){
			attacthSuffix = "fileTxt";
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
		    	 }else if(att.endsWith("rar")){
		    		 attacthSuffix = "fileRar";
		    	 }
			}else{
				attacthSuffix = "fileTxt";
			}
		}
		return attacthSuffix;
    }

	@Override
	public boolean checkExisting(String fileName) {
		// TODO Auto-generated method stub
		List<Attachment> list = attachmentDao.findByFileName(fileName);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
}
