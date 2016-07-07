package com.lhfeiyu.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil{

	public static String uploadFile(HttpServletRequest request,MultipartFile file,String fileBaseName,String basePath) throws Exception{
		String filePath = null;
		if(null != file && file.getSize() > 0){
			String realPath = request.getSession().getServletContext().getRealPath(basePath);
			String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
	        //System.out.println(fileSuffix);
			//String path = "/file/user/";
			String timeMills = String.valueOf(Calendar.getInstance().getTimeInMillis());
	        String fileName = fileBaseName+timeMills+fileSuffix;
	        filePath = basePath + fileName;
	        String fileWriteRealPath = realPath+"/"+ fileName;
	        System.out.println("--"+fileWriteRealPath);
	        byte[] bytes = file.getBytes();
	        FileOutputStream fos = new FileOutputStream(fileWriteRealPath);
	        fos.write(bytes);
	        fos.close();
		}
		return filePath;
	}
	
    public static String uploadRepairImg(String path,Integer pkey, MultipartFile file) throws IOException, SQLException {
        String imgPath = "";
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        String photoPath = path+"\\"+ pkey + fileSuffix;
        byte[] bytes = file.getBytes();
        FileOutputStream fos = new FileOutputStream(photoPath);
        fos.write(bytes);
        fos.close();
        imgPath = "/resources/userFile/img/repair/"+pkey+fileSuffix;
        return imgPath;
    }

}
