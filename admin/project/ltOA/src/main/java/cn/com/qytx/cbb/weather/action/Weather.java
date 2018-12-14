package cn.com.qytx.cbb.weather.action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 获取天气预报,主要是根据中央气象局提供的接口
 * <br/>User: 黄普友
 * <br/>Date: 13-3-19
 * <br/>Time: 下午5:56
 */
public class Weather {

    /**
     * 获取天气的内容
     * @param weatherUrl 中央气象局提供的天气预报接口
     * @param 返回天气预报的内容
     */
    public static String getWeather(String weatherUrl){
        String currentLine = "";
        String strReturn = "";
        URL url = null;
        HttpURLConnection conn = null;
        InputStream in = null;
        BufferedReader buff = null;
        try {
            url = new URL(weatherUrl);
            //打开地址链接
            conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            //接收数据
            in = conn.getInputStream();
            //如有乱码注意编码方式，如：UTF-8
            buff = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            while((currentLine = buff.readLine()) != null) {
                strReturn += currentLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	if(in!=null){
            		in.close();
            	}
            	if(buff!=null){
            		buff.close();
            	}
            } catch (IOException e) {
                return "8EF0000";
            }
        }
        return strReturn;
    }

}
