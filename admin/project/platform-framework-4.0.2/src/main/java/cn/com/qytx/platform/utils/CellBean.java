package cn.com.qytx.platform.utils;

import jxl.biff.DisplayFormat;
import jxl.format.Colour;

/**
 * 单元格属性 (可扩展)
 * @author Administrator
 */
public class CellBean
{
    /**
     * 单元格内容
     */
    private String content;

    /**
     * 单元格格式化
     */
    private DisplayFormat displayFormat;
    
    /**
     * 单元格背景色
     */
    private Colour backgroundColour;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public DisplayFormat getDisplayFormat()
    {
        return displayFormat;
    }

    public void setDisplayFormat(DisplayFormat displayFormat)
    {
        this.displayFormat = displayFormat;
    }

    public Colour getBackgroundColour()
    {
        return backgroundColour;
    }

    public void setBackgroundColour(Colour backgroundColour)
    {
        this.backgroundColour = backgroundColour;
    }
}
