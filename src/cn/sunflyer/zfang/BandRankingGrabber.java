package cn.sunflyer.zfang;

import java.util.ArrayList;

import cn.sunflyer.zfang.obj.BandRanking;
import cn.sunflyer.zfang.obj.UserInfo;

/**
 * 等级考试成绩抓取类
 * */
public class BandRankingGrabber {
	
	/**
	 * 获取等级考试信息
	 * @param i UserInfo对象
	 * @return 如果用户信息有效，返回查询到的信息。否则，如果查询结果为空或者信息错误，返回null
	 * */
	public static ArrayList<BandRanking> getBandRanking(UserInfo i){
		if(i != null){
			String pCon = EduSystem.getJwxtContent("xsdjkscx.aspx", "&gnmkdm=N121605", "", i);
			String[] pList = EduSystem.getTable(pCon);
			if(pList != null){
				ArrayList<BandRanking> pArr = new ArrayList<>();
				for(int z = 1 ; z < pList.length ; z ++){
					String[] pRow = EduSystem.getRowData(pList[z]);
					if(pRow.length >= 6){
						pArr.add(new BandRanking(pRow));
					}					
				}
				return pArr;
			}
		}
		return null;
	}

}
