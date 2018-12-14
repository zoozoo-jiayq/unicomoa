package cn.com.qytx.cbb.file.service.impl.xwpf.converter;



import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlink;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.qytx.cbb.file.service.IConvert;
import cn.com.qytx.cbb.file.service.impl.util.HtmlFile;
import cn.com.qytx.cbb.util.StringUtil;


public class DocxConverter implements IConvert {
	
	/**represents the html page*/
	private HtmlDocumentFacade facade;
	
	/** html page container*/
	private Element page;
	
	private Element window;
	
	private XWPFDocument docx ;
	
	private String imgFolderPath;
	
	private  final String IMG_FOLDER = "images".concat(File.separator);
	
	private final String imgPath;

	
	public DocxConverter( String filePath, String output,final String imagesPhysicalPath,String imageUrl) throws IOException, InvalidFormatException, ParserConfigurationException{
		
		StringUtil.getFilePath("");
		{// create image folder 
			int pos = output.lastIndexOf(".");
			 imgFolderPath = output.substring(0, pos).concat(File.separator).concat(IMG_FOLDER);
			
			File folder = new File(imgFolderPath);
			if(!folder.canRead()){
			    boolean result = folder.mkdirs();
			    if (!result){
			        folder = null;   
			    }
			}
			folder = null;	
		}
		File foldercc = new File(output);
		String imgFolder=HtmlFile.getFileName(foldercc.getName(),".");
		foldercc=null;

		//imgPath = StringUtil.getFileName(filePath, false).concat(File.separator).concat(IMG_FOLDER);
		imgPath=imageUrl+imgFolder+"/images/";
	}

	public  void convert(String path, String output,final String imagesPhysicalPath,String imageUrl,String title) throws Exception{
		
		//DocxConverter converter = new DocxConverter( path, output,imagesPhysicalPath,imageUrl);
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		OPCPackage container = OPCPackage.open(path);
		docx = new XWPFDocument(container);
		
//		styles = docx.getStyles();
		
		this.facade = new HtmlDocumentFacade(document);
		facade.setTitle(title);
		window = facade.createBlock();
		page = facade.createBlock();
		
		facade.getBody().appendChild(window);
		window.appendChild(page);
		
		addStyle(facade.getBody(), "background: #aaa;");
		
//		set page framework style
		addStyleClass(window, "window", "background:#fff; margin:0 auto;width:210mm;");
		
		addStyleClass(page,"page",  " background:#fff; margin:1.0in 1.0in;");
		
		// TODO  process header
		// process paragraphs
		List<IBodyElement> elements = docx.getBodyElements();
		for(IBodyElement element : elements){
			
			if(element instanceof XWPFParagraph){
				processParagraph((XWPFParagraph)element, page);
				
			}else if(element instanceof XWPFTable){
				processTable((XWPFTable)element, page);
			}
		}
		facade.updateStylesheet();
		saveAsHtml(output, facade.getDocument());
		
	}
	/**
	 * process table..
	 * @param t 
	 */
	private void processTable(XWPFTable t, Element container) {
		// TODO Auto-generated method stub
		Element p = facade.createParagraph();
		Element table = facade.createTable();
		
		List<XWPFTableRow> rows = t.getRows();
		for(XWPFTableRow row : rows){
			processRow(row, table);
		}
		p.appendChild(table);
		table.setAttribute("border", "1");
		table.setAttribute("cellspacing", "0");
		table.setAttribute("cellpadding", "3");
		table.setAttribute("style","border-collapse: collapse;");
		container.appendChild(p);
	}
	
	private void processRow(XWPFTableRow row, Element table) {
		Element tr = facade.createTableRow();
		List<XWPFTableCell> cells = row.getTableCells();
		for(XWPFTableCell cell : cells){
			processCell(cell, tr);
		}
		// resolve row style.
		{
			StringBuilder sb = new StringBuilder();
			if(row.getHeight() != 0)
				sb.append("height:").append(row.getHeight()/1440.0).append("in");
			addStyle(tr, sb.toString());
		}
		table.appendChild(tr);
	}

