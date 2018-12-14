package cn.com.qytx.cbb.compoundImage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class JoinPicUtil {

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
    	
		BufferedImage newBitmap = null;
		try {
			newBitmap = ImageIO.read(this.getClass().getResourceAsStream("chatGroupbg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public BufferedImage joinPic(List<File> filelist) throws IOException {
		if(filelist==null){
			return null;
		}
		List<BitmapEntity> mEntityList = getBitmapEntitys(filelist.size());
		BufferedImage[] mBitmaps = new BufferedImage[filelist.size()];
		for(int i=0; i<filelist.size(); i++){
			try{
				mBitmaps[i] = ImageIO.read(filelist.get(i));
			}catch(Exception e){
				e.printStackTrace();
				mBitmaps[i] =  ImageIO.read(this.getClass().getResourceAsStream("Chat_ab_man@3x.png"));
			}
		}
		BufferedImage combineBitmap = getCombineBitmaps(mEntityList,mBitmaps);
		return combineBitmap;
	}

}
