package cn.sunflyer.zfang.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import cn.sunflyer.zfang.EduSystem;
import cn.sunflyer.zfang.GradeGrabber;
import cn.sunflyer.zfang.obj.GradeInfo;
import cn.sunflyer.zfang.obj.UserInfo;

public class GradeMap {
	
	private static final String FILE_PATH = "studata.data";
	
	private static final int INTERVAL_MIN = 30;
	
	private static GradeMap mInstance = null;
	
	private GradeMap(){
		this.mGradeMap = new HashMap<>();
		mUserPro = new Properties();
		try {
			File mConfig = new File(FILE_PATH);
			if(mConfig.exists()){
				mUserPro.load(new FileInputStream(mConfig));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mInited = false;
		this.mSyncTimer = new Timer();
		this.mSyncTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				if(mInited){
					new Thread(new Runnable(){
						@Override
						public void run() {
							Logger.log("Info : 计划同步处理任务已启动。");
							syncAllWithSend();
						}
					}).start();
				}
			}
			
		} , new Date() , INTERVAL_MIN * 60 * 1000);
	}
	
	public static GradeMap getInstance(){
		if(mInstance == null)
			mInstance = new GradeMap();
		return mInstance;
	}
	
	//学号 对应 《科目ID，成绩》表
	private HashMap<String,HashMap<String,GradeInfo>> mGradeMap ;	
	
	//属性集合
	private Properties mUserPro;
	
	private boolean mInited ;
	
	private Timer mSyncTimer;
	
	private void saveConfig(){
		if(!mUserPro.isEmpty()){
			try {
				mUserPro.store(new FileOutputStream(new File(FILE_PATH)), "Data");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void removeConfigUser(String name){
		this.mUserPro.remove(name);
		saveConfig();
	}
	
	private void addConfigUser(String name,String pass){
		this.mUserPro.put(name, pass);
		saveConfig();
	}
	
	//添加一个新的用户
	public boolean addNew(String userId,String password){
		if(!isNullOrEmpty(userId) && !isNullOrEmpty(password) && !mUserPro.containsKey(userId) && !mGradeMap.containsKey(userId) ){
			UserInfo p = EduSystem.login(userId, password);
			if(p != null){
				addUserData(userId,password);
				GradeInfo[] gi = GradeGrabber.getGrade(p);
				if(gi != null && gi.length > 0){
					HashMap<String,GradeInfo> gmap = new HashMap<>();
					for(GradeInfo x : gi){
						gmap.put(x.classCode, x);
					}
					mGradeMap.put(userId, gmap);
				}
				return true;
			}
		}
		return false;
	}
	
	private void addUserData(String name,String pass){
		addConfigUser(name, pass);		
	}
	
	//删除指定的用户
	public boolean removeUser(String userId){
		if(!isNullOrEmpty(userId)){
			mGradeMap.remove(userId);
			removeConfigUser(userId);
			return true;
		}
		return false;
	}
	
	/**初始化成绩数据，不会发送短信*/
	public void syncStart(){
		syncAll(false);
		this.mInited = true;
	}
	
	/**
	 * 同步所有注册用户成绩信息并发送变动
	 * */
	public void syncAllWithSend(){
		syncAll(true);
	}
	
	/**
	 * 同步全部在册用户
	 * */
	private void syncAll(boolean sendSms){
		Logger.log("Info : 同步用户 - 开始全局同步，发送短消息参数：" + sendSms);
		String[] uiddata = mUserPro.keySet().toArray(new String[]{});
		if(uiddata != null){
			int succ = 0;
			for(String uid : uiddata){
				UserInfo ui = EduSystem.login(uid, mUserPro.getProperty(uid));
				if(ui != null){
					GradeInfo[] gi = GradeGrabber.getGrade(ui);
					if(gi != null){
						if(sendSms){
							HashMap<String,GradeInfo> gmap = this.mGradeMap.get(uid);
							if(gmap == null)
								gmap = new HashMap<>();
							StringBuffer pSb = new StringBuffer("【理工大学成绩订阅】用户" + ui.name + "订阅的成绩变动：");
							int classNum = 0;
							for(GradeInfo g : gi){
								if(!gmap.containsKey(g.classCode)){
									classNum ++;
									pSb.append(g.className).append(",学分").append(g.credit).append(",成绩").append(g.grade).append(";");
								}
								gmap.put(g.classCode, g);
							}
							if(classNum > 0){
								pSb.deleteCharAt(pSb.length() - 1).append("。系统目前每").append(INTERVAL_MIN).append("分钟检查一次，因此通知可能出现延迟，还请理解！");
								Logger.log("Sms : 已向 发送短信通知，变更发生课程数目：" + classNum + ",发送结果为" + Sms.send("", pSb.toString()));
							}
						}else{
							HashMap<String,GradeInfo> gmap = new HashMap<>();
							for(GradeInfo g : gi){
								gmap.put(g.classCode, g);
							}
							mGradeMap.put(uid, gmap);
						}
					}
					succ ++;
				}else{
					Logger.log("Info : 同步用户 - " + uid + " 出现错误，无法登陆到教务系统");
				}
			}
			Logger.log("Info : 同步用户 ： 全部用户同步完毕，总计需要同步" + mUserPro.keySet().size() + ",成功" + succ);
		}else{
			Logger.log("Info : 没有可以同步的用户。");
		}
	}
	
	public boolean sync(String userId , boolean sendSms){
		return false;
	}
	
	public static boolean isNullOrEmpty(String i){
		return i == null ? true : i.trim().equals("") ;
	}

	
	
}
