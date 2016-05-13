package nsi.ita.chatbots.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import nsi.ita.chatbots.config.File_Config;
import nsi.ita.chatbots.config.Log_Config;

public class TableFile_KeyValue_Util {

	//文件路径
	private String strFilePath;
	
	//是否预读
	private boolean IsPreread;
	
	//文件内容
	private HashMap<String, String> hmContent;
	
	//键值表分隔符
	final private String separator = "=>";
	
	/**
	 * 构造函数
	 * @param Path
	 */
	public TableFile_KeyValue_Util(String Path) {
		this.strFilePath = new String(Path);
	}
	
	/**
	 * 构造函数
	 * @param Path
	 * @param IsPreread
	 */
	public TableFile_KeyValue_Util(String Path,boolean IsPreread) {
		this.strFilePath = new String(Path);
		this.IsPreread = IsPreread;
	}
	
	/**
	 * @return path
	 */
	public String getPath() {
		return strFilePath;
	}

	/**
	 * @param path 要设置的 path
	 */
	public void setPath(String path) {
		clear();
		strFilePath = path;
	}

	/**
	 * @return isPreread
	 */
	public boolean IsPreread() {
		return IsPreread;
	}

	/**
	 * @param isPreread 要设置的 isPreread
	 */
	public void setPreread(boolean isPreread) {
		IsPreread = isPreread;
	}
	
	/**
	 * 释放/清空
	 */
	public void clear(){
		strFilePath = null;//这里可能会造成内存浪费
		IsPreread = false;
		hmContent.clear();
	}

	/**
	 * 获取指定键名的对应值
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		
		//判断是否指定了文件
		if(strFilePath == null)
			return null;
		
		//判断数据检索来源
		if(IsPreread){
			//判断数据是否已预读
			if(hmContent == null){
				hmContent = readFileToMemory(strFilePath);
			}
			getValueFromMemory(key);
		}else{
			return getValueFromFile(strFilePath,key);
		}
		
		return "";
	}
	
	/**
	 * 从预读的哈希表中获取对应值
	 * @param key
	 * @return
	 */
	private String getValueFromMemory(String key){
		return hmContent.get(key);
	}
	
	/**
	 * 从文件中逐行查找对应的值
	 * @param filePath	文件路径
	 * @param key		键名
	 * @return
	 */
	@SuppressWarnings("resource")
	private String getValueFromFile(String filePath,String key){
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			// 判断文件是否存在
			if (file.isFile() && file.exists()) { 
				
				// 考虑到编码格式
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if(!lineTxt.substring(0, File_Config.Comment.length()).equals(File_Config.Comment)){
						String[] l = lineTxt.split(separator);
						if(lineTxt.substring(0, l[0].length()).equals(key)){
							read.close();
							return l[1];
						}
					}
				}
				read.close();
				return null;
			} else {
				//找不到指定的文件
				return null;
			}
		} catch (Exception e) {
			//读取文件内容出错
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取文件内容到内存，并转为哈希表
	 * @param Path
	 * @return
	 */
	private HashMap<String, String> readFileToMemory(String Path){
		
		//定义一个哈希表
		HashMap<String, String> hm = new HashMap<String, String>();
		
		//获取完整的文件内容
		for(String line:new RWfile_Util("").ReadFile(Path)){
			try{
				hm.put(line.split(separator)[0], line.split(separator)[1]);
			}catch(Exception ex){
				String log =  String.format("Class Name:[%s],Error Message:[%s],Annotation=[%s],Path=[%s]",
						"TableFile_KeyValue_Util",ex.getMessage(),"可能是键值表文件某一行格式不正确或不存在.",Path);
				Log_Config.LOG(log);
				//到这里基本上可以判断
				if(hm == null)
					return null;
			}
		}
		return hm;
	}

}
