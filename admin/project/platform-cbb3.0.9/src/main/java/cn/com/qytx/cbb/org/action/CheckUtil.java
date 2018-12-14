package cn.com.qytx.cbb.org.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查工具类
 * User: 黄普友
 * Date: 12-12-20
 * Time: 下午5:24
 */
public class CheckUtil {

    /**
     *验证email
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.find();
    }

    /**
     * 判断电话或者手机号码是否符合格式
     * 必须为11位或者12位电话
     * @param tel
     * @return
     */
    public static boolean checkTel(String tel)
    {
          if(tel==null||tel.equals(""))
          {
              return false;
          }
           if(tel.length()!=11 && tel.length()!=12)
           {
               return false;
           }
         return isNumeric(tel);
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }
    /**
	  * 验证手机号码
	  * @param mobiles
	  * @return  [0-9]{5,9}
	  */
	 public static boolean isMobileNO(String mobiles){
	  boolean flag = false;
	  try{
		  Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9])|(14[0-9]))\\d{8}$");
	   Matcher m = p.matcher(mobiles);
	   flag = m.matches();
	  }catch(Exception e){
	   flag = false;
	  }
	  return flag;
	 }

}
