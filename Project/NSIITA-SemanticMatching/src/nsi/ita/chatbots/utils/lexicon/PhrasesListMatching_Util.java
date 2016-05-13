package nsi.ita.chatbots.utils.lexicon;

import java.util.ArrayList;
import java.util.List;

import nsi.ita.chatbots.bean.PhrasesBean;
import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class PhrasesListMatching_Util {
	
	//列表地址
	private String Path = "";
	
	//列表名称
	private String FileName = "";
	
	//生成结果
	private List<PhrasesBean> lstResult = new ArrayList<PhrasesBean>();

	//项数
	private int intSize;
	
	/**
	 * 构造函数
	 * @param Path
	 */
	public PhrasesListMatching_Util(String fileName){
		
		//文件夹路径及文件名
		this.Path = Library_Config.Phrases_Path;
		this.FileName = fileName;
		
		//读取文件内容
		String[] lines = new RWfile_Util(Path).ReadFile(FileName);
		
		//获取总项数
		intSize = lines.length;
		
		//添加到Bean对象的列表中
		for(int i=0;i<intSize;i++){
			lstResult.add(new PhrasesBean(lines[i]));
		}
	}
	
	/**
	 * 获取Bean列表
	 * @return
	 */
	public List<PhrasesBean> getAllPhrases() {
		return lstResult;
	}
}
