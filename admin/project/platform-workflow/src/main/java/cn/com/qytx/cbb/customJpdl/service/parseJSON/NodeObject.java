package cn.com.qytx.cbb.customJpdl.service.parseJSON;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 功能：JSON的节点对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:40:26 
 * 修改日期：2013-3-22 上午11:40:26 
 * 修改列表：
 */
public class NodeObject {

	private String nodeId;
	private String type;
	private BaseTextObject text;
	private Props props;
	private String processName;//流程名称
	private Integer order = 0;
	private String el;

	//Transaction属性
	private Set<PathObject> paths = new HashSet<PathObject>();
	
	//添加节点
	public void addPathObject(PathObject po){
		this.paths.add(po);
	}
	
	public Set<PathObject> getPaths(){
		return paths;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Props getProps() {
		return props;
	}
	public void setProps(Props props) {
		this.props = props;
	}
	public BaseTextObject getText() {
		return text;
	}
	public void setText(BaseTextObject text) {
		this.text = text;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getEl() {
		return el;
	}

	public void setEl(String el) {
		this.el = el;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str =  "节点类型："+getType()+";属性："+getProps()+";路径：";
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		for(Iterator iterator = paths.iterator();iterator.hasNext();){
			PathObject poObject = (PathObject) iterator.next();
			sb.append(poObject.toString());
		}
		return sb.toString();
	}
	
	
}
