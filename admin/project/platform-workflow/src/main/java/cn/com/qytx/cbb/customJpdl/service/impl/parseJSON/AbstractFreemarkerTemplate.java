package cn.com.qytx.cbb.customJpdl.service.impl.parseJSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.customJpdl.service.parseJSON.NodeObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 功能：ftl文件解析模板类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午8:50:13 
 * 修改日期：2013-3-22 上午8:50:13 
 * 修改列表：
 */
public abstract class AbstractFreemarkerTemplate {

	//freemarker配置器
	private Configuration config = new Configuration();
	
	//xml模板
	private String xmlTemplateStringPath ;
	
	//数据模型
	private Map root;

	
	/**
	 * 功能：抽象方法，获取模板文件路径
	 * @param
	 * @return：模板文件路径
	 * @throws   
	 */
	public abstract String getXmlTemplatePath();
	
	/**
	 * 功能：抽象方法，根绝节点对象，生成模板文件的数据模型
	 * @param:nodeObject，节点对象
	 * @return：ftl的数据模型
	 * @throws   
	 */
	public abstract Map generateRoot(NodeObject nodeObject);

	
	/**
	 * 功能：抽象方法，获取节点类型
	 * @param
	 * @return，节点类型
	 * @throws   
	 */
	public abstract String getNodeType();


	/**
	 * 功能：模板方法，调用抽象类，根据模板文件生成JPDL文件
	 * @param：nodelist，模板数据；
	 * @return:根据模板生成的jpdl文件
	 * @throws   
	 */
	public String execute(List<NodeObject> nodelist){
		try {
			StringBuilder resultString = new StringBuilder();
			this.xmlTemplateStringPath = getXmlTemplatePath();
			InputStream is = this.getClass().getResourceAsStream("/cn/com/qytx/cbb/customJpdl/service/impl/template/"+xmlTemplateStringPath);
			if(is == null){
			    return "";
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb =new StringBuffer();
			String temp = br.readLine();
			while(temp!=null){
				sb.append(temp);
				temp = br.readLine();
			}
			if(nodelist!=null){
				for(Iterator iterator = nodelist.iterator(); iterator.hasNext();){
					NodeObject nodeObject = (NodeObject) iterator.next();
					if(nodeObject.getType().equals(getNodeType())){
						this.root 			   = generateRoot(nodeObject);
						StringReader reader= new StringReader(sb.toString());
					    Template template = new Template(xmlTemplateStringPath,reader,config);
						StringWriter stringWriter = new StringWriter();  
						template.setEncoding("UTF-8");  
						template.process(root,stringWriter);
						resultString.append(stringWriter.toString()+"\r\n");
					}
				}
			}
			return resultString.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
