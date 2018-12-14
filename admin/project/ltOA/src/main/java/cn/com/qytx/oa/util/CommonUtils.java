package cn.com.qytx.oa.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 功能:公用工具类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-10 上午9:53
 * 修改日期: 13-4-10 上午9:53
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class CommonUtils {

    /**
     * Object实例转为字符串，若为null则返回"",否则调用String.valueOf(obj)
     *
     * @param obj Object实例
     * @return 字符串
     */
    public static String obj2Str(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    /**
     * 判断一个字符串为空串
     *
     * @param str 字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断一个字符串为空串
     *
     * @param str 字符串
     * @return true or false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 获取字符串的指定长度
     * @param src 源字符串
     * @param length 指定的长度
     * @return 被截取后的
     */
    public static String getShortStr(String src, int length) {

        if (StringUtils.isEmpty(src)) {
            return "";
        }
        if (src.length() <= length) {
            return src;
        } else {
            return src.substring(0, length)+"...";
        }
    }
}
