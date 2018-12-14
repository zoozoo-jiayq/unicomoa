package cn.com.qytx.platform.utils.encrypt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 */
public class MD5 {
	/**
	 * MD5加密
	 * @param
	 *
	 */
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * byte数组转为16进制字符串
	 * @param b byte数组
	 * @return 16进制数组
	 */
	public static String toHexString(byte[] b) {  
        StringBuilder sb = new StringBuilder(b.length * 2);  
        for (int i = 0; i < b.length; i++) {  
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);  
            sb.append(HEX_DIGITS[b[i] & 0x0f]);  
        }  
        return sb.toString();  
	  }
	  
	  //MD5加密
    public String encrypt(String s) {
       try {
           // Create MD5 Hash
           MessageDigest digest = MessageDigest.getInstance("MD5");
           digest.update(s.getBytes());
           byte messageDigest[] = digest.digest();
                       
           return toHexString(messageDigest);
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }      
       return "";
   }

}
