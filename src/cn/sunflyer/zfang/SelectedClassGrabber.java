package cn.sunflyer.zfang;

import java.util.ArrayList;

import cn.sunflyer.zfang.obj.SelectedClassInfo;
import cn.sunflyer.zfang.obj.UserInfo;

/**
 * 选课抓取器
 * */
public class SelectedClassGrabber {

	/**
	 * 抓取已经选好的课程。仅限选课期间可用
	 * */
	public static ArrayList<SelectedClassInfo> getSelectedClassInfo(UserInfo i){
		if(i != null){
			String pCurr = EduSystem.getJwxtContent("xf_xszyyxkc.aspx", "&gnmkdm=N121201", "", i);
			if(pCurr != null){
				String[] pList = EduSystem.getTable(pCurr.substring(pCurr.lastIndexOf(EduSystem.TABLE_SIGNATURE)));
				if(pList != null){
					ArrayList<SelectedClassInfo> pArr = new ArrayList<>();
					for(int z = 1 ; z < pList.length ; z ++){
						String[] pRow = EduSystem.getRowData(pList[z]);
						if(pRow.length >= 8){
							pArr.add(new SelectedClassInfo(pRow));
						}
					}
					return pArr;
				}
			}
		}
		return null;
	}
	
}
