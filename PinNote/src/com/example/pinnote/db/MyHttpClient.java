package com.example.pinnote.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.net.http.AndroidHttpClient;

public class MyHttpClient {
	public String sendHttpPost(String targetUrl, Map<String ,String> paraMap, String encode){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if ((null != paraMap) && !paraMap.isEmpty()){
			for (Map.Entry<String, String> entry : paraMap.entrySet()){
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		try {
			UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(list, encode);
			HttpPost httpPost = new HttpPost();
			httpPost.setEntity(urlEntity);
			HttpClient httpClient = AndroidHttpClient.newInstance("");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
				InputStream inputStream = httpResponse.getEntity().getContent();
				return inputStreamFormat(inputStream, encode);
			}
			else{
				httpErrorProc(httpResponse);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String sendHttpGet(String targetUrl, Map<String ,String> paraMap, String encode){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		String paramsString = "";
		if ((null != paraMap) && !paraMap.isEmpty()){
			for (Map.Entry<String, String> entry : paraMap.entrySet()){
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			paramsString = "?" + URLEncodedUtils.format(list, "UTF-8");
		}
		
		try {
			HttpGet httpGet = new HttpGet(targetUrl + paramsString);
			HttpClient httpClient = AndroidHttpClient.newInstance("");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
				InputStream inputStream = httpResponse.getEntity().getContent();
				return inputStreamFormat(inputStream, encode);
			}
			else{
				httpErrorProc(httpResponse);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String sendHttpPut(String targetUrl, Map<String ,String> paraMap, String encode){
		return "";
	}
	
	/* 将暑促转换为字符串  */
	public String inputStreamFormat(InputStream inputStream, String encode){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result="";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data,0,len);    
				}
				result=new String(outputStream.toByteArray(),encode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/* HTTP响应错误处理 */
	private void httpErrorProc(HttpResponse httpResponse){
		
	}
	
}
