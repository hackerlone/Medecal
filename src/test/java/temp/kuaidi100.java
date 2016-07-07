package temp;

import com.lhfeiyu.util.dust.kuaiDi_MSG;




public class kuaidi100
{
	public static void main(String[] agrs)
	{
		
			//URL url= new URL("http://api.kuaidi100.com/api?id=8d325a25f8f7efb0&com=huitongkuaidi&nu=280226059652&show=0&muti=1&order=desc");
			String key = "8d325a25f8f7efb0";
			//String com = "yuantong";
			//String nu = "880702299910068328";
			String com = "huitongkuaidi";
			String nu = "280226059652";
			//URL url= new URL("http://www.kuaidi100.com/applyurl?key="+key+"&com="+com+"&nu="+nu+"");
			/*id= 8d325a25f8f7efb0
			 * */
		String content = kuaiDi_MSG.ResultMessage(key, com, nu, true);	
		System.out.println(content);
	}
}
