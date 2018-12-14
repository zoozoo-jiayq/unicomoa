package cn.com.qytx.cbb.module.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.com.qytx.cbb.module.domain.ModuleInfoMobile;
import cn.com.qytx.cbb.module.service.IModuleInfoMobile;
import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 手机端模块下载action
 * @author liyanlei
 *
 */
public class DownLoadAction extends BaseActionSupport{
	
	@Resource(name="moduleInfoMobileService")
	private IModuleInfoMobile moduleInfoMobileService;
	private String keyword;
	private String fileName;
	
	/**
	 * 手机端下载
	 */
	public String execute(){
		 	HttpServletResponse response = this.getResponse();
	        response.setContentType("application/vnd.ms-excel");

	        OutputStream outp = null;
	        try
	        {
	        	List<ModuleInfoMobile> list = moduleInfoMobileService.findPager(keyword);
	            List<ModuleInfoMobile> listSelected = new ArrayList<ModuleInfoMobile>();
	            fileName = URLEncoder.encode("moduleList.xls", "UTF-8");
	            // 把联系人信息填充到map里面
	            response.addHeader("Content-Disposition",
	                    "attachment;filename=" + fileName);// 解决中文
	                                                                                            // 文件名问题
	            outp = response.getOutputStream();
	            List<Map<String, Object>> mapList = analyzeResult(list);

	            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList, getExportKeyList());
	            exportExcel.exportWithSheetName("手机端模块");
	        }catch(Exception e){
	        	
	        }
		return null;
	}
	private List<Map<String, Object>> analyzeResult(List<ModuleInfoMobile> list)
    {
        // 把订单信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for(ModuleInfoMobile moduleInfoMobile : list){
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("name",moduleInfoMobile.getName());
        	map.put("code",moduleInfoMobile.getCode());
        	map.put("url",moduleInfoMobile.getUrl());
        	map.put("orderList",moduleInfoMobile.getOrderList());
        	mapList.add(map);
        }
        return mapList;
    }
	 private List<String> getExportHeadList(){
	        List<String> headList = new ArrayList<String>();
	        headList.add("菜单名称");
	        headList.add("菜单代码");
	        headList.add("页面路径");
	        headList.add("排序号");
	        return headList;
	    }
	    
	    private List<String> getExportKeyList(){
	        List<String> headList = new ArrayList<String>();
	        headList.add("name");
	        headList.add("code");
	        headList.add("url");
	        headList.add("orderList");
	        return headList;
	    }
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
