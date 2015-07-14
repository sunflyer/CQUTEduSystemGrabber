package cn.sunflyer.zfang.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.json.Json;
import javax.json.JsonObject;

public class Sms {
	
	private static final String API_KEY = "ddb42464dcb3a9285213e7fc80b7ed3b";
	
	public static boolean send(String no,String con){
		if(!GradeMap.isNullOrEmpty(no) && !GradeMap.isNullOrEmpty(con)){
			return sendSms(no,con);
		}
		return false;
	}

	private static boolean sendSms(String no , String con){
		try {
			HttpURLConnection pHuc = (HttpURLConnection)(new URL("http://yunpian.com/v1/sms/send.json")).openConnection();
			pHuc.setRequestMethod("POST");
			pHuc.setDoOutput(true);
			StringBuffer pSb = new StringBuffer("apikey=");
			pSb.append(API_KEY).append("&mobile=").append(no).append("&text=").append(URLEncoder.encode(con,"UTF-8"));
			pHuc.getOutputStream().write(pSb.toString().getBytes());
			
			JsonObject pJson = Json.createReader(pHuc.getInputStream()).readObject();
			if(pJson != null){
				JsonObject pRes = pJson.getJsonObject("result");
				Logger.log("Sms : 发送短信到" + no + "，服务器返回结果为：错误代码" + pJson.getInt("code") + ",消息：" + pJson.getString("msg") + (pRes != null ? (".附加参数：" + pRes.toString()) : ""));			
				return "OK".equals(pJson.getString("msg"));
			}	
			
		} catch (IOException e) {
			Logger.log("Sms : Error : 发送短信出现错误");
			Logger.logE(e);
		}		
		return false;
	}
	
}
