package cn.com.qytx.cbb.compoundImage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class Test {
	public static void main(String[] args) {
		try {
			JoinPicRoundUtil roundUtil = new JoinPicRoundUtil();
			List<BufferedImage> list = new ArrayList<BufferedImage>();
			list.add(roundUtil.getImageByName("张永峰"));
			list.add(roundUtil.getImageByName("徐长江"));
//			list.add(roundUtil.getImageByName("吴洲"));
			list.add(roundUtil.getImageByName("李华伟"));
			BufferedImage bi = new JoinPicRoundUtil().joinPic(list.toArray(new BufferedImage[]{}));
			if(bi!=null){
				File f = new File("e:/upload/test.png");
				if(!f.exists()){
					f.mkdirs();
					f.createNewFile();
				}
				ImageIO.write(bi, "png", f);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
