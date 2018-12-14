package cn.com.qytx.cbb.customForm.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.com.qytx.cbb.customForm.domain.FormProperties;

/**
 * 功能:解析HTML提取属性
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
public class ParseHtml {
	//name属性匹配表达式
	public final static String NAME_REGEX = "_\\w+_";
	
	//cnname属性匹配表达式("@([\\s\\w[\\u4e00-\\u9fa5]]*)");
	//public final static String CN_NAME_REGEX = "%[\u4E00-\u9FA5]*%";
	public final static String CN_NAME_REGEX = "%[\\s\\w[\\u4e00-\\u9fa5]]*%";
	
	//组件name
	private String zjname = ""; 
	
	/*
	 * 解析HTML源码，解析出name属性和cnName属性，并构建属性对象
	 */
	/**
	 * 解析HTML源码，解析出name属性和cnName属性，并构建属性对象
	 * @param htmlSource 表单源代码
	 * @param formId 表单id
	 * @return 表单对象列表
	 */
	public List<FormProperties> parser(String htmlSource,Integer formId){
		List<FormProperties> list = new ArrayList<FormProperties>();
		
		//建立name属性匹配规则
		Pattern namepttern = Pattern.compile(NAME_REGEX);
		Matcher namematcher =  namepttern.matcher(htmlSource);
		//System.out.println(namematcher.groupCount());
		
		//建立cnname属性匹配规则
		Pattern cnnamePattern = Pattern.compile(CN_NAME_REGEX);
		Matcher cnnameMatcher = cnnamePattern.matcher(htmlSource);
		Document doc = Jsoup.parse(htmlSource);
		int i = 0;
		while(namematcher.find() && cnnameMatcher.find()){
			String name = namematcher.group();
			name = name.substring(1, name.length()-1);
			String cnname=cnnameMatcher.group();
			cnname = cnname.substring(1, cnname.length()-1);
			/**当控件是radio时只生成一条记录*/
			//start
			if (zjname.equals(name)) {
				continue;
			}
			i++;
			//构造属性对象
			FormProperties properties = new FormProperties();
			properties.setPropertyName("_"+name+"_");
			properties.setPropertyNameCh(cnname);
			String wn = "_"+name+"_";
			Elements es = doc.select("[name="+wn+"]");
			if(es!=null && es.size()>0){
				String refDetail = es.get(0).attr("ref_detail");
				if(refDetail!=null && refDetail.length()>0){
					properties.setParentName(refDetail);
				}
			}
			properties.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()+(i*1000)));
			properties.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			properties.setFormId(formId);
			properties.setOrderIndex(i);
			list.add(properties);
			zjname = name;
		}
		return list;
	}
}
