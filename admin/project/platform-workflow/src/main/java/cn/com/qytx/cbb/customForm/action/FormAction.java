package cn.com.qytx.cbb.customForm.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customForm.service.IFormPropertyValue;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
/**
 * 表单Action
 * 表单操作
 * 版本: 1.0
 * 开发人员：吴洲
 * 创建日期: 2013-3-14下午15：27
 */
public class FormAction extends BaseActionSupport {
	
    private final int LENGTH = 6;
    private final int BUFF_SIZE = 8192;
    
	/**接口类*/
	// 表单属性服务类
	@Resource(name = "formAttributeService")
	IFormAttribute formAttributeService;
	//表单控件属性服务类
	@Resource(name = "formPropertiesService")
	IFormProperties formPropertiesService;
	//表单控件值服务类
	@Resource(name = "formPropertyValueService")
	IFormPropertyValue formPropertyValueService;
	/**
	 * 上传文件路径
	 */
	@Resource(name="filePathConfig")
	private FilePathConfig filePathConfig;
	
	
	private String formName = "";//表单名称
	private Integer formType = 0;//表单类型id
	private Integer formId = 0;//表单id
	private String formContent = "";//表单控件源代码
	private FormAttribute fa = null;//表单
	private String fileuploadFileName = "";//上传路径
	private String oldFormName = "";//表单的原始名称
	private String searchName = "";//搜索表单名称
	
