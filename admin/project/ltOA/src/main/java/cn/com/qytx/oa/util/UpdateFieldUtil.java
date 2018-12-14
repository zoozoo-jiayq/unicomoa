package cn.com.qytx.oa.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:对象更新，将新的对象中不为null的属性值赋给旧对象
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-25
 * 修改日期: 2013-03-25
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class UpdateFieldUtil {

    /**
     * 对象更新方法，将新的对象中不为null的属性值赋给旧对象
     *
     * @param objNew          新对象（如：来自前台组合好的对象）
     * @param objOld          旧对象（如：来自数据库中的对象）
     * @param notUpdateFields 不需要更新的属性字段列表
     * @return Object 被更新过的旧对象objOld
     * @throws Exception 异常：反射相关异常
     */
    public static Object update(Object objNew, Object objOld, String... notUpdateFields) throws Exception {
        List<String> notList = new ArrayList<String>();
        if (notUpdateFields != null && notUpdateFields.length != 0) {
            notList = Arrays.asList(notUpdateFields);
        }
        //默认统一屏蔽的字段
        List<String> defaultNotList=new ArrayList<String>();
        //屏蔽Java类文件版本号
        defaultNotList.add("serialVersionUID");
        /**获得继承对象**/
		Field[] superArr = objOld.getClass().getSuperclass().getDeclaredFields();
		Field[] currentArr = objOld.getClass().getDeclaredFields();
		Field[] newArr = null;
		if(superArr!=null){
			newArr = new Field[(superArr.length+currentArr.length)];
		}else{
			newArr = new Field[(currentArr.length)];
		}
		int i =0;
		if(superArr!=null&&superArr.length>0){
			for(Field object : superArr){
				newArr[i]=object;
				i++;
			}
		}
		if(currentArr!=null&&currentArr.length>0){
			for(Field object : currentArr){
				newArr[i]=object;
				i++;
			}
		}
		
		/**获得继承对象 end**/
//        Field[] objOldFields = objOld.getClass().getDeclaredFields();
        for (Field field : newArr) {
            String fieldName = field.getName();
            //检查是否为指定了的不需要更新字段
            if (notList.contains(fieldName)||defaultNotList.contains(fieldName)) {
                continue;
            }
            String methodNamePart = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String getMethodName = "get" + methodNamePart;
            String setMethodName = "set" + methodNamePart;
            //从新对象中执行get方法获取新的值
            Object value = objNew.getClass().getMethod(getMethodName, new Class[]{}).invoke(objNew);
            if (value != null) {
                //调用旧对象的Set方法赋新值
                objOld.getClass().getMethod(setMethodName, new Class[]{value.getClass()}).invoke(objOld, value);
            }
        }
        return objOld;
    }

    private static String upCaseFirstChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 对此类中的update的测试方法
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String... args) throws Exception {

        UserInfo userInfoOld = new UserInfo();

        userInfoOld.setUserName("汤波涛");
        userInfoOld.setLoginName("tbt");

        UserInfo userInfoNew = new UserInfo();
        userInfoNew.setUserName("汤波涛-新名");

        update(userInfoNew, userInfoOld);


    }

}
