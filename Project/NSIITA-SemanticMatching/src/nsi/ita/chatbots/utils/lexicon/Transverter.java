package nsi.ita.chatbots.utils.lexicon;

import java.util.ArrayList;
import java.util.List;

import nsi.ita.chatbots.bean.AnalyticElementBean;
import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.config.library.Lexicon_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

import org.ansj.domain.Term;

public class Transverter {

	public List<AnalyticElementBean> Trans_Ansj(List<Term> parse){
		List<AnalyticElementBean> rst = new ArrayList<AnalyticElementBean>();
		int MergeCount = 0;
		for(int i=0;i<parse.size();i++){
			AnalyticElementBean ae = new AnalyticElementBean();
			ae.word = parse.get(i).getName();
			ae.index = i - MergeCount;
			ae.word_tag_meaning = parse.get(i).getNatureStr();
			
			//替换用户自定义新意词
			if(Lexicon_Config.CustomWords.containsKey(ae.word)){
				ae.word_tag_meaning = Lexicon_Config.CustomWords.get(ae.word);
			}
			
			//合并相连的名称(.n)
			String Tag = "n";
			if(rst.size()>0 && rst.get(rst.size()-1).word_tag_meaning.equals(Tag) && ae.word_tag_meaning.equals(Tag)){
				//合并后的词语
				String newWord = rst.get(rst.size()-1).word + ae.word;
				String FileName = Tag + "." + newWord + ".dic";
				
				//TODO 这里有问题！
				String Dic_Context = new RWfile_Util(Library_Config.Synonyms_Path).ReadFile(FileName)[0];
				
				//该词语是否存在于词库
				if(Dic_Context != null){
					ae.word = newWord;
					ae.index--;
					MergeCount++;
					rst.remove(rst.size()-1);
				}
			}
			
			rst.add(ae);
		}
		
		return rst;
	}

}
