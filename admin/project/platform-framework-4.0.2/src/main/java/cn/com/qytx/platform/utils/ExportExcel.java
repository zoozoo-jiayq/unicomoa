package cn.com.qytx.platform.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出工具类
 * @author Administrator
 */
public class ExportExcel
{
    /**
     * 数据信息
     */
    private List<Map<String, Object>> mapList;

    /**
     * 列名
     */
    private List<String> headList;

    /**
     * 列字段key
     */
    private List<String> keyList;

    /**
     * 自定义样式数据
     */
    private List<List<CellBean>> cellData;

    /**
     * 输出流
     */
    private OutputStream os = null;

    /**
     * WritableWorkbook
     */
    private WritableWorkbook workbook = null;

    /**
     * 构造函数
     * @param outputStream 输出流
     * @param headList 列名
     * @param mapList 内容信息
     * @param keyList 列字段
     */
    public ExportExcel(OutputStream outputStream, List<String> headList,
            List<Map<String, Object>> mapList, List<String> keyList)
    {
        this.os = outputStream;
        this.headList = headList;
        this.mapList = mapList;
        this.keyList = keyList;
    }

    /**
     * 有参构造方法
     * @param outputStream 输出流
     * @param dataMapList 数据的map形式列表
     * @param headKeyValueMap 列头和对应值的Key的Map集合，例如:{['name':'姓名'],['gender':'性别']}
     */
    public ExportExcel(OutputStream outputStream,List<Map<String,Object>> dataMapList,Map<String,String> headKeyValueMap){

        this.os=outputStream;
        this.mapList=dataMapList;
        this.headList= new LinkedList<String>();
        this.keyList=new LinkedList<String>();
        
        Set<Map.Entry<String,String>> set = headKeyValueMap.entrySet();
        Iterator<Map.Entry<String,String>> ite = set.iterator();
        while(ite.hasNext())
        {
            Map.Entry<String,String> entry = ite.next();
            keyList.add(entry.getKey());
            headList.add(entry.getValue());
        }
//        for(String key:headKeyValueMap.keySet()){
//            keyList.add(key);
//            headList.add(headKeyValueMap.get(key));
//        }
    }

    /**
     * 自定义单元格样式构造函数
     * @param response HttpServletResponse
     * @param headList 列名
     * @param cellData 自定义样式数据
     */
    public ExportExcel(OutputStream outputStream, List<String> headList,
            List<List<CellBean>> cellData)
    {
        this.os = outputStream;
        this.headList = headList;
        this.cellData = cellData;
    }

    /**
     * 导出
     */
    public void export()
    {
        try
        {
            workbook = Workbook.createWorkbook(os);

            // 设置sheet页
            WritableSheet ws = workbook.createSheet("sheet 1", 0);
            writeSheetHead(ws);
            writeSheetBody(ws);
            workbook.write();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            this.close();
        }
    }

    /**
     * 导出
     */
    public void exportWithSheetName(String sheetName)
    {
        try
        {
            workbook = Workbook.createWorkbook(os);

            // 设置sheet页
            WritableSheet ws = workbook.createSheet(sheetName, 0);
            writeSheetHead(ws);
            writeSheetBody(ws);
            workbook.write();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            this.close();
        }
    }

    
    /**
     * 写标题
     * @param ws ws
     */
    private void writeSheetHead(WritableSheet ws)
    {
        try
        {
            Label lb = null;
            if (null != headList && !headList.isEmpty())
            {
                for (int i = 0; i < headList.size(); i++)
                {
                    lb = new Label(i, 0, headList.get(i));

                    // 设置表格内容信息
                    ws.addCell(lb);

                    // 设置表格宽度
                    ws.setColumnView(i, 15);// 0
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 写入导出文件体
     * @param ws WritableSheet
     * @throws WriteException
     * @throws RowsExceededException
     */
    private void writeSheetBody(WritableSheet ws) throws RowsExceededException, WriteException
    {
        if (null != mapList)
        {
            writeDefaultBody(ws);
        }
        else if (null != cellData)
        {
            writeUserDefinedBody(ws);
        }
    }

    /**
     * 写默认格式单元格
     * @param ws WritableSheet
     */
    private void writeDefaultBody(WritableSheet ws)
    {
        try
        {
            // 设置单元格格式为text
            WritableCellFormat format = new WritableCellFormat(NumberFormats.TEXT);

            // 自动换行
            format.setWrap(true);
            Label lb = null;
            for (int i = 0; i < mapList.size(); i++)
            {
                Map<String, Object> map = mapList.get(i);
                if (null != keyList && !keyList.isEmpty())
                {
                    for (int j = 0; j < keyList.size(); j++)
                    {
                        lb = new Label(j, (i + 1), (null != map.get(keyList.get(j)) ? map.get(
                                keyList.get(j)).toString() : ""), format);
                        ws.addCell(lb);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 输入用户自定单元格样式
     * @param ws WritableSheet
     * @throws WriteException
     * @throws RowsExceededException
     */
    private void writeUserDefinedBody(WritableSheet ws) throws RowsExceededException, WriteException
    {
        Label lb = null;
        for (int i = 0; i < cellData.size(); i++)
        {
            List<CellBean> cellList = cellData.get(i);
            if (null != cellList && !cellList.isEmpty())
            {
                CellBean tempCellBean = null;
                for (int j = 0; j < cellList.size(); j++)
                {
                    tempCellBean = cellList.get(j);
                    if (null != tempCellBean)
                    {
                        lb = new Label(j, (i + 1), cellList.get(j).getContent(),
                                getWritableCellFormat(tempCellBean));
                        ws.addCell(lb);
                    }
                }
            }
        }
    }

    private WritableCellFormat getWritableCellFormat(CellBean cellBean)
    {
        WritableCellFormat format = new WritableCellFormat();
        if (null != cellBean)
        {
            if (null != cellBean.getDisplayFormat())
            {
                // 设置单元格格式
                format = new WritableCellFormat(cellBean.getDisplayFormat());
            }

            // 设置单元格背景色
            Colour backgroundColour = cellBean.getBackgroundColour();
            if (null != backgroundColour)
            {
                try
                {
                    format.setBackground(backgroundColour);
                }
                catch (WriteException e)
                {
                    e.printStackTrace();
                }
            }

        }
        return format;
    }

    /**
     * 关闭工作流
     */
    private void close()
    {
        if (null != workbook)
        {
            try
            {
                workbook.close();
            }
            catch (WriteException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (null != os)
        {
            try
            {
                os.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<Map<String, Object>> getMapList()
    {
        return mapList;
    }

    public void setMapList(List<Map<String, Object>> mapList)
    {
        this.mapList = mapList;
    }

    public List<String> getHeadList()
    {
        return headList;
    }

    public void setHeadList(List<String> headList)
    {
        this.headList = headList;
    }

    public List<String> getKeyList()
    {
        return keyList;
    }

    public void setKeyList(List<String> keyList)
    {
        this.keyList = keyList;
    }
}
