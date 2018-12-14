package cn.com.qytx.oa.publicaddress.domain;

/**
 * 通讯簿常量类
 * @author lhw
 */
public class AddressConst
{

    /**
     * 删除联系人组
     */
    public static final int DELETE_TYPE = 1;

    /**
     * 清空联系人组
     */
    public static final int CLEAR_TYPE = 2;

    /**
     * 默认分组值
     */
    public static final int DEFAULT_GROUP = 0;
    
    /**
     * 公共通讯簿默认分组值 -1表示查询所有组
     */
    public static final int DEFAULT_PUBLIC_GROUP = -2;

    /**
     * 默认分组显示值
     */
    public static final String DEFAULT_GROUP_NAME = "默认";

    /**
     * 女
     */
    public static final String WOMEN = "女";

    /**
     * 女
     */
    public static final int WOMEN_INT = 0;
    
    /**
     * 男
     */
    public static final String MEN = "男";
    
    /**
     * 男
     */
    public static final int MEN_INT = 1;
    
    /**
     * 个人通讯簿组类型
     */
    public static final int GROUPTYPE_PERSONAL = 1;
    
    /**
     * 公共通讯簿组类型
     */
    public static final int GROUPTYPE_PUBLIC = 2;   
    
    /**
     * 公共通讯簿组类型
     */
    public static final String PRIVDATA_MODULE_ADDRESS = "通讯簿公布范围";   
    
    /**
     * 权限范围,全体人员
     */
    public static final String ALL_USER_NAME = "全体人员";
    
    /**
     * 权限范围,全体部门
     */
    public static final String ALL_GROUP_NAME = "全体部门";
    
    /**
     * 权限范围,全体角色
     */
    public static final String ALL_ROLE_NAME = "全体角色";

}
