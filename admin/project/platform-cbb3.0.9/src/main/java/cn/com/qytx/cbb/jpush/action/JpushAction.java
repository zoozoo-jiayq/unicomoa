package cn.com.qytx.cbb.jpush.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import cn.com.qytx.cbb.jpush.service.IPush;
import cn.com.qytx.cbb.jpush.service.impl.ErrorCode;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.CompanyInfo;

/**
 * 推送接口实现action
 * User:黄普友
 * Date: 13-7-6
 * Time: 上午11:00
 */
public class JpushAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
    @Resource(name = "pushService")
    private IPush jpushService;
    /**
     * 告知手机端需要数据同步（同步到手机端）
     * @return
     */
    public String sendCustomMessageWithTag()
    {
        try
        {
        	CompanyInfo companyInfo = (CompanyInfo) getSession().getAttribute("companyInfo");
            int companyId = companyInfo.getCompanyId();
        	this.getSession().getAttribute("");
            Map<String, String> hm = new HashMap<String, String>();
            hm.put("type", "1");
            int result= jpushService.sendCustomMessageWithTag(String.valueOf(companyId),"","","",hm,getValue("masterSecret"),getValue("appKey"));
            if(result==ErrorCode.NOERROR.value())
            {
                ajax("0");
            }
            else
            {
                ajax("1");
            }
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            try {
                ajax("2");
            } catch (Exception e) {
                LOGGER.error(ex.getMessage());
            }
            return null;
        }
        return null;
    }
    /**
     * 获取配置信息
     * @param keyString
     * @return
     */
    public  String getValue(String keyString){
    	Properties prop = null;
		prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("/application.properties"));
			return prop.getProperty(keyString);
		} catch (Exception e) {
		    LOGGER.error(e.getMessage());
		}
		return null;
    }
}
