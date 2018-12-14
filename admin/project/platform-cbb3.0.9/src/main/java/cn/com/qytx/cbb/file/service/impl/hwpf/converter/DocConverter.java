package cn.com.qytx.cbb.file.service.impl.hwpf.converter;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import cn.com.qytx.cbb.file.service.IConvert;
import cn.com.qytx.cbb.file.service.impl.util.HtmlFile;

public class DocConverter implements IConvert
{
    /**
     *
     * @param fileName 要转换的文件
     * @param outPutFile 目标文件
     * @param imagesPhysicalPath  图片的物理路径
     * @param imageUrl        //图片的html地址 word中如果存在图片，
     *                        由于word图片处理中 生成的图片不好命名，为了避免文件重复，这里路径可以设置为不重复
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public  void convert(String fileName, String outPutFile,final String imagesPhysicalPath,final String imageUrl,String title)throws TransformerException, IOException,
            ParserConfigurationException
    {
        try
        {
            if(!(new File(imagesPhysicalPath).isDirectory()))
            {
                new File(imagesPhysicalPath).mkdir();
            }
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }
        Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        HtmlDocumentFacade facade = new HtmlDocumentFacade(document);
        facade.setTitle(title);
        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(facade);
        PicturesManager picturesManager=new PicturesManager() {
            public String savePicture(byte[] content,
                                      PictureType pictureType, String suggestedName,
                                      float widthInches, float heightInches ) {
                return imageUrl+"/"+suggestedName;
            }
        };
        wordToHtmlConverter.setPicturesManager( picturesManager);
        wordToHtmlConverter.processDocument(wordDocument);
        //save pictures
        List pics=wordDocument.getPicturesTable().getAllPictures();
        if(pics!=null){
            for(int i=0;i<pics.size();i++){
                Picture pic = (Picture)pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(imagesPhysicalPath + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        
        Document htmlDocument = wordToHtmlConverter.getDocument();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        Properties pro = System.getProperties();
        String encode = pro.getProperty("file.encoding");
        if(encode.toUpperCase().equals("UTF-8")){
        	serializer.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
        }else{
        	serializer.setOutputProperty( OutputKeys.ENCODING, "GBK" );
        }
//        serializer.setOutputProperty(OutputKeys.ENCODING, "GBK");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        String content= new String(out.toByteArray());
        HtmlFile.writeFile(content, outPutFile);
        out.close();
    }
}
