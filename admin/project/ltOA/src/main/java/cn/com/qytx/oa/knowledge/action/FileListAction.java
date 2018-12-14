package cn.com.qytx.oa.knowledge.action;

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
import cn.com.qytx.oa.knowledge.domain.Knowledge;
import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.oa.knowledge.service.IKnowledge;
import cn.com.qytx.oa.knowledge.service.IKnowledgeType;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能: 文件柜列表 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2016年2月26日
 * 修改日期: 2016年2月26日
 * 修改列表:
 */
public class FileListAction extends BaseActionSupport {

    /**
     * 知识库类型逻辑接口
     */
    @Autowired
    private transient IKnowledgeType knowledgeTypeService;

    /**
     * 知识库逻辑接口
     */
    @Autowired
    private transient IKnowledge knowledgeService;
	
	@Autowired
	IAttachment attachmentService;
	
	@Autowired
	IUser userService;
	
	/**
	 * 类型id
	 */
	private Integer typeId=0;
	
	/**
	 * 模块id
	 */
	private Integer moduleId;
	
	private Integer userId;
	
	private Integer isFirst=0;
	
	private String keyWord;
	
	private Integer id;
	
	/**
	 * 功能：获得文件夹和文件列表
	 */
	public void getList(){
		UserInfo userInfo = userService.findOne(userId);
		
		List<KnowledgeType> tlist = null;
		if(isFirst == 1){//第一次才加载文件夹
			tlist = knowledgeTypeService.findByParentId(typeId, moduleId);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<Map<String, Object>> sortList = new ArrayList<Map<String,Object>>();
		//遍历文件
		if(tlist!=null && tlist.size()>0){
			//遍历文件夹
			for(Iterator<KnowledgeType> it = tlist.iterator(); it.hasNext();){
				KnowledgeType fs = it.next();
				Map<String,Object> m = new HashMap<String, Object>();
				String id = fs.getVid()+"";
				if(StringUtils.isNotEmpty(id)){
					m.put("id", id);
					m.put("name", fs.getName());
					m.put("size", 0);
					sortList.add(m);
				}
			}
			if(typeId == 0){
				Map<String,Object> r = new HashMap<String, Object>();
				r.put("totalPage", "0");
				r.put("typeList", sortList);
				r.put("knowledgeList", new ArrayList<Map<String, Object>>());
				
				ajax(r);
				return;
			}
		}
	    Sort sort = new Sort(new Sort.Order(Direction.DESC, "createTime"));
	    
	    Page<Knowledge> pageInfo = knowledgeService.findPage(getPageable(sort), userInfo, typeId, moduleId);
		List<Map<String,Object>> knowledgeList = new ArrayList<Map<String,Object>>();
		if(pageInfo.getContent()!=null && pageInfo.getContent().size()>0){
			for(Iterator<Knowledge> it = pageInfo.getContent().iterator(); it.hasNext();){
				Knowledge fc = it.next();
				
				Map<String,Object> m = new HashMap<String, Object>();
				
					m.put("id", fc.getVid()+"");
					m.put("title", fc.getTitle());
					
					String content = "";
					if(fc.getContentVoice()!=null){
						if(fc.getContentVoice().length()>30){
							content = fc.getContentVoice().substring(0,30);
						}else{
							content = fc.getContentVoice();
						}
					}
					
					m.put("content", content);
					m.put("hasAttach", "0");
					if(StringUtils.isNotEmpty(fc.getAttachmentIds())&& !fc.getAttachmentIds().equals(",")){
						m.put("hasAttach", "1");
					}
					knowledgeList.add(m);
			}
		}
		
		Map<String,Object> r = new HashMap<String, Object>();
		r.put("totalPage", pageInfo.getTotalPages()+"");
		r.put("typeList", sortList);
		r.put("knowledgeList", knowledgeList);
		
		ajax(r);
		return;
	}
	
	/**
	 * 功能：加载知识库详情
	 */
	public void loadDetail(){
		Knowledge knowledge = knowledgeService.findById(id);
		Map<String, Object> m = new HashMap<String, Object>();
		if(knowledge!=null){
			m.put("title", knowledge.getTitle());
			m.put("content", knowledge.getContentInfo());
			m.put("fileList",new ArrayList());
			if(StringUtils.isNotEmpty(knowledge.getAttachmentIds()) && !knowledge.getAttachmentIds().equals(",")){
				List<Attachment> attachList = attachmentService.getAttachmentsByIds(knowledge.getAttachmentIds());
				if(attachList!=null && attachList.size()>0){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>(); 
					for(Attachment attachment:attachList){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", attachment.getId()+"");
						map.put("name", attachment.getAttachName());
						map.put("createTime", format.format(attachment.getCreateTime()));
						map.put("size", formetFileSize(attachment.getAttachSize()));
						listMap.add(map);
					}
					m.put("fileList", listMap);
				}
			}
		}
		ajax(m);
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
	

	public Integer getTypeId() {
		return typeId;
	}


	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}


	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
