package cn.com.qytx.cbb.org.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * @author jiayongqiang
 * Excel工具类
 */
public class ExcelUtil {
	
	 /**
     * 由文件生成部门列表
     * @param excel
     * @return
     */
    public static Map<String,GroupInfo> getGroupList(int companyId,String excel){
    	Map<String,GroupInfo> result = new HashMap<String, GroupInfo>();
    	IGroup groupService = (IGroup) SpringUtil.getBean("groupService");
        Workbook wb = null;
        try
        {
            InputStream is = new FileInputStream(excel);
            wb = Workbook.getWorkbook(is);
            Sheet sheet = wb.getSheet("通讯录");    //获取工作Excel
            //第一行不判断
            for (int j = 1; j < sheet.getRows(); j++) {
                Cell[] cells = sheet.getRow(j);    //读取一行
                if (cells != null && cells.length > 0) {
                    String groupName="";//部门名称
                    for (int k = 0; k < cells.length; k++) {
                        String val = cells[k].getContents();//内容
                        if (val != null) {
                            val = val.trim();
                        }
                        if (k == 1) {
                            groupName=val;
                        }
                    }
                    if(result.containsKey(groupName)){
                    	continue;
                    }else{
                    	GroupInfo g = groupService.loadGroupByPathName(companyId, groupName);
                    	result.put(groupName, g);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            if(wb!=null)
            {
                wb.close();
            }
        }
        return result;
    }

	/**
     * 由文件生成用户列表
     * @param excel
     * @return
     */
    public static List<UserInfo> getUserList(String excel)
    {
        List<UserInfo> list =new ArrayList<UserInfo>();
        Workbook wb = null;
        try
        {
            InputStream is = new FileInputStream(excel);
            wb = Workbook.getWorkbook(is);
            Sheet sheet = wb.getSheet("通讯录");    //获取工作Excel
            boolean flag = true;
            //第一行不判断
            for (int j = 1; j < sheet.getRows(); j++) {

                Cell[] cells = sheet.getRow(j);    //读取一行
                if (cells != null && cells.length > 0) {
                	UserInfo user=new UserInfo();
                	String loginName="";//用户名
                    String userName="";//姓名
                    String groupName="";//部门名称
                    String sex="";//性别
                    String phone="";//手机
                    
                    String temp = "";
                    for (int k = 0; k < cells.length; k++) {
                        String val = cells[k].getContents();//内容
                        if (val != null) {
                            val = val.trim();
                        }
                        temp +=val;
                        if (k == 0) {
                            userName=val;
                        }else if (k == 1) {
                            groupName=val;
                        }else if (k == 2) {
                            sex=val;
                        }else if (k == 3) {
                            phone=val;
                        }
                    }//column
                    if(StringUtils.isBlank(temp)){
                    	continue;
                    }
                    user.setLoginName(loginName);
                    user.setUserName(userName);
                    user.setGroupName(groupName);
                    user.setPhone(phone);
                    if (!StringUtils.isEmpty(sex)){
                        if("女".equals(sex)){
                            user.setSex(0);
                        }else{
                            user.setSex(1);
                        }
                    }
                    
                    list.add(user);
                }
                if (!flag) {
                    break;
                }
            }//one sheet

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            if(wb!=null)
            {
                wb.close();
            }
        }
        return list;
    }
}
