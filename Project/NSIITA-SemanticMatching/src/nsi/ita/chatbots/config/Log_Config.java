package nsi.ita.chatbots.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import nsi.ita.chatbots.utils.RWfile_Util;

public class Log_Config {

	//日志文件地址
	public final static String Log_Path = System.getProperty("user.dir")
			+ "\\library\\LOG\\";
	
	//记录日志信息
	public static void LOG(String content){
		String FileName = "LOG_" + getDate("yyyy-MM-dd") + ".log";
		new RWfile_Util(Log_Path).AppendMethod(FileName,content);
	}
	
	//获取当前日期  yyyy-MM-dd HH:mm:ss
	public static String getDate(String fm){
		
		//设置日期格式
		SimpleDateFormat df = new SimpleDateFormat(fm);
		
		// new Date()为获取当前系统时间
		return df.format(new Date()) + "";
	}
	
}
