package cn.com.qytx.cbb.compoundImage.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import cn.com.qytx.cbb.compoundImage.service.CompoundHeadImageService;
import cn.com.qytx.cbb.compoundImage.service.CompressImageUtil;
import cn.com.qytx.cbb.compoundImage.service.JoinPicUtil;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

@Service
public class CompoundHeadImageServiceImpl implements CompoundHeadImageService {

	@Resource
	private IUser userService;
	@Resource
	private FilePathConfig filePathConfig;
	@Resource
	private JoinPicUtil joinPicUtil;
	
	@Override
	public String compoundHeadImageService(int chatId, String userIds) {
		// TODO Auto-generated method stub
		List<UserInfo> us = userService.findUsersByIds(userIds);
		List<File> fs = new ArrayList<File>();
		for(int i=0; i<us.size(); i++){
			String photo = us.get(i).getPhoto();
			photo = filePathConfig.getFileUploadPath()+"/upload/"+photo;
			File t = new File(photo);
			if(t.exists()){
				fs.add(t);
			}else{
				String file = "";
				if(us.get(i).getSex()==null || us.get(i).getSex()==1){
						file = this.getClass().getResource("../Chat_ab_man@3x.png").getFile();
				}else{
						file = this.getClass().getResource("../Chat_ab_women@3x.png").getFile();
				}
				fs.add(new File(file));
			}
		}
		if(fs.size()>0 && fs.size()<=9){
			try {
				BufferedImage bi = joinPicUtil.joinPic(fs);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "";
	}

}
