package cn.sunflyer.zfang.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SocketData {
	
	public static final int PORT = 3456;
	
	//多线程执行器
	private Executor mThread ;

	private ServerSocket mSvrSocket;
	
	private SocketTimer mSocketTimer;
	
	private Thread mThreadMain ;
	
	private boolean mContinueToGo = true;
	
	private GradeMap mGm = GradeMap.getInstance();
	
	public SocketData() throws IOException{
		mSvrSocket = new ServerSocket(PORT);
		mThread = Executors.newCachedThreadPool();
		mSocketTimer = new SocketTimer();
	}
	
	public void startService(){
		
		System.out.println("Info : 启动服务");
		
		mGm.syncStart();
		
		mThreadMain = new Thread(new Runnable(){

			@Override
			public void run() {
				while(mContinueToGo){
					try {
						Socket pClient = mSvrSocket.accept();
						System.out.println("连接 ： 已与 " + pClient.getRemoteSocketAddress().toString() + ":" + pClient.getPort() + " 成功，本地端口：" + pClient.getLocalPort());
						mSocketTimer.addProcess(pClient);					
						mThread.execute(new ServerThread(pClient));
					} catch (IOException e) {
						if(e instanceof SocketException) {
								mContinueToGo = false;
						}
						Logger.logE(e);
					}										
				}				
			}			
		});
		mThreadMain.start();
	}
	
	public void stopService() throws IOException{
		
		mContinueToGo = false;
		
		mSvrSocket.close();
				
	}
	
}
