package cn.com.qytx.fileserver.bussiness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * 视频文件处理
 * @author jiayongqiang
 *
 */
public class VideoFileProcesser {
	private static  VideoFileProcesser instance = null ;
	
	public static String basePath="";
	
	private Properties properties;
	
	private VideoFileProcesser() {
		super();
		try {
			this.properties = init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Properties init() throws IOException{
		Properties ps = new Properties();
		InputStream is = VideoFileProcesser.class.getResourceAsStream("/application.properties");
		ps.load(is);
		return ps;
	}
	
	public synchronized static VideoFileProcesser getInstanceId(){
		if(instance == null){
			instance = new VideoFileProcesser();
		}
		return instance ;
	}
	
	/**
	 * 获取视频文件的时长
	 * @param sourceFile
	 * @return
	 */
	public String duration(String sourceFile){
		List<String> duration = new ArrayList<String>();
		//String ffmpegroot = basePath+this.properties.getProperty("ffmpeg");
		String ffmpegroot = this.properties.getProperty("ffmpeg");
        duration.add(ffmpegroot);
        duration.add("-i");
        duration.add(sourceFile);
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(duration);
        pb.redirectErrorStream(true);
        InputStream is = null;
        BufferedReader br = null;
        try {
			Process p = pb.start();
			is = p.getInputStream();
			StringBuffer outS = new StringBuffer("");
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = br.readLine();
			while(line!=null){
				outS.append(line);
				line = br.readLine();
			}
			String out = outS.toString();
			int index = out.indexOf("Duration:");
			int end = out.indexOf(",", index);
			if(index>=0){
				String result = out.substring(index+10, end);
				return result;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return null;
	}

	/**
	 * 视频文件截图处理
	 * @param sourceFile 视频源文件
	 * @param width 截图宽度
	 * @param height 截图高度
	 * @param seconds 截图时间点 linux只支持5s以内
	 * @return  截图生成的文件
	 */
	public String cutPic(String sourceFile,int width, int height,int seconds){
        List<String> cutpic = new ArrayList<String>();
        //String ffmpegroot = basePath+this.properties.getProperty("ffmpeg");
        String ffmpegroot = this.properties.getProperty("ffmpeg");
        cutpic.add(ffmpegroot);
        cutpic.add("-loglevel");
        cutpic.add("quiet");
        cutpic.add("-i");
        cutpic.add(sourceFile);
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); 
        cutpic.add(seconds+"");
        cutpic.add("-t"); 
        cutpic.add("0.001");
        cutpic.add("-s");
        cutpic.add(width+"*"+height); 
        String picRoot = this.properties.getProperty("cutpic_dir");
        String fileName = UUID.randomUUID().toString()+".jpg";
        String picFile = picRoot+"/"+fileName;
        cutpic.add(basePath+picFile);
        
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cutpic);
        builder.redirectErrorStream(true);
        try {
			Process process=builder.start();
			int a=process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(InterruptedException ie){
			ie.printStackTrace();
		}
        return picFile;
	}
	
}
