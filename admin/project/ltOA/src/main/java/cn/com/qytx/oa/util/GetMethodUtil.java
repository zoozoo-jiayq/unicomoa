/**
 * 
 */
package cn.com.qytx.oa.util;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 彭小东
 * 创建日期: 2015-2-12
 * 修改日期: 2015-2-12
 * 修改列表: 
 */
public class GetMethodUtil
{
    
    public static String getExectionMethod(){
        StackTraceElement[] temp=Thread.currentThread().getStackTrace();
        StackTraceElement a=(StackTraceElement)temp[2];
        return "方法："+a.getMethodName();
    }

}
