package cn.sunflyer.zfang;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

import cn.sunflyer.zfang.obj.DataVal;
import cn.sunflyer.zfang.obj.UserInfo;

/**
 * 教务系统登录和页面获取类
 * @author CrazyChen
 * */
public class EduSystem {
	
	/**
	 * 获取教务系统的页面内容
	 * @param addr 请求页面名称
	 * @param param get参数
	 * @param postdata post的参数
	 * @param i 已经登陆的用户信息，请使用 <CODE>login</CODE>获取
	 * @return 返回获取到的网页数据，GBK编码
	 * @see EduSystem.login()
	 * 
	 * */
	public static String getJwxtContent(String addr , String param ,String postdata,UserInfo i){
		if(postdata != null){
			try {
		
				HttpURLConnection pHuc = getConnection(addr , param , i);
				
				pHuc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				pHuc.setRequestProperty("Content-Length", postdata.length() + "");
				pHuc.setRequestProperty("Referer", i.addr + addr + i.b() + param);
				pHuc.setRequestProperty("Origin", "http://jwxt.i.cqut.edu.cn");
				pHuc.setRequestProperty("Host", "jwxt.i.cqut.edu.cn");
				SHttpUtil.setBasicReq(pHuc, null);
				pHuc.setRequestMethod("POST");
				pHuc.setDoOutput(true);
				pHuc.getOutputStream().write(postdata.getBytes());

				String pRes = getContent(pHuc);
				
				return pRes;
			} catch ( IOException e) {
				
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static final String TABLE_SIGNATURE = "<tr class=\"datelisthead\">";
	
	/**
	 * 抓取页面返回的表格
	 * @return 如果数据中包含表格，则返回一个字符串数组，每个字符串表示一行，其中包含有< td >< /td >等标签
	 * @see EduSystem.getRowData
	 * */
	public static String[] getTable(String content){
		if(content.contains("<tr class=\"datelisthead\">")){
			String pData = EduSystem.catchKey(content , TABLE_SIGNATURE , "</table>");
			String[] pClassList = pData.replace("<tr>","").replace("<tr class=\"alt\">","").split("</tr>");
			return pClassList;
		}
		return null;
	}
	
	/**格式化行数据，返回数组*/
	public static String[] getRowData(String row){
		if(row != null){
			return row.trim().replace("<td></td>","<td> </td>").replace("&nbsp;", "无").replace("<td>", "").split("</td>");
		}
		return null;
	}
	
	/**
	 * 抓取关键字
	 * */
	public static String catchKey(String origin,String start,String end){
		int pStartPath = origin.indexOf(start);
		if(pStartPath < 0) return null;
		return origin.substring(pStartPath + start.length() , origin.indexOf(end , pStartPath));
	}
	
	/**
	 * GBK编码网页
	 * */
	public static String getContent(HttpURLConnection h){
		try {
			return SHttpUtil.getInputStreamContent(h.getInputStream(), "GB2312");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		return null;
	}
	
	/**开启一个连接
	 * @param path 网页名称
	 * @param param 参数
	 * @param i 用户信息
	 * */
	public static HttpURLConnection getConnection(String path , String param ,UserInfo i){
		if( i != null){
			HttpURLConnection pHuc = SHttpUtil.getConnection(i.addr + path + "?" + i.b() + param);
			pHuc.setRequestProperty("Referer", i.addr + "/xs_main.aspx?xh=" + i.no);
			SHttpUtil.setBasicReq(pHuc, null);
			return pHuc;
		}
		return null;
	}
	
	/**
	 * 登录到教务系统，并取得用户session对象
	 * @param no 用户名/学号
	 * @param key 密码
	 * @return 如果登陆成功，返回一个UserInfo对象，否则返回null
	 * */
	public static UserInfo login(String no,String key){
		
		DataVal pDv = getViewState();
		
		try {
			String pFormData = "__VIEWSTATE=" + URLEncoder.encode(pDv.getData().toString(), "UTF-8").replace("+", "%20") +
			"&txtUserName="+ no +
			"&TextBox2="+ key +
			"&txtSecretCode=&RadioButtonList1=%D1%A7%C9%FA&Button1=&lbLanguage=&hidPdrs=&hidsc=";
			
			HttpURLConnection pHuc = SHttpUtil.getConnection(pDv.getName().toString());
			SHttpUtil.setBasicReq(pHuc, null);
			pHuc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			pHuc.setRequestProperty("Host", "jwxt.i.cqut.edu.cn");
			pHuc.setRequestMethod("POST");
			pHuc.setDoOutput(true);
			pHuc.getOutputStream().write(pFormData.getBytes());
			
			String pResponseData = SHttpUtil.getInputStreamContent(pHuc.getInputStream(),"GBK");
			
			if(pResponseData.indexOf(no) >= 0){
				//登陆成功
				int pNamePath = pResponseData.indexOf("<span id=\"xhxm\">");
				if(pNamePath < 0){
					System.out.println("登陆失败！请检查用户名和密码，然后重试");
					return null;
				}
				
				String pName = pResponseData.substring(pNamePath + "<span id=\"xhxm\">".length() ,pResponseData.indexOf("同学</span>",pNamePath));
				
				String pAddr = pHuc.getURL().toString().substring(0 , pHuc.getURL().toString().indexOf(")/")) + ")/";
				
				return new UserInfo(pAddr,pName,no);
			}
		
			//抓名字，后面要用
			
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("登陆失败！检查输入的用户名和密码，然后重试");
		return null;
	}
	
	/**
	 * 从教务系统获取 - viewstate
	 * @return DataVal对象，其中Name属性对应URL地址，Data对应ViewState
	 * */
	public static DataVal getViewState(){
		HttpURLConnection pHuc = SHttpUtil.getConnection("jwxt.i.cqut.edu.cn");
		
		String pageData = SHttpUtil.getHtmlContent(pHuc);
		int path = pageData.indexOf("name=\"__VIEWSTATE\"");
		String view = pageData.substring(path , pageData.indexOf("/>", path));
		String pViewState = view.substring(view.indexOf("value=\"") + "value=\"".length() , view.length() - 2);
		return new DataVal(pHuc.getURL().toString() , pViewState);
	}
	
	/**
	public static void login(String no,String key){
		try {
			String cookie = getCookieSessionId();
			
			HttpURLConnection pHucStart = SHttpUtil.getConnection("i.cqut.edu.cn/portal.do?service=http%3A%2F%2Fi.cqut.edu.cn%2Fportal.do");
			pHucStart.setRequestProperty("Cookie", cookie);
			
			String postData = "useValidateCode=0&isremenberme=0&ip=&username=" + no + "&password=" + key + "&losetime=30&lt=" 
					+ getLt(SHttpUtil.getHtmlContent(pHucStart))
					+ "&_eventId=submit&submit1=+";
			
			//TODO : Login With Params
			
			HttpURLConnection pHuc = SHttpUtil.getConnection("i.cqut.edu.cn/zfca/login");
			
			pHuc.setRequestProperty("Cookie", cookie);
			pHuc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			pHuc.setRequestProperty("Origin", "http://i.cqut.edu.cn");
			pHuc.setRequestProperty("Host", "i.cqut.edu.cn");
			pHuc.setRequestProperty("Referer", "http://i.cqut.edu.cn/zfca/login");
			
			pHuc.setRequestProperty("Content-Length", postData.length() + "");
			pHuc.setRequestMethod("POST");
			pHuc.setDoOutput(true);
			pHuc.getOutputStream().write(postData.getBytes());
			
			//TODO : GET COOKIE FOR LOGIN
			//获取COOKIE
			String pParamCookie = SHttpUtil.getResponseCookie(pHuc);		
			//获取TICKET
			String pParamTicket = pHuc.getURL().toString();
			pParamTicket = pParamTicket.substring(pParamTicket.indexOf("&ticket="));
			
			//进入用户数字化校园
			HttpURLConnection pHucUserCenter = SHttpUtil.getConnection("i.cqut.edu.cn/portal.do?caUserName=" + no + pParamTicket);
			SHttpUtil.setBasicReq(pHucUserCenter, pParamCookie);
			
			String pDataUserCenter = SHttpUtil.getInputStreamContent(pHucUserCenter.getInputStream(),"GBK");
			
			System.out.println(pDataUserCenter);
			
			System.out.println(pHucUserCenter.getURL().toString());
			
			SHttpUtil.printResponseHeader(pHucUserCenter);
			
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
	}
	**/
	
	/**
	 * 获取表单页面的lt项
	 * */
	public static String getLt(String con){
		if(con != null && !"".equals(con)){
			int pPath = con.indexOf("name=\"lt\"");
			if(pPath < 0) return null;
			String pTar[] = con.substring(pPath,pPath+100).split(" ");
			for(String x:pTar){
				if(x.contains("value"))
					return x.substring(x.indexOf("\"") + 1, x.length() - 1);
			}
			
		}
		return null;
	}
	
	/**获取登录COOKIE*/
	public static String getCookieSessionId(){
		StringBuffer pSb = new StringBuffer();
		
		String[] addr = {"i.cqut.edu.cn" , "i.cqut.edu.cn/portal.do"};
		for(String x: addr){
			List<DataVal> pHeaderOne = SHttpUtil.getConnectionHeader(x);
			if(pHeaderOne != null){
				for(DataVal a : pHeaderOne){
					if("Set-Cookie".equals(a.getName())){
						pSb.append(a.getData());
						if(a.getData().charAt(a.getData().length() - 1) != ';'){
							pSb.append(";");
						}
						break;
					}
				}
			}
		}
		
		return pSb.toString();
	}

}
