package cn.com.qytx.cbb.compoundImage.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

public class JoinPicRoundUtil {
    private Color colors[] = {new Color(71, 193, 168),
    		new Color(227, 183, 154), new Color(58, 126, 165),
    		new Color(172, 176, 213), new Color(247, 156, 173),
    		new Color(218, 85, 124), new Color(225, 195, 71),
    		new Color(145, 173, 185), new Color(56, 183, 234),
    		new Color(242, 100, 50)};
	
    public  List<BitmapEntity> getBitmapEntitys(int count) {
        List<BitmapEntity> mList = new LinkedList<BitmapEntity>();
        String value = readPropertiesByKey(count+"");
        String[] arr1 = value.split(";");
        int length = arr1.length;
        for (int i = 0; i < length; i++) {
            String content = arr1[i];
            String[] arr2 = content.split(",");
            BitmapEntity entity = null;
            for (int j = 0; j < arr2.length; j++) {
                entity = new BitmapEntity();
                entity.x = Float.valueOf(arr2[0]) ;
                entity.y = Float.valueOf(arr2[1]) ;
                entity.width = Float.valueOf(arr2[2]) ;
                entity.height = Float.valueOf(arr2[3]);
            }
            mList.add(entity);
        }
        return mList;
    }
    
    /**
     * 读取指定key值的value
     * @param count
     * @return
     */
    private String readPropertiesByKey(String key){
    	Properties p = new Properties();
    	InputStream is = this.getClass().getResourceAsStream("compoundImage.properties");
    	try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return p.get(key).toString();
    }
	
    /**
     * 按照指定的坐标合成两个BufferedImage
     * @param first
     * @param second
     * @param x
     * @param y
     * @return
     */
    public static BufferedImage mixtureBitmap(BufferedImage first, BufferedImage second,BitmapEntity entity) {
		if (first == null || second == null ) {
			return null;
		}
		//获取第一个图片的信息
		int width = first.getWidth();
	    int height = first.getHeight();
	    int[] ImageArrayOne = new int[width*height];ImageArrayOne = first.getRGB(0,0,width,height,ImageArrayOne,0,width);
		//获取第二个图片的信息
	    int widthTwo = Math.round(entity.width);
	    int heightTwo = Math.round(entity.width);
	    int[] ImageArrayTwo = new int[Math.round(entity.width*entity.height)];ImageArrayTwo = second.getRGB(0,0,widthTwo,heightTwo,null,0,widthTwo);
		//合成
	    //创建一个新的BufferedImage
	    BufferedImage newBitmap = new BufferedImage(first.getWidth(), first.getHeight(), BufferedImage.TYPE_INT_RGB); 
	    newBitmap.setRGB(0, 0, width, height, ImageArrayOne, 0, width);
	    newBitmap.setRGB(Math.round(entity.x), Math.round(entity.y), widthTwo, heightTwo, ImageArrayTwo, 0, widthTwo);
		return newBitmap;
	}
    
    /**
     * 循环合成指定的BufferedImage
     * @param mEntityList
     * @param bitmaps
     * @return
     */
    public  BufferedImage getCombineBitmaps(List<BitmapEntity> mEntityList,
			BufferedImage... bitmaps) {
    	
//		BufferedImage newBitmap = null;
//			newBitmap = ImageIO.read(this.getClass().getResourceAsStream("ic_background.png"));
		BufferedImage newBitmap = new BufferedImage(200,200, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newBitmap.createGraphics();
		g.setPaint(Color.WHITE);
		g.fill(new Rectangle(200, 200));
		g.dispose();
		
		List<File> tempfilelist = new ArrayList<File>();
		for (int i = 0; i < mEntityList.size(); i++) {
			
			//先压缩
			File nf = CompressImageUtil.compress(Math.round(mEntityList.get(i).width), Math.round(mEntityList.get(i).height), bitmaps[i]);
			if(nf!=null){
				try {
					tempfilelist.add(nf);
					BufferedImage bi = ImageIO.read(nf);
					newBitmap = mixtureBitmap(newBitmap, bi, mEntityList.get(i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(int i=0; i<tempfilelist.size(); i++){
			tempfilelist.get(i).deleteOnExit();
		}
		return newBitmap;
	}
	public BufferedImage joinPic(BufferedImage[] mBitmaps) throws IOException {
		if(mBitmaps==null){
			return null;
		}
		List<BitmapEntity> mEntityList = getBitmapEntitys(mBitmaps.length);
//		BufferedImage[] mBitmaps = new BufferedImage[filelist.size()];
//		for(int i=0; i<filelist.size(); i++){
//			try{
//				mBitmaps[i] = getRoundImg(ImageIO.read(filelist.get(i)),1);
//			}catch(Exception e){
//				e.printStackTrace();
//				mBitmaps[i] =  getRoundImg(ImageIO.read(this.getClass().getResourceAsStream("Chat_ab_man@3x.png")),1);
//			}
//		}
		BufferedImage combineBitmap = getCombineBitmaps(mEntityList,mBitmaps);
		return getRoundImg(combineBitmap,2);
	}
	
	/*
	 * 获得圆形图片
	 */
	public BufferedImage getRoundImg(BufferedImage bi,int type){
		try {
			// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
			BufferedImage bi2 = new BufferedImage(bi.getWidth(),
					bi.getHeight(), BufferedImage.TYPE_INT_RGB);

			Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi.getWidth(),
					bi.getHeight());

			Graphics2D g2 = bi2.createGraphics();
			g2.setBackground(Color.WHITE);
//			if(type==1){
//				g2.setPaint(new Color(221, 221, 221));//#DDDDDD
//			}
			g2.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
			g2.setClip(shape);
			// 使用 setRenderingHint 设置抗锯齿
			g2.drawImage(bi, 0, 0, null);
			g2.dispose();
			
			return bi2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bi;
	}

	/*
	 * 根据用户姓名获得用户对应的彩色头像
	 */
	public BufferedImage getImageByName(String name){
		// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
		try {
			if(StringUtils.isNotEmpty(name)){
				name = name.replace(" ", "");
				if(name.length()>2){
					name = name.substring(name.length()-2,name.length());
				}
				if(StringUtils.isNotEmpty(name)){
					BufferedImage bi = new BufferedImage(200,
							200, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = bi.createGraphics();
					g.setPaint(getColorByName(name));
					g.fill(new Rectangle(200, 200));
					g.setColor(Color.WHITE);
					g.setFont(new Font("微软雅黑", Font.PLAIN, 60));
					g.drawString(name, 36, 120);
					g.dispose();
					return getRoundImg(bi,1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    // 根据名字计算图片背景颜色
    private Color getColorByName(String name) {
        int num = 0;
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                num += (int) name.charAt(i);
            }
        }
        return colors[num % 10];
    }
}
