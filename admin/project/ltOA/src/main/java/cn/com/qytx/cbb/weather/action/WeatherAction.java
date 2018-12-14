package cn.com.qytx.cbb.weather.action;

import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 获取天气预报Action
 * 版本: 1.0
 * 开发人员：黄普友
 * 创建日期: 2013-3-20
 * 修改日期：2013-3-21
 * 修改列表：
 */
public class WeatherAction extends BaseActionSupport {
    //天气预报action
    private String url;

    /**
     * 获取天气预报Action
     * @return
     */
    public String getWeather()
    {
        try{
           String result= Weather.getWeather(url);
            if(result!=null)
            {
                ajax(result);
            }
        }
        catch (Exception ex)
        {
             ex.printStackTrace();
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