	/**
	 * 增加表单
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public String addForm(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			List<FormAttribute> falist = new ArrayList<FormAttribute>();
			if(userInfo!=null){
			falist = formAttributeService.findByFormNameCompanyId(userInfo.getCompanyId(), fa.getFormName());
			}
			Map map = new HashedMap();
			if (falist.size()>0) {
				map.put("marked", 0);
				map.put("formId",0);// 
			}else {
				if (userInfo != null && userInfo.getCompanyId() != null) {
					fa.setCompanyId(userInfo.getCompanyId());
				}
				fa.setFormSource("");
				fa.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				fa.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				fa.setIsNewVersion(1);//1.是最新版本  0.不是最新版本
				fa.setCompanyId(TransportUser.get().getCompanyId());
				formAttributeService.saveOrUpdate(fa);
				map.put("marked", 1);//1、保存成功   0、表单名称已经存在
				map.put("formId", fa.getFormId());// 
			}
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	/**
	 * load表单
	 */
	public String loadForm(){
		fa = formAttributeService.findById(formId);
		return SUCCESS;
	}
	/**
	 * 修改表单
	 */
	public String editForm(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			Map map = new HashedMap();
			if (fa.getFormName().equals(oldFormName)) {
				//表单名称没有修改
				FormAttribute oldForm = new FormAttribute();
				oldForm = formAttributeService.findById(fa.getFormId());
				oldForm.setCategoryId(fa.getCategoryId());
				oldForm.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				formAttributeService.saveOrUpdate(oldForm);
				map.put("marked", 1);//1、保存成功   0、表单名称已经存在
			}else {
				//表单名称修改
				List<FormAttribute> falist = new ArrayList<FormAttribute>();
				falist = formAttributeService.findByFormNameCompanyId(userInfo.getCompanyId(), fa.getFormName());
				if (falist.size()>0) {
					map.put("marked", 0);
				}else {
					FormAttribute oldForm;
					oldForm = formAttributeService.findById(fa.getFormId());
					oldForm.setFormName(fa.getFormName());
					oldForm.setCategoryId(fa.getCategoryId());
					oldForm.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					formAttributeService.saveOrUpdate(oldForm);
					map.put("marked", 1);//1、保存成功   0、表单名称已经存在
				}
			}
			
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	/**
	 * 删除表单
	 */
	public String deleteForm(){
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			FormAttribute fainfo;
			fainfo = formAttributeService.findById(formId);
			//通过名字删除表单和所有历史版本
			int isDelete = formAttributeService.deleteByFormNameCompanyId(userInfo.getCompanyId(), fainfo.getFormName(),fainfo.getFormId());
			Map map = new HashedMap();
			if (isDelete==1) {
				map.put("marked", 1);//1、删除成功   0、表单已经被使用不能删除
			}else {
				map.put("marked", 0);//1、删除成功   0、表单已经被使用不能删除
			}
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	/**
     * 获取表单列表
     * @return 表单信息集合json
     */
	@SuppressWarnings("unchecked")
	public String findFormList(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到登录用户
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
            //分页信息
            Page<FormAttribute> page = formAttributeService.findByPage(getPageable(),userInfo.getCompanyId(),searchName,formType);
            
            int iTotalRecords = formAttributeService.getTotalCount(userInfo.getCompanyId(), formType ,searchName);
            List<FormAttribute> faList = page.getContent(); 
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if (faList != null) {
            	int i = getPageable().getPageNumber() * this.getIDisplayLength() + 1;
                for(FormAttribute faInfo : faList) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", faInfo.getFormId());//id
                    map.put("no", i);
                    map.put("name", faInfo.getFormName());//名字
                    list.add(map);
                    i++;
                }
            }
            ajaxPage(page,list);
		} catch (Exception e) {  
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			return null;
		}
		return null;
	}
	/**
	 * 保存表单控件属性
	 * @return null
	 */
	public String saveFormProperties(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			//Integer isUse = formPropertyValueService.findFormPropertyValueByformId(formId);
			Integer isUse = 0;
			Map map = new HashedMap();
			if (isUse==1) {
				map.put("marked", 0);//表单已经被使用不能修改
			}else {
				List<FormProperties> falist;
				//得到所有控件的名称和中文名称
				ParseHtml parseHtml = new ParseHtml();
				falist = parseHtml.parser(formContent,formId);
				if (falist.size()>0) {
					for (FormProperties formProperties : falist) {
						formProperties.setCompanyId(userInfo.getCompanyId());
					}
					formPropertiesService.insertFormItem(falist);
				}
				//保存表单源代码到表单属性表
				FormAttribute faInfo;
				faInfo = formAttributeService.findById(formId);
				faInfo.setFormSource(formContent);
				faInfo.setCompanyId(userInfo.getCompanyId());
				formAttributeService.saveOrUpdate(faInfo);
				map.put("marked", 1);//表单保存成功
			}
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	
	/**
	 * 功能：表单详情
	 * @return
	 */
	public String viewForm(){
		fa = formAttributeService.findById(formId);
		if (fa!=null&&fa.getFormSource()!=null) {
			formContent = fa.getFormSource();
		}
		return SUCCESS;
	}
	/**
	 * 表单设计器
	 * @return null
	 */
	public String loadFormDesigner(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			fa = formAttributeService.findById(formId);
			if(fa.getFormSource()!=null){
				//fa.setFormSource(fa.getFormSource().replaceAll("\"","\'"));
			}
			Map map = new HashedMap();
			map.put("fa", fa);
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	
	/*
	 * add by jiayq 2016.2.20,同步加载表单设计器的表单数据
	 */
	public String toEditor(){
		fa = formAttributeService.findById(formId);
		return "editor";
	}
	
	/**
	 * 导出表单
	 * @return null
	 */
	public String reportForm(){
		try{
			HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
			FormAttribute fainfo;
			fainfo = formAttributeService.findById(formId);
			String title = "";
			String content="";
//			String editer="吴洲";
			if (fainfo!=null && fainfo.getFormName()!=null) {
				title = fainfo.getFormName();
			}
			if (fainfo!=null && fainfo.getFormSource()!=null) {
				content = fainfo.getFormSource();
			}
			String filePath = "";
			//filePath=filePathConfig.getFileUploadPath()+"/upload/customForm/template.htm";
			filePath = ServletActionContext.getServletContext().getRealPath("/logined")+"/customForm/template.htm";
			//out.print(filePath);
			String templateContent="";
			FileInputStream fileinputstream = new FileInputStream(filePath);//读取模块文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes);
			//out.print(templateContent);
			templateContent=templateContent.replaceAll("###title###",title);
			templateContent=templateContent.replaceAll("###content###",content);
			//templateContent=templateContent.replaceAll("###author###",editer);//替换掉模块中相应的地方
			//out.print(templateContent);
			// 根据时间得文件名
			Calendar calendar = Calendar.getInstance();
			String fileame = String.valueOf(calendar.getTimeInMillis()) +".html";
			//out.print(fileame);
			//文件输出
			//fileame = ServletActionContext.getServletContext().getRealPath("/download")+"/"+fileame;//生成的html文件保存路径
			fileame=filePathConfig.getFileUploadPath()+"/upload/customForm/"+fileame;
			FileOutputStream fileoutputstream = new FileOutputStream(fileame);//建立文件输出流
			byte tag_bytes[] = templateContent.getBytes();
			fileoutputstream.write(tag_bytes);
			fileoutputstream.close();


			
			 BufferedInputStream bis = null;
	         BufferedOutputStream bos = null;
	         OutputStream fos = null;
	         InputStream fis = null;
	         String  filepath=fileame;//本地绝对路径
	         File uploadFile = new File(filepath);
	         fis = new FileInputStream(uploadFile);
	         bis = new BufferedInputStream(fis);
	         fos = response.getOutputStream();
	         bos = new BufferedOutputStream(fos);
	         response.setCharacterEncoding("UTF-8");
		    response.setHeader("Content-disposition",
		                  "attachment;filename=" +
		                  URLEncoder.encode(uploadFile.getName(), "utf-8"));
		    int bytesRead = 0;
		          //用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
	          byte[] buffer = new byte[BUFF_SIZE];
	          while ((bytesRead = bis.read(buffer, 0, BUFF_SIZE)) != -1) {
	              bos.write(buffer, 0, bytesRead);
	          }         
	          bos.flush();
	          fis.close();
	          bis.close();
	          fos.close();
	          bos.close();  
		  /**
		  在tomcat中jsp编译成servlet之后在函数_jspService(HttpServletRequest request, HttpServletResponse response)的最后
		有一段这样的代码
		finally {
		      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
		    }
		这里是在释放在jsp中使用的对象，会调用response.getWriter(),因为这个方法是和
		response.getOutputStream()相冲突的！所以会出现以上这个异常。
		然后当然是要提出解决的办法，其实挺简单的（并不是和某些朋友说的那样--
		将jsp内的所有空格和回车符号所有都删除掉），
		在使用完输出流以后调用以下两行代码即可：
		out.clear();
		out = pageContext.pushBody();
		  */
		  //out.clear();
          //out = pageContext.pushBody();
			}
			catch(IOException e){
			e.printStackTrace();
			}
		return null;
	}
	/**
	 * 导入表单
	 * @return null
	 */
	public String importForm(){
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			FormAttribute fainfo;
			fainfo = formAttributeService.findById(formId);
			Map map = new HashedMap();
			String templateContent="";
	//		String catalinaHome = System.getProperty("catalina.home"); //tomcat根目录
//			String path = catalinaHome+"/upload/"+fileuploadFileName;
			String catalinaHome = filePathConfig.getFileUploadPath();
			FileInputStream fileinputstream = new FileInputStream(catalinaHome+"/upload/"+fileuploadFileName);//读取模块文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes);
			//判断表单是否符合规范   是否包含_和%
			if(templateContent.contains("_")&&templateContent.contains("%")){
				if (fainfo!=null && fainfo.getFormSource()!=null && !fainfo.getFormSource().equals("")) {
					//表单未被使用
					int beg = templateContent.indexOf("<body>");
					templateContent = templateContent.substring(beg+LENGTH);
					int end = templateContent.indexOf("</body>");
					templateContent = templateContent.substring(0,end);
					fainfo.setFormSource(templateContent);
					//保存表单控件
					List<FormProperties> falist ;
					//得到所有控件的名称和中文名称
					ParseHtml parseHtml = new ParseHtml();
					falist = parseHtml.parser(templateContent,formId);
					if (falist.size()>0) {
						for (FormProperties formProperties : falist) {
							formProperties.setCompanyId(userInfo.getCompanyId());
						}
						formPropertiesService.insertFormItem(falist);
					}
					fainfo.setCompanyId(TransportUser.get().getCompanyId());
					//保存表单源代码到表单属性表
					formAttributeService.saveOrUpdate(fainfo);
					map.put("marked", 1);
				}else {
					//表单还没有创建控件，是空表单
					int beg = templateContent.indexOf("<body>");
					templateContent = templateContent.substring(beg+LENGTH);
					int end = templateContent.indexOf("</body>");
					templateContent = templateContent.substring(0,end);
					if(fainfo!=null){
					fainfo.setFormSource(templateContent);
					fainfo.setCompanyId(TransportUser.get().getCompanyId());
					}
					///保存表单控件
					List<FormProperties> falist;
					//得到所有控件的名称和中文名称
					ParseHtml parseHtml = new ParseHtml();
					falist = parseHtml.parser(templateContent,formId);
					if (falist.size()>0) {
						for (FormProperties formProperties : falist) {
							formProperties.setCompanyId(userInfo.getCompanyId());
						}
						formPropertiesService.insertFormItem(falist);
					}
					//保存表单源代码到表单属性表
					formAttributeService.saveOrUpdate(fainfo);
					map.put("marked", 1);
				}
			}else {
				map.put("marked", 2);
			};
			
			
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		
		return null;
	}
	/**
	 * 得到表单名字
	 * @return 表单名字
	 */
	public String getFormName() {
		return formName;
	}
	/**
	 * 设置表单名字
	 * @param formName 表单名字
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}
	/**
	 * 得到表单类别
	 * @return 表单类别
	 */
	public Integer getFormType() {
		return formType;
	}
	/**
	 * 设置表单类别
	 * @param formType 表单类别
	 */
	public void setFormType(Integer formType) {
		this.formType = formType;
	}
	/**
	 * 
	 * 得到表单属性对象
	 * @return 表单属性对象
	 */
	public FormAttribute getFa() {
		return fa;
	}
	/**
	 * 设置表单属性对象
	 * @param fa 表单属性对象
	 */
	public void setFa(FormAttribute fa) {
		this.fa = fa;
	}
	/**
	 * 得到表单源文件
	 * @return 表单源文件
	 */
	public String getFormContent() {
		return formContent;
	}
	/**
	 * 设置表单源代码
	 * @param formContent 表单源代码
	 */
	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}
	/**
	 * 得到表单id
	 * @return 表单id
	 */
	public Integer getFormId() {
		return formId;
	}
	/**
	 * 设置表单id
	 * @param formId 表单id
	 */
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	/**
	 * 得到上传路径
	 * @return 上传路径
	 */
	public String getFileuploadFileName() {
		return fileuploadFileName;
	}
	/**
	 * 设置上传路径 
	 * @param fileuploadFileName 上传路径
	 */
	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}
	/**
	 * 得到表单的原始名字
	 * @return oldFormName 表单的原始名字
	 */
	public String getOldFormName() {
		return oldFormName;
	}
	/**
	 * 设置表单是原始名字
	 * @param oldFormName 表单的原始名字
	 */
	public void setOldFormName(String oldFormName) {
		this.oldFormName = oldFormName;
	}
	/**
	 * 得到搜索内容
	 * @return the searchName
	 */
	public String getSearchName() {
		return searchName;
	}
	/**
	 * 设置搜索内容
	 * @param searchName the searchName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public static void main(String[] args) {
		
		System.out.println();
	}
}
