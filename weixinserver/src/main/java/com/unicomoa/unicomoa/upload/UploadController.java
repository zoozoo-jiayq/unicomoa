package com.unicomoa.unicomoa.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unicomoa.unicomoa.base.BaseController;
import com.unicomoa.unicomoa.base.GlobalConfig;
import com.unicomoa.unicomoa.utils.DateUtils;

@RestController
@CrossOrigin
public class UploadController extends BaseController {

	@Resource
	private GlobalConfig config;
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public Object upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		String uploadDir = config.getUploadDir();
		String originFilename = file.getOriginalFilename();
		String path = String.format("%s/%s/%s/",System.getProperty("user.dir"),uploadDir, DateUtils.date2Str(new Date(System.currentTimeMillis()), "yyyyMMdd"));
		File p = new File(path);
		p.mkdirs();
		File f = new File(p,originFilename);
		f.createNewFile();
		file.transferTo(f);
		return SUCCESS(String.format("/%s/%s",DateUtils.date2Str(new Date(System.currentTimeMillis()), "yyyyMMdd"),originFilename));
	}
	
	@RequestMapping(value="/view/{date}/{filename}")
	public void view(@PathVariable(name="date") String date,@PathVariable(name="filename")String filename) throws IOException {
		response.setContentType("image/*");
		File f = new File(System.getProperty("user.dir")+"/"+config.getUploadDir()+"/"+date+"/"+filename);
		InputStream is = new FileInputStream(f);
		byte[] bs = new byte[1024];
		int l = is.read(bs);
		while(l>0) {
			response.getOutputStream().write(bs, 0, l);
			l = is.read(bs, 0, 1024);
		}
		response.getOutputStream().flush();
	}
}
