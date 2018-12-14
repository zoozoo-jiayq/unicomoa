/**
 * 
 */
package cn.com.qytx.oa.util;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 彭小东
 * 创建日期: 2014-12-17
 * 修改日期: 2014-12-17
 * 修改列表: 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.tools.ant.taskdefs.Expand;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
*
*/
public class ZipDecompression
{

    /**
     * @param sourceZip
     *            待解压文件路径
     * @param destDir
     *            解压到的路径
     */
    public static void unZip(String sourceZip, String destDir, String encode)
    {
        // 保证文件夹路径最后是"/"或者"\"
        char lastChar = destDir.charAt(destDir.length() - 1);
        if (lastChar != '/' && lastChar != '\\')
        {
            destDir += File.separator;
        }
        Expand e = new Expand();
        e.setSrc(new File(sourceZip));
        e.setOverwrite(false);
        e.setDest(new File(destDir));
        /*
         * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
         * 所以解压缩时要制定编码格式
         */
        e.setEncoding(encode == null ? "GBK" : encode);
        e.execute();
        // String root="";
        // File file=new File(destDir);
        // String[] tempList = file.list();
        // if(tempList.length>0){
        // for(int i=0;i<tempList.length;i++){
        // File temp = new File(destDir+"\\" + tempList[i]);
        // if(temp.isDirectory()){
        // root=destDir+"\\"+temp.getName();
        // }else{
        // temp.delete();
        // }
        //
        // }
        // }
    }

    /**
     * 功能： 文件解析工具
     * @param knowledgefile
     * @param file
     * @return
     */
    public static StringBuilder getKnowledgeContent(String knowledgefile, File file)
    {
        StringBuilder content = new StringBuilder("");
        String ext = knowledgefile.substring(knowledgefile.lastIndexOf('.') + 1);
        if ("txt".equals(ext))
        {
            try
            {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = br.readLine();
                while (lineTxt != null)
                {
                    content.append(lineTxt);
                    lineTxt = br.readLine();
                }
                br.close();
                isr.close();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else if ("htm".equals(ext) || "html".equals(ext))
        {
            try
            {
                String charset = "gbk";
                Document doc = Jsoup.parse(file, charset);
                Elements metas = doc.getElementsByTag("meta");
                for (Element meta : metas)
                {
                    String attr = meta.attr("content");
                    if (attr.indexOf("utf") > 0 || attr.indexOf("UTF") > 0)
                    {
                        charset = "utf-8";
                        break;
                    }
                }
                if ("utf-8".equals(charset))
                {
                    doc = Jsoup.parse(file, charset);
                }
                Element body = doc.getElementsByTag("body").get(0);
                // String title = body.getElementById("articleTitle").html();
                // String content =
                // body.getElementsByClass("document").get(0).html();
                if (body != null)
                {
                    content.append(body.html());
                }
                else
                {
                    content.append("");
                }
            }
            catch (Exception e)
            {

                e.printStackTrace();
            }
        }
        else if ("doc".endsWith(ext))
        {
            try
            {
                FileInputStream fis = new FileInputStream(file);
                WordExtractor doc = new WordExtractor(fis);
                content.append(doc.getText());
                fis.close();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            return new StringBuilder("errorType");
        }
        return content;
    }

    /*
     * public static void main(String[] args) {
     * String sourcePath = "C:/model.zip";
     * String destPath = "C:/test";
     * unZip("F:\\2\\知识库.zip", "F:\\3","GB2312");
     * }
     */
}
