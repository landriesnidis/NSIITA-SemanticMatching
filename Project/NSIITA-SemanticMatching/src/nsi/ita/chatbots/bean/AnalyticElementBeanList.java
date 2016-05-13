package nsi.ita.chatbots.bean;

import java.util.ArrayList;
import java.util.List;

import nsi.ita.chatbots.utils.lexicon.Transverter;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

public class AnalyticElementBeanList extends ArrayList<AnalyticElementBean> {

	private static final long serialVersionUID = -4157021210424743445L;

	public AnalyticElementBeanList(String Phrases){
		
	}
	
	
	@Override
	public int size() {
		// TODO 自动生成的方法存根
		return super.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO 自动生成的方法存根
		return super.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		// TODO 自动生成的方法存根
		return super.contains(o);
	}

	@Override
	public int indexOf(Object o) {
		// TODO 自动生成的方法存根
		return super.indexOf(o);
	}

	@Override
	public AnalyticElementBean get(int index) {
		// TODO 自动生成的方法存根
		return super.get(index);
	}

	@Override
	public AnalyticElementBean set(int index, AnalyticElementBean element) {
		// TODO 自动生成的方法存根
		return super.set(index, element);
	}

	 @Override
	public boolean add(AnalyticElementBean e) {
		// TODO 自动生成的方法存根
		return super.add(e);
	}

	 @Override
	public AnalyticElementBean remove(int index) {
		// TODO 自动生成的方法存根
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		// TODO 自动生成的方法存根
		return super.remove(o);
	}

	@Override
	public void clear() {
		// TODO 自动生成的方法存根
		super.clear();
	}
	
	/**
	 * 判断列表中是否存在指定词语和对应词性
	 * @param word
	 * @param type
	 * @return
	 */
	public boolean AEBean_IsContains(String word,String type){
		for(int i=0;i<super.size();i++){
			String mWord = super.get(i).word;
			String mType = super.get(i).word_tag_meaning;
			
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
	public String getWordByTag(String Tag, int Tag_index){
		
		//索引
		int index =0;
		
		//查找对应词性的词语
		if(super.isEmpty()) return null;
		for(int i=0;i<super.size();i++){
			if(super.get(i).word_tag_meaning.equals(Tag)){
				if(Tag_index == index){
					return super.get(i).word;
				}else{
					index++;
				}
			}
		}
		return null;
	}
	
	/**
	 * 查找字符串  Phrases 中，第 Tag_index 个 词性为 Tag 的词语
	 * @param Phrases
	 * @param Tag
	 * @param Tag_index
	 * @return
	 */
	public String getWordByTag(String Phrases, String Tag, int Tag_index){
		//分词结果
		List<Term> parse = BaseAnalysis.parse(Phrases);
				
		//分词结果转换为通用格式
		super.addAll(new Transverter().Trans_Ansj(parse));
		
		return getWordByTag(Tag, Tag_index);
	}
	
}
