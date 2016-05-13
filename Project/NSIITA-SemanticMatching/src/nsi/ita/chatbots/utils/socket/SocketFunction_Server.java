package nsi.ita.chatbots.utils.socket;

import java.io.File;

import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class SocketFunction_Server {
	
	String[] arr_cmd = null;
	
	/**
	 * 解析命令
	 * @param cmd
	 * @return
	 */
	public String analysis(String cmd){
		
		arr_cmd = cmd.split(" ");
		
		// 接受客户端共享的资源文件
		if(mateOrder("share")){
			//格式：share [type] [filename] [rootword] [mode]
			return share();
		}
		
		
		return "命令 " + arr_cmd[0] + " 无法识别！";
	}
	
	/**
	 * 比较字符串（命令行首单词）
	 * @param str
	 * @return
	 */
	private boolean mateOrder(String str){
		return str.equals(arr_cmd[0].toLowerCase());
	}
	
	/**
	 * 接受客户端共享的资源文件share
	 * @return
	 */
	private String share(){
		try{
			String path = "";
			File file = new File(path + arr_cmd[2]);
			
			if(arr_cmd[1].equals("lexicon")){
				path = Library_Config.Lexicon_Path;
			}else if(arr_cmd[1].equals("phrases")){
				path = Library_Config.Phrases_Path;
			}else if(arr_cmd[1].equals("language")){
				path = Library_Config.Language_Path;
			}else if(arr_cmd[1].equals("synonyms")){
				path = Library_Config.Synonyms_Path;
			}
			
			//文件读写工具
			RWfile_Util rwfu = new RWfile_Util(path,arr_cmd[5].equals("linux"));
			
			boolean isExists = file.exists();
			
			if(arr_cmd[4].equals("0")){
				if(!isExists){
					rwfu.AppendMethod(arr_cmd[2], arr_cmd[3]);
					return "资源文件 " + arr_cmd[1] + " - " + arr_cmd[2] + " 添加成功！";
				}
				return "资源文件 " + arr_cmd[1] + " - " + arr_cmd[2] + " 已存在！";
			}else if(arr_cmd[4].equals("1")){
				return "";
			}else if(arr_cmd[4].equals("2")){
				if(file.delete()){
					rwfu.AppendMethod(arr_cmd[2], arr_cmd[3]);
					return "资源文件 " + arr_cmd[1] + " - " + arr_cmd[2] + " 替换成功！";
				}else{
					return "资源文件 " + arr_cmd[1] + " - " + arr_cmd[2] + " 替换失败，目标文件无法删除！";
				}
			}else{
				return "share共享文件功能 - 参数错误.";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return "资源文件 " + arr_cmd[1] + "" + " 添加时异常：" + ex.getLocalizedMessage();
		}
	}
	
}
