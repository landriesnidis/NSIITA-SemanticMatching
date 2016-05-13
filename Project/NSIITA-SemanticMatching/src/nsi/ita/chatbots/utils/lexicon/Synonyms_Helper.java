package nsi.ita.chatbots.utils.lexicon;

import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class Synonyms_Helper {

	public static void main(String[] args) {
		
		//新词
		String newWord 	= "课";
		//词根
		String rootWord = "课程";
		//词性
		String mType 	= "n";
		
		//文件名
		String FileName = mType + "." + newWord + ".dic";
		//写入并读取
		new RWfile_Util(Library_Config.Synonyms_Path).AppendMethod(FileName, rootWord);
		System.out.println(new RWfile_Util(Library_Config.Synonyms_Path).ReadFile(FileName));
	}
}
