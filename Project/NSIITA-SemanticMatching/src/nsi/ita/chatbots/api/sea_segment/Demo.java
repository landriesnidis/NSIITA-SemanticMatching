package nsi.ita.chatbots.api.sea_segment;

import java.util.List;

import nsi.ita.chatbots.bean.AnalyticElementBean;


public class Demo {
	
	public static void main(String[] args) {
		Sae_Segment ss = new Sae_Segment();
		
		ss.setTEXT("新浪中文分词服务");
		List<AnalyticElementBean> la = ss.exeAnalysis();
		
		for(int i=0;i<la.size();i++){
			System.out.println(la.get(i).word + "\t" + la.get(i).word_tag + "\t" + la.get(i).index + "\t" + la.get(i).word_tag_meaning);
		}
	}
}
