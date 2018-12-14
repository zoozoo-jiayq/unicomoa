package cn.com.qytx.cbb.jbpmApp.action.mobile;

import java.net.URLDecoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplDetail;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.IJson;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplNull;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplServerException;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplSuccess;
import cn.com.qytx.cbb.jbpmApp.service.mobile.JbpmMobileService;

/**
 * 功能：查询详情
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:16:06 
 * 修改日期：上午9:16:06 
 * 修改列表：
 */
public class DetailAction extends MyBaseAction {
	private static final Logger logger=LoggerFactory.getLogger(DetailAction.class);
	@Resource(name="jbpmMobileService")
	private JbpmMobileService jbpmMobileService;
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource
	private IFormAttribute formAttributeService;
	private String instanceId;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	/**
	 * @throws Exception 
	 * 功能：查看详情
	 * @param
	 * @return
	 * @throws   
	 */
	public String view() throws Exception{
		IJson json = null;
		try {
			instanceId = URLDecoder.decode(instanceId, "UTF-8");
			long t1=System.currentTimeMillis();
			logger.info(Thread.currentThread().getName()+"开始获取流程表单详情数据，流程instanceId="+instanceId+",开始时间t1="+t1);
			DataElementImplDetail detail = jbpmMobileService.findByInstanceId(instanceId);
			json = new JsonImplSuccess<DataElementImplDetail>();
			json.setDatas(detail);
			long t2=System.currentTimeMillis();
			logger.info(Thread.currentThread().getName()+"已获取流程表单详情数据，流程instanceId="+instanceId+",当前时间t2="+t2+",间隔时间t2-t1="+(t2-t1));
			ajax(json.getMobileClientResponse());
		} catch (Exception e) {
			json = new JsonImplServerException();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：查看原始表单
	 * @param
	 * @return
	 * @throws   
	 */
	public String viewForm() throws Exception{
		IJson json = null;
		try {
			instanceId = URLDecoder.decode(instanceId, "UTF-8");
			int formId =  jbpmMobileService.viewForm(instanceId);
			FormAttribute fa = formAttributeService.findById(formId);
			json = new JsonImplSuccess<String>();
			if(formId>0){
				json.setDatas(fa.getFormSource());
				ajax(fa.getFormSource());
				return null;
			}else{
				json = new JsonImplNull();
			}
			ajax(json.getMobileClientResponse());
		} catch (Exception e) {
			json = new JsonImplServerException();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
