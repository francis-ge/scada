package com.sharpower.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;
import com.opensymphony.xwork2.ActionSupport;

public class Text2AudioAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private InputStream inputStream;
	private int contentLength;
	private String contentDisposition;
	private String text;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public int getContentLength() {
		return contentLength;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String textToAudio(){
		try {
			URL url = new URL("https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=dcFP41BoCqemHDhIy00zcPX1Pgu7MKUV&client_secret=j7pytnwQP5aAeOtlLUU1WI651xfUPp8L");
		   
			URLConnection urlConnection = url.openConnection();                                                    // 打开连接
			BufferedReader br = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"utf-8")); // 获取输入流
		    String line = null;
		    StringBuilder sb = new StringBuilder();
		    
		    while ((line = br.readLine()) != null) {
		        sb.append(line);
		    }   
				    
			Map<String, Object> dataMap = (Map<String, Object>) JSONUtil.deserialize(sb.toString());
			
			String tok = (String) dataMap.get("access_token");
			
			URL url1 = new URL("http://tsn.baidu.com/text2audio?tex=" + text + "&ctp=1&cuid=54EE7593B904&lan=zh&tok=" + tok);
			
			URLConnection urlConnection1 = url1.openConnection();  
			
			inputStream = urlConnection1.getInputStream();
			
			contentLength = inputStream.available();
			
			contentDisposition = "inline;filename=\"Donwload_" + new Date() + ".mp3\"";
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
