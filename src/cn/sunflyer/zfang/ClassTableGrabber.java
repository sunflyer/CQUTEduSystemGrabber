package cn.sunflyer.zfang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.sunflyer.zfang.anno.Invoker;
import cn.sunflyer.zfang.obj.UserInfo;

public class ClassTableGrabber {

	public static final String CLASS_TABLE_FILE = "classtable.html";
	/**
	 * 抓课表，打印课表网页源代码
	 * */
	@Invoker(name="当前课表信息")
	public static void getClassTable(UserInfo i){
		String data = EduSystem.getJwxtContent("xskbcx.aspx", "&gnmkdm=N121602", "", i);
		
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
	
	
}
