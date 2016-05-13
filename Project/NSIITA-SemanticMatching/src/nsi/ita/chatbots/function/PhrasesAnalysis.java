package nsi.ita.chatbots.function;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import nsi.ita.chatbots.bean.AnalyticElementBean;
import nsi.ita.chatbots.bean.AnalyticElementBeanList_Utils;
import nsi.ita.chatbots.bean.LanguageBean;
import nsi.ita.chatbots.bean.SimilarBean;
import nsi.ita.chatbots.bean.SimilarBeanList;
import nsi.ita.chatbots.config.Library_Config;
import nsi.ita.chatbots.config.library.Language_Config;
import nsi.ita.chatbots.config.library.Lexicon_Config;
import nsi.ita.chatbots.config.library.Phrases_Config;
import nsi.ita.chatbots.utils.CustomScript_Util;
import nsi.ita.chatbots.utils.Similarity_Util;
import nsi.ita.chatbots.utils.lexicon.DictionaryMatching_Util;
import nsi.ita.chatbots.utils.lexicon.Transverter;

public class PhrasesAnalysis {

	// 分隔符Separator ： 提供项答案时用以分隔
	final String SEP = ";";

	/**
	 * 语句入口
	 * 
	 * @param args
	 * @return
	 */
	public String toPhrasesAnalysis(String args, String languagePkg) {

		// 返回结果
		String parseResults = "";

		// 分词结果
		List<Term> parse = ToAnalysis.parse(args);

		// 分词结果转换为通用格式
		List<AnalyticElementBean> lstWords = new Transverter()
				.Trans_Ansj(parse);

		// 实例化AnalyticElementBean对象的列表的工具类
		AnalyticElementBeanList_Utils AEB_util = new AnalyticElementBeanList_Utils(
				lstWords);

		// 匹配字典
		DictionaryMatching_Util dmUtil = new DictionaryMatching_Util();
		dmUtil.FindDictionary(lstWords);

		// 实例化相似度计算工具类
		Similarity_Util smrUtil = new Similarity_Util();

		// 排序结果
		SimilarBeanList lstSBean = new SimilarBeanList();

		// 循环标准语句表
		for (int i = 0; i < Phrases_Config.getPhrases().length; i++) {
			String temp = Phrases_Config.getPhrases()[i];
			SimilarBean sBean = new SimilarBean();
			sBean.setPhrases(temp);
			
			double sm = smrUtil.similarity(temp, args);
			sBean.setSimilarity(sm);
			System.out.println(String.format("\t对比词语 %s 和 %s 相似度为：" + sm,temp, args));
			
			lstSBean.add(sBean);
		}

		// 对比相似度差值以确定返回内容
		// …………相似度最大值的Bean对象为最佳结果
		// …………与最佳结果相似度大于 0% 、小于等于 1% 的为相似结果
		// …………与最佳结果相似度大于 1% 、小于等于 5% 的为可能结果
		// 注：相似度区间范围应该由相似度的配置信息中读入
		List<SimilarBean> lstLikeness_Rst = null;
		List<SimilarBean> lstPossible_Rst = null;
		SimilarBean bestBean = lstSBean.getBeanByMaxSimilarity();
		if (bestBean != null) {
			lstLikeness_Rst = lstSBean.getBeanListByLimitSimilarity(bestBean,
					0.1, 0.0);
			lstPossible_Rst = lstSBean.getBeanListByLimitSimilarity(bestBean,
					0.5, 0.1);
		}

		parseResults += "最佳答案：\n";
		String BestResult = UseLanguagePackage(bestBean, languagePkg, AEB_util,
				lstWords);
		parseResults += BestResult;
		parseResults += "\n\n";

		if (lstLikeness_Rst != null && lstLikeness_Rst.size()>0) {
			parseResults += "相似答案：\n";
			for (SimilarBean likeBean : lstLikeness_Rst) {
				parseResults += UseLanguagePackage(likeBean, languagePkg,
						AEB_util, lstWords);
				parseResults += "\n";
			}
			parseResults += "\n\n";
		}

		if (lstPossible_Rst != null && lstPossible_Rst.size()>0) {
			parseResults += "可能答案：\n";
			for (SimilarBean psbBean : lstPossible_Rst) {
				parseResults += UseLanguagePackage(psbBean, languagePkg,
						AEB_util, lstWords);
				parseResults += "\n";
			}
		}

		// 如果仍无结果则返回无法匹配
		if ("".equals(BestResult))
			return "无法识别";

		return parseResults;
	}

