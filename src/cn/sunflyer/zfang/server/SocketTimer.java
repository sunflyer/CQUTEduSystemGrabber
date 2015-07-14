package cn.sunflyer.zfang.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SocketTimer{

	private Timer mTimer;
	
	private HashMap<Socket,Integer> mProcess;
	
	public SocketTimer(){
		this.mTimer = new Timer();
		this.mProcess = new HashMap<>();
		
		this.mTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(Socket s:mProcess.keySet()){
					shortTime(s,1);
				}
			}
			
		}, System.currentTimeMillis(), 6000);
		
	}
	
	public void addProcess(Socket s){
		mProcess.put(s, 15);
		System.out.println("Info : 新的处理端口已被加入，目标地址" + s.getInetAddress().toString() + ":" + s.getPort());
	}
	
	public void refresh(Socket s){
		mProcess.put(s, 15);
	}
	
	public void remove(Socket s){
		mProcess.remove(s);
	}
	
	public void shortTime(Socket s,int minutes){
		if(s != null && minutes >= 0){
			if(s.isClosed()){
				remove(s);
				System.out.println("Info : 远程端口"  + s.getInetAddress().toString() + ":" + s.getPort() + "已被关闭");
				return;
			}
			Integer now = mProcess.get(s);
			if(now != null){
				if(now - minutes <= 0){
					mProcess.remove(s);
					try {
						s.close();
					} catch (IOException e) {
						System.out.println("尝试关闭来自 " + s.getInetAddress().getHostAddress() + ":" + s.getPort() + " 端口 " + s.getLocalPort() + "出现错误  - " + e.getMessage());
					}
				}else
					mProcess.put(s, now - minutes);
			}
		}
		
	}
	
}
