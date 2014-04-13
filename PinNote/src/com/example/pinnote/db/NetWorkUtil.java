package com.example.pinnote.db;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.net.http.AndroidHttpClient;

public class NetWorkUtil {
	private static Socket socket = null;
	private static String serverIpAddr = "";
	private static int serverPort = 80;
	public static Socket getSocket(){
		if (null == socket){
			try {
				socket = new Socket(serverIpAddr, serverPort);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return socket;
		}
		return socket;
	}
	
	public static String getServerData(String targetUrl){
		return null;
	}
	
	
}
