package cn.sunflyer.zfang.obj;

import cn.sunflyer.zfang.anno.Load;

public class GradeInfoEx extends AbsInfo {
	
	@Load(name="学年",path=0,isReq = true)
	public String stuYear;
	
	@Load(name="学期",path=1,isReq=true)
	public String stuPeriod;
	
	@Load(name="课程代码",path=2,isReq=true)
	public String classCode;
	
	@Load(name="课程名称",path=3,isReq=true)
	public String className;
	
	@Load(name="课程类型",path=4,isReq=true)
	public String classType;
	
	@Load(name="学分",path=6,isReq=true)
	public String credit;
	
	@Load(name="成绩",path=11,isReq=true)
	public String grade;
	
	@Load(name="开课学院",path=14,isReq=true)
	public String classInst;
	
	public GradeInfoEx(String[] d){
		super(d);
	}
}
