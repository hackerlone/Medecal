package com.lhfeiyu.util.dust;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class kuaiDi_MSG {
	
	public static String ResultMessage(String key,String com,String nu,Boolean flag){
		String content = "";
		URL url = null;
		try{
			//key=8d325a25f8f7efb0
			if(flag){
				url= new URL("http://api.kuaidi100.com/api?id="+key+"&com="+com+"&nu="+nu+"&show=0&muti=1&order=desc");
			}else{
				url= new URL("http://www.kuaidi100.com/applyurl?key="+key+"&com="+com+"&nu="+nu+"");
			}
			URLConnection con=url.openConnection();
			 con.setAllowUserInteraction(false);
			   InputStream urlStream = url.openStream();
	            byte b[] = new byte[10000];
	            int numRead = urlStream.read(b);
	            content = new String(b, 0, numRead);
	            while (numRead != -1)
	            {
	                numRead = urlStream.read(b);
	                if (numRead != -1)
	                {
	                    String newContent = new String(b, 0, numRead, "UTF-8");
	                    content += newContent;
	                }
	            }
			   urlStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return content;
	}
}
