package cn.com.qytx.cbb.file.service.impl.pdf.convert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import cn.com.qytx.cbb.file.service.IConvert;
import cn.com.qytx.cbb.file.service.impl.util.HtmlFile;

/**
 * pdf转换成html User:黄普友 Date: 13-7-8 Time: 下午9:17
 */
public class PdfConverter implements IConvert {
	/**
	 * 转换成html
	 * 
	 * @param fileName  PDF文件
	 * @param outPutFile 目标文件
	 * @param imagesPhysicalPath 图片的物理路径
	 * @param imageUrl 图片的html地址
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void convert(String fileName, String outPutFile,final String imagesPhysicalPath, String imageUrl,String title) throws IOException {
		PDDocument doc = PDDocument.load(fileName);
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
		sb.append("<head>\n");
		sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />\n");
		sb.append("<title>"+title+"</title>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		List pages = doc.getDocumentCatalog().getAllPages();
		for (int i = 0; i < pages.size(); i++) {
			try {
				PDPage page = (PDPage) pages.get(i);
				BufferedImage image = page.convertToImage();
				Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
				ImageWriter writer = (ImageWriter) iter.next();
				String imageFileName = UUID.randomUUID().toString() + ".jpg";
				sb.append("<img src='" + imageUrl + "/" + imageFileName + "?_clientType=wap' alt='图片'/>\n");
				File outFile = new File(imagesPhysicalPath + "/"+ imageFileName);
				FileOutputStream out = new FileOutputStream(outFile);
				ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
				writer.setOutput(outImage);
				writer.write(new IIOImage(image, null, null));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		sb.append("</body>\n");
		sb.append("</html>\n");
		doc.close();
		HtmlFile.writeFile(sb.toString(), outPutFile);
	}
}
