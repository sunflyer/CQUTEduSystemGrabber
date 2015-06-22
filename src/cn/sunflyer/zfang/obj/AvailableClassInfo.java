package cn.sunflyer.zfang.obj;

import cn.sunflyer.zfang.anno.Load;

public class AvailableClassInfo extends AbsInfo{
	
	@Load(name="课程代码",path = 2,isReq = true)
	public String classCode;
	@Load(name="课程名称",path = 3,isReq = true)
	public String className;
	@Load(name="课程学分",path = 4,isReq = true)
	public String classCredit;
	@Load(name="课程周学时",path = 5,isReq = true)
	public String classTime;
	@Load(name="考查方式",path = 6,isReq = true)
	public String examType;
	@Load(name="开课学院",path = 7,isReq = true)
	public String institute;
	@Load(name="专业方向",path = 8,isReq = true)
	public String professionTarget;
	@Load(name="课程类型",path = 9,isReq = true)
	public String classType;
	@Load(name="容量限制",path = 10,isReq = true)
	public String classLimitNum;
	
	public AvailableClassInfo(String[] data){
		super(data);
	}

}
