package cn.sunflyer.zfang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.sunflyer.zfang.obj.DataVal;
import cn.sunflyer.zfang.obj.GradeInfo;
import cn.sunflyer.zfang.obj.UserInfo;

public class Grabber {
	
	public static void main(String[] args){
		//String a = "dDw0MjY4MTY5NTE7dDxwPGw8dGpxcjs+O2w8MDs+PjtsPGk8MT47PjtsPHQ8O2w8aTwxPjtpPDc+O2k8OT47PjtsPHQ8dDw7dDxpPDE2PjtAPOWFqOmDqDsyMDAxLTIwMDI7MjAwMi0yMDAzOzIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjs+O0A85YWo6YOoOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2Oz4+Oz47Oz47dDw7bDxpPDA+O2k8MT47aTwyPjs+O2w8dDw7bDxpPDA+Oz47bDx0PHA8bDxpbm5lcmh0bWw7PjtsPDIwMTQtMjAxNeWtpuW5tOesrDLlrabmnJ/lrabkuaDmiJDnu6k7Pj47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjs+O2w8dDxwPGw8aW5uZXJodG1sOz47bDzlrablj7fvvJoxMTMwMzA4MDIyNjs+Pjs7Pjt0PHA8bDxpbm5lcmh0bWw7PjtsPOWnk+WQje+8mumZiOiAgOeShzs+Pjs7Pjt0PHA8bDxpbm5lcmh0bWw7PjtsPOWtpumZou+8muiuoeeul+acuuenkeWtpuS4juW3peeoi+WtpumZojs+Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjs+O2w8dDxwPGw8aW5uZXJodG1sOz47bDzkuJPkuJrvvJrova/ku7blt6XnqIs7Pj47Oz47dDxwPGw8aW5uZXJodG1sOz47bDzooYzmlL/nj63vvJoxMTMwMzA4MDI7Pj47Oz47Pj47Pj47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8MT47aTwxPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+O2w8aTwwPjs+O2w8dDw7bDxpPDE+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47aTw3PjtpPDg+O2k8OT47aTwxMD47aTwxMT47aTwxMj47aTwxMz47aTwxND47aTwxNT47aTwxNj47aTwxNz47aTwxOD47aTwxOT47aTwyMD47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8KDIwMTQtMjAxNS0yKS0xMDQ4NS0yMDA2MDA2Ny0xOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDE0LTIwMTU7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDEwNDg1Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzpqazlhYvmgJ3kuLvkuYnln7rmnKzljp/nkIbmpoLorro7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWFrOWFseW/heS/rjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8My4wOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw2ODs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85oCd5oOz5pS/5rK75pWZ6IKy5a2m6ZmiOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47Pj47Pj47Pj47Pj47PoxSwCzz58CE8lc5E2D70ijzaluc";
		//String b = "dDw0MjY4MTY5NTE7dDxwPGw8dGpxcjs+O2w8MDs+PjtsPGk8MT47PjtsPHQ8O2w8aTwxPjtpPDc+O2k8OT47PjtsPHQ8dDw7dDxpPDE2PjtAPOWFqOmDqDsyMDAxLTIwMDI7MjAwMi0yMDAzOzIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjs+O0A85YWo6YOoOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2Oz4+Oz47Oz47dDw7bDxpPDA+O2k8MT47aTwyPjs+O2w8dDw7bDxpPDA+Oz47bDx0PHA8bDxpbm5lcmh0bWw7PjtsPDIwMTQtMjAxNeWtpuW5tOesrDLlrabmnJ/lrabkuaDmiJDnu6k7Pj47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjs+O2w8dDxwPGw8aW5uZXJodG1sOz47bDzlrablj7fvvJoxMTMwMzA4MDIyNjs+Pjs7Pjt0PHA8bDxpbm5lcmh0bWw7PjtsPOWnk+WQje+8mumZiOiAgOeShzs+Pjs7Pjt0PHA8bDxpbm5lcmh0bWw7PjtsPOWtpumZou+8muiuoeeul+acuuenkeWtpuS4juW3peeoi+WtpumZojs+Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjs+O2w8dDxwPGw8aW5uZXJodG1sOz47bDzkuJPkuJrvvJrova/ku7blt6XnqIs7Pj47Oz47dDxwPGw8aW5uZXJodG1sOz47bDzooYzmlL/nj63vvJoxMTMwMzA4MDI7Pj47Oz47Pj47Pj47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8MT47aTwxPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+O2w8aTwwPjs+O2w8dDw7bDxpPDE+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47aTw3PjtpPDg+O2k8OT47aTwxMD47aTwxMT47aTwxMj47aTwxMz47aTwxND47aTwxNT47aTwxNj47aTwxNz47aTwxOD47aTwxOT47aTwyMD47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8KDIwMTQtMjAxNS0yKS0xMDQ4NS0yMDA2MDA2Ny0xOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDE0LTIwMTU7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDEwNDg1Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzpqazlhYvmgJ3kuLvkuYnln7rmnKzljp/nkIbmpoLorro7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWFrOWFseW/heS/rjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8My4wOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw2ODs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85oCd5oOz5pS/5rK75pWZ6IKy5a2m6ZmiOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47Pj47Pj47Pj47Pj47PoxSwCzz58CE8lc5E2D70ijzaluc";
		//System.out.println(a.equals(b));
		/**
		String username,password;
		Scanner psc = new Scanner(System.in);
		System.out.println("输入用户名：");
		username = psc.nextLine();
		System.out.println("输入密码：");
		password = psc.nextLine();
		
		UserInfo pUi = login(username,password);
		**/
		//TODO CHANGE YOUR NAME AND PASSWORD HERE
		UserInfo pUi = login("USERNAME","PASSWORD");
		getGrade(pUi);
		getClassTable(pUi);
		//psc.close();
	}
	
	
	public static final String CLASS_TABLE_FILE = "classtable.html";
	/**
	 * 抓课表，打印课表网页源代码
	 * */
	public static void getClassTable(UserInfo i){
		String data = getJwxtContent("xskbcx.aspx", "&gnmkdm=N121602", "", i);
		
		try {
			//data = new String(data.getBytes("GB2312"),"GB2312");
			File pFile = new File(CLASS_TABLE_FILE);
			FileWriter pFw = new FileWriter(pFile);
			pFw.write(data);
			pFw.close();
			Runtime.getRuntime().exec("explorer " + CLASS_TABLE_FILE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 抓取成绩
	 * */
	public static void getGrade(UserInfo i){
		if(i != null){			
			
			//抓取viewstate
			String pCurrentData = getContent(getConnection("xscjcx.aspx" , "&gnmkdm=N121604" , i));
			String pv = catchKey(pCurrentData  , "name=\"__VIEWSTATE\" value=\"" ,"\" />" );
			
			try {
				String postdata = "__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=" + URLEncoder.encode(pv,"GB2312") + "&hidLanguage=&ddlXN=&ddlXQ=&ddl_kcxz=&btn_zcj=%C0%FA%C4%EA%B3%C9%BC%A8";				
				pCurrentData = getScoreContent(postdata,i);
					
				//System.out.println(data);
				if(pCurrentData.indexOf("<tr class=\"datelisthead\">") < 0 || pCurrentData.contains("错误") || pCurrentData.contains("ERROR")){
					System.out.println("获取信息失败。");
					return;
				
				}
				
				String pData = catchKey(pCurrentData , "<tr class=\"datelisthead\">" , "</table>");
				String[] pClassList = pData.replace("<tr>","").replace("<tr class=\"alt\">","").split("</tr>");
				
				ArrayList<GradeInfo> pGrade = new ArrayList<GradeInfo>();
				
				for(int z = 1 ; z < pClassList.length ; z++){
					String pRep[] = pClassList[z].trim().replace("<td></td>","<td>无</td>").replace("&nbsp;", "无").replace("<td>", "").split("</td>");
					if(pRep.length >= 10)
						pGrade.add(new GradeInfo(pRep));
				}
				
				System.out.println("课程学年\t课程学期\t课程代码\t课程学分\t课程绩点\t课程成绩\t是否重修\t课程名称");
				
				for(GradeInfo x : pGrade){
					//System.out.println(x.toString());
					System.out.println(x.stuYear + "\t" + x.stuPeriod  + "\t" + x.classCode + "\t" + x.credit + "\t" + x.gradeNorm + "\t" + x.gradeMid + "\t" + x.isRestudy + "\t" + x.className);
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/**
			//当前页面抓到的成绩
			if(pCurrentData.indexOf("<tr class=\"datelisthead\">") < 0 || pCurrentData.contains("错误") || pCurrentData.contains("ERROR")){
				System.out.println("获取信息失败。");
				return;
			}
			
			String pData = catchKey(pCurrentData , "<tr class=\"datelisthead\">" , "</table>");
			String[] pClassList = pData.replace("<tr>","").replace("<tr class=\"alt\">","").split("</tr>");
			
			ArrayList<GradeInfo> pGrade = new ArrayList<GradeInfo>();
			
			for(int z = 1 ; z < pClassList.length ; z++){
				String pRep[] = pClassList[z].trim().replace("&nbsp;", "无").replace("<td>", "").split("</td>");
				if(pRep.length >= 17)
					pGrade.add(new GradeInfo(pRep));
			}
			
			for(GradeInfo x : pGrade){
				System.out.println(x.toString());
			}
			
			//System.out.println(pCurrentData);
			
			
			//尝试后续抓取
			try {
				String pVis = catchKey(pCurrentData , "name=\"__VIEWSTATE\" value=\"" ,"\" />" );
				pVis = getScoreContent(getScoreContentPost("ddlxn","",URLEncoder.encode(pVis , "GB2312"),"%C8%AB%B2%BF","2",null),i);
				pVis = catchKey(pVis, "name=\"__VIEWSTATE\" value=\"" ,"\" />");
				
				String x = pVis;
				
				pVis = getScoreContent(getScoreContentPost("ddlxq","",URLEncoder.encode(pVis , "GB2312"),"%C8%AB%B2%BF","%C8%AB%B2%BF",null),i);
				pVis = catchKey(pVis, "name=\"__VIEWSTATE\" value=\"" ,"\" />");
				
				String y = pVis;
				System.out.println(x.equals(y));
				
				pVis = getScoreContent(getScoreContentPost("","",URLEncoder.encode(pVis , "GB2312"),"%C8%AB%B2%BF","%C8%AB%B2%BF","&btnCx=+%B2%E9++%D1%AF+"),i);
				
				String z = catchKey(pVis, "name=\"__VIEWSTATE\" value=\"" ,"\" />");
				System.out.println(z.equals(x));
				
				System.out.println(pVis);
					
			} catch(Exception e){
				e.printStackTrace();
			}
			**/
		}
	}
	
	public static String getScoreContentPost(String et,String ea,String vs,String ddlxn,String ddlxq,String d){
		return "__EVENTTARGET=" + et +"&__EVENTARGUMENT=" + ea + "&__VIEWSTATE=" + vs +
							"&ddlxn=" + ddlxn + "&ddlxq=" + ddlxq + (d == null ? "" : "");
		//%C8%AB%B2%BF
	}
	
	public static String getScoreContent(String postdata,UserInfo i){
		return getJwxtContent("xscjcx.aspx", "&gnmkdm=N121604", postdata, i);
	}
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("登陆失败！检查输入的用户名和密码，然后重试");
		return null;
	}
	
	/**
	 * 从教务系统获取 - viewstate
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
			// TODO Auto-generated catch block
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
