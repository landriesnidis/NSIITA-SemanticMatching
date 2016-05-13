package nsi.ita.chatbots.config.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;

import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;

public class Lexicon_Config {
	
	//记录已加载的词库名称
	public static List<String> Lexicons = new ArrayList<String>();
	
	//已导入词库中所有的基本词列表
	public static HashMap<String, String> CustomWords = new HashMap<String,String>();
		
	/**
	 * 初始化词库集
	 */
	public static void Init(){
		
		//文件夹路径及文件名
		String Path = Library_Config.Lexicon_Path;
						
		//读取文件内容
		String[] lines = new RWfile_Util(Path).ReadFile("_config.dat");
		
		
		//加载词库
		for(String fileName:lines){
			if(!loadExistingFileFromLexicon(fileName)){
				System.out.println("\t警告：找不到词库 " + fileName);
			}else{
				System.out.println("\t词库 " + fileName + " 加载成功！");
			}
		}
	}
	
	
	/**
	 * 加载已存在的文件到词库中
	 * @param fileName
	 * @return
	 */
	public static boolean loadExistingFileFromLexicon(String fileName){
		
		//判断词库是否已加载过
		if(Lexicons.contains(fileName)){
			return true;
		}
		
		//文件夹路径及文件名
		String Path = Library_Config.Lexicon_Path;
				
		//读取文件内容
		String[] lines = new RWfile_Util(Path).ReadFile(fileName);
		if(lines[0] == null){
			System.out.println("词库 " + fileName + " 导入失败！");
			return false;
		}
		
		//导入词典内容
		for(int i=0;i<lines.length;i++){
			try{
				
				//判断新增词语是否是基本词(即：分词结果是否由单个元素组成)
				String[] line_split = lines[i].split("\\|");
				int weight;
				String word;
				
				//判断配置文件中的单条数据是否完整
				//完整数据由：  词语|权值	两部分组成
				//如果完整则正常导入
				//如果数据缺失（即无权值参数）则使用默认值
				if(line_split.length >= 2){
					word = line_split[0];
					weight = Integer.parseInt(line_split[1]);
				}else{
					word = lines[i];
					weight = Library_Config.LexiconConfiguratioFile_DefaultWeights;
				}

				List<Term> parse = ToAnalysis.parse(word);
				if(parse.size() > 1){
					//复合新词加入分词系统
					UserDefineLibrary.insertWord(word, fileName, weight);
				}else{
					//新意单词加入自定义词表
					CustomWords.put(word, fileName);
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
				System.out.println("警告：词库 " + fileName + " 第 " + (i+1) + " 行数据错误！");
			}
		}
		
		//记录加载完毕的词库
		Lexicons.add(fileName);
		return true;
	}

}
