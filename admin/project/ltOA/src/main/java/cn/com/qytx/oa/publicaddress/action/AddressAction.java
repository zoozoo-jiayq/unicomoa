package cn.com.qytx.oa.publicaddress.action;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.daily.domain.Daily;
import cn.com.qytx.cbb.daily.domain.DailyReview;
import cn.com.qytx.cbb.daily.service.IDaily;
import cn.com.qytx.cbb.daily.service.IDailyReview;
import cn.com.qytx.cbb.daily.vo.DailySearch;
import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.cbb.org.util.ImportBean;
import cn.com.qytx.cbb.org.util.ImportExcelService;
import cn.com.qytx.oa.dataPriv.service.IDataPriv;
import cn.com.qytx.oa.publicaddress.domain.Address;
import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.publicaddress.domain.AddressGroup;
import cn.com.qytx.oa.publicaddress.service.IAddress;
import cn.com.qytx.oa.publicaddress.service.IAddressGroup;
import cn.com.qytx.oa.publicaddress.vo.AddressVo;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;

import com.google.gson.Gson;

/**
 * 功能:通讯薄联系人action
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表: .
 */
public class AddressAction extends BaseActionSupport
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 2363545326219702098L;

    /**
     * 日志类
     */
    private static final Logger LOGGER = Logger.getLogger(AddressAction.class);

    /**
     * 通讯薄联系人service
     */
    @Autowired
    IAddress addressImpl;

    /**
     * dailyImpl
     */
    @Autowired
    IDaily dailyImpl;

    /**
     * dailyReviewImpl
     */
    @Autowired
    IDailyReview dailyReviewImpl;

    /**
     * 用户信息
     */
    @Autowired
    IUser userService;

    /**
     * 权限设置impl
     */
    @Autowired
    IDataPriv dataPrivImpl;

    /**
     * 组用户信息
     */
    @Autowired
    IGroupUser groupUserService;

    /**
     * 角色
     */
    @Autowired
    IRole roleService;

    /**
     * 通讯薄联系人组service
     */
    @Autowired
    IAddressGroup addressGroupImpl;

    @Autowired
    private IGroup groupService;
    /**
     * 联系人信息
     */
    private Address address;

    /**
     * 联系人Vo
     */
    private AddressVo addressVo;

    /**
     * 日志search
     */
    private DailySearch dailySearch;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 群组Id
     */
    private Integer groupId;

    /**
     * 群组名称
     */
    private String groupName;

    /**
     * 联系人Id
     */
    private Integer[] addressId;

    /**
     * 导入文件名
     */
    private String fileUploadName;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 部门ids
     */
    private String groupIds;


    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 标记是否为公共通讯簿 1表示为公共
     */
    public Integer publicSign;

    /**
     * 通讯簿群组信息
     */
    private List<AddressGroup> addressGrouplist;

    /**
     * 当前用户所在的组信息
     */
    private GroupInfo groupInfoBean;

    /**
     * 当前登录用户信息
     */
    private UserInfo user;

    /**
     * 请求详细页面的来源 android安卓
     */
    private String requestSrc;


    /**
     * 添加通讯薄联系人信息
     */
    public void addAddress()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 基本参数校验
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 判断新增联系人是否重复(姓名和电话相同)
            Address old = addressImpl.getAddressByParams(address.getName(), address.getFamilyMobileNo());
            if (null != old){
                ajax("exist");
                return;
            }
            
            // 根据联系人名字获取首字母
            String firstLetter = Pinyin4jUtil.getPinYinHeadChar(address.getName().substring(0, 1));
            address.setFirstLetter(firstLetter);

            // 初始化统一参数
            address.setIsDelete(CbbConst.NOT_DELETE);
            address.setCreateUserInfo(user);
            address.setCreateTime(new Timestamp(new Date().getTime()));
            address.setLastUpdateTime(new Timestamp(new Date().getTime()));
            address.setLastUpdateUserId(user.getUserId());
            address.setCompanyId(user.getCompanyId());

            // 保存联系人信息
            addressImpl.saveAddress(address);
            out.print("success");
        }
        catch (Exception e)
        {
            LOGGER.error("addAddressGroup error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 获取组下的联系人信息
     */
    public void getAddressList()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            user = (UserInfo) this.getSession().getAttribute("adminUser");
            addressVo.setCreateUserInfo(user);

            // 设置排序字段
            if (StringUtil.isEmpty(addressVo.getSortFiled()))
            {
                addressVo.setSortFiled("orderNo");
                addressVo.setSortType("asc");
            }

            // 查询公共通讯簿联系人 或者 获取所有通讯簿联系人
            if ((null != addressVo.getPublicSign() && 1 == addressVo.getPublicSign())
                    || (null != addressVo.getAddressGroupId() && (CbbConst.SEARCH_ALL == addressVo
                            .getAddressGroupId() || AddressConst.DEFAULT_GROUP == addressVo
                            .getAddressGroupId())))
            {
                int uId = user.getUserId();
                
                
                // 获取所属组
                groupInfoBean = groupService.findOne(user.getGroupId());

                // 获取所属角色
                List<Integer> listRole = new ArrayList<Integer>();
                List<RoleInfo> roleIdList = roleService.getRoleByUser(uId);
                if (null != roleIdList)
                {
                    for (RoleInfo roleInfo : roleIdList)
                    {
                        listRole.add(roleInfo.getRoleId());
                    }
                }

                String dataPrivHql = dataPrivImpl.getDataPrivHql("ag.id",
                        AddressConst.PRIVDATA_MODULE_ADDRESS, "", uId, groupInfoBean == null ? 0
                                : groupInfoBean.getGroupId(), listRole);
                addressVo.setDataPrivHql(dataPrivHql);

                String userDataPrivHql = dataPrivImpl.getDataPrivHql("id",
                        AddressConst.PRIVDATA_MODULE_ADDRESS, "", uId, groupInfoBean == null ? 0
                                : groupInfoBean.getGroupId(), listRole);
                addressGrouplist = addressGroupImpl.findPublicByUser(user, userDataPrivHql);
            }

            List<Address> list = addressImpl.findAllAddress(addressVo);

            // 组装结果信息
            List<Map<String, Object>> mapList = analyzeResult(list);
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            out.print(jsons);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    private List<Map<String, Object>> analyzeResult(List<Address> list)
    {
        // 把订单信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (list != null)
        {
            for (Address tempAddress : list)
            {
                Map<String, Object> map = new HashMap<String, Object>();

                // ID
                Integer id = tempAddress.getId();
                map.put("id", id);

                // 组ID
                Integer addressGroupId = tempAddress.getAddressGroupId();
                map.put("addressGroupId", addressGroupId);

                // 组名
                map.put("groupName",
                        tempAddress.getGroupName() == null ? "" : tempAddress.getGroupName());

                // 姓名
                String name = tempAddress.getName();
                map.put("name", name);

                // 性别
                Integer sex = tempAddress.getSex();
                map.put("sex", null == sex ? "" : sex);
                if (null != sex && 1 == sex)
                {
                    map.put("sexStr", AddressConst.MEN);
                }
                else if (null != sex && 0 == sex)
                {
                    map.put("sexStr", AddressConst.WOMEN);
                }

                // 照片
                String photo = tempAddress.getPhoto();
                map.put("photo", null == photo ? "" : photo);

                // 单位名称
                String companyName = tempAddress.getCompanyName();
                map.put("companyName", null == companyName ? "" : companyName);

                // 工作电话
                String officePhone = tempAddress.getOfficePhone();
                map.put("officePhone", null == officePhone ? "" : officePhone);

                // 家庭电话
               // String familyPhoneNo = tempAddress.getFamilyPhoneNo();
              //  map.put("familyPhoneNo", null == familyPhoneNo ? "" : familyPhoneNo);

                // 手机
                String familyMobileNo = tempAddress.getFamilyMobileNo();
                map.put("familyMobileNo", null == familyMobileNo ? "" : familyMobileNo);

//                 电子邮件
                String familyEmail = tempAddress.getFamilyEmail();
                map.put("familyEmail", null == familyEmail ? "" : familyEmail);

                // 昵称
                String nickname = tempAddress.getNickname();
                map.put("nickname", null == nickname ? "" : nickname);

                // 职务
                String postInfo = tempAddress.getPostInfo();
                map.put("postInfo", null == postInfo ? "" : postInfo);

                // 生日
                Timestamp birthday = tempAddress.getBirthday();
                if (null != birthday)
                {
                    map.put("birthday",
                            DateTimeUtil.dateToString(birthday, CbbConst.DATE_FORMAT_STR));
                }

                // 配偶
                String wifeName = tempAddress.getWifeName();
                map.put("wifeName", null == wifeName ? "" : wifeName);

                // 子女
                String children = tempAddress.getChildren();
                map.put("children", null == children ? "" : children);

                // 家庭邮编
                //String familyPostCode = tempAddress.getFamilyPostCode();
               // map.put("familyPostCode", null == familyPostCode ? "" : familyPostCode);

                // 家庭住址
               // String familyAddress = tempAddress.getFamilyAddress();
               // map.put("familyAddress", null == familyAddress ? "" : familyAddress);

                // 单位邮编
                String companyPostCode = tempAddress.getCompanyPostCode();
                map.put("companyPostCode", null == companyPostCode ? "" : companyPostCode);

                // 单位地址
                String companyAddress = tempAddress.getCompanyAddress();
                map.put("companyAddress", null == companyAddress ? "" : companyAddress);

                // 工作传真
                String officeFax = tempAddress.getOfficeFax();
                map.put("officeFax", null == officeFax ? "" : officeFax);

                // 备注
                String remark = tempAddress.getRemark();

                // 共享联系人
                String shareToUserIds = tempAddress.getShareToUserIds();
                map.put("shareToUserIds", null == shareToUserIds ? "" : shareToUserIds);

                // 编辑权限人
                String allowUpdateUserIds = tempAddress.getAllowUpdateUserIds();
                map.put("allowUpdateUserIds", null == allowUpdateUserIds ? "" : allowUpdateUserIds);

                // 创建人
                Integer createId = tempAddress.getCreateUserInfo().getUserId();
                map.put("createId", null == createId ? "" : createId);
                map.put("remark", null == remark ? "" : remark);

                // 公共组下人员需判断是否有维护权限
                if (null != groupInfoBean)
                {
                    map.put("isMaintainPriv",
                            isMaintainPriv(tempAddress.getAddressGroupId(), addressGrouplist,
                                    user.getUserId(), groupInfoBean.getGroupId()));
                }
                else
                {
                    map.put("isMaintainPriv", true);
                }

                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 功能：判断公共通讯簿联系人,当前用户是否有维护权限
     * @param addressGroupId 通讯簿组Id
     * @param templist 当前登录人所开放的公共通讯簿组的信息
     * @param userId 当前登录人用户Id
     * @param groupId 当前登录人群组Id
     * @return 是否有权限
     */
    private boolean isMaintainPriv(Integer addressGroupId, List<AddressGroup> templist,
            Integer userId, Integer groupId)
    {
        // 非单独查询公共通讯簿,即查询个人通讯薄时,公共通讯薄联系人都没有权限修改
        if (null == addressVo || null == addressVo.getPublicSign()
                || 1 != addressVo.getPublicSign())
        {
            if (null != templist && !templist.isEmpty())
            {
                for (AddressGroup tempAddressGroup : templist)
                {
                    if (addressGroupId == tempAddressGroup.getId().intValue())
                    {
                        return false;
                    }
                }

                if (AddressConst.DEFAULT_PUBLIC_GROUP == addressGroupId)
                {
                    return false;
                }
            }
            return true;
        }

        // 默认组,所有人都能修改
        if (AddressConst.DEFAULT_PUBLIC_GROUP == addressGroupId)
        {
            return true;
        }

        if (null != templist && !templist.isEmpty())
        {
            for (AddressGroup tempAddressGroup : templist)
            {
                if (addressGroupId != tempAddressGroup.getId().intValue())
                {
                    continue;
                }

                // 用户是否拥有权限
                String maintainUserIds = tempAddressGroup.getMaintainUserIds();
                if (!StringUtil.isEmpty(maintainUserIds))
                {
                    if (maintainUserIds.indexOf("," + userId + ",") >= 0)
                    {
                        return true;
                    }
                }

                // 用户所在的组是否拥有权限
                String maintainGroupIds = tempAddressGroup.getMaintainGroupIds();
                if (!StringUtil.isEmpty(maintainGroupIds))
                {
                    if (maintainGroupIds.indexOf("," + groupId + ",") >= 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<Map<String, Object>> dailyResult(List<Daily> list)
    {
        // 把订单信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (list != null)
        {
            for (Daily daily : list)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                // ID
                map.put("id", daily.getId());

                // 日志日期
                map.put("dailyTime",
                        DateTimeUtil.dateToString(daily.getDailyTime(), CbbConst.DATE_FORMAT_STR));

                // 创建时间
                map.put("createTime",
                        DateTimeUtil.dateToString(daily.getDailyTime(), CbbConst.TIME_FORMAT_STR));

                // 类型
                if (daily.getType() == 1)
                {
                    map.put("type", "工作日志");
                }
                else
                {
                    map.put("type", "个人日志");
                }

                // 标题
                map.put("title", daily.getTitle());

                // 内容
                map.put("content", delHTMLTag(daily.getContent()));

                List reviewList = dailyReviewImpl.getReviewListByDailyId(daily.getId());
                StringBuffer review = new StringBuffer();
                for (int k = 0; k < reviewList.size(); k++)
                {
                    DailyReview dr = (DailyReview) reviewList.get(k);
                    review.append(dr.getUser().getUserName()+ "   "+ DateTimeUtil.dateToString(daily.getDailyTime(),CbbConst.TIME_FORMAT_STR) + "\n" + delHTMLTag(dr.getContent()) + "\n");
                }

                // 点评
                map.put("review", review.toString());

                // 附件名称
                String attachNames = "";
                attachNames = daily.getAttachName();
                if (attachNames != null)
                {
                    attachNames = attachNames.replaceAll(",", "\n");
                }
                else
                {
                    attachNames = "";
                }

                map.put("attachNames", attachNames);

                mapList.add(map);
            }
        }
        return mapList;
    }

    public static String delHTMLTag(String htmlStr)
    {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 获取联系人详细信息
     * @return 对应页面映射
     */
    @SuppressWarnings("unchecked")
    public String getDetailInfo()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            addressVo.setCreateUserInfo(user);

            // 获取联系人信息
            Address address = addressImpl.findById(addressVo.getId());

            // 填充共享联系人和修改权限人
            if (null != address)
            {
                String shareToUserIds = address.getShareToUserIds();
                if (!StringUtil.isEmpty(shareToUserIds))
                {
                    List<UserInfo> list = userService.findUsersByIds(
                            shareToUserIds, null, null);
                    address.setShareToUserNames(getUserInfo(list));
                }
                String allowUpdateUserIds = address.getAllowUpdateUserIds();
                if (!StringUtil.isEmpty(allowUpdateUserIds))
                {

                    List<UserInfo> list = userService.findUsersByIds(
                            allowUpdateUserIds, null, null);
                    address.setAllowUpdateUserNames(getUserInfo(list));
                }

            }
            this.getRequest().setAttribute("address", address);
            return operateType;
        }
        catch (Exception e)
        {
            LOGGER.error("getDetailInfo error. ", e);
        }
        return null;
    }

    private String getUserInfo(List<UserInfo> list)
    {
        StringBuffer sb = new StringBuffer();
        if (null != list)
        {
            for (UserInfo userInfo : list)
            {
                sb.append(userInfo.getUserName() + ",");
            }
        }
        return sb.toString();
    }

    /**
     * 更新联系人信息
     */
    public void updateAddress()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 根据联系人名字获取首字母
            String firstLetter = Pinyin4jUtil.getPinYinHeadChar(address.getName().substring(0, 1));
            address.setFirstLetter(firstLetter);

            // 判断联系人是否重复
            Address oldBean = addressImpl.getAddressByParams(address.getName(), address.getFamilyMobileNo());
            if (null != oldBean && oldBean.getId().intValue() != address.getId()){
                ajax("exist");
                return;
            }
            
            // 查询联系人信息
            Address addressBean = addressImpl.findById(address.getId());
            if (null == addressBean)
            {
                // 联系人不存在
                out.print("notexist");
                return;
            }

//            UpdateFieldUtil.update(address, addressBean);

            // 保存联系人信息
            address.setCompanyId(getLoginUser().getCompanyId());
            addressImpl.updateAddress(address);
            out.print("success");
        }
        catch (Exception e)
        {
            LOGGER.error("updateAddress error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 共享联系人
     */
    public void shareAddress()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 基本参数校验 TODO

            // 获取联系人基本信息
            Address addressBean = addressImpl.findById(addressVo.getId());
            if (null == addressBean)
            {
                // 联系人不存在
                out.print("notexist");
                return;
            }

            // 更新联系人共享信息
            if (!StringUtils.isEmpty(addressVo.getShareStartTime()))
            {
                addressBean.setStartShareTime(DateTimeUtil.stringToTimestamp(
                        addressVo.getShareStartTime(), CbbConst.TIME_FORMAT_STR));
            }
            else
            {
                addressBean.setStartShareTime(null);
            }
            if (!StringUtils.isEmpty(addressVo.getShareEndTime()))
            {
                addressBean.setEndShareTime(DateTimeUtil.stringToTimestamp(
                        addressVo.getShareEndTime(), CbbConst.TIME_FORMAT_STR));
            }
            else
            {
                addressBean.setEndShareTime(null);
            }
            addressBean.setAllowUpdateUserIds(addressVo.getAllowUpdateUserIds());
            addressBean.setShareToUserIds(addressVo.getShareToUserIds());

            // 设置是否发送事务请求
            addressBean.setAffairsSign(addressVo.getAffairsSign());

            // 保存联系人信息
            addressImpl.saveOrUpdate(addressBean);
            out.print("success");
        }
        catch (Exception e)
        {
            LOGGER.error("shareAddress error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 获取共享组下的联系人信息
     */
    public void getShareAddressList()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            addressVo.setCreateUserInfo(user);
            List<Address> list = addressImpl.findShareAddress(groupId, user.getUserId());

            // 组装结果信息
            List<Map<String, Object>> mapList = analyzeResult(list);
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            out.print(jsons);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    private List<String> getExportHeadList()
    {
        List<String> headList = new ArrayList<String>();
        headList.add("姓名");
        headList.add("性别");
        headList.add("生日");
        headList.add("昵称");
        headList.add("职位");
        headList.add("配偶");
        headList.add("子女");
        headList.add("单位名称");
        headList.add("单位地址");
        headList.add("单位邮编");
        headList.add("工作电话");
        headList.add("工作传真");
        //headList.add("家庭住址");
        //headList.add("家庭邮编");
      //  headList.add("家庭电话");
        headList.add("手机");
       // headList.add("电子邮件");
        headList.add("备注");
        return headList;
    }

    private List<String> getDailyExportHeadList()
    {
        List<String> headList = new ArrayList<String>();

        headList.add("日期");
        headList.add("写日志时间");
        headList.add("日志类型");
        headList.add("日志标题");
        headList.add("日志内容");
        headList.add("点评");
        headList.add("附件名称");
        return headList;
    }

    private List<String> getExportKeyList()
    {
        List<String> headList = new ArrayList<String>();
        headList.add("name");
        headList.add("sexStr");
        headList.add("birthday");
        headList.add("nickname");
        headList.add("postInfo");
        headList.add("wifeName");
        headList.add("children");
        headList.add("companyName");
        headList.add("companyAddress");
        headList.add("companyPostCode");
        headList.add("officePhone");
        headList.add("officeFax");
      //  headList.add("familyAddress");
      //  headList.add("familyPostCode");
        //headList.add("familyPhoneNo");
        headList.add("familyMobileNo");
      //  headList.add("familyEmail");
        headList.add("remark");
        return headList;
    }

    private List<String> getDailyExportKeyList()
    {
        List<String> headList = new ArrayList<String>();
        headList.add("dailyTime");
        headList.add("createTime");
        headList.add("type");
        headList.add("title");
        headList.add("content");
        headList.add("review");
        headList.add("attachNames");
        return headList;
    }

    /**
     * 删除联系人信息
     */
    public void deleteAddress()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 基本参数校验
            if (null == addressId)
            {
                out.print("param error");
                return;
            }

            // 删除联系人信息
            addressImpl.deleteAddresses(addressId);
            out.print("success");
        }
        catch (Exception e)
        {
            LOGGER.error("shareAddress error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 导出文件
     */
    public void exportAddress()
    {
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");

        OutputStream outp = null;
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            addressVo.setCreateUserInfo(user);
            List<Address> list = addressImpl.getPublicAddress(addressVo);

            // 把联系人信息填充到map里面
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String("联系人.xls".getBytes(), "iso8859-1"));// 解决中文
                                                                                            // 文件名问题
            outp = response.getOutputStream();
            List<Map<String, Object>> mapList = analyzeResult(list);

            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList,
                    getExportKeyList());
            exportExcel.export();
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
        finally
        {
        }
    }

    /**
     * 功能：验证字符串是否为空
     * @param str 需验证字符串
     * @return 字符串
     */
    public String validateIsEmpty(String str)
    {
        if (str == null || str.equals(""))
        {
            return "";
        }
        else
        {
            return str;
        }
    }
	/**
	 * 功能：根据二级局获取部门ID集合
	 * @param
	 * @return
	 * @throws   
	 */
	private String getGroupIds(int userId){
		UserInfo user = userService.findOne(userId);
		GroupInfo forkGroup = groupService.getForkGroup(user.getCompanyId(),userId);
		List<GroupInfo> groupList = null;
		if(forkGroup != null){
			groupList = groupService.getSubGroupList(forkGroup.getGroupId());
			groupList.add(forkGroup);
		}
		String groupIds = "";
		if(groupList!=null){
			for(Iterator<GroupInfo> it = groupList.iterator(); it.hasNext();){
				GroupInfo temp = it.next();
				groupIds+=temp.getGroupId();
				if(it.hasNext()){
					groupIds+=",";
				}
			}
		}
		return groupIds;
	}



    /**
     * 导入通讯簿联系人信息
     */
    public void importAddress()
    {
        PrintWriter writer = null;
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try
        {
            UserInfo userInfo = (UserInfo) getSession().getAttribute("adminUser");

            // 获取导入文件路径 // tomcat根目录
            String catalinaHome = System.getProperty("catalina.home"); 
            String targetDirectory = catalinaHome + "/upload";
            fileUploadName = targetDirectory + File.separator + fileUploadName;

            // 处理excel信息
            ImportAddressDataImpl addressData = new ImportAddressDataImpl();
            ImportBean importBean = ImportExcelService.importExcel(fileUploadName, addressData,
                    targetDirectory, getExportHeadList());

            List<Address> list = addressData.getAddressList();
            addressImpl.saveAddressList(list, groupName, groupId, userInfo);
            writer = new PrintWriter(this.getResponse().getWriter());

            // 设置成功导入的条数
            if (null != list && !list.isEmpty())
            {
                importBean.setSuccessNum(list.size());
            }

            dataMap.put("importData", importBean);
            Gson json = new Gson();
            String jsons = json.toJson(dataMap);
            writer.print(jsons);
            writer.flush();
            writer.close();
        }
        catch (Exception ex)
        {
            LOGGER.error("uploadFileAgain error", ex);
        }
    }

    /**
     * 获取公共组下的联系人信息
     */
    public void getPublicAddressPage()
    {
        try
        {

            // 分页信息

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            addressVo.setCreateUserInfo(user);

            Sort sort = new Sort(new Sort.Order(Direction.ASC, "orderNo"));
            Page<Address> page = addressImpl.findPublicAddressByPage(this.getPageable(sort), addressVo);
            this.ajaxPage(page, analyzeResult(page.getContent()));
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
    }

    /**
     * 功能：过滤个人组联系人
     */
    public void getOwnAddressByName()
    {

        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<Address> addressList = addressImpl.findOwnAddressByName(address.getName(), user);

            // 组装结果信息
            List<Map<String, Object>> mapListOwn = new ArrayList<Map<String, Object>>();
            if (null != addressList && !addressList.isEmpty())
            {
                for (Address tempAddress : addressList)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // ID
                    Integer id = tempAddress.getId();
                    map.put("id", id);

                    // 姓名
                    String name = tempAddress.getName();
                    map.put("name", name);

                    // 手机
                    String familyMobileNo = tempAddress.getFamilyMobileNo();
                    map.put("familyMobileNo", null == familyMobileNo ? "" : familyMobileNo);

                    // 职务
                    String postInfo = tempAddress.getPostInfo();
                    map.put("postInfo", null == postInfo ? "" : postInfo);
                    mapListOwn.add(map);
                }
            }

            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapListOwn);

            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            ajax(jsons);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
    }

    /**
     * 获取个人组下的联系人信息
     */
    public void getWapAddressList()
    {
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<Address> addressList = addressImpl.findAddressByType(addressVo.getAddressGroupId()
                    + "", user, 1);

            // 组装结果信息
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (null != addressList && !addressList.isEmpty())
            {
                for (Address tempAddress : addressList)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // ID
                    Integer id = tempAddress.getId();
                    map.put("id", id);

                    // 姓名
                    String name = tempAddress.getName();
                    map.put("name", name);

                    // 手机
                    String familyMobileNo = tempAddress.getFamilyMobileNo();
                    map.put("familyMobileNo", null == familyMobileNo ? "" : familyMobileNo);

                    // 职务
                    String postInfo = tempAddress.getPostInfo();
                    map.put("postInfo", null == postInfo ? "" : postInfo);
                    mapList.add(map);
                }
            }

            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);

            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            ajax(jsons);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public AddressVo getAddressVo()
    {
        return addressVo;
    }

    public void setAddressVo(AddressVo addressVo)
    {
        this.addressVo = addressVo;
    }

    public String getOperateType()
    {
        return operateType;
    }

    public void setOperateType(String operateType)
    {
        this.operateType = operateType;
    }

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    public Integer[] getAddressId()
    {
        return addressId;
    }

    public void setAddressId(Integer[] addressId)
    {
        this.addressId = addressId;
    }

    public DailySearch getDailySearch()
    {
        return dailySearch;
    }

    public void setDailySearch(DailySearch dailySearch)
    {
        this.dailySearch = dailySearch;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public Integer getPublicSign()
    {
        return publicSign;
    }

    public void setPublicSign(Integer publicSign)
    {
        this.publicSign = publicSign;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getFileUploadName()
    {
        return fileUploadName;
    }

    public void setFileUploadName(String fileUploadName)
    {
        this.fileUploadName = fileUploadName;
    }

    public String getGroupIds()
    {
        return groupIds;
    }

    public void setGroupIds(String groupIds)
    {
        this.groupIds = groupIds;
    }

    public String getRequestSrc()
    {
        return requestSrc;
    }

    public void setRequestSrc(String requestSrc)
    {
        this.requestSrc = requestSrc;
    }

}
