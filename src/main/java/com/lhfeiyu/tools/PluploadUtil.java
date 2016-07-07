package com.lhfeiyu.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Plupload是一个上传插件。
 * 上传原理为单个文件依次发送至服务器.
 * 上传打文件时可以将其碎片化上传。但是一般情况下，不会这样做，
 * 所以这里更多的是处理普通文件的批量上传,这里主要处理文件上传。
 * http://www.cnblogs.com/shujinjie/articles/4293111.html
 */
public class PluploadUtil {
    //private static final int BUF_SIZE = 2 * 1024;
	//private static final int BUF_SIZE = 1024;
	private static final int BUF_SIZE = 512;
    /**上传失败响应的成功状态码*/
    //public static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
    /**上传失败响应的失败状态码*/
    //public static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
    
    /**
     * 用于Plupload插件的文件上传,自动生成唯一的文件保存名
     * @param plupload - 存放上传所需参数的bean
     * @param dir - 保存目标文件目录
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String upload(Plupload plupload,File dir,File tempDir,String random) throws IllegalStateException, IOException {
        //生成唯一的文件名
    	String name = plupload.getName().replaceAll("[/'\"\\;,:-<>]", "");
        String filename = "" + random+"__" +  name;
        upload(plupload, dir,tempDir, filename,random);
        return filename;
    }
    
    /**
     * 用于Plupload插件的文件上传
     * @param plupload - 存放上传所需参数的bean
     * @param dir - 保存目标文件目录
     * @param filename - 保存的文件名
     * @throws IllegalStateException
     * @throws IOException
     */
    public static void upload(Plupload plupload, File saveDir,File tempDir, String filename,String random) throws IllegalStateException, IOException {
        int chunks = plupload.getChunks();    //获取总的碎片数
        int chunk = plupload.getChunk();    //获取当前碎片(从0开始计数)
        //System.out.println(plupload.getMultipartFile() + "----------");//null
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();    
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
        if(map != null) {
            if (!tempDir.exists()) tempDir.mkdirs();    //如果目标文件夹不存在则创建新的文件夹
            Iterator<String> iter = map.keySet().iterator();//事实上迭代器中只存在一个值,所以只需要返回一个值即可
            while(iter.hasNext()) {
                String str = iter.next();
                List<MultipartFile> fileList =  map.get(str);
                for(MultipartFile multipartFile : fileList) {
                    plupload.setMultipartFile(multipartFile);//因为只存在一个值,所以最后返回的既是第一个也是最后一个值
                    //File targetFile = new File(dir.getPath()+ "/" + filename);//创建新目标文件
                    File saveFile = new File(saveDir.getPath()+ "/" + filename);//创建新目标文件
                    if (chunks > 1) {//当chunks>1则说明当前传的文件为一块碎片，需要合并
                        File tempChunkFile = new File(tempDir.getPath()+ "/"+random+"__"+ multipartFile.getName());//需要创建临时文件名，最后再更改名称
                        saveUploadFile(multipartFile.getInputStream(), tempChunkFile, chunk == 0 ? false : true);//如果chunk==0,则代表第一块碎片,不需要合并
                        if (chunks - chunk == 1) {//上传并合并完成，则将临时名称更改为指定名称
                        	//tempChunkFile.renameTo(targetFile);
                            tempChunkFile.renameTo(saveFile);
                        }
                    } else {
                    	//multipartFile.transferTo(targetFile);//否则直接将文件内容拷贝至新文件
                        multipartFile.transferTo(saveFile);//否则直接将文件内容拷贝至新文件
                    }
                }
            }
        }
    }
    
    /**
     * 保存上传文件，兼合并功能
     */
    private static void saveUploadFile(InputStream input, File targetFile, boolean append) throws IOException {
        OutputStream out = null;
        try {
            if (targetFile.exists() && append) {
                out = new BufferedOutputStream(new FileOutputStream(targetFile, true), BUF_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(targetFile), BUF_SIZE);
            }
            byte[] buffer = new byte[BUF_SIZE];
            int len = 0;
            //写入文件
            while ((len = input.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
        	System.out.println("保存上传文件出现异常_"+new Date().toString());
            throw e;
        } finally {
            if (null != input) {//关闭输入输出流
                try {input.close();} 
                catch (IOException e) {e.printStackTrace();}
            }
            if (null != out) {
                try {out.close();} 
                catch (IOException e) {e.printStackTrace();}
            }
        }
    }
    
    /**
     * 判断是否全部上传完成,碎片需合并后才返回真。
     */
    public static boolean isUploadFinish(Plupload plupload) {
        //return (plupload.getChunks() - plupload.getChunk() == 1);
    	if(plupload.getChunks() == 0){
    		return true;
    	}else{
    		return (plupload.getChunks() - plupload.getChunk() == 1);
    	}
    }
    
    /**
     * 将文件复制到新的地址，并删除原文件。
     */
    public static String cutFile(String basePath,String oldFilePath,String newFilePath){
    	//String realPath = request.getSession().getServletContext().getRealPath("/");
    	String oldPath = basePath + oldFilePath;
    	String newPath = basePath + newFilePath;
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        oldFile.renameTo(newFile);
        oldFile.delete();//删除原文件
        
        return getFileFormatedSize(newFile);
    }
    
    public static String getFileFormatedSize(File file){
    	long fileS = file.length();
    	DecimalFormat df = new DecimalFormat("#.00");
    	String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) +"G";
        }
        return fileSizeString;
    }
    
    /**
     * 将文件复制到新的地址，并删除原文件。
     */
    public static void cutFiles(String basePath,String[] oldFilePathAry,String[] newFilePathAry){
    	String oldFilePath = "";
    	String newFilePath = "";
    	for(int i = 0;i<oldFilePathAry.length;i++){
    		oldFilePath = oldFilePathAry[i];
    		newFilePath = newFilePathAry[i];
    		if(null != oldFilePath && oldFilePath.length()>1){
    			cutFile(basePath, oldFilePath, newFilePath);
    		}
    	}
    }
    
}

/**
 * try {
	    //上传文件
	    filename= PluploadUtil.upload(plupload, dirFile);
	    //判断文件是否上传成功（被分成块的文件是否全部上传完成）
	    if (PluploadUtil.isUploadFinish(plupload)) {
	        System.out.println(plupload.getName() + "----");
	        System.out.println(savePath+"&&"+filename + "=====");
	        //限制图片大小不能小于300*300
	        BufferedImage bi = ImageIO.read(new FileInputStream(new File(savePath+filename)));
	        System.out.println("width="+bi.getWidth());
	        System.out.println("height="+bi.getHeight());
	        if(bi.getWidth()<200||bi.getHeight()<200){
	            msg.put("fail", "True");
	            msg.put("error","图片尺寸不符合要求，大于300*300");
	        }else{
	            //将图片等比压缩为300*300大小的图片
	            IconCompressUtil.compressImg(new File(savePath+filename), 300, new File(savePath+filename));    
	            msg.put("success", "True");
	            msg.put("filename", filename);
	            msg.put("filepath", savePath+"temp"+filename);
	            System.out.println("头像保存位置="+savePath+"temp"+filename);
	            msg.put("width", bi.getWidth()+"");
	            msg.put("height", bi.getHeight()+"");
	        }
	    }else{
	        msg.put("fail", "True");
	    }
	    
	} catch (IllegalStateException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
 */
