package cn.com.qytx.cbb.compoundImage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;

import cn.com.qytx.cbb.compoundImage.service.CompoundHeadImageService;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 提供压缩头像服务
 * @author jiayongqiang
 *
 */
public class CompoundHeadImageAction extends BaseActionSupport {

	private int chatId;
	private String userIds;
	@Resource
	private CompoundHeadImageService compoundHeadImageService;
	@Resource
	private FilePathConfig filePathConfig;
	
	/**
	 * 压缩头像服务
	 * @return
	 * @throws IOException 
	 */
	public String compoundHeadImage() throws IOException{
		String fileName = compoundHeadImageService.compoundHeadImageService(chatId, userIds);
		if(fileName!=null && !fileName.equals("")){
			File f = new File(filePathConfig.getFileUploadPath()+"/upload/"+fileName);
			FileInputStream fs = new FileInputStream(f);
			OutputStream os = getResponse().getOutputStream();
			byte[] buff = new byte[1024];
			int i=0;
			while ((i = fs.read(buff))>0){
				os.write(buff, 0, i);
				buff = new byte[1024];
				os.flush();
			}
			fs.close();
		}
		return null;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
}
