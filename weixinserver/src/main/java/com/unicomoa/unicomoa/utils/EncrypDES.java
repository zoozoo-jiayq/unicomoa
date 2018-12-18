package com.unicomoa.unicomoa.utils;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 DES加密介绍
      DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现
 。
 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class EncrypDES {
	
	private static String password = "www.xiaoxinai.cn";
	
    /**
     * 加密
     * @param datasource byte[]
     * @param password String
     * @return byte[]
     */
    public static  String encrypt(byte[] datasource) {            
        try{
	        SecureRandom random = new SecureRandom();
	        DESKeySpec desKey = new DESKeySpec(password.getBytes());
	        //创建一个密匙工厂，然后用它把DESKeySpec转换成
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey securekey = keyFactory.generateSecret(desKey);
	        //Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance("DES");
	        //用密匙初始化Cipher对象,ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
	        //现在，获取数据并加密
	        //正式执行加密操作
	        return Base64.getEncoder().encodeToString(cipher.doFinal(datasource));
        }catch(Exception e) {
        	
        }
        return null;
}
    /**
     * 解密
     * @param src byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static String decrypt(String src) throws Exception {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");//返回实现指定转换的 Cipher 对象
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return new String(cipher.doFinal(Base64.getDecoder().decode(src)));
    }
    
//    public static String bytes2hex(byte[] bytes){
//		BigInteger bigInteger = new BigInteger(1, bytes);
//		return bigInteger.toString(16);
//	}
//    
//    public static byte[] hextoBytes(String str) {
//        if(str == null || str.trim().equals("")) {
//            return new byte[0];
//        }
//
//        byte[] bytes = new byte[str.length() / 2];
//        for(int i = 0; i < str.length() / 2; i++) {
//            String subStr = str.substring(i * 2, i * 2 + 2);
//            bytes[i] = (byte) Integer.parseInt(subStr, 16);
//        }
//
//        return bytes;
//    }

}