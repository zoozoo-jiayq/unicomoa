package cn.com.qytx.oa.file.action.mobile;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.file.domain.FileContent;
import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.oa.file.service.IFileContent;
import cn.com.qytx.oa.file.service.IFileSort;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;

/**
 * 功能：手机端接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:48:10 
 * 修改日期：上午10:48:10 
 * 修改列表：
 */
public class MobileFileListAction extends MobileBaseAction {
	@Autowired
	IFileContent fileContentImpl;
	
	@Autowired
	IFileSort fileSortService;
	
	@Autowired
	IAttachment attachmentService;

	
	private int fileSort = 0;
	
	//文件夹ID
	private int sortId;
	
	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	
	
	/**
	 * @throws Exception 
	 * 功能：查询指定文件夹下的文件列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String listFile() throws Exception{
		int parentId = 0 ;
		if(sortId>0){
			FileSort fs = fileSortService.findByFileSortId(sortId);
			parentId = fs.getParentId();
		}
		
		List<FileSort> sortlist = fileSortService.findSortByParendId(sortId, fileSort,0);
		Page<FileContent> list = fileContentImpl.getFileConentListById((PageRequest)this.getPageable(), sortId, null,null,null,null,null,0);
		List<FileContent> flist = list.getContent();
		StringBuffer sb = new StringBuffer("[");
		
		//遍历文件
		if(sortlist!=null && sortlist.size()>0){
			//遍历文件夹
			for(Iterator<FileSort> it = sortlist.iterator(); it.hasNext();){
				FileSort fc = it.next();
				sb.append("{");
				String id = fc.getSortId()+"";
				
				if(id!=null && !id.equals("")){
					sb.append("\"id\":\"").append(id).append("\",");
					sb.append("\"type\":\"").append("sort").append("\",");
					sb.append("\"parentId\":\"").append(parentId).append("\",");
					sb.append("\"name\":\"").append(fc.getSortName()).append("\",");
					sb.append("\"size\":\"0\"");
					sb.append("}");
					if(it.hasNext()){
						sb.append(",");
					}
				}
			}
			
			if(sortId == 0){
				sb.append("]");
				ajax("100||"+sb.toString());
				return null;
			}
			if(flist!=null && flist.size()>0){
				sb.append(",");
			}
		}

		for(Iterator<FileContent> it = flist.iterator(); it.hasNext();){
			FileContent fc = it.next();
			sb.append("{");
			String aidstr = fc.getAttachment();
			String id = "";
			if(aidstr!=null){
				String strs[] = aidstr.split(",");
				if(strs.length>1){
					id = strs[1];
				}
			}
			if(id!=null && !id.equals("")){
				sb.append("\"id\":\"").append(id).append("\",");
				sb.append("\"type\":\"").append("file").append("\",");
				sb.append("\"parentId\":\"").append(parentId).append("\",");
				sb.append("\"name\":\"").append(fc.getSubject()).append("\",");
				Attachment attach = attachmentService.findOne(Integer.valueOf(id));
				if(attach!=null&&attach.getAttachSize()!=null){
					Long sizeForKB = (attach.getAttachSize())/1024;
					sb.append("\"size\":\"").append(sizeForKB.toString()).append("\"");
				}else{
					sb.append("\"size\":\"0\"");
				}
				sb.append("}");
				if(it.hasNext()){
					sb.append(",");
				}
			}
		}
		
		sb.append("]");
		ajax("100||"+sb.toString());
		return null;
	}

	public int getFileSort() {
		return fileSort;
	}

	public void setFileSort(int fileSort) {
		this.fileSort = fileSort;
	}
	
}
