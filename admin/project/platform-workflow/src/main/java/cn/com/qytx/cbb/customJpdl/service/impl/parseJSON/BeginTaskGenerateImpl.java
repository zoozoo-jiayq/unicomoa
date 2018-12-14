package cn.com.qytx.cbb.customJpdl.service.impl.parseJSON;

import java.util.HashMap;
import java.util.Map;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.parseJSON.NodeObject;


/**
 * 功能：开始任务节点模板实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:17:10 
 * 修改日期：2013-3-22 上午11:17:10 
 * 修改列表：
 */
public class BeginTaskGenerateImpl extends AbstractFreemarkerTemplate {

	

	/**
	 * 功能：返回任务节点模板路径
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String getXmlTemplatePath() {
		return "jpdl.beginTask.ftl";
	}

	/**
	 * 功能：生成任务节点数据模型
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Map generateRoot(NodeObject node) {
		Map map = new HashMap();
		map.put("name", node.getText().getText());
		map.put("paths", node.getPaths());
		map.put("creater", "#{creater}");
		return map;
	}

	/**
	 * 功能：获取节点类型
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String getNodeType() {
		return JpdlInterface.NODE_TYPE_START;
	}

}
