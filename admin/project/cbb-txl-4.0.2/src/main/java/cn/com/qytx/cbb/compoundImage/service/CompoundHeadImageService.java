package cn.com.qytx.cbb.compoundImage.service;

public interface CompoundHeadImageService {

	/**
	 * 根据提供的用户ID集合生成头像集合
	 * @param chatId 群组ID
	 * @param userId 用户集合iD
	 * @return
	 */
	public String compoundHeadImageService(int chatId,String userIds);
}
