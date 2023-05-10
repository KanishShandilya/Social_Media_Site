package com.kanish.ksite.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



@Component
public class FileHelperUpload {
	public final String UPLOAD_DIR="D:\\JAVA_PROJECT\\backend\\ksite\\src\\main\\resources\\static\\image";
	//public final String UPLOAD_DIR=new ClassPathResource("static/image/").getFile().getAbsolutePath();
	
	public FileHelperUpload() throws IOException{
		
	}
	
	public void deleteFile(String fileName) {

        File file= new File(UPLOAD_DIR+File.separator+fileName);
 
        file.delete();
        return;
	}
	public String uploadFile(MultipartFile file) {
		String res="failed";
		
		try {
			InputStream is=file.getInputStream();
			byte[] data=new byte[is.available()];
			is.read(data);
			String s=file.getOriginalFilename();
			String fileName=generateUUID()+"."+s.substring(s.lastIndexOf(".") + 1);
			FileOutputStream fos=new FileOutputStream(UPLOAD_DIR+File.separator+fileName);
			fos.write(data);
			fos.flush();
			fos.close();
			res=fileName;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private String generateUUID() {
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	
	public byte[] downloadFile(String fileName) throws IOException {
		if(fileName==null) {
			fileName="empty_profile.jpg";
		}
		String filePath="";
		try {
        filePath=UPLOAD_DIR+File.separator+fileName;
        }
		catch(Exception e) {
			filePath=UPLOAD_DIR+File.separator+"empty_profile.jpg";
		}
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
