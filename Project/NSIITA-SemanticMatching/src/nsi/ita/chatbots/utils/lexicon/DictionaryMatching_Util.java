package nsi.ita.chatbots.utils.lexicon;

import java.util.List;

import nsi.ita.chatbots.bean.AnalyticElementBean;
import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

public class DictionaryMatching_Util {

	/**
	 * 匹配词根字典
	 * @param parse
	 * @return
	 */
	public List<AnalyticElementBean> FindDictionary(List<AnalyticElementBean> parse){
		for(int i=0;i<parse.size();i++){
			String mWord = parse.get(i).word;
			String mType = parse.get(i).word_tag_meaning;
			String FileName = mType + "." + mWord + ".dic";
			
			//TODO 这里有问题！
			String Dic_Context = new RWfile_Util(Library_Config.Synonyms_Path).ReadFile(FileName)[0];
			
			//该词语是否存在于词库
			if(Dic_Context != null){
				//该词语是否为词根
				if(mWord.equals(Dic_Context))
					continue;
				AnalyticElementBean aeb = new AnalyticElementBean();
				aeb.word = Dic_Context;
				aeb.word_tag_meaning = mType;
				aeb.index = i;
				parse.set(i, aeb);
				//检查一遍，防止是近义词
				i--;
			}
		}
		return parse;
	}
	
	/**
	 * 根据传入的AnalyticElementBean类型参数，找到对应字典中的词根，并返回
	 * @param parse
	 * @return
	 */
	public String FindDictionary(AnalyticElementBean parse){
		String mWord = parse.word;
		String mTag = parse.word_tag_meaning;
		return FindDictionary(mWord, mTag);
	}
	
	/**
	 * 根据传入的词语和指定的词性，找到对应字典中的词根，并返回
	 * @param Word
	 * @param Tag
	 * @return
	 */
	public String FindDictionary(String Word, String Tag){
		String mWord = Word;
		String mTag = Tag;
		String FileName = mTag + "." + mWord + ".dic";
		
		//TODO 这里有问题！
		String Dic_Context = new RWfile_Util(Library_Config.Synonyms_Path).ReadFile(FileName)[0];
		
		if(Dic_Context != null){
			return Dic_Context;
		}
		return "";
	}

}
