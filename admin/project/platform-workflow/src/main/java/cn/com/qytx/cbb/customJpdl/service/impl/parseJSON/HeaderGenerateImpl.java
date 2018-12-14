package cn.com.qytx.cbb.customJpdl.service.impl.parseJSON;

import java.util.HashMap;
import java.util.Map;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.parseJSON.NodeObject;


/**
 * 功能：开始节点实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:23:05 
 * 修改日期：2013-3-22 上午11:23:05 
 * 修改列表：
 */
public class HeaderGenerateImpl extends AbstractFreemarkerTemplate {

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.impl.customJpdl.parseJSON.AbstractFreemarkerTemplate#getXmlTemplatePath()
	 */
	@Override
	public String getXmlTemplatePath() {
		return "jpdl.header.ftl";
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.impl.customJpdl.parseJSON.AbstractFreemarkerTemplate#generateRoot(cn.com.qytx.oa.service.customJpdl.parseJSON.NodeObject)
	 */
	@Override
	public Map generateRoot(NodeObject nodeObject) {
		Map map = new HashMap();
		map.put("beginTask", nodeObject.getText().getText());
		map.put("name", nodeObject.getProcessName());
		return map;
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.oa.impl.customJpdl.parseJSON.AbstractFreemarkerTemplate#getNodeType()
	 */
	@Override
	public String getNodeType() {
		return JpdlInterface.NODE_TYPE_START;
	}

}
