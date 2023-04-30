package com.instagram.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinarySevice {

	Cloudinary cloudinary;
	
	@Value("${cloudinary.name}")
	private String cloud_name;
	
	@Value("${cloudinary.key}")
	private String api_key;
	
	@Value("${cloudinary.secret}")
	private String api_secret;
	
	private Map<String, String> valuesMap = new HashMap<>();
	
	
	public CloudinarySevice() {
		valuesMap.put("cloud_name", cloud_name);
		valuesMap.put("api_key", api_key);
		valuesMap.put("api_secret", api_secret);
		cloudinary = new Cloudinary(valuesMap);
	}

	@SuppressWarnings("rawtypes")
	public Map upload(MultipartFile multipartFile) throws IOException {
		File file = convert(multipartFile);
		Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Map delete (String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		return result;
	}
	
	private File convert(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(multipartFile.getBytes());
		fo.close();
		return file;
	}
}
