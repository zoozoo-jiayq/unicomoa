package cn.com.qytx.cbb.publicDom.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.com.qytx.cbb.customForm.dao.FormPropertiesDao;
import cn.com.qytx.cbb.customForm.dao.FormPropertyValueDao;
import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.domain.FormPropertyValue;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：公文内容工具类，提供获取自定义表单，版式文件，附件的功能
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午1:41:26 
 * 修改日期：下午1:41:26 
 * 修改列表：
 */
public class DomContentUtil {
    
    private static final Log LOGGER = LogFactory.getLog(DomContentUtil.class);

	/**
	 * 功能：获取自定义表单文件
	 * @param
	 * @return
	 * @throws   
	 */
	public static File getCustomform(int formId,String instanceId,String imgs,String title){
		FormPropertyValueDao formPropertyValueDao = (FormPropertyValueDao) SpringUtil.getBean("formPropertyValueDao");
		FormPropertiesDao formPropertiesDao = (FormPropertiesDao) SpringUtil.getBean("formPropertiesDao");
		IJbpmApp jbpmAppService = (IJbpmApp) SpringUtil.getBean("jbpmAppService");
		IFormAttribute formAttributeService =  (IFormAttribute) SpringUtil.getBean("formAttributeService");
		
		//表单源码
		FormAttribute fa = formAttributeService.findById(formId);
		
		//表单数据
		List<FormPropertyValue> fpvlist = formPropertyValueDao.findFormPropertyValueByProcessId(instanceId);
		List<FormProperties> fpList = formPropertiesDao.findByFormId(formId);
		
		//add by 贾永强 添加获取审批意见控件和阅者签名控件信息的功能
		List<JbpmAdvice> jbpmAdvicelist = jbpmAppService.findTdAdvicebyInstanceId(instanceId);
		String[] readerNames = jbpmAppService.findReaderNamesByInstanceId(instanceId);
		
		if(fpList!=null && fa!=null && fa.getFormSource()!=null){
			String result = generateHtmlWithData(fa.getFormSource(), fpvlist, fpList,jbpmAdvicelist,readerNames);
			if(imgs!=null){
				Gson gson = new Gson();
				List<String> imglist = gson.fromJson(imgs, new TypeToken<List<String>>(){}.getType());
				String _style =  "z-index:-1;float:left";
				result+="<div>";
				if(imglist!=null){
					for(int i=0; i<imglist.size(); i++){
						String imgsource = imglist.get(i);
						result+="<img border='0' transcolor='#FFFFFF' style='"+_style+"' src='"+imgsource+"'>";
					}
				}
				result+="</div>";
			}
			
			try {
				String r = UUID.randomUUID().toString();
				File formFile = File.createTempFile(title==null?r:title+r, ".html");
				formFile.setWritable(true);
				FileWriterWithEncoding fw = new FileWriterWithEncoding(formFile, "UTF-8");
				fw.write(result);
				fw.flush();
				fw.close();
				return formFile;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 功能：生成带有数据的HTML
	 * @param
	 * @return
	 * @throws   
	 */
	private static String generateHtmlWithData(String sourceHtml,List<FormPropertyValue> fpvlist,List<FormProperties> fpList,List<JbpmAdvice> advicelist,String[] readerNames){
		Document doc = Jsoup.parse(sourceHtml);
		doc.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		for(int i=0 ; i<fpList.size(); i++){
			FormProperties fp = fpList.get(i);
			String name  = fp.getPropertyName();
			String value = "";
			for(int j=0; j<fpvlist.size(); j++){
				FormPropertyValue fpv = fpvlist.get(j);
				if(fpv.getBeanPropertyId().intValue() == fp.getPropertyId().intValue()){
					value = fpv.getBeanValue();
					break;
				}
			}
			if(value != null && !value.equals("")){
				//多行输入框
				Element textAreaEle = doc.select("textarea[name="+name+"]").first();
				//下拉框
				Element selectEle = doc.select("select[name="+name+"]").first();
				//单选
				Elements radioEle = doc.select("input[type=radio][name="+name+"]");
				//复选
				Element ssss = doc.select("input[type=checkbox][name="+name+"]").first();
				//单行输入框
				Element inputEle = doc.select("input[name="+name+"]").first();
				
				if(textAreaEle!=null){
					textAreaEle.html(value);
				}
				if(selectEle!=null){
					Elements temps = selectEle.select("option");
					if(temps!=null){
						for(int l=0; l<temps.size(); l++){
							Element temp = temps.get(l);
							if(temp.val().equals(value)){
								temp.attr("selected", "selected");
								break;
							}
						}
						
					}
				}
				if(radioEle!=null){
					for(int m = 0; m<radioEle.size(); m++){
						Element temp = radioEle.get(m);
						if(temp.val().equals(value)){
							temp.attr("checked", "checked");
							break;
						}
					}
				}
				if(inputEle!=null){
					inputEle.val(value);
				}
				if(ssss!=null){
					if(value.equals("checked")){
						ssss.attr("checked", "checked");
					}
				}
			}
		}
		
		//add by 贾永强
		if(advicelist!=null){
			Map<String,String> adviceMap = new HashMap<String, String>();
			for(int i=0; i<advicelist.size(); i++){
				JbpmAdvice jbpmAdvice = advicelist.get(i);
				String editorName = jbpmAdvice.getEditorName();
				String content =jbpmAdvice.getContent().replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "")
						 .replaceAll("</[a-zA-Z]+[1-9]?>", "")+"\r\n    签名:"+jbpmAdvice.getUserName()+"  "+jbpmAdvice.getAdviceTime();
				String orial = adviceMap.get(editorName);
				if(orial == null){
					orial = "";
				}
				orial+=content;
				adviceMap.put(editorName, orial);
			}
			Set<Map.Entry<String, String>> set = adviceMap.entrySet();
			for(Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();){
				Map.Entry<String, String>  temp = it.next();
				String editorName = temp.getKey();
				String content = temp.getValue();
				doc.select("#"+editorName).val(content);
			}
		}
		if(readerNames!=null){
			//查找所有的阅者签名控件
			Map<String,String> readerNamesMap = new HashMap<String, String>();
			Elements es = doc.select("input[readflag]");
			if(es!=null){
				for(int i=0; i<es.size(); i++){
					Element element = es.get(i);
					if(i<readerNames.length){
						element.attr("value",readerNames[i] );
					}
				}
			}
		}
		
		
		return doc.toString();
	}
	
	/**
	 * 功能：获取版式文件
	 * @param
	 * @return
	 * @throws   
	 */
	public static File getPdfFile(String instanceId){
		IDocumentExtService documentExtService = (IDocumentExtService) SpringUtil.getBean("documentExtService");
		DocumentExt de = documentExtService.findByProcessInstanceId(instanceId);
		int attachId = de.getAttachId();
		String docFile = DomDocUtil.getInputFileByAttachId(attachId);
		String pdfFile = docFile+".pdf";
		File f = new File(pdfFile);
		if(f.exists()){
			return f;
		}
		return null;
	}
	
	/**
	 * 功能：获取附件文件
	 * @param
	 * @return
	 * @throws   
	 */
	public static File[] getAttachs(String instanceId){
	    try{
    		PublicDomService publicDomService = (PublicDomService) SpringUtil.getBean("publicDomService");
    		FilePathConfig filePathConfig = (FilePathConfig) SpringUtil.getBean("filePathConfig");
    		List<Attachment> list = AttachUtil.getAttachmentList(instanceId);
    		if(list!=null){
    		//	String catalinaHome = System.getProperty("catalina.home");
    			String catalinaHome = filePathConfig.getFileUploadPath();
    			File[] files = new File[list.size()];
    			for(int i=0; i<list.size(); i++){
    				String path = list.get(i).getAttachFile();
    				String filePath = catalinaHome+"/upload/"+path;
    				File temp = new File(filePath);
    				if(temp.exists()){
    					files[i] = temp;
    				}
    			}
    			return files;
    		}
		}catch(Exception e){
		    LOGGER.error("附件不存在或者已刪除!");
		    e.printStackTrace();
		}
		return new File[0];
	}
}
