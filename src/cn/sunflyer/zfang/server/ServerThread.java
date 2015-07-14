package cn.sunflyer.zfang.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable{

	private Socket mClient;
	
	public ServerThread(Socket s){
		this.mClient = s;
	}
	
	@Override
	public void run() {
		execute(this.mClient);
	}
	
	public Socket getSocket(){
		return this.mClient;
	}
	
	public static void execute(Socket s){
		if(s != null){
			try{
				PrintStream pSend = new PrintStream(s.getOutputStream());
				BufferedReader pGet = new BufferedReader(new InputStreamReader(s.getInputStream()));
				while(true){
					String pReq = pGet.readLine();
					if("bye".equals(pReq)){
						break;
					}
					System.out.println("Request : " + s.getInetAddress() + ":" + s.getPort() + " - " + pReq);
					
					String pRes = Analyser.execute(pReq);
					pSend.println(pRes == null ? "{\"res\":\"error-null\"}" : pRes);
				}
			}catch(Exception e){
				Logger.log("错误：执行远程发送的数据指令出现异常" + Analyser.getSocketInfo(s));
			}
		}
	}

}
