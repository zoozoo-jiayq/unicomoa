package cn.com.qytx.cbb.customJpdl.service.parseJSON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;


/**
 * 功能：解析JSON字符串到JAVA对象，实现ITERATOR接口，实现按顺序保存节点类型
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-25 上午9:31:31 
 * 修改日期：2013-3-25 上午9:31:31 
 * 修改列表：
 */
public class ParseResult implements Iterator<NodeObject>{
	
	private Map<String, NodeObject> states ;
	
	private Map<String, PathObject> paths;
	
	private ProcessProerty props;
	
	private List<NodeObject> nodeObjects;
	
	//第一个节点，起始节点
	private NodeObject firstNodeObject = null;
	
	//当前节点，实现iterator接口需要此对象
	private NodeObject currentNodeObject = null;
	
	//node节点顺序
//	private static int NODE_ORDER = 1 ;

	public Map<String, NodeObject> getStates() {
		return states;
	}

	public void setStates(Map<String, NodeObject> states) {
		this.states = states;
	}

	public Map<String, PathObject> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, PathObject> paths) {
		this.paths = paths;
	}

	
	public ProcessProerty getProps() {
		return props;
	}

	public void setProps(ProcessProerty props) {
		this.props = props;
	}


	public List<NodeObject> getNodeObjects() {
		return nodeObjects;
	}

	public void setNodeObjects(List<NodeObject> nodeObjects) {
		this.nodeObjects = nodeObjects;
	}

	/**
	 * 功能：构造JPDL节点数据模型
	 * @param
	 * @return
	 * @throws   
	 */
	public List<NodeObject> getJpdlNode(){
		Set<Entry<String, PathObject>> pathSet = paths.entrySet();
		//解析PATH,将PATH分配到指派的NODE节点,(完善NODE节点)
		for(Iterator iterator = pathSet.iterator(); iterator.hasNext();){
			Entry<String, PathObject> entry = (Entry<String, PathObject>) iterator.next();
			PathObject pathObject = entry.getValue();
			pathObject.setId(entry.getKey());
			String from = pathObject.getFrom();
			
			//修改to属性，原来是rectx改为指向name
			String to = pathObject.getTo();
			to = states.get(to).getText().getText();
			pathObject.setTo(to);
			NodeObject nodeObject = states.get(from);
			nodeObject.addPathObject(pathObject);
			nodeObject.setProcessName(props.getProps().getName().getValue());
		}
		Set<Entry<String, NodeObject>> sets = states.entrySet();
		List<NodeObject> result = new ArrayList<NodeObject>();
		//Set转为List
		for(Iterator it = sets.iterator(); it.hasNext();){
			Entry<String, NodeObject> entry = (Entry<String, NodeObject>) it.next();
			NodeObject no = entry.getValue();
			//如果是判断节点，则设置EL表达式
			if(no.getType().equals(JpdlInterface.NODE_TYPE_DECISON) 
					&& no.getProps().getExpr()!=null && !no.getProps().getExpr().getValue().equals("")){
				reBuildExpr(no.getProps().getExpr().getValue(), no.getPaths());
			}
			result.add(no);
		}
		
		//查找第一个节点
		for(int i=0;i<result.size();i++ ){
			NodeObject  nodeObject = result.get(i);
			if(nodeObject.getType().equals(JpdlInterface.NODE_TYPE_START)){
				firstNodeObject = nodeObject;
				break;
			}
		}
		this.nodeObjects = result;
		return result;
	}

	
	//重构EL表达式,给判断节点添加EL表达式,添加#{}
	private void reBuildExpr(String expr,Set poList){
		String[] paths = expr.split(",");
		for(int i=0;i<paths.length;i++){
			String temp = paths[i];
			String[] strs = temp.split(":");
			if(strs.length == 2){
				String key = strs[0];
				String el = strs[1];
				StringBuffer reel = new StringBuffer("#{");
				reel.append(el);
				reel.append("}");
				for(Iterator it = poList.iterator();it.hasNext();){
					PathObject po = (PathObject) it.next();
					if(po.getId().equals(key)){
						po.setExpr(reel.toString());
						break;
					}
				}
			}
		}
	}

	
	
	/**
	 * 功能：iterator接口，判断是否还有下一个节点
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public boolean hasNext() {
		if(currentNodeObject==null){
			return true;
		}else{
			if(currentNodeObject.getPaths().size()>0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能：iterator接口，获取下一个节点
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public NodeObject next() {
		if(currentNodeObject==null){
			currentNodeObject = firstNodeObject;
		}else{
			Set<PathObject> paths = currentNodeObject.getPaths();
			List<PathObject> pathlist = new ArrayList<PathObject>(paths);
			if(pathlist.size()>0){
				PathObject po = pathlist.get(0);
				String nextName = po.getTo();
				for(Iterator<NodeObject> iterator = nodeObjects.iterator();iterator.hasNext();){
					NodeObject temp  = iterator.next();
					if(temp.getText().getText().equals(nextName)){
						currentNodeObject = temp;
						break;
					}
				}
			}
		}
		return currentNodeObject;
	}

	/**
	 * 功能：该接口不予实现
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public void remove() {
	}
}
