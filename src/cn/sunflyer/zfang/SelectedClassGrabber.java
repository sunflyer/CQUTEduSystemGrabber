package cn.sunflyer.zfang;

import java.util.ArrayList;

import cn.sunflyer.zfang.anno.Invoker;
import cn.sunflyer.zfang.obj.AvailableClassInfo;
import cn.sunflyer.zfang.obj.SelectedClassInfo;
import cn.sunflyer.zfang.obj.UserInfo;

/**
 * 选课抓取器
 * */
public class SelectedClassGrabber {

	/**
	 * 抓取已经选好的课程。仅限选课期间可用
	 * */
	@Invoker(name = "当前选课信息")
	public static SelectedClassInfo[] getSelectedClassInfo(UserInfo i){
		
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
					return pArr.toArray(new SelectedClassInfo[]{});
				}
			}
		}
		return null;
	}
	
	@Invoker(name = "可选课程信息")
	public static AvailableClassInfo[] getAvailableClassInfo(UserInfo i){
		if(i != null){
			String pCurr = EduSystem.getJwxtContent("xf_xszyyxkc.aspx", "&gnmkdm=N121201", "", i);
			System.out.println(pCurr);
			if(pCurr != null){
				String pList[] = EduSystem.getTable(pCurr);
				if(pList!=null){
					ArrayList<AvailableClassInfo> pArr = new ArrayList<>();
					for(int z = 1;z < pList.length ; z++){
						System.out.println(pList[z]);
						String [] pRow = EduSystem.getRowData(pList[z]);
						if(pRow.length > 10){
							pArr.add(new AvailableClassInfo(pRow));
						}
					}
					return pArr.toArray(new AvailableClassInfo[]{});
				}
			}
		}
		return null;
	}
	
}
