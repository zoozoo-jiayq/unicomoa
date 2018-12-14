package cn.com.qytx.cbb.customJpdl.service.impl.parseJSON;

import java.util.HashMap;
import java.util.Map;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.parseJSON.NodeObject;


/**
 * 功能：任务节点实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:24:01 
 * 修改日期：2013-3-22 上午11:24:01 
 * 修改列表：
 */
public class TaskGenerateImpl extends AbstractFreemarkerTemplate {

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.impl.customJpdl.parseJSON.AbstractFreemarkerTemplate#getXmlTemplatePath()
	 */
	@Override
	public String getXmlTemplatePath() {
		return "jpdl.task.ftl";
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.impl.customJpdl.parseJSON.AbstractFreemarkerTemplate#generateRoot(cn.com.qytx.oa.service.customJpdl.parseJSON.NodeObject)
	 */
	@Override
	public Map generateRoot(NodeObject nodeObject) {
		Map map = new HashMap();
		map.put("name", nodeObject.getText().getText());
		map.put("candidate", "#{"+JpdlInterface.VAR_CANDIDATE_USERS+"}");
		map.put("paths", nodeObject.getPaths());
		return map;
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.impl.customJpdl.parseJSON.AbstractFreemarkerTemplate#getNodeType()
	 */
	@Override
	public String getNodeType() {
		return JpdlInterface.NODE_TYPE_TASK;
	}

}
