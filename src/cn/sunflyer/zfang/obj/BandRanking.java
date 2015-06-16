package cn.sunflyer.zfang.obj;

import cn.sunflyer.zfang.anno.Load;

public class BandRanking extends AbsInfo{
	
	@Load(name = "学年", path = 0 , isReq = true)
	public String stuYear;
	
	@Load(name = "学期" , path = 1 , isReq = true)
	public String stuPeriod;
	
	@Load(name = "等级考试名称" , path = 2 , isReq = true)
	public String bandName;
	
	@Load(name = "准考证号" , path = 3 , isReq = true)
	public String accessId;
	
	@Load(name = "考试日期" , path = 4 , isReq = true)
	public String date;
	
	@Load(name = "成绩" , path = 5 , isReq = true)
	public String score;
	
	public BandRanking(String[] data){
		super(data);
	}

}
