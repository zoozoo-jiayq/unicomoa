package cn.com.qytx.cbb.publicDom.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.dao.DocumentExtDao;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("documentExtService")
@Transactional
public class DocumentExtImpl extends BaseServiceImpl<DocumentExt> implements IDocumentExtService {
	/**公文扩展dao */
	@Resource(name="documentExtDao")
	DocumentExtDao  documentExtDao;

	/**
	 * 功能：查询某个公务类别下的公文数
	 * @param doctypeId
	 * @return
	 */
	@Override
	public int getDocTypeCount(Integer doctypeId) {
		return documentExtDao.getDocTypeCount(doctypeId);
	}

	@Override
	public DocumentExt findByProcessInstanceId(String processInstanceId) {
		 return documentExtDao.findByProcessInstanceId(processInstanceId);
	}

	 
}
