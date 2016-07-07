package com.lhfeiyu.util.dust;

import java.util.TimerTask;


/**
 * 数据备份业务逻辑
 * @author lxy
 *
 */
public class TimeData extends TimerTask{
	/*@Override
	public void run() {
		 //FileSystemView fsv = FileSystemView.getFileSystemView();
	      
         // 列出所有windows 磁盘
          File[] fs = File.listRoots();
          File file = null;
          // 显示磁盘卷标
//              System.out.println(fsv.getSystemDisplayName(fs[i]));
        	  if(fs.length>1){
            	   file = new File("d:\\CRMTWO数据库备份");
        		  //判断文件夹是否存在,如果不存在则创建文件夹
        		  if (!file.exists()) {
        			  file.mkdir();
        		  }
        		  String str="mysqldump -uroot -proot crm_test>d:\\CRMTWO数据库备份/crm(%date:~0,4%年%date:~5,2%月%date:~8,2%日%time:~0,2%时%time:~3,2%分%time:~6,2%秒).sql";
        	       	 try{
        	       	 Runtime rt=Runtime.getRuntime();
        	       	 rt.exec("cmd /c"+str);
        	       	 
        	        File files[] = file.listFiles();
            	       if(files.length>14){
            	    	   for(int j=0;j<files.length;j++){  
            	    		   long createTime = (new Date(files[j].lastModified()).getTime()+1000*60*60*24*7);
            	    		   long nowTimes = (new Date().getTime());
            	    		   if(createTime<nowTimes){
            	    			   File dflie = new File("D:\\CRMTWO数据库备份"+"\\"+files[j].getName());
	            	    		   dflie.delete();
            	    		   }
            	    	   }
            	       }
        	       	 }catch(IOException e){
        	       	 e.printStackTrace();
        	       	 System.out.println("备份失败！");
        	       	 }
              
              }else{
           	   file = new File("c:\\CRMTWO数据库备份");
        		  //判断文件夹是否存在,如果不存在则创建文件夹
        		  if (!file.exists()) {
        			  file.mkdir();
        		  }
        		  String str="mysqldump -uroot -proot zero>c:\\CRMTWO数据库备份/crm(%date:~0,4%年%date:~5,2%月%date:~8,2%日%time:~0,2%时%time:~3,2%分%time:~6,2%秒).sql";
        	       	 try{
        	       	 Runtime rt=Runtime.getRuntime();
        	       	 rt.exec("cmd /c"+str);
        	       	 
        	        File files[] = file.listFiles();
            	       if(files.length>14){
            	    	   for(int j=0;j<files.length;j++){  
            	    		   long createTime = (new Date(files[j].lastModified()).getTime()+1000*60*60*24*7);
            	    		   long nowTimes = (new Date().getTime());
            	    		   if(createTime<nowTimes){
            	    			   File dflie = new File("c:\\CRMTWO数据库备份"+"\\"+files[j].getName());
	            	    		   dflie.delete();
            	    		   }
            	    	   }
            	       }
        	       	 }catch(IOException e){
        	       	 e.printStackTrace();
        	       	 System.out.println("备份失败！");
        	       	 }
            }
	}*/
	

	@Override
	public void run(){
			
	}


}