	/**
	 * 使用语言包
	 * 
	 * @param sBean
	 * @param languagePkg
	 * @param AEB_util
	 * @param lstWords
	 * @return
	 */
	private String UseLanguagePackage(SimilarBean sBean, String languagePkg,
			AnalyticElementBeanList_Utils AEB_util,
			List<AnalyticElementBean> lstWords) {
		// 将结果拼成字符串
		String parseResults = "";
		if (sBean != null) {
			String bestPhrases = sBean.getPhrases();
			String[] bestParam = Phrases_Config.getParmasByPhrases(bestPhrases);

			String[] arrParam;
			if (bestParam != null) {
				arrParam = new String[bestParam.length];
				int index = 0;
				for (String s : bestParam)
					arrParam[index++] = s;
			} else {
				arrParam = null;
			}

			// 判断语言包功能的开关是否打开了
			if (Library_Config.LanguagePackageFunction_Switch) {
				
				if (languagePkg == null) {
					
					// 当未指定一个语言包的时候
					System.out.println("\t目标语句为： " + bestPhrases);
					// 遍历存放语言包的HashMap
					String PackageName = null;
					HashMap<String, List<LanguageBean>> map = Language_Config.LanguagePackage;
					Iterator<Entry<String, List<LanguageBean>>> iter = map
							.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<String, List<LanguageBean>> entry = iter.next();
						String key = entry.getKey();
						List<LanguageBean> val = entry.getValue();

						System.out.println("\t当前查找的语言包: " + key);

						// 遍历每个语言包的各项
						for (LanguageBean bean : val) {
							System.out.println("\t\t" + bean.Phrase);
							if (bean.Phrase.equals(bestPhrases)) {
								PackageName = key;
								System.out.println("\t已找到符合的语言包： "
										+ PackageName);
								break;
							}
						}

						// 如果找到了则跳出遍历
						if (PackageName != null) {
							languagePkg = PackageName.replace(".list", "");
							break;
						}
					}
					

				} 

				if (languagePkg != null){

					// 使用语言包(这里使用了指定语言包！！！)
					List<LanguageBean> lstLBean = Language_Config.LanguagePackage
							.get(languagePkg + ".list");
					boolean isFind = false;

					for (LanguageBean lb : lstLBean) {
						if (lb.Phrase.equals(bestPhrases)) {
							isFind = true;
							// 如果参数不为空则循环替换参数
							if (arrParam != null) {
								for (int i = 0; i < arrParam.length; i++) {
									arrParam[0] = AEB_util.getWordByTag(
											lstWords, arrParam[i], 0);
								}
							}
							String slb = lb.addParamToAffirmative(arrParam);

							// 判断结果是否为空
							if (slb != null)
								parseResults += new CustomScript_Util(slb)
										.execute();
							else
								parseResults += lb.Negative;
							break;
						}
					}

					// 不使用语言包
					if (isFind == false) {
						String Params = "";
						if (arrParam != null)
							for (int i = 0; i < arrParam.length; i++) {
								if (Lexicon_Config
										.loadExistingFileFromLexicon(arrParam[i])) {
									Params += ("|" + AEB_util.getWordByTag(
											lstWords, arrParam[i], 0));
								} else {
									Params += ("|" + "-");
								}
							}
						parseResults += (sBean.getPhrases() + Params);
					}

				}

			} else {
				// 系统关闭了语言包功能
				// 拼字符串
				parseResults += bestPhrases;
				if (arrParam != null)
					for (String p : arrParam) {
						parseResults += ("|" + p);
					}
			}

		}
		return parseResults;
	}
}
