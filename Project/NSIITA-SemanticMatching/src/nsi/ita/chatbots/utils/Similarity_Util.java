package nsi.ita.chatbots.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import nsi.ita.chatbots.bean.AnalyticElementBean;
import nsi.ita.chatbots.utils.lexicon.DictionaryMatching_Util;
import nsi.ita.chatbots.utils.lexicon.Transverter;

public class Similarity_Util {
	
	//权值表		--		权值数据来自对三十条测试数据的分析
	final HashMap<String,Integer> lstWeight = new HashMap<String,Integer>();
	
	//
	public Similarity_Util(){
		InitDict();
	}
	
	public void InitDict(){
		lstWeight.put("n",33+13);
		lstWeight.put("v",23+6+2+2);
		lstWeight.put("d",8);
		lstWeight.put("r",8+4+2);
		lstWeight.put("u",5+4);
		lstWeight.put("y",4);
		lstWeight.put("vn",4);
		lstWeight.put("a",3);
		lstWeight.put("m",2);
	}
	
	/**
	 * 将通用格式构成的文字列表转换为Map<文字,权值>的格式
	 * @param lst1
	 * @param lst2
	 * @return
	 */
	public double similarity(List<AnalyticElementBean> lst1,List<AnalyticElementBean>lst2){

		new DictionaryMatching_Util().FindDictionary(lst1);
		new DictionaryMatching_Util().FindDictionary(lst2);
		
		Map<String, Integer> dict1 = transLstAEB2Map(lst1);
		Map<String, Integer> dict2 = transLstAEB2Map(lst2);
		return similarity(dict1, dict2);
	}
	
	/**
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public double similarity(String str1,String str2){
		//分词结果
		List<Term> parse1 = BaseAnalysis.parse(str1);
		List<Term> parse2 = BaseAnalysis.parse(str2);
		//分词结果转换为通用格式
		List<AnalyticElementBean> lst1 = new Transverter().Trans_Ansj(parse1);
		List<AnalyticElementBean> lst2 = new Transverter().Trans_Ansj(parse2);
		
		return similarity(lst1, lst2);
	}
	
	private Map<String, Integer> transLstAEB2Map(List<AnalyticElementBean> LstBean){
		Map<String, Integer> dict = new HashMap<String, Integer>();
		for(int i=0;i<LstBean.size();i++){
			int mWeight = 0;
			if(lstWeight.containsKey(LstBean.get(i).word_tag_meaning)){
				mWeight = lstWeight.get(LstBean.get(i).word_tag_meaning);
			}
			dict.put(LstBean.get(i).word, mWeight);
		}
		return dict;
	}

	/**
	* 计算带有权重的相似度
	* 使用余弦定理，相似度算法的实现
	* @param dict1：Map<String,Integer>：Map<特征词,权重值>
	* @param dict2：Map<String,Integer>：Map<特征词,权重值>
	* @return
	*/ 
	private double similarity(Map<String, Integer> dict1, Map<String, Integer> dict2) {
		double similarity = 0.0, numerator = 0.0, denominator1 = 0.0, denominator2 = 0.0;
		if (dict1.size() == 0 || dict2.size() == 0) {
			similarity = 0.0;
			return similarity;
		}
		int value1 = 0;
		int value2 = 0;
		for (String keyword : dict1.keySet()) {
			value1 = dict1.get(keyword);
			if (dict2.containsKey(keyword)) {
				value2 = dict2.get(keyword);
				dict2.remove(keyword);
			} else {
				value2 = 0;
			}

			numerator += value1 * value2;
			denominator1 += value1 * value1;
			denominator2 += value2 * value2;
		}

		for (String keyword : dict2.keySet()) {
			value2 = dict2.get(keyword);
			denominator2 += value2 * value2;
		}
		similarity = numerator / (Math.sqrt(denominator1 * denominator2));
		
		if(Double.isNaN(similarity)){
			return 0.0;
		}else{
			return similarity;
		}
	}
}
