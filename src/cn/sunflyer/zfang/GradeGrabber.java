package cn.sunflyer.zfang;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

//import javax.swing.JOptionPane;
import cn.sunflyer.zfang.obj.GradeInfo;
import cn.sunflyer.zfang.obj.UserInfo;

/**
 * 成绩抓取器
 * */
public class GradeGrabber {
	
	/**
	 * 如果评分为等级，根据等级返回实际分数。
	 * 依据学生手册规定修改。
	 * */
	public static int getGradeLevel(String g){
		if(g != null){
			switch(g){
			case "优秀":return 95;
			case "良好":return 85;
			case "中等":return 75;
			case "合格":return 70;
			case "及格":return 65;
			case "不合格":return 50;
			case "不及格":return 55;
			}
		}
		return 0;
	}
	
	/**
	 * 统计绩点，包括全部科目
	 * */
	public static double getGradePoint(ArrayList<GradeInfo> i){
		if(i != null){
			//去除重复项
			HashMap<String,GradeInfo> pHm = new HashMap<>();
			for(GradeInfo x:i){
				if(pHm.containsKey(x.classCode)){
					try{
						if(Double.valueOf(x.grade) > Double.valueOf(pHm.get(x.classCode).grade)){
							pHm.put(x.classCode, x);
						}
					}catch(Exception e){
						if(getGradeLevel(x.grade) > getGradeLevel(pHm.get(x.classCode).grade)){
							pHm.put(x.classCode, x);
						}
					}
				}else{
					pHm.put(x.classCode, x);
				}
			}
			double point = 0;
			double credit = 0;
			
			//开始统计
			for(GradeInfo x:pHm.values()){
				
				credit += Double.valueOf(x.credit);
				point += Double.valueOf(x.point) * Double.valueOf(x.credit);
			}
			return credit == 0 ? 0 : point /credit;
		}
		return 0;
	}
	
	/**只统计除体育和公共选修以外的课程*/
	public static double getGradePointNoPE(ArrayList<GradeInfo> i){
		if(i != null){
			//去除重复项
			HashMap<String,GradeInfo> pHm = new HashMap<>();
			for(GradeInfo x:i){
				if((!x.className.contains("大学体育") && !x.classType.contains("全院素质任选"))){
					if(pHm.containsKey(x.classCode)){
						try{
							if(Double.valueOf(x.grade) > Double.valueOf(pHm.get(x.classCode).grade)){
								pHm.put(x.classCode, x);
							}
						}catch(Exception e){
							if(getGradeLevel(x.grade) > getGradeLevel(pHm.get(x.classCode).grade)){
								pHm.put(x.classCode, x);
							}
						}
					}else{
						pHm.put(x.classCode, x);
					}
				}				
			}
			double point = 0;
			double credit = 0;
			
			//开始统计
			for(GradeInfo x:pHm.values()){
				
				credit += Double.valueOf(x.credit);
				point += Double.valueOf(x.point) * Double.valueOf(x.credit);
			}
			return credit == 0 ? 0 : point /credit;
		}
		return 0;
	}
	
	/**
	 * 抓取成绩
	 * 可能包括重修后留下的重复项
	 * */
	public static ArrayList<GradeInfo> getGrade(UserInfo i){
		if(i != null){			
			
			//抓取viewstate
			//String pCurrentData = EduSystem.getContent(EduSystem.getConnection("xscjcx.aspx" , "&gnmkdm=N121604" , i));
			String pCurrentData = EduSystem.getJwxtContent("xscjcx.aspx" , "&gnmkdm=N121604" , "" ,i);
			String pv = EduSystem.catchKey(pCurrentData  , "name=\"__VIEWSTATE\" value=\"" ,"\" />" );
			
			try {
				String postdata = "__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=" + URLEncoder.encode(pv,"GB2312") + "&hidLanguage=&ddlXN=&ddlXQ=&ddl_kcxz=&btn_zcj=%C0%FA%C4%EA%B3%C9%BC%A8";				
				pCurrentData = getScoreContent(postdata,i);
					
				//System.out.println(data);
				
				
				//String pData = EduSystem.catchKey(pCurrentData , "<tr class=\"datelisthead\">" , "</table>");
				//String[] pClassList = pData.replace("<tr>","").replace("<tr class=\"alt\">","").split("</tr>");
				String[] pClassList = EduSystem.getTable(pCurrentData);
				
				if(pClassList == null || pCurrentData.contains("错误") || pCurrentData.contains("ERROR")){
					System.out.println("获取信息失败。");
					return null;			
				}
				
				ArrayList<GradeInfo> pGrade = new ArrayList<GradeInfo>();
				
				for(int z = 1 ; z < pClassList.length ; z++){
					String pRep[] = EduSystem.getRowData(pClassList[z]);
					if(pRep.length >= 10)
						pGrade.add(new GradeInfo(pRep));
				}
				
				return pGrade;
				
			} catch (UnsupportedEncodingException e) {
				
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
		return null;
	}
	
	public static String getScoreContentPost(String et,String ea,String vs,String ddlxn,String ddlxq,String d){
		return "__EVENTTARGET=" + et +"&__EVENTARGUMENT=" + ea + "&__VIEWSTATE=" + vs +
							"&ddlxn=" + ddlxn + "&ddlxq=" + ddlxq + (d == null ? "" : "");
		//%C8%AB%B2%BF
	}
	
	public static String getScoreContent(String postdata,UserInfo i){
		return EduSystem.getJwxtContent("xscjcx.aspx", "&gnmkdm=N121604", postdata, i);
	}
	
	
	/**Console Output Grade*/
	public static void printGrade(UserInfo i){
		printGrade(getGrade(i));
	}
	
	/**Console Output Grade*/
	public static void printGrade(ArrayList<GradeInfo> pGrade){
		if(pGrade == null) return;
		
		System.out.println("课程学年\t课程学期\t课程代码\t课程学分\t课程绩点\t课程成绩\t是否重修\t课程名称");
		
		for(GradeInfo x : pGrade){
			//System.out.println(x.toString());
			System.out.println(x.stuYear + "\t" + x.stuPeriod  + "\t" + x.classCode + "\t" + x.credit + "\t" + x.point + "\t" + x.grade + "\t" + x.noteSec + "\t" + x.className);
		}
		System.out.println("平均绩点:" + getGradePoint(pGrade));
	}

}
