package nsi.ita.chatbots.config.library;

import java.util.ArrayList;
import java.util.List;

import nsi.ita.chatbots.bean.PhrasesBean;
import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.utils.RWfile_Util;
import nsi.ita.chatbots.utils.lexicon.PhrasesListMatching_Util;

public class Phrases_Config {

	// 短语数组
	private static String[] Phrases = null;

	// 短语完整列表
	private static List<PhrasesBean> lstPBean = new ArrayList<PhrasesBean>();

	/**
	 * 初始化短语列表
	 */
	public static void Init() {

		// 文件夹路径及文件名
		String Path = Library_Config.Phrases_Path;

		// 读取文件内容
		String[] lines = new RWfile_Util(Path).ReadFile("_config.dat");

		// 加载词库
		for (String fileName : lines) {
			try{
				lstPBean.addAll(new PhrasesListMatching_Util(fileName).getAllPhrases());
				System.out.println("\t短语集 " + fileName + " 加载成功！");
			}catch(Exception ex){
				System.out.println("\t短语集 " + fileName + " 无法加载！" + ex.getMessage());
			}
		}

		trimArrPhrases();

	}

	/**
	 * 读取短语列表信息，整理短语数组
	 */
	private static void trimArrPhrases() {
		Phrases = null;
		Phrases = new String[lstPBean.size()];
		int i = 0;
		for (PhrasesBean bean : lstPBean) {
			Phrases[i++] = bean.getPhrases();
		}
	}

	/**
	 * Getter and Setter
	 */
	public static String[] getPhrases() {
		return Phrases;
	}

	public static List<PhrasesBean> getLstPBean() {
		return lstPBean;
	}

	public static String[] getParmasByPhrases(String Phrases) {
		if (lstPBean.size() != 0) {
			for (PhrasesBean pb : lstPBean) {
				if (Phrases.equals(pb.getPhrases())) {
					return pb.getParamAll();
				}
			}
		}
		return null;
	}
}
