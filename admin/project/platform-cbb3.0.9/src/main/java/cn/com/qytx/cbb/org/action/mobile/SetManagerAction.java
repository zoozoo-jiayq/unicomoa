package cn.com.qytx.cbb.org.action.mobile;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.qytx.cbb.org.service.MobileSetService;

/**
 * <br/>功能  群组管理手机接口
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月6日
 * <br/>修改日期  2016年1月6日
 * <br/>修改列表
 */
@RequestMapping("/org/mobile/set")
@Controller
public class SetManagerAction extends BaseOrgController {
	protected final static Logger logger = LoggerFactory
			.getLogger(SetManagerAction.class);

	@Autowired
	MobileSetService mobileSetService;
	
	/**
	 * 功能：新增群组人员
	 * 
	 * @return
	 */
	@RequestMapping("/addGroupUser.c")
	public void addGroupUser(
			@RequestParam(value = "companyId", required = true) Integer companyId,
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "groupId", required = true) Integer groupId,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "userIds", required = true) String userIds,
			HttpServletResponse response)
			throws Exception {

		logger.info("接收到请求,method=addGroupUsers,companyId=" + companyId
				+ ",userId=" + userId + ",userName=" + userName + ",groupId="
				+ groupId + ",userIds=" + userIds);
		String result = mobileSetService.addSetUsers(companyId, userId,
				userName, groupId, userIds);
		if (result != null) {
			sendPush(companyId);
		}
		SUCCESS(result,response);
	}

	/**
	 * 功能：删除群组人员
	 * 
	 * @return
	 */
	@RequestMapping("/deleteGroupUser.c")
	public void deleteGroupUser(
			@RequestParam(value = "companyId", required = true) Integer companyId,
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "groupId", required = true) Integer groupId,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "userIds", required = true) String userIds,
			HttpServletResponse response)
			throws Exception {
		logger.info("接收到请求,method=deleteGroupUsers,companyId=" + companyId
				+ ",userId=" + userId + ",userName=" + userName + ",groupId="
				+ groupId + ",userIds=" + userIds);
		String result = mobileSetService.deleteSetUsers(companyId, userId,
				userName, groupId, userIds);
		if (result != null) {
			sendPush(companyId);
		}
		SUCCESS(result,response);
	}
}
