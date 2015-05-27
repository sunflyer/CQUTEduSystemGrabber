package cn.sunflyer.zfang.obj;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UserInfo {

	public String addr ;
	
	public String name ;
	
	public String no;
	
	public String b(){
		try {
			return "xh=" + no + "&xm=" + URLEncoder.encode(name,"GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "xh" + no + "&xm=" + name;
	}
	
	public UserInfo(String addr,String name,String no){
		this.addr = addr;
		this.name = name;
		this.no = no;
	}
	
	public String toString(){
		return addr + " , " + name + " , " + no;
	}
	
}
