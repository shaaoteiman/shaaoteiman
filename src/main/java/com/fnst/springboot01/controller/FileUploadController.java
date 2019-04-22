package com.fnst.springboot01.controller;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@GetMapping("/file")
	public String file(){
		return "file";
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file")MultipartFile file){
		try {
			if(!file.isEmpty()){
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file.getOriginalFilename()));
				out.write(file.getBytes());
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "上传成功";
	}
}
