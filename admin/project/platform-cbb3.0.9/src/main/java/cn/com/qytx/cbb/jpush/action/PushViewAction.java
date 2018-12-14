package cn.com.qytx.cbb.jpush.action;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import cn.com.qytx.cbb.jpush.domain.PushInfo;
import cn.com.qytx.cbb.jpush.service.IPushInfo;
import cn.com.qytx.cbb.jpush.service.IPushUser;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
/**
 * 
 * <br/>功能: 推送详情
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-3-22
 * <br/>修改日期: 2013-3-22
 * <br/>修改列表:
 */
public class PushViewAction  extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
	@Resource(name = "pushInfoService")
    IPushInfo pushInfoService;
	
	@Resource(name = "pushUserService")
    IPushUser pushUserService;
	
	@Resource(name = "userService")
    IUser userService;

	 //推送id
	private Integer pushId;
	private int type;//跳转类型 0详情页面1修改页面

	public Integer getPushId() {
		return pushId;
	}

	public void setPushId(Integer pushId) {
		this.pushId = pushId;
	}

	/**
	 * 
	 * 功能：推送详情
	 */
	public String viewPush(){
		try {
			UserInfo userObject=(UserInfo)getSession().getAttribute("adminUser");
			if(userObject!=null&&pushId!=null){
					PushInfo pushInfo=pushInfoService.findOne(pushId);
					//发布时间
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					pushInfo.setPushTimeStr(sdf.format(pushInfo.getPushTime()));
					super.getRequest().setAttribute("pushInfo", pushInfo);
					//得到推荐公司(名字)
					String names=pushUserService.findUserNamesByPushId(pushId);
					if (names.startsWith(",")) {
						names = names.substring(1);
					}
					if (names.endsWith(",")) {
						names = names.substring(0,names.length()-1);
					}
					super.getRequest().setAttribute("names", names);
					//得到推荐公司(id)
					String ids=pushUserService.findUserIdsByPushId(pushId);
					if (ids.startsWith(",")) {
						ids = ids.substring(1);
					}
					if (ids.endsWith(",")) {
						ids = ids.substring(0,ids.length()-1);
					}
					super.getRequest().setAttribute("ids", ids);
					//得到推荐发起人
					String recomendName=pushInfo.getUserName();
//					Map<Integer, String> companySerMap = userService.getAllUserName();
//					String recomendName = "";
//					if (companySerMap!=null&&companySerMap.get(pushInfo.getUserId())!=null) {
//						recomendName = companySerMap.get(pushInfo.getUserId());
//					}
					super.getRequest().setAttribute("recomendName", recomendName);
			}
		} catch (Exception e) {  
		    LOGGER.error(e.getMessage());
			if (type == 1) {
				return "update";
			}else {
				return "detail";
			}
		}
		if (type == 1) {
			return "update";
		}else {
			return "detail";
		}
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
