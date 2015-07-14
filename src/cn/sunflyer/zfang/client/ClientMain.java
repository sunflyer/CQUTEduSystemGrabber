package cn.sunflyer.zfang.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {

	public static void main(String[] args) throws IOException{
		Socket s = new Socket("localhost",3456);
		
		PrintWriter pw = new PrintWriter(s.getOutputStream());
		pw.print("hello");
		System.out.println("已发送数据");
		BufferedReader pb = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(pb.readLine());
		s.close();
	}
	
}
