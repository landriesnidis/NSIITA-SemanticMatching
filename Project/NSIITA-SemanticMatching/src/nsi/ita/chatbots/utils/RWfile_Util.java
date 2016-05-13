package nsi.ita.chatbots.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nsi.ita.chatbots.config.File_Config;

/**
 * 读写TXT文件
 * 
 * @author 李贵杰 2015年9月11日16:36:28
 */
public class RWfile_Util {

	// 文件夹路径
	String Path = null;

	/**
	 * 构造函数
	 * 
	 * @param Path
	 */
	public RWfile_Util(String Path) {
		this.Path = Path;
	}
	public RWfile_Util(String Path,boolean isLinux) {
		if(isLinux){
			Path = Path.replace("\\", "/");
		}
		this.Path = Path;
	}

	/**
	 * 功能：Java读取txt文件的内容 步骤： 
	 * 1：先获得文件句柄
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 * 4：一行一行的输出。readline()。 
	 * 备注：需要考虑的是异常情况
	 * @param filePath 文件地址
	 */
	public String[] ReadFile(String FileName) {
		
		//没有参数时默认为过滤注释符
		return ReadFile(FileName, true);
	}
	public String[] ReadFile(String FileName, boolean filterComment) {
		try {
			String encoding = "GBK";
			String filePath = Path + FileName;
			List<String> rst = new ArrayList<String>();
			
			File file = new File(filePath);
			// 判断文件是否存在
			if (file.isFile() && file.exists()) { 
				
				// 考虑到编码格式
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if(!filterComment || (filterComment && !lineTxt.substring(0, File_Config.Comment.length()).equals(File_Config.Comment))){
						rst.add(lineTxt);
					}
						
				}
				read.close();
				
				String[] arrStr = new String[rst.size()];
				for(int i=0;i<arrStr.length;i++){
					arrStr[i] = rst.get(i);
				}
				
				
				return arrStr;
			} else {
				//找不到指定的文件
				String[] arrStr = {null};
				return arrStr;
			}
		} catch (Exception e) {
			//读取文件内容出错
			e.printStackTrace();
			String[] arrStr = {null};
			return arrStr;
		}
	}
	
	/**
	 * 追加文件 - 使用FileWriter 
	 * @param FileName
	 * @param content
	 */
    public boolean AppendMethod(String FileName, String content) {  
        try {  
        	//文件路径
        	String FilePath = Path + FileName;
        	
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            FileWriter writer = new FileWriter(FilePath, true);
            
            //写入内容
            writer.write(content);  
            
            //关闭文件
            writer.close();  
            
            return true;
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;
        }  
    }  

}
