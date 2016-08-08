package com.sharpower.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;

import org.apache.struts2.json.JSONReader;
import org.apache.struts2.json.JSONUtil;
import org.junit.Test;

public class javaHttpTest {
	
	@Test
	public void testPost() throws Exception{

	    URL url = new URL("https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=dcFP41BoCqemHDhIy00zcPX1Pgu7MKUV&client_secret=j7pytnwQP5aAeOtlLUU1WI651xfUPp8L");
	   
	    URLConnection urlConnection = url.openConnection();                                                    // 打开连接
	    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8")); // 获取输入流
	    String line = null;
	    StringBuilder sb = new StringBuilder();
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }   

	   JSONReader jsonReader = new JSONReader();
	    
	    Map<String, Object> dataMap = (Map<String, Object>) JSONUtil.deserialize(sb.toString());
	    
	    String tok =  (String) dataMap.get("access_token");
	    
	    URL url1 = new URL("http://tsn.baidu.com/text2audio?tex=发送反馈&ctp=1&cuid=54EE7593B904&lan=zh&tok=" + tok);
	    
	    URLConnection urlConnection1 = url1.openConnection();  
	    
	    File file = new File("D:\\eclipseWorkspace\\sharpower_scada\\1.mp3");
	    
	    System.out.println(file.getParent());
	    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//	    FileOutputStream fileOutputStream = new FileOutputStream(file);
//	    InputStream inputStream = urlConnection1.getInputStream();
//
//	    byte[] b = new byte[10];
//	    	
//	    while (inputStream.read(b)!=-1) {
//	    	fileOutputStream.write(b);
//		}
//	    inputStream.close();
//	    fileOutputStream.close();
	    
	    //FileInputStream fileInputStream = new FileInputStream(file);
	    AudioFormat audioFormat = audioInputStream.getFormat();
	    
//	    if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
//            audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
//                    audioFormat.getSampleRate(), 16, audioFormat
//                            .getChannels(), audioFormat.getChannels() * 2,
//                    audioFormat.getSampleRate(), false);
//            audioInputStream = AudioSystem.getAudioInputStream(audioFormat,
//                    audioInputStream);
//        }
	    
	    Info info = new Info(SourceDataLine.class, audioFormat);
	    SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
	    sourceDataLine.open(audioFormat);
	    sourceDataLine.start();
	    		
	    //System.out.println(sb.toString());
	}
}
