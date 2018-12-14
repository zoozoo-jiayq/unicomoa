package cn.com.qytx.cbb.compoundImage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;


/**
 * 压缩图片
 * @author jiayongqiang
 *
 */
public class CompressImageUtil {
	
	public static File compress(int width,int height,BufferedImage oldImage){
		File temp = null;
		try {
			temp = File.createTempFile(UUID.randomUUID().toString(), ".png");
			Thumbnails.of(oldImage).size(width, height).keepAspectRatio(false).toFile(temp);
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

}

