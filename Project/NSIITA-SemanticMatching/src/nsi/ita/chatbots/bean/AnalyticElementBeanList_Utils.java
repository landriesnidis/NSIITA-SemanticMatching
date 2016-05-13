package nsi.ita.chatbots.bean;

import java.util.ArrayList;
import java.util.List;

public class AnalyticElementBeanList_Utils {
	
	//列表AnalyticElementBean
	List<AnalyticElementBean> lstWords = null;
	
	/**
	 * @return lstWords
	 */
	public List<AnalyticElementBean> getLstWords() {
		return lstWords;
	}

	/**
	 * @param lstWords 要设置的 lstWords
	 */
	public void setLstWords(List<AnalyticElementBean> lstWords) {
		this.lstWords = lstWords;
	}

	/**
	 * 构造函数，初始化列表Bean
	 * @param lstWords
	 */
	public AnalyticElementBeanList_Utils(List<AnalyticElementBean> lstWords){
		this.lstWords = lstWords;
	}
	
	/**
	 * 判断列表中是否存在指定词语和对应词性
	 * @param word
	 * @param type
	 * @return
	 */
	public boolean AEBean_IsContains(String word,String type){
		for(int i=0;i<lstWords.size();i++){
			String mWord = lstWords.get(i).word;
			String mType = lstWords.get(i).word_tag_meaning;
			
			if(mWord.equals(word) && mType.equals(type)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查找列表 lstBean 中，第 Tag_index 个 词性为 Tag 的词语
	 * @param lstBean
	 * @param Tag
	 * @param Tag_index
	 * @return
	 */
	public String getWordByTag(List<AnalyticElementBean> lstBean, String Tag, int Tag_index){
		
		//索引
		int index =0;
		
		//查找对应词性的词语
		if(lstBean == null) return null;
		for(int i=0;i<lstBean.size();i++){
			if(lstBean.get(i).word_tag_meaning.equals(Tag)){
				if(Tag_index == index){
					return lstBean.get(i).word;
				}else{
					index++;
				}
			}
		}
		return null;
	}
	
	/**
	 * 查找List<AnalyticElementBean>中的 词性Tag1 + Tag2 形式的句子，返回List<AnalyticElementBean>类型
	 * @param lstWords
	 * @return
	 */
	public List<AnalyticElementBean> get_TagPhrasesAsBean(String Tag1,String Tag2){
		
		//动名词组合
		List<AnalyticElementBean> Phrases = new ArrayList<AnalyticElementBean>();
		
		//取词开始标志
		boolean IsBegin = false, IsEnd = false;
		
		//查找句子
		if(lstWords == null) return null;
		for(int i=0;i<lstWords.size();i++){
			//查找动词
			if(lstWords.get(i).word_tag_meaning.equals(Tag1) || IsBegin == true){
				//从最接近名词的动词开始截句
				if(IsBegin == false)
					IsBegin = true;
				
				if(lstWords.get(i).word_tag_meaning.equals(Tag1) && IsBegin == true)
					Phrases.clear();
				
				Phrases.add(lstWords.get(i));
			}
			//查找名词
			if(lstWords.get(i).word_tag_meaning.equals(Tag2) && IsBegin == true){
				if(i+1 < lstWords.size())
					if(lstWords.get(i+1).word_tag_meaning.equals(Tag2))
						continue;
				IsEnd = true;
				break;
			}
		}
		
		//如果未查到完整语句则返回空
		if(IsEnd == false)
			return null;
		
		return Phrases;
	}

	/**
	 * 查找List<AnalyticElementBean>中的V+N形式的句子，返回String类型
	 * @param lstWords
	 * @return
	 */
	public String get_TagPhrasesAsString(String Tag1,String Tag2){
		
		//截取动名词首尾的句子
		lstWords = get_TagPhrasesAsBean(Tag1,Tag2);
		
		//是否存在
		if(lstWords == null)
			return null;
		
		//组成字符串
		String Phrases = "";
		for(int i=0;i<lstWords.size();i++){
			Phrases += lstWords.get(i).word;
		}
		
		return Phrases;
	}
}
