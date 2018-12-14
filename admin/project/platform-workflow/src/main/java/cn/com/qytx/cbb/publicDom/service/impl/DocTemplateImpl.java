package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.dao.DocTemplateDao;
import cn.com.qytx.cbb.publicDom.domain.DocTemplate;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("docTemplateService")
@Transactional
public class DocTemplateImpl extends BaseServiceImpl<DocTemplate> implements IDocTemplateService {
	/**正文模板dao */
	@Resource(name="docTemplateDao")
	DocTemplateDao  docTemplateDao;

	@Override
	public List<DocTemplate> findAll(Integer companyId) {
		return docTemplateDao.findAlByCompanyIdl( companyId);
	}


	@Override
	public Page<DocTemplate> findPageList(Integer docTemplateType, String name,int companyId,
			Pageable pageInfo) {
		return docTemplateDao.findPageList(docTemplateType,name,companyId,pageInfo);
	}

	

	@Override
	public DocTemplate findByName(String name) {
		return docTemplateDao.findByName(name);
	}

	@Override
	public int getCount(Integer categoryId) {
		return docTemplateDao.getCount(categoryId);
	}


	@Override
	public List<DocTemplate> findByCategory(int categoryId, Integer userId,
			Integer companyId) {
		List<DocTemplate> res  = new ArrayList<DocTemplate>();
		List<DocTemplate> list = docTemplateDao.findByCategory(categoryId,companyId);
		if(list!=null&&list.size()>0){
			for(DocTemplate docT : list){
			    String userIds = docT.getUserIds();
			    if(userIds!=null&&!"".equals(userIds)){
			    	String[] arr = userIds.split(",");
			    	if(arr!=null&&arr.length>0){
			    		for(String ids:arr){
			    			if(ids!=null&&!"".equals(ids)){
			    				int id = Integer.parseInt(ids);
			    				if(id==userId){
			    					res.add(docT);
			    				}
			    			}
			    		}
			    	}
			    }
			}
		}
		return res;
	}

	@Override
	public List<DocTemplate> findAll(int companyId, int userId) {
		List<DocTemplate> res  = new ArrayList<DocTemplate>();
		List<DocTemplate> list = docTemplateDao.findAlByCompanyIdl(companyId);
		if(list!=null&&list.size()>0){
			for(DocTemplate docT : list){
			    String userIds = docT.getUserIds();
			    if(userIds!=null&&!"".equals(userIds)){
			    	String[] arr = userIds.split(",");
			    	if(arr!=null&&arr.length>0){
			    		for(String ids:arr){
			    			if(ids!=null&&!"".equals(ids)){
			    				int id = Integer.parseInt(ids);
			    				if(id==userId){
			    					res.add(docT);
			    				}
			    			}
			    		}
			    	}
			    }
			}
		}
		return res;
	}


	@Override
	public boolean deleteMore(String ids) {
		// TODO Auto-generated method stub
		return docTemplateDao.deleteMore(ids);
	}

	  
	 
}
