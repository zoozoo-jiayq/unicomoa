package cn.com.qytx.cbb.notify.utils;

public class WapContentUtils {
	/**
	 * 处理图片百分比
	 * @param content
	 * @return
	 */
	public static String dealContentWithImage(String content){
		for(int i = 0 ; i< content.length()-5;i++){
			if(content.subSequence(i,i+5).equals("<img ")){
				for(int j= i;j< content.length()-2;j++){
					if(content.subSequence(j,j+2).equals("/>")){
						content = content.replaceAll(content.substring(i,j+2),generNewImageContent(content.substring(i,j+2)));
						break;
					}
				}
			}
		}
		return content;
	}
	/**
	 * 替换单个image图片样式
	 * @param content
	 * @return
	 */
	private static String generNewImageContent(String content){
		String newImgContent = content;
		if(content.contains("style=\"")){
			newImgContent = content.replaceAll("style=\"","style=\"width:100%;max-width:100%;");
		}else{
			newImgContent = content.replaceAll("<img ","<img style=\"width:100%;max-width:100%;\" ");
		}
		return content.replaceAll(content,newImgContent);
	}
}
