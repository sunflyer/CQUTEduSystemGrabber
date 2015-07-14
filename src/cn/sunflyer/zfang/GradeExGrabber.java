package cn.sunflyer.zfang;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import cn.sunflyer.zfang.anno.Invoker;
import cn.sunflyer.zfang.obj.GradeInfoEx;
import cn.sunflyer.zfang.obj.UserInfo;

public class GradeExGrabber {
	
	/**
	 * 如果评分为等级，根据等级返回实际分数。
	 * 依据学生手册规定修改。
	 * */
	public static double getGradeLevel(String g){
		if(g != null){
			switch(g){
			case "优秀":return 95;
			case "良好":return 85;
			case "中等":return 75;
			case "合格":return 70;
			case "及格":return 65;
			case "不合格":return 50;
			case "不及格":return 55;
			default:
				try{
					return Double.valueOf(g);
				}catch(Exception e){
					
				}
			}
		}
		return 0;
	}
	
	/**
	 * 统计绩点，包括全部科目
	 * */
	public static double getGradePoint(GradeInfoEx[] i){
		if(i != null){
			//去除重复项
			HashMap<String,GradeInfoEx> pHm = new HashMap<>();
			for(GradeInfoEx x:i){
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
			double gradetemp = 0;
			//开始统计
			for(GradeInfoEx x:pHm.values()){				
				credit += Double.valueOf(x.credit);
				gradetemp = getGradeLevel(x.grade);
				point += gradetemp >= 60 ? (gradetemp - 50) / 10 * Double.valueOf(x.credit) : 0;
			}
			return credit == 0 ? 0 : point /credit;
		}
		return 0;
	}
	
	/**只统计除体育和公共选修以外的课程*/
	public static double getGradePointNoPE(GradeInfoEx[] i){
		if(i != null){
			//去除重复项
			HashMap<String,GradeInfoEx> pHm = new HashMap<>();
			for(GradeInfoEx x:i){
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
			double gradetemp = 0;
			//开始统计
			for(GradeInfoEx x:pHm.values()){				
				credit += Double.valueOf(x.credit);
				gradetemp = getGradeLevel(x.grade);
				point += gradetemp >= 60 ? (gradetemp - 50) / 10 * Double.valueOf(x.credit) : 0;
			}
			return credit == 0 ? 0 : point /credit;
		}
		return 0;
	}
	
	public static String getConclusionEx(Object i){
		if(i != null){
			GradeInfoEx[] d = (GradeInfoEx[])i;
			String res = "当前全部成绩绩点为 ： " + String.format("%.2f", getGradePoint(d)) + " , 除公共选修和体育课外成绩绩点为 ： " + String.format("%.2f", getGradePointNoPE(d));
			return res;
		}
		return "";
	}
	
	@Invoker(name = "当前成绩信息（前端教务系统）" , hasConclusion = true , conclusionMethod = "getConclusionEx")
	public static GradeInfoEx[] getGradeGx(UserInfo i){
		if(i != null){
			String pCurr = EduSystem.getJwxtContent("xscjcx_dq.aspx", "&gnmkdm=N121607", "", i);
			String pv = EduSystem.catchKey(pCurr, "name=\"__VIEWSTATE\" value=\"" ,"\" />");
			
			try{
				String postdata = "__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=" + URLEncoder.encode(pv,"GB2312") + "&ddlxn=%C8%AB%B2%BF&ddlxq=%C8%AB%B2%BF&btnCx=+%B2%E9++%D1%AF+";
				pCurr = EduSystem.getJwxtContent("xscjcx_dq.aspx", "&gnmkdm=N121607", postdata, i);
				
				String []pClassList = EduSystem.getTable(pCurr);
				if(pClassList == null || pCurr.contains("错误") || pCurr.contains("ERROR")){
					System.out.println("获取信息失败。");
					return null;			
				}
				
				ArrayList<GradeInfoEx> pGrade = new ArrayList<>();
				
				for(int z = 1 ; z < pClassList.length ; z ++){
					String data[] = EduSystem.getRowData(pClassList[z]);
					if(data.length > 14){
						pGrade.add(new GradeInfoEx(data));
					}
				}
				
				return pGrade.toArray(new GradeInfoEx[]{});
			}catch(Exception e){
				
			}
		}
		return null;
	}

}
