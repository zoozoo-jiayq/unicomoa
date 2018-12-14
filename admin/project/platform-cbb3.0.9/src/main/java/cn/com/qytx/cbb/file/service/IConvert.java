package cn.com.qytx.cbb.file.service;

import java.io.IOException;

/**
 * User:黄普友
 * Date: 13-7-8
 * Time: 下午10:46
 */
public interface IConvert {

    /**
     * 转换成html
     * @param fileName 要转换的文件
     * @param outPutFile 目标文件
     * @param imagesPhysicalPath  图片的物理路径
     * @param imageUrl        //图片的html地址
     * @throws IOException
     */
    public  void convert(String fileName, String outPutFile,final String imagesPhysicalPath,String imageUrl,String title) throws Exception;
}
