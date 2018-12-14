package cn.com.qytx.oa.publicaddress.action;

import java.util.ArrayList;
import java.util.List;

import jxl.format.Colour;
import cn.com.qytx.cbb.org.util.CellBean;
import cn.com.qytx.cbb.org.util.DealExcelData;
import cn.com.qytx.cbb.org.util.ImportBean;
import cn.com.qytx.oa.publicaddress.domain.Address;
import cn.com.qytx.oa.publicaddress.domain.AddressConst;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import cn.com.qytx.platform.utils.pinyin.Pinyin4jUtil;

/**
 * 文件名:
 * CopyRright(C):北京全亚通信技术有限公司
 * 创建人:贾永强
 * 创建日期:2013-2-28上午9:46:30
 * 修改人:
 * 修改日期:
 * 功能描述:
 * 版本号:
 */
public class ImportAddressDataImpl extends DealExcelData
{

    /**
     * 校验通过的联系人信息
     */
    private List<Address> addressList;

    /*
     * (non-Javadoc)
     * @see
     * cn.com.qytx.demo.importData.DealExcelData#dealOneLine(cn.com.qytx.demo
     * .importData.ImportBean, java.util.List)
     */
    @Override
    public void dealOneLine(ImportBean importBean, List<String> list)
    {
        // 初始化校验是否通过
        boolean isCheck = true;
        List<CellBean> cellBeanList = new ArrayList<CellBean>();
        Address tempAddress = new Address();

        // 空行及设置为错误
        if (null != list && !list.isEmpty() && list.size() > 0)
        {

            for (int i = 0; i < list.size(); i++)
            {
                CellBean cellBean = new CellBean();

                String val = list.get(i);

                // 去掉空格
                if (!StringUtil.isEmpty(val))
                {
                    val = val.trim();
                }
                cellBean.setContent(val);

                // 单元格数据校验 排序号：
                switch (i)
                {
                case 0:
                    // 姓名： 25个字符以内 非空
                    if (!StringUtil.isEmpty(val) && val.length() <= 25)
                    {
                        tempAddress.setName(val);

                        // 根据联系人名字获取首字母
                        String firstLetter = Pinyin4jUtil.getPinYinHeadChar(val.substring(0, 1));
                        tempAddress.setFirstLetter(firstLetter);
                    }
                    else
                    {
                        isCheck = false;

                        // 设置背景色为红色
                        cellBean.setBackgroundColour(Colour.RED);
                    }
                    break;
                case 1:
                    // 性別： 可為空,必須為男或者女
                    if (!StringUtil.isEmpty(val))
                    {
                        if (AddressConst.MEN.equals(val))
                        {
                            tempAddress.setSex(AddressConst.MEN_INT);
                        }
                        else if (AddressConst.WOMEN.equals(val))
                        {
                            tempAddress.setSex(AddressConst.WOMEN_INT);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 2:
                    // 生日：yyyy-MM-dd 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (null != DateTimeUtil.stringToTimestamp(val, CbbConst.DATE_FORMAT_STR))
                        {
                            tempAddress.setBirthday(DateTimeUtil.stringToTimestamp(val,
                            		CbbConst.DATE_FORMAT_STR));
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 3:
                    // 昵称：25字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 25)
                        {
                            tempAddress.setNickname(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 4:
                    // 职务：25字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 25)
                        {
                            tempAddress.setPostInfo(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 5:
                    // 配偶：25字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 25)
                        {
                            tempAddress.setWifeName(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 6:
                    // 子女：25字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 25)
                        {
                            tempAddress.setChildren(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 7:
                    // 单位名称： 200字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 200)
                        {
                            tempAddress.setCompanyName(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 8:
                    // 单位地址： 200字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 200)
                        {
                            tempAddress.setCompanyAddress(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 9:
                    // 单位邮编： 6为数字 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (isZipCode(val))
                        {
                            tempAddress.setCompanyPostCode(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 10:
                    // 工作电话： 7-12位有效数字 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (isPhone(val))
                        {
                            tempAddress.setOfficePhone(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 11:
                    // 工作传真：7-12位有效数字 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (isPhone(val))
                        {
                            tempAddress.setOfficeFax(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 12:
                    // 家庭住址： 200字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 200)
                        {
                            tempAddress.setFamilyAddress(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 13:
                    // 家庭邮编： 6为数字 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (isZipCode(val))
                        {
                            tempAddress.setFamilyPostCode(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 14:
                    // 家庭电话： 7-12位有效数字 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (isPhone(val))
                        {
                            tempAddress.setFamilyPhoneNo(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 15:
                    // 手机：手机号码 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (isMobilePhone(val))
                        {
                            tempAddress.setFamilyMobileNo(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 16:
                    // 电子邮件： 电子邮件格式 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (isEmail(val))
                        {
                            tempAddress.setFamilyEmail(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                case 17:
                    // 备注： 500字符以内 可为空
                    if (!StringUtil.isEmpty(val))
                    {
                        if (val.length() <= 500)
                        {
                            tempAddress.setRemark(val);
                        }
                        else
                        {
                            isCheck = false;

                            // 设置背景色为红色
                            cellBean.setBackgroundColour(Colour.RED);
                        }
                    }
                    break;
                }
                cellBeanList.add(cellBean);
            }
        }
        else
        {
            isCheck = false;
            CellBean cellBean = new CellBean();
            cellBean.setContent("");

            // 设置背景色为红色
            cellBean.setBackgroundColour(Colour.RED);
            cellBeanList.add(cellBean);
        }

        // 校验失败将错误信息保存在错误列表中
        if (!isCheck)
        {
            importBean.getErrorBeans().add(cellBeanList);
        }
        else
        {
            if (null == addressList)
            {
                addressList = new ArrayList<Address>();
            }
            addressList.add(tempAddress);
        }

    }

    /**
     * 功能：是否为数字
     * @param str
     * @return
     */
    public static boolean isNum(String str)
    {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 功能：是否为邮编
     * @param str
     * @return
     */
    public static boolean isZipCode(String str)
    {
        return str.matches("^[0-9]{6}$");
    }

    /**
     * 功能：是否为电话号码
     * @param str
     * @return
     */
    public static boolean isPhone(String str)
    {
        return str.matches("^[0-9]{7,12}$");
    }

    /**
     * 功能：是否为手机号码
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str)
    {
        return str.matches("^(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$");
    }

    /**
     * 功能：是否为邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str)
    {
        return str.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public List<Address> getAddressList()
    {
        return addressList;
    }

    public void setAddressList(List<Address> addressList)
    {
        this.addressList = addressList;
    }
}
