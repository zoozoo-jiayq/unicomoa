package cn.com.qytx.oa.file.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.file.domain.FileContent;
import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.oa.file.service.IFileContent;
import cn.com.qytx.oa.file.service.IFileSort;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;

/**
 * 功能: 文件柜列表 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2016年2月26日
 * 修改日期: 2016年2月26日
 * 修改列表:
 */
public class FileListAction extends BaseActionSupport {

	@Autowired
	IFileSort fileSortService;
	
	@Autowired
	IFileContent fileContentImpl;
	
	@Autowired
	IAttachment attachmentService;
	
	/**
	 * 文件夹id
	 */
	private Integer sortId=0;
	
	/**
	 * 模块id
	 */
	private Integer moduleId;
	
	private Integer type=1;
	
	private Integer fileId;
	
	private Integer userId;
	
	private Integer isFirst=0;
	
	private String keyWord;
	
	/**
	 * 功能：获得文件夹和文件列表
	 */
	public void getList(){
		int parentId = 0 ;
		if(sortId>0){
			FileSort fs = fileSortService.findByFileSortId(sortId);
			parentId = fs.getParentId();
		}
		
		List<FileSort> slist = null;
		if(isFirst == 1){//第一次才加载文件夹
			slist = fileSortService.findSortByParendId(sortId, moduleId,type);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<Map<String, Object>> sortList = new ArrayList<Map<String,Object>>();
		//遍历文件
		if(slist!=null && slist.size()>0){
			//遍历文件夹
			for(Iterator<FileSort> it = slist.iterator(); it.hasNext();){
				FileSort fs = it.next();
				Map<String,Object> m = new HashMap<String, Object>();
				String id = fs.getSortId()+"";
				if(StringUtils.isNotEmpty(id)){
					m.put("id", id);
					m.put("type", "sort");
					m.put("parentId", parentId+"");
					m.put("name", fs.getSortName());
					m.put("size", 0);
					sortList.add(m);
				}
			}
			if(sortId == 0){
				Map<String,Object> r = new HashMap<String, Object>();
				r.put("totalPage", "0");
				r.put("sortList", sortList);
				r.put("fileList", new ArrayList<Map<String, Object>>());
				
				ajax(r);
				return;
			}
		}
		
		Page<FileContent> list = fileContentImpl.getFileConentListById((PageRequest)this.getPageable(), sortId, keyWord,null,null,null,userId,type);
		List<FileContent> flist = list.getContent();
		List<Map<String, Object>> fileList = new ArrayList<Map<String,Object>>();
		if(flist!=null && flist.size()>0){
			for(Iterator<FileContent> it = flist.iterator(); it.hasNext();){
				FileContent fc = it.next();
				
				Map<String,Object> m = new HashMap<String, Object>();
				
				if(StringUtils.isNotEmpty(fc.getAttachment())){
					String id = fc.getContentId()+"";
					m.put("id", id);
					m.put("type", "file");
					m.put("parentId", parentId+"");
					m.put("name", fc.getSubject());
					m.put("createTime", format.format(fc.getCreateTime()));
					m.put("size", "0");
					
					String attId="";
					String strs[] = fc.getAttachment().split(",");
					if(strs.length>1){
						attId = strs[1];
					}
					m.put("attId", attId+"");
					Attachment attach = attachmentService.findOne(Integer.valueOf(attId));
					if(attach!=null&&attach.getAttachSize()!=null){
						m.put("size", formetFileSize(attach.getAttachSize()));
					}
					fileList.add(m);
				}
			}
		}
		
		Map<String,Object> r = new HashMap<String, Object>();
		r.put("totalPage", list.getTotalPages()+"");
		r.put("sortList", sortList);
		r.put("fileList", fileList);
		
		ajax(r);
		return;
	}
	
	/**
	 * 
	 * 功能：根据大小得到b k m g
	 * 
	 * @param fileS
	 * @return
	 */
	public String formetFileSize(long fileS) {// 转换文件大小
		// DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormat df = new DecimalFormat("0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	/**
	 * 功能：查看文件详情
	 * @return
	 */
	public String toFileDetail(){
		if(fileId!= null){
			
		}
		return null;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
}
