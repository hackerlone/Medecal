package com.lhfeiyu.action.common;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.Picture;
import com.lhfeiyu.service.PictureService;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Plupload;
import com.lhfeiyu.tools.PluploadUtil;

@Controller
public class UploadAction {
	
	@Autowired
	private PictureService pictureService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/uploadPage")
	public ModelAndView upload_html() {
		return new ModelAndView("/upload/upload");
	}
	
	private static String[] folders = {"file/default/","file/common/","file/contract/","file/avatar/",
									   "file/shop/","file/inst/","file/forum/","file/article/","file/sys/",
									   "file/advert/","file/download/"};

	/**上传处理方法*/
    @ResponseBody
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public JSONObject upload(Plupload plupload,@RequestParam("random")String random,
    		@RequestParam(required=false,value="folderType")Integer folderType,
    		HttpServletRequest request, HttpServletResponse response) {
    	//String operate="";
    	String FileDir="";
    	String TempFileDir="";
        //输出信息
        //System.out.println(plupload.getChunk() + "===" + plupload.getName() + "---" + plupload.getChunks());
        //System.out.println("operate="+operate);
        String filename =null;
        plupload.setRequest(request);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        //String ymd = sdf.format(new Date());
        
        // 文件保存目录路径
        String savePath = plupload.getRequest().getSession().getServletContext().getRealPath("/") + FileDir;
        // 临时文件目录
        String tempPath = plupload.getRequest().getSession().getServletContext().getRealPath("/") + TempFileDir;
        JSONObject json = new JSONObject();
        String folder = folders[0];
        if(null != folderType && folderType >= 0 && folderType < 6){
        	folder = folders[folderType];
        }
        savePath += folder;
        tempPath += "temp/file/";
        File dirSaveFile = new File(savePath);// 创建文件夹
        if (!dirSaveFile.exists()){
        	dirSaveFile.mkdirs();
        }
        File dirTempFile = new File(tempPath);// 创建临时文件夹
        if (!dirTempFile.exists()){
            dirTempFile.mkdirs();
        }
        //System.out.println(dirFile.getPath());
        try {
            filename= PluploadUtil.upload(plupload, dirSaveFile,dirTempFile,random);//上传文件
            if (PluploadUtil.isUploadFinish(plupload)) {//判断文件是否上传成功（被分成块的文件是否全部上传完成）
            	//System.out.println(plupload.getName() + "----");
    	        //System.out.println(savePath+"&&"+filename + "=====");
    	        String filePath = "/"+folder+filename;
     	        Integer fileDBId = insertPicture(filePath);//将上传的图片添加到数据库，并返回ID
    	        json.put("status", "success");
    	        json.put("filePath", filePath);
    	        json.put("fileDBId", fileDBId);
            }else{
            	json.put("msg", "上传失败");
            }
        } catch (Exception e) {
        	Result.catchError(e, logger, "LH_ERROR_获取评论分类信息出现异常_", json);
        }
        return json;
    }
    
    /** 将上传的图片添加到数据库，并返回ID */
    private Integer insertPicture(String filePath){
    	Date date = new Date();
    	Picture pic = new Picture();
    	int idx1 = filePath.indexOf("__");
    	int idx2 = filePath.lastIndexOf(".");
    	String title = "";
    	if(idx1 > 1 && idx2 > 1)title = filePath.substring(idx1+2,idx2);
    	String serial = CommonGenerator.getSerialByDate("p");
    	pic.setPicPath(filePath);
    	pic.setSerial(serial);
    	pic.setHits(0);
    	pic.setScans(0);
    	pic.setTitle(title);
    	pic.setMainStatus(1);
    	pic.setCreatedAt(date);
    	pictureService.insert(pic);
    	return pic.getId();
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
