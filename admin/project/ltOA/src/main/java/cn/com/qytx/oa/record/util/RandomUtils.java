package cn.com.qytx.oa.record.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 功能:随机字符串生成工具类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-23
 * 修改日期: 2013-03-23
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class RandomUtils {


    /**
     * 使用当前时间产生随机数,本方法使用线程锁
     * @return 时间串，精确到时间的毫秒级
     */
    public synchronized static String createRandomStringFromDate(){

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String result=simpleDateFormat.format(new Date());
        return result + new Random().nextInt(10000);
    }

    /**
     * 获取指定位数的随机字符串
     * @param length 位数
     * @return 随机字符串
     */
    public synchronized static String createRandomString(int length) {
        if (length < 1) {
            return null;
        }
        Random random = new Random();
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz0123456789QWERTYUIOPASDFGHJKLZXCVBNM").toCharArray();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[random.nextInt(71)];
        }
        return new String(randBuffer);
    }
}
