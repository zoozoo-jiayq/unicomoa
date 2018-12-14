package cn.com.qytx.cbb.affairSetting.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;

import cn.com.qytx.cbb.affairSetting.dao.AffairSettingDao;
import cn.com.qytx.cbb.affairSetting.domain.AffairSetting;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 事务提醒标签 控制事务提醒类型
 * 
 * @author xisongyan createTime 2014.7.8
 */
public class AffairShowTag extends TagSupport {

	private String moduleCode;// 模块编码

	private String sendType;// 发送事务提醒的类型
	
	private String affairName;//标签前缀名称

	
	public String getAffairName() {
		return affairName;
	}

	public void setAffairName(String affairName) {
		this.affairName = affairName;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public int doStartTag() throws JspException {
		JspWriter out = super.pageContext.getOut();
		try {
			String affairShow = "";
			int num = 0;
			AffairSettingDao affairSettingDao = (AffairSettingDao) SpringUtil
					.getBean("affairSettingDao");

			AffairSetting affairsetting = affairSettingDao
					.findByCode(moduleCode);
			if (affairsetting != null) {
				if (!StringUtil.isBlank(sendType)) {// 当sendType有值时候，保留对应的选项
					String[] arr = sendType.split("_");
					if (affairsetting.getAffairPriv() == 1) {
						if ("1".equals(arr[0])) {
							affairShow += "<label class='radio'><input type='checkbox'  id='affairType1' checked  value='1'/>在线消息</label>";
							num++;
						} else {
							affairShow += "<label class='radio'><input type='checkbox'  id='affairType1'  value='1'/>在线消息</label>";
							num++;
						}
					}

					if (affairsetting.getSmsPriv() == 1) {
						if ("1".equals(arr[1])) {
							affairShow += "<label class='radio'><input type='checkbox'  id='affairType2' checked  value='1'/>短信提醒</label>";
							num++;
						} else {
							affairShow += "<label class='radio'><input type='checkbox'  id='affairType2'  value='1'/>短信提醒</label>";
							num++;
						}
					}

					if (affairsetting.getPushPriv() == 1) {
						if ("1".equals(arr[2])) {
							affairShow += "<label class='radio'><input type='checkbox'  id='affairType3' checked  value='1'/>手机推送</label>";
							num++;
						}else {

							affairShow += "<label class='radio'><input type='checkbox'  id='affairType3' value='1'/>手机推送</label>";
							num++;

							}
					}

				} else {// 当sendType没有值时候，是初始化设置
					if (affairsetting.getAffairPriv() == 1) {
						affairShow += "<label class='radio'><input type='checkbox'  id='affairType1' checked  value='1'/>在线消息</label>";
						num++;
					}

					if (affairsetting.getSmsPriv() == 1) {
						affairShow += "<label class='radio'><input type='checkbox'  id='affairType2' checked value='1'/>短信提醒</label>";
						num++;
					}

					if (affairsetting.getPushPriv() == 1) {
						affairShow += "<label class='radio'><input type='checkbox'  id='affairType3' checked value='1'/>手机推送</label>";
						num++;
					}

				}
			}
			if (num > 0) {
				if(StringUtils.isNotBlank(affairName)){		
					affairShow = "<tr id='affairShowId'><th>"+affairName+"：</th><td>" + affairShow + "</td></tr>";
				}else{
				affairShow = "<trid='affairShowId' ><th>提醒：</th><td>" + affairShow + "</td></tr>";
				}
			} else {
				affairShow = "<tr id='affairShowId' style='display:none'><th>提醒：</th><td></td></tr>";
			}
			out.println(affairShow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

}
