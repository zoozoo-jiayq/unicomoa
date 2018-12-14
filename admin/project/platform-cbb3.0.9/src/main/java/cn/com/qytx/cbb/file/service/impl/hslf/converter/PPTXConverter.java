package cn.com.qytx.cbb.file.service.impl.hslf.converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import cn.com.qytx.cbb.file.service.IConvert;
import cn.com.qytx.cbb.file.service.impl.util.HtmlFile;

/**
 * User:黄普友
 * Date: 13-7-8
 * Time: 下午11:34
 */
public class PPTXConverter implements IConvert {
    /**
     * 转换成html
     * @param fileName 要转换的文件
     * @param outPutFile 目标文件
     * @param imagesPhysicalPath  图片的物理路径
     * @param imageUrl        //图片的html地址
     * @throws java.io.IOException
     */
    public  void convert(String fileName, String outPutFile,final String imagesPhysicalPath,String imageUrl,String title ) throws Exception
    {
    
        FileInputStream is = new FileInputStream(fileName);
        XMLSlideShow ppt = new XMLSlideShow(is);
        is.close();

        double zoom = 1; // magnify it by 2
        AffineTransform at = new AffineTransform();
        at.setToScale(zoom, zoom);

        Dimension pgsize = ppt.getPageSize();
        StringBuffer sb=new StringBuffer();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        sb.append("<head>\n");
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />\n");
        sb.append("<title>"+ title+"</title>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        XSLFSlide[] slide = ppt.getSlides();

		for (int i = 0; i < slide.length; i++) {
			for (XSLFShape shape : slide[i].getShapes()) {
				if (shape instanceof XSLFTextShape) {
					XSLFTextShape txtshape = (XSLFTextShape) shape;
					for (XSLFTextParagraph p : txtshape.getTextParagraphs()) {
						for (XSLFTextRun textRun : p.getTextRuns()) {
							String fontFamily = textRun.getFontFamily();
							textRun.setFontFamily("宋体");
						}
					}
				}
			}
            BufferedImage img = new BufferedImage((int)Math.ceil(pgsize.width*zoom), (int)Math.ceil(pgsize.height*zoom), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();

            graphics.setTransform(at);
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            slide[i].draw(graphics);
            
            String imageFileName= UUID.randomUUID().toString()+".png";
            sb.append("<img src='"+imageUrl+"/"+imageFileName+"' alt='图片'/>\n");
            FileOutputStream out = new FileOutputStream(imagesPhysicalPath + imageFileName);
            javax.imageio.ImageIO.write(img, "png", out);
            out.close();
            
        }
        sb.append("</body>\n");
        sb.append("</html>\n");
        HtmlFile.writeFile(sb.toString(),outPutFile);
    }
	public static boolean isTrueType(String fontName) {
		String[] trueType = new String[] { "Tahoma", "Times New Roman","Calibri", "Arial" };
		for (String type : trueType) {
			if (type.equals(fontName))
				return true;
		}
		return false;
	}
}
