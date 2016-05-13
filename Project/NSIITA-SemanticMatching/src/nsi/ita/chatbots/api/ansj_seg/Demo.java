package nsi.ita.chatbots.api.ansj_seg;

import java.util.List;
import java.util.Scanner;

import nsi.ita.chatbots.function.PhrasesAnalysis;

import org.ansj.dic.LearnTool;
import org.ansj.domain.Nature;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

public class Demo {

//	public static void main(String[] args) {
//		
//		List<Term> parse = NlpAnalysis.parse("寝室关灯时间");
//	    for(int i=0;i<parse.size();i++){
//	    	System.out.println(parse.get(i));
//	    }
//		Main_Ansj_seg as = new Main_Ansj_seg();
//		System.out.println(as.Analysis(s1));
//		System.out.println(as.Analysis(s2));
//		System.out.println(as.Analysis(s1+s2));
//	}
//}
	
	
	public static void main(String[] args) {
        // 增加新词,中间按照'\t'隔开
        UserDefineLibrary.insertWord("ansj中文分词", "教师.list", 1000);
        List<Term> terms = ToAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!");
        System.out.println("增加新词例子:" + terms);
         //删除词语,只能删除.用户自定义的词典.
        UserDefineLibrary.removeWord("ansj中文分词");
        terms = ToAnalysis.parse("我觉得ansj中文分词是一个不错的系统!我是王婆!");
        System.out.println("删除用户自定义词典例子:" + terms);
	}
}
//    public static void main(String[] args) {
//
//        //构建一个新词学习的工具类。这个对象。保存了所有分词中出现的新词。出现次数越多。相对权重越大。
//        LearnTool learnTool = new LearnTool() ;
//
//        //进行词语分词。也就是nlp方式分词，这里可以分多篇文章
//        NlpAnalysis.parse("李贵杰，张志波，张宁，徐书迪，石骐源，王博，王鑫，刘伟松，田晓光",learnTool) ;
//
//        //取得学习到的topn新词,返回前10个。这里如果设置为0则返回全部
//        System.out.println(learnTool.getTopTree(10));
//
//        //只取得词性为Nature.NR的新词
//        System.out.println(learnTool.getTopTree(10,Nature.NR));
//
//    }

