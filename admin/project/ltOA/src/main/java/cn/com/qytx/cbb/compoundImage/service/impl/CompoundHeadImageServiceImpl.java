package cn.com.qytx.cbb.compoundImage.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import cn.com.qytx.cbb.compoundImage.service.CompoundHeadImageService;
import cn.com.qytx.cbb.compoundImage.service.JoinPicRoundUtil;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

@Service
public class CompoundHeadImageServiceImpl implements CompoundHeadImageService {

	@Resource
	private IUser userService;
	@Resource
	private FilePathConfig filePathConfig;
	
	@Override
	public String compoundHeadImageService(int chatId, String userIds) {
		List<UserInfo> us = userService.findUsersByIds(userIds);
		if(us!=null && us.size()>0){
			//声明图片构造函数
			JoinPicRoundUtil roundUtil = new JoinPicRoundUtil();
			//封装图片数组
			List<BufferedImage> biList = new ArrayList<BufferedImage>();
			for(int i=0; i<us.size(); i++){
				if(biList.size()<4){
					String photo = us.get(i).getPhoto();
					photo = filePathConfig.getFileUploadPath()+"/upload/"+photo;
					File t = new File(photo);
					if(t.exists()){
						try {
							biList.add(roundUtil.getRoundImg(ImageIO.read(t),1));
						} catch (IOException e) {
							e.printStackTrace();
							BufferedImage bi = roundUtil.getImageByName(us.get(i).getUserName());
							if(bi!=null){
								biList.add(bi);
							}
						}
					}else{
						BufferedImage bi = roundUtil.getImageByName(us.get(i).getUserName());
						if(bi!=null){
							biList.add(bi);
						}
					}
				}
			}
			//进行图片截取和合成
			if(biList.size()>0){
				try {
					BufferedImage bi = roundUtil.joinPic(biList.toArray(new BufferedImage[]{}));
					if(bi!=null){
						String biname = "chatGroup/"+chatId+".png";
						File f = new File(filePathConfig.getFileUploadPath()+"/upload/"+biname);
						if(!f.exists()){
							f.mkdirs();
							f.createNewFile();
						}
						ImageIO.write(bi, "png", f);
						return biname;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}

}
