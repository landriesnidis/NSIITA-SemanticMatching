package nsi.ita.chatbots.api.sea_segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nsi.ita.chatbots.bean.AnalyticElementBean;
import nsi.ita.chatbots.utils.HTTP_Request;

/**
 * 通过调用新浪云计算 - 中文分词服务(SaeSegment) 实现对中文语义的解析
 * @author NSI-ITA Landriesnidis
 */
public class Sae_Segment {
	
	private String API_URL = "http://simonfenci.sinaapp.com/index.php?key=simon&wd=";
	
	private String TEXT = "";
	
	private HashMap<String, String> Analytical_Dictionary = new HashMap<String, String>();
	
	/**
	 * 构造函数
	 * @param Text
	 */
	public Sae_Segment(){
		Init_Dictionary();
	}
	
	public Sae_Segment(String Text){
		Init_Dictionary();
		TEXT = Text;
	}
	
	/**
	 * 获取要解析语义的文本
	 * @return
	 */
	public String getTEXT() {
		return TEXT;
	}
	
	/**
	 * 设置要解析语义的文本
	 * @param text
	 */
	public void setTEXT(String text) {
		TEXT = text;
	}
	
	/**
	 * 获取解析后的文本
	 * @return
	 */
	private String GetParseText(){
		return HTTP_Request.SendHttp_Get(API_URL + TEXT);
	}
	
	/**
	 * 解析API返回的字符串，存入AnalyticElementBean类型数组并返回
	 * @return
	 */
	public List<AnalyticElementBean> exeAnalysis(){
		//获取数据
		String text = GetParseText();
		
		//添加过滤(预处理)
		text = text.replace(" ", "");
		text = text.substring("Array(".length());
		String[] arrWord = text.split("\\)");
		
		//存入列表并返回
		ArrayList<AnalyticElementBean> laeb = new ArrayList<AnalyticElementBean>();
		for(int i=0;i<arrWord.length;i++){
			String s = arrWord[i];
			AnalyticElementBean aeb = new AnalyticElementBean();
			aeb.word = s.substring(s.indexOf("[word]=>") + "[word]=>".length(), s.indexOf("[word_tag]"));
			aeb.word_tag = Integer.parseInt(s.substring(s.indexOf("[word_tag]=>") + "[word_tag]=>".length(), s.indexOf("[index]")));
			aeb.index = Integer.parseInt(s.substring(s.indexOf("[index]=>") + "[index]=>".length()));
			aeb.word_tag_meaning = Analytical_Dictionary.get("" + aeb.word_tag);
			if(aeb.word_tag_meaning == null)
				aeb.word_tag_meaning = "未录入";
			
			laeb.add(aeb);
		}
		return laeb;
	}
	
