package cn.sunflyer.zfang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import cn.sunflyer.zfang.obj.DataVal;

public class SHttpUtil {
	
	public static HttpURLConnection getConnection(String url){
		try{
			return (HttpURLConnection)new URL(url.startsWith("http://") ? url : "http://" + url).openConnection();
		}catch(Exception e){
			
		}
		return null;
	}
	
	public static List<DataVal> getConnectionHeader(String url){
		return getConnectionHeader(getConnection(url));
	}
	
	public static List<DataVal> getConnectionHeader(HttpURLConnection h){
		if(h != null){
			List<DataVal> pData = new ArrayList<DataVal>();
			Set<Entry<String,List<String>>> pHeaderFields = h.getHeaderFields().entrySet();
			for(Entry<String,List<String>> x:pHeaderFields){
				//System.out.println("Name : " + x.getKey() + " , Value : " + x.getValue());
				pData.add(new DataVal(x.getKey() , ((List<String>)x.getValue()).get(0)));
			}
			return pData;
		}
		return null;
	}
	
	public static String getHtmlContent(String url){
		return getHtmlContent(getConnection(url));
	}
	
	public static String getHtmlContent(HttpURLConnection h){
		try {
			return h == null ? null : getInputStreamContent(h.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getInputStreamContent(InputStream i){
		return getInputStreamContent(i, "UTF-8");
	}
	
	public static String getInputStreamContent(InputStream i,String encoding){
		if( i!= null){
			BufferedReader pBr;
			try {
				pBr = new BufferedReader(new InputStreamReader(i , encoding));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
			StringBuffer pSb = new StringBuffer();
			int a = -1;
			try {
				while((a = pBr.read()) != -1){
					pSb.append((char)a);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pSb.toString();
		}
		return null;
	}
	
	public static HttpURLConnection post(String url,String postdata,String cookie){
		HttpURLConnection pHuc = getConnection(url);
		
		pHuc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		pHuc.setRequestProperty("Origin", url.substring(0 , url.indexOf("/")));
		pHuc.setRequestProperty("Host", url.substring(0 , url.indexOf("/")));
		pHuc.setRequestProperty("Cookie", cookie);
			
		try {
			pHuc.setRequestMethod("POST");
			pHuc.setDoOutput(true);
			pHuc.getOutputStream().write(postdata == null ? "".getBytes() : postdata.getBytes());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return pHuc;
	}

	public static void setBasicReq(HttpURLConnection h,String c){
		if(h != null){
			if( c != null)
				h.setRequestProperty("Cookie", c);
			h.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			h.setRequestProperty("User-Agent", "Sunflyer Application - Simple Class Grabber Version 1.0");
		}
	}
	
	public static String getResponseCookie(HttpURLConnection h){
		return getResponseHeader(h, "Set-Cookie");
	}
	
	public static String getResponseHeader(HttpURLConnection h,String name){
		if( h != null && name != null){
			List<DataVal> pData = getConnectionHeader(h);
			if(pData != null){
				for(DataVal x: pData){
					if(name.equals(x.getName()))
						return x.getData().toString();
				}
			}
		}
		return null;
	}
	
	public static void printResponseHeader(HttpURLConnection h){
		if(h == null) return;
		List<DataVal> list = getConnectionHeader(h);
		if( list != null){
			for(DataVal x: list){
				System.out.println(x.toString());
			}
		}
	}
	
}
