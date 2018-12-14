package cn.com.qytx.cbb.customJpdl.service.impl.parseJSON;

import java.util.HashMap;
import java.util.Map;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.parseJSON.NodeObject;

/**
 * 功能：判断节点模板实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:19:30 
 * 修改日期：2013-3-22 上午11:19:30 
 * 修改列表：
 */
public class DecisionGenerateImpl extends AbstractFreemarkerTemplate {

	/**
	 * 功能：返回判断节点模板路径
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String getXmlTemplatePath() {
		return "jpdl.decison.ftl";
	}

	/**
	 * 功能：返回判断节点数据模型
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Map generateRoot(NodeObject nodeObject) {
		Map map = new HashMap();
		map.put("name", nodeObject.getText().getText());
		map.put("paths", nodeObject.getPaths());
		return map;
	}

	/**
	 * 功能：返回节点类型
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String getNodeType() {
		return JpdlInterface.NODE_TYPE_DECISON;
	}

}