	/**
	 * 初始化解析词典
	 */
	private void Init_Dictionary(){
		
		
//		Analytical_Dictionary.put("0", "不知道");
//		Analytical_Dictionary.put("10", "形容词");
//		Analytical_Dictionary.put("20", "区别词");
//		Analytical_Dictionary.put("30", "连词");
//		Analytical_Dictionary.put("31", "体词连接");
//		Analytical_Dictionary.put("32", "分句连接");
//		Analytical_Dictionary.put("40", "副词");
//		Analytical_Dictionary.put("41", "副词(不)");
//		Analytical_Dictionary.put("42", "副词(没)");
//		Analytical_Dictionary.put("50", "叹词");
//		Analytical_Dictionary.put("60", "方位词");
//		Analytical_Dictionary.put("61", "方位短语");
//		Analytical_Dictionary.put("62", "方位短语");
//		Analytical_Dictionary.put("63", "方位短语");
//		Analytical_Dictionary.put("64", "方位短语");
//		Analytical_Dictionary.put("70", "前接成分");
//		Analytical_Dictionary.put("71", "数词前缀");
//		Analytical_Dictionary.put("72", "时间词前缀");
//		Analytical_Dictionary.put("73", "姓氏");
//		Analytical_Dictionary.put("74", "姓氏");
//		Analytical_Dictionary.put("80", "后接成分");
//		Analytical_Dictionary.put("81", "数词后缀");
//		Analytical_Dictionary.put("82", "时间词后缀");
//		Analytical_Dictionary.put("83", "名词后缀");
//		Analytical_Dictionary.put("84", "处所词后缀");
//		Analytical_Dictionary.put("85", "状态词后缀");
//		Analytical_Dictionary.put("86", "状态词后缀");
//		Analytical_Dictionary.put("87", "状态词后缀");
//		Analytical_Dictionary.put("90", "数词");
//		Analytical_Dictionary.put("95", "名词");
//		Analytical_Dictionary.put("96", "人名");
//		Analytical_Dictionary.put("97", "机构团体");
//		Analytical_Dictionary.put("98", "");
//		Analytical_Dictionary.put("99", "机构团体名");
//		Analytical_Dictionary.put("100", "其他专名");
//		Analytical_Dictionary.put("101", "名处词");
//		Analytical_Dictionary.put("102", "地名");
//		Analytical_Dictionary.put("103", "数词开头的名词");
//		Analytical_Dictionary.put("104", "区别/代词开头的名词");
//		Analytical_Dictionary.put("107", "拟声词");
//		Analytical_Dictionary.put("108", "介词");
//		Analytical_Dictionary.put("110", "量词");
//		Analytical_Dictionary.put("111", "动量词");
//		Analytical_Dictionary.put("112", "时间量词");
//		Analytical_Dictionary.put("113", "货币量词");
//		Analytical_Dictionary.put("120", "代词");
//		Analytical_Dictionary.put("121", "副词性代词");
//		Analytical_Dictionary.put("122", "数词性代词");
//		Analytical_Dictionary.put("123", "名词性代词");
//		Analytical_Dictionary.put("124", "处所词性代词");
//		Analytical_Dictionary.put("125", "时间词性代词");
//		Analytical_Dictionary.put("126", "谓词性代词");
//		Analytical_Dictionary.put("127", "区别词性代词");
//		Analytical_Dictionary.put("130", "处所词");
//		Analytical_Dictionary.put("131", "处所词");
//		Analytical_Dictionary.put("132", "时间词");
//		Analytical_Dictionary.put("133", "时间专指");
//		Analytical_Dictionary.put("140", "助词");
//		Analytical_Dictionary.put("141", "定语助词");
//		Analytical_Dictionary.put("142", "状语助词");
//		Analytical_Dictionary.put("143", "补语助词");
//		Analytical_Dictionary.put("144", "谓词后助词");
//		Analytical_Dictionary.put("145", "体词后助词");
//		Analytical_Dictionary.put("146", "助词");
//		Analytical_Dictionary.put("150", "标点符号");
//		Analytical_Dictionary.put("151", "顿号");
//		Analytical_Dictionary.put("152", "句号");
//		Analytical_Dictionary.put("153", "分句尾标点");
//		Analytical_Dictionary.put("154", "搭配型标点左部");
//		Analytical_Dictionary.put("155", "搭配型标点右部");
//		Analytical_Dictionary.put("156", "中缀型符号");
//		Analytical_Dictionary.put("160", "语气词");
//		Analytical_Dictionary.put("170", "及物动词");
//		Analytical_Dictionary.put("171", "不及物谓词");
//		Analytical_Dictionary.put("172", "动补结构动词");
//		Analytical_Dictionary.put("173", "动词“是”");
//		Analytical_Dictionary.put("174", "动词“有”");
//		Analytical_Dictionary.put("175", "趋向动词");
//		Analytical_Dictionary.put("176", "助动词");
//		Analytical_Dictionary.put("180", "状态词");
//		Analytical_Dictionary.put("190", "语素字");
//		Analytical_Dictionary.put("191", "名词语素");
//		Analytical_Dictionary.put("192", "动词语素");
//		Analytical_Dictionary.put("193", "处所词语素");
//		Analytical_Dictionary.put("194", "时间词语素");
//		Analytical_Dictionary.put("195", "状态词语素");
//		Analytical_Dictionary.put("196", "状态词语素");
//		Analytical_Dictionary.put("200", "不及物谓词");
//		Analytical_Dictionary.put("201", "数量短语");
//		Analytical_Dictionary.put("202", "代量短语");
//		Analytical_Dictionary.put("210", "副形词");
//		Analytical_Dictionary.put("211", "名形词");
//		Analytical_Dictionary.put("212", "副动词");
//		Analytical_Dictionary.put("213", "名动词");
//		Analytical_Dictionary.put("230", "空格");
		
		
		Analytical_Dictionary.put("0", "不知道");
		Analytical_Dictionary.put("10", "形容词");
		Analytical_Dictionary.put("20", "区别词");
		Analytical_Dictionary.put("30", "连词");
		Analytical_Dictionary.put("31", "体词连接");
		Analytical_Dictionary.put("32", "分句连接");
		Analytical_Dictionary.put("40", "副词");
		Analytical_Dictionary.put("41", "副词(不)");
		Analytical_Dictionary.put("42", "副词(没)");
		Analytical_Dictionary.put("50", "叹词");
		Analytical_Dictionary.put("60", "方位词");
		Analytical_Dictionary.put("61", "方位短语(处所词+方位词)");
		Analytical_Dictionary.put("62", "方位短语(名词+方位词“地上”)");
		Analytical_Dictionary.put("63", "方位短语(动词+方位词“取前”)");
		Analytical_Dictionary.put("64", "方位短语(动词+方位词“取前”)");
		Analytical_Dictionary.put("70", "前接成分");
		Analytical_Dictionary.put("71", "数词前缀(“数”---数十)");
		Analytical_Dictionary.put("72", "时间词前缀(“公元”“明永乐”)");
		Analytical_Dictionary.put("73", "姓氏");
		Analytical_Dictionary.put("74", "姓氏");
		Analytical_Dictionary.put("80", "后接成分");
		Analytical_Dictionary.put("81", "数词后缀(“来”--,十来个)");
		Analytical_Dictionary.put("82", "时间词后缀(“初”“末”“时”)");
		Analytical_Dictionary.put("83", "名词后缀(“们”)");
		Analytical_Dictionary.put("84", "处所词后缀(“苑”“里”)");
		Analytical_Dictionary.put("85", "状态词后缀(“然”)");
		Analytical_Dictionary.put("86", "状态词后缀(“然”)");
		Analytical_Dictionary.put("87", "状态词后缀(“然”)");
		Analytical_Dictionary.put("90", "数词");
		Analytical_Dictionary.put("95", "名词");
		Analytical_Dictionary.put("96", "人名(“如：毛泽东”)");
		Analytical_Dictionary.put("97", "机构团体(“团”的声母为t，名词代码n和t并在一起。“公司”)");
		Analytical_Dictionary.put("98", "");
		Analytical_Dictionary.put("99", "机构团体名(北大)");
		Analytical_Dictionary.put("100", "其他专名(“专”的声母的第1个字母为z，名词代码n和z并在一起。)");
		Analytical_Dictionary.put("101", "名处词");
		Analytical_Dictionary.put("102", "地名(名处词专指：“中国”)");
		Analytical_Dictionary.put("103", "n-m,数词开头的名词(三个学生)");
		Analytical_Dictionary.put("104", "n-rb,以区别词/代词开头的名词(该学校，该生)");
		Analytical_Dictionary.put("107", "拟声词");
		Analytical_Dictionary.put("108", "介词");
		Analytical_Dictionary.put("110", "量词");
		Analytical_Dictionary.put("111", "动量词(“趟”“遍”)");
		Analytical_Dictionary.put("112", "时间量词(“年”“月”“期”)");
		Analytical_Dictionary.put("113", "货币量词(“元”“美元”“英镑”)");
		Analytical_Dictionary.put("120", "代词");
		Analytical_Dictionary.put("121", "副词性代词(“怎么”)");
		Analytical_Dictionary.put("122", "数词性代词(“多少”)");
		Analytical_Dictionary.put("123", "名词性代词(“什么”“谁”)");
		Analytical_Dictionary.put("124", "处所词性代词(“哪儿”)");
		Analytical_Dictionary.put("125", "时间词性代词(“何时”)");
		Analytical_Dictionary.put("126", "谓词性代词(“怎么样”)");
		Analytical_Dictionary.put("127", "区别词性代词(“某”“每”)");
		Analytical_Dictionary.put("130", "处所词(取英语space的第1个字母。“东部”)");
		Analytical_Dictionary.put("131", "处所词(取英语space的第1个字母。“东部”)");
		Analytical_Dictionary.put("132", "时间词(取英语time的第1个字母)");
		Analytical_Dictionary.put("133", "时间专指(“唐代”“西周”)");
		Analytical_Dictionary.put("140", "助词");
		Analytical_Dictionary.put("141", "定语助词(“的”)");
		Analytical_Dictionary.put("142", "状语助词(“地”)");
		Analytical_Dictionary.put("143", "补语助词(“得”)");
		Analytical_Dictionary.put("144", "谓词后助词(“了、着、过”)");
		Analytical_Dictionary.put("145", "体词后助词(“等、等等”)");
		Analytical_Dictionary.put("146", "助词(“所”)");
		Analytical_Dictionary.put("150", "标点符号");
		Analytical_Dictionary.put("151", "顿号(“、”)");
		Analytical_Dictionary.put("152", "句号(“。”)");
		Analytical_Dictionary.put("153", "分句尾标点(“，”“；”)");
		Analytical_Dictionary.put("154", "搭配型标点左部");
		Analytical_Dictionary.put("155", "搭配型标点右部(“》”“]”“）”)");
		Analytical_Dictionary.put("156", "中缀型符号");
		Analytical_Dictionary.put("160", "语气词(取汉字“语”的声母。“吗”“吧”“啦”)");
		Analytical_Dictionary.put("170", "及物动词(取英语动词verb的第一个字母。)");
		Analytical_Dictionary.put("171", "不及物谓词(谓宾结构“剃头”)");
		Analytical_Dictionary.put("172", "动补结构动词(“取出”“放到”)");
		Analytical_Dictionary.put("173", "动词“是”");
		Analytical_Dictionary.put("174", "动词“有”");
		Analytical_Dictionary.put("175", "趋向动词(“来”“去”“进来”)");
		Analytical_Dictionary.put("176", "助动词(“应该”“能够”)");
		Analytical_Dictionary.put("180", "状态词(不及物动词,v-o、sp之外的不及物动词)");
		Analytical_Dictionary.put("190", "语素字");
		Analytical_Dictionary.put("191", "名词语素(“琥”)");
		Analytical_Dictionary.put("192", "动词语素(“酹”)");
		Analytical_Dictionary.put("193", "处所词语素(“中”“日”“美”)");
		Analytical_Dictionary.put("194", "时间词语素(“唐”“宋”“元”)");
		Analytical_Dictionary.put("195", "状态词语素(“伟”“芳”)");
		Analytical_Dictionary.put("196", "状态词语素(“伟”“芳”)");
		Analytical_Dictionary.put("200", "不及物谓词(主谓结构“腰酸”“头疼”)");
		Analytical_Dictionary.put("201", "数量短语(“叁个”)");
		Analytical_Dictionary.put("202", "代量短语(“这个”)");
		Analytical_Dictionary.put("210", "副形词(直接作状语的形容词)");
		Analytical_Dictionary.put("211", "名形词(具有名词功能的形容词)");
		Analytical_Dictionary.put("212", "副动词(直接作状语的动词)");
		Analytical_Dictionary.put("213", "名动词(指具有名词功能的动词)");
		Analytical_Dictionary.put("230", "空格");
	}
	
}