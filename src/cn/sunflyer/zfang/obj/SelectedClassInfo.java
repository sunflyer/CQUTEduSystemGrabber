package cn.sunflyer.zfang.obj;

import cn.sunflyer.zfang.anno.Load;

public class SelectedClassInfo extends AbsInfo{
	
	@Load(name="课程编号" , path = 0 , isReq = true)
	public String classCode;
	
	@Load(name="课程名称" , path = 1 , isReq = true)
	public String className;
	
	@Load(name="课程学分" , path = 2 , isReq = true)
	public String classCredit;
	
	@Load(name="周学时" , path = 3 , isReq = true)
	public String weeklyTime;
	
	@Load(name="考核方式" , path = 4 , isReq = true)
	public String checkType;
	
	@Load(name="开课学院" , path = 5 , isReq = true)
	public String classInstitution;
	
	@Load(name="专业方向" , path = 6 , isReq = true)
	public String professionTarget;
	
	@Load(name="课程类型" , path = 7 , isReq = true)
	public String classType;
	
	@Load(name="教师名称" , path = 8 , isReq = true)
	public String teacherName;
	
	public SelectedClassInfo(String[] da){
		super(da);
	}

}
