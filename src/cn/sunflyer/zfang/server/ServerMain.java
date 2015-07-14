package cn.sunflyer.zfang.server;

import java.io.IOException;

public class ServerMain {
	
	public static void main(String[] args) throws IOException{
		
		SocketData pSd = new SocketData();
		
		pSd.startService();
		
	}

}
