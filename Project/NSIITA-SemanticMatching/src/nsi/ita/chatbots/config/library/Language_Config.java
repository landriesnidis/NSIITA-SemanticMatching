package nsi.ita.chatbots.config.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nsi.ita.chatbots.bean.LanguageBean;
import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class Language_Config {

	//语言包
	public static HashMap<String,List<LanguageBean>> LanguagePackage = new HashMap<String, List<LanguageBean>>();
	
	/**
	 * 初始化语言包
	 */
	public static void Init() {

		// 文件夹路径及文件名
		String Path = Library_Config.Language_Path;

		// 读取文件内容
		String[] lines = new RWfile_Util(Path).ReadFile("_config.dat");

		// 循环加载语言包
		for (String fileName : lines) {
			try{
				//打开语言包内容
				String[] lang = new RWfile_Util(Path).ReadFile(fileName);
				List<LanguageBean> langs = new ArrayList<LanguageBean>();
				for(String strlang : lang){
					//语言包内单行的格式为：原句|肯定句|否定句
					String[] mLine = strlang.split("\\|");
					langs.add(new LanguageBean(fileName,mLine[0],mLine[1],mLine[2]));
				}
				
				//将该语言包加入总包中
				LanguagePackage.put(fileName, langs);
				System.out.println("\t语言包 " + fileName + " 加载成功！");
			}catch(Exception ex){
				System.out.println("\t语言包 " + fileName + " 无法加载！");
			}
		}

	}
}
