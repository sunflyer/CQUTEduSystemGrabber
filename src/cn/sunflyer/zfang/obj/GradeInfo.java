package cn.sunflyer.zfang.obj;

import java.lang.reflect.Field;

import cn.sunflyer.zfang.anno.Load;

public class GradeInfo {
	
	@Load(name= "学年" , path = 0 , isReq = true)
	public String stuYear;
	@Load(name = "学期" , path = 1 , isReq = true)
	public String stuPeriod;
	@Load(name = "课程代码" , path = 2 , isReq = true)
	public String classCode;
	@Load(name = "课程名称" , path = 3, isReq = true)
	public String className;
	@Load(name = "课程性质" , path = 4)
	public String classType;
	@Load(name = "课程归属" , path = 5)
	public String classTo;
	@Load(name = "学分" , path = 6 , isReq = true)
	public String credit;
	@Load(name = "绩点" ,path = 7 , isReq = true)
	public String gradeNorm;
	@Load(name = "成绩" , path = 8 , isReq = true)
	public String gradeMid;
	@Load(name = "辅修标记" , path = 9 , isReq = true)
	public String gradeEnd;
	@Load(name = "补考成绩" , path = 10)
	public String gradeSec;
	@Load(name = "重修成绩" , path = 11)
	public String isRestudy;
	@Load(name = "开课学院" , path = 12)
	public String classIst;
	@Load(name = "备注" , path = 13)
	public String note;
	@Load(name = "重修标记" , path = 14 , isReq = true)
	public String noteSec;

	
	public GradeInfo(String[] data){
		Class<?> pCls = this.getClass();
		Field [] pData = pCls.getFields();
		for( Field x : pData){
			Load pli = x.getAnnotation(Load.class);
			if(pli != null){
				try {
					x.set(this, data[pli.path()]);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(IndexOutOfBoundsException e){
					System.out.println("访问数组溢出 ： " + pli.name() + "于位置" + pli.path());
				}
			}
		}
	}
	
	public String toString(){
		Class<?> cls = this.getClass();
		Field[] data = cls.getFields();
		StringBuffer pSb = new StringBuffer();
		for(Field x : data){
			Load pli = x.getAnnotation(Load.class);
			if(pli != null && pli.isReq()){
				try {
					pSb.append(pli.name() + " : " + x.get(this) + " , ");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return pSb.toString();
	}

}