	private void processCell(XWPFTableCell cell, Element tr) {
		Element td = facade.createTableCell();
		facade.createTableColumn();
		
		List<XWPFParagraph> paragraphs = cell.getParagraphs();
		for(XWPFParagraph p : paragraphs){
			try {
				processTableParagraph(p,  td);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//resolve cell styles
		StringBuilder sb = new StringBuilder();
		{
			CTTc c = cell.getCTTc();
			if (null!=c) {
				sb.append("width:").append(c.getTcPr().getTcW().getW().doubleValue()/1440.0).append("in");
			}
			if(cell.getColor() != null)
				sb.append(";background-color:#").append(cell.getColor());
			// resovel text alignment
			if(c!=null&&c.getPArray(0)!=null&&c.getPArray(0).getPPr()!=null){
				CTJc jc = c.getPArray(0).getPPr().getJc();
				if(jc != null){
					switch(jc.getVal().intValue()){
					case STJc.INT_RIGHT	 : sb.append(";text-align:").append("right");	break;
					case STJc.INT_CENTER : sb.append(";text-align:").append("center");	break;
					default:break;
					}
				}
			}
			try{
				// if the verticalAlignment set
				XWPFTableCell.XWPFVertAlign valign = cell.getVerticalAlignment();
				switch(valign){
				case BOTTOM : sb.append(";vertical-align:").append("bottom");	break;
				case CENTER : sb.append(";vertical-align:").append("middle");	break;
				case TOP	: sb.append(";vertical-align:").append("top");		break;
				default		: sb.append(";vertical-align:").append("top");		break;
				}
			}catch(NullPointerException ex){
				sb.append(";vertical-align:").append("top");
			}
		}
		addStyle(td, sb.toString());
		tr.appendChild(td);
	}
	
	
	private void processTableParagraph(XWPFParagraph paragraph, Element page) throws IOException{
		processParagraphMulti(paragraph,  page, true);
	}
	
	private void processParagraph(XWPFParagraph paragraph,  Element page) throws IOException{
		processParagraphMulti(paragraph,  page, false);
	}

	/**
	 * process paragraph
	 * @param paragraph
	 * @param output 
	 * @param page use to contain the paragraph content.
	 * @param isTableParagraph 
	 * @throws IOException when icture extract error.
	 */
	private void processParagraphMulti(XWPFParagraph paragraph,  Element page, boolean isTableParagraph) throws IOException{
		
		// p.type  : list , picture , title, empty
		Element ctner = null;
		if(paragraph.getText().length() == 0  && paragraph.getRuns().size() == 0 ){
			// empty
			ctner = facade.createParagraph();
			ctner.setTextContent("\u00a0");
			page.appendChild(ctner);
			return;
		}else if(paragraph.getStyle() != null && !"-1".equals(paragraph.getStyle())){
			// title & list
			String typeStr = paragraph.getStyle();
			Integer type ;
			try{
				// title
				type = Integer.parseInt(typeStr);
				switch(type){
					case 1:	ctner = createElement("h1"); 	break;
					case 2: ctner = createElement("h2"); 	break;
					case 3: ctner = createElement("h3"); 	break;
					case 4: ctner = createElement("h4");	break;
					case 5: ctner = createElement("h5");	break;
					default: ctner = facade.createParagraph();
				}
			}catch(NumberFormatException ex){
				// list
				ctner = createElement("li");
				if(isTableParagraph)
					ctner.setAttribute("style", "list-style:none");
			}
		}
		
		// normal && picture
		// when normal case, the 'ctner' may not initialized.
		if(ctner == null){
			if(isTableParagraph)
				ctner = facade.createBlock();
			else
				ctner = facade.createParagraph();
			// TODO  set text-indent right time.
//			addStyle(ctner,"text-indent:2em;");
		}
		
		List<XWPFRun> runs = paragraph.getRuns();
		for(XWPFRun r : runs){
			processRun(ctner, r);
		}
		// resolve paragraph styles;
		if(paragraph.getAlignment() != ParagraphAlignment.LEFT){
			switch(paragraph.getAlignment()){
			case CENTER:	addStyle(ctner, "text-align:center;");	break;
			case RIGHT:		addStyle(ctner, "text-align:right"); 	break;
//			case DISTRIBUTE:addStyle(ctner, ""); 	break; //两边分布
			default:break;
			}
		}
		page.appendChild(ctner);

	}
	
	private void processRun(Element container, XWPFRun r) throws IOException {
		
		List<XWPFPicture> pics = r.getEmbeddedPictures();
		if(!pics.isEmpty()){
			processImage( container, pics);
		} 
		
		Element runCtner = null; 
		switch(r.getSubscript()){
			case SUBSCRIPT: runCtner = createElement("sub");break;
			case SUPERSCRIPT: runCtner = createElement("sup");break;
			default : runCtner = facade.getDocument().createElement("span");
		}
				
		StringBuilder sb = new StringBuilder();
		if(r.getColor()!= null)
			sb.append("color:#").append(r.getColor());
		if(r.getFontSize() != -1)
			sb.append(";font-size:").append(r.getFontSize()).append("pt");
		if(r.getFontFamily() != null)
			sb.append(";font-family:'").append(r.getFontFamily()).append("'");
		if(r.isBold())
			sb.append(";font-weight:").append(800);
		if(r.isItalic())
			sb.append(";font-style:").append("italic ");
		if(r.getUnderline() != UnderlinePatterns.NONE){
			switch(r.getUnderline()){
				case DOUBLE:	sb.append(";border-bottom:").append("4px double"); break;
				case DOTTED: 	sb.append(";border-bottom:").append("1px dotted"); break;
				case DASH:		sb.append(";border-bottom:").append("1px dashed");	break;
				default: 	sb.append(";text-decoration:").append("underline"); break;
			}
		}
		
//		r.getClass().isInstance(XWPFHyperlinkRun);
		try{
			
			XWPFHyperlinkRun hlRun = (XWPFHyperlinkRun)r;
			XWPFHyperlink hyperlink = hlRun.getHyperlink(docx);
			Element a  = facade.createHyperlink(hyperlink.getURL());
			a.setAttribute("name", hyperlink.getId());
			a.setTextContent(hlRun.getText(0));
			runCtner.appendChild(a);
			if(sb.length() != 0)
				addStyleClass(a, "a", sb.toString());
		}catch(Exception ex){
//			ex.printStackTrace();
			runCtner.setTextContent(r.getText(0));
			if(sb.length() != 0)
				addStyleClass(runCtner, runCtner.getTagName(), sb.toString());
		}
		
		
		container.appendChild(runCtner);
	}

	/**
	 * extract pictures form pics save to output & add it to the container

	 * @param pics
	 * @throws IOException
	 */
	private void processImage(Element wrap, List<XWPFPicture> pics) throws IOException {
		

		
		
//		Element wrapDiv = facade.createBlock();
//		wrap.setAttribute("class", "img_wrap");
		for(XWPFPicture pic : pics){
			
			XWPFPictureData data = pic.getPictureData();
			
			ByteArrayInputStream is = new ByteArrayInputStream(data.getData());
			BufferedImage image = ImageIO.read(is);
			// TODO image type convert   like .tif etc.
			String imgFullPath = imgFolderPath.concat(data.getFileName());
			{// extract picture
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(imgFullPath));
					fos.write(data.getData());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally{
					if(fos != null) fos.close();
				}
			}
			{// add picture to html page
				//TODO get image alignment , width , style etc.
				
				Element img = facade.createImage(imgPath.concat(data.getFileName()));
				if(!StringUtil.isEmpty(pic.getDescription())){
					img.setAttribute("Title", pic.getDescription());
				}
				if(image != null && image.getWidth() > 600){
					img.setAttribute("width", "600px");
				}
				img.setAttribute("align", "center");
				wrap.appendChild(img);
				
			}
		}
	}

	/**
	 * @param output
	 * @param document
	 * @throws IOException
	 * @throws TransformerException
	 */
	private void saveAsHtml(String output,Document document) throws IOException, TransformerException{
		
//		check path
		File folder = new File(StringUtil.getFilePath(output));	
		if(!folder.canRead()){
		    boolean result = folder.mkdirs(); 
		    if (!result){
		        return;
		    }
		}
			
		folder = null;
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        // TODO set encoding from a command argument
        Properties pro = System.getProperties();
        String encode = pro.getProperty("file.encoding");
        if(encode.toUpperCase().equals("UTF-8")){
        	serializer.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
        }else{
        	serializer.setOutputProperty( OutputKeys.ENCODING, "GBK" );
        }
        serializer.setOutputProperty( OutputKeys.INDENT, "yes" );
        serializer.setOutputProperty( OutputKeys.METHOD, "html" );
        serializer.setOutputProperty(OutputKeys.STANDALONE , "yes"); 
        serializer.setOutputProperty( OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD HTML 4.01 Transitional//EN"); //
        serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.w3.org/TR/html4/strict.dtd");
        
        serializer.transform( domSource, streamResult );
        String content= new String(out.toByteArray());
        HtmlFile.writeFile(content, output);
        out.close();
	}
    
	private void addStyleClass(Element element, String className, String style){
		facade.addStyleClass(element, className, style);
	}
	
	/**
	 * add the given style to element directly. <tag style="..."
	 * @param element
	 * @param style
	 */
	private void addStyle(Element element, String style){
		String exist = element.getAttribute("style");
		if(StringUtil.isEmpty(exist)){
			element.setAttribute("style", style);
		}else{
			if(exist.endsWith(";"))
				element.setAttribute("style", exist.concat(style));
			else
				element.setAttribute("style", exist.concat(";").concat(style));
		}
	}
	
	private Element createElement(String tagName){
		return facade.getDocument().createElement(tagName);
	}
}
