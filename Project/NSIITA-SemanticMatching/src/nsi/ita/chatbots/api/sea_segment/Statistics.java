package nsi.ita.chatbots.api.sea_segment;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import nsi.ita.chatbots.bean.AnalyticElementBean;


public class Statistics {

	//词频分析表
	static HashMap<String,Integer> hmFrequency;
	
	//测试语句
	static String[] Sentence_Array = new String[100];
	static int Sentence_Count = 0;
	static Scanner Sentence_Inputer = new Scanner(System.in);
	static final String TAG_Ending = "exit";

	
	
	/**
	 * 主函数（这个还用说么？）
	 * @param args
	 */
	public static void main(String[] args) {
		Input_DefaultValues();
		//Input_TestSentence();
		Data_Collection();
		Output_Result();
	}
	
//	/**
//	 * 输入测试数据到数组中
//	 */
//	private static void Input_TestSentence(){
//		
//		//输入字符串
//		String input_text = null;
//		
//		//循环输入例句，并对例句计数
//		for(Sentence_Count=0;Sentence_Count<Sentence_Array.length;){
//			System.out.println("当前输入第（" + (Sentence_Count+1) + "/100）条例句：");
//			input_text = Sentence_Inputer.next();
//			
//			if(!input_text.equals(TAG_Ending)){
//				Sentence_Array[Sentence_Count] = input_text;
//				Sentence_Count++;
//			}else{
//				break;
//			}
//		}
//	}
	
	private static void Input_DefaultValues(){
		Sentence_Count = 29;
		Sentence_Array[0] = "宿舍Win8电脑上不去网";
		Sentence_Array[1] = "查询成绩";
		Sentence_Array[2] = "我想查一下我的成绩";
		Sentence_Array[3] = "宿舍断电了";
		Sentence_Array[4] = "寝室灯不亮了";
		Sentence_Array[5] = "我得专业课";
		Sentence_Array[6] = "怎么加入IT协会";
		Sentence_Array[7] = "IT协会成员介绍";
		Sentence_Array[8] = "学校可以抽烟吗";
		Sentence_Array[9] = "怎麽样可以申请奖学金";
		Sentence_Array[10] = "学校有哪些社团";
		Sentence_Array[11] = "学生会都有哪些部门";
		Sentence_Array[12] = "自管会都有哪些部门";
		Sentence_Array[13] = "寝室的纱窗没了怎么办";
		Sentence_Array[14] = "寝室钥匙没了找谁";
		Sentence_Array[15] = "我们学校的女生多吗";
		Sentence_Array[16] = "现在大一新生有多少人";
		Sentence_Array[17] = "学校图书馆什么时候能完工";
		Sentence_Array[18] = "一封家书有什么意义";
		Sentence_Array[19] = "院长的电话是多少";
		Sentence_Array[20] = "想投诉找谁";
		Sentence_Array[21] = "学校都有哪些活动";
		Sentence_Array[22] = "食堂饭菜怎么样";
		Sentence_Array[23] = "学校女生多吗";
		Sentence_Array[24] = "老师上课负责吗";
		Sentence_Array[25] = "方向怎么选择";
		Sentence_Array[26] = "学校监控怎么查";
		Sentence_Array[27] = "寝室丢东西怎么办";
		Sentence_Array[28] = "为什么D座一楼住的是男生";
		Sentence_Array[29] = "转换就业是什么";
		
		
	}
	
	/**
	 * 将解析结果存入词频表中
	 */
	private static void Data_Collection(){
		
		//实例化 SAE中文分词服务API 工具类
		Sae_Segment ss = new Sae_Segment();
		
		//实例化解析表
		hmFrequency = new HashMap<String, Integer>();
		
		//解析每一条测试语句
		for(int index=0;index<Sentence_Count;index++){
			//设置语句
			ss.setTEXT(Sentence_Array[index]);
			System.out.println("\nNo." + (index+1) + "：" + Sentence_Array[index]);
			//语句成分列表
			List<AnalyticElementBean> la = ss.exeAnalysis();
			//操作语句中每一个成分
			for(int i=0;i<la.size();i++){
				System.out.println(la.get(i).word + "\t" 
							+ la.get(i).word_tag + "\t" 
							+ la.get(i).index + "\t" 
							+ la.get(i).word_tag_meaning);
				if(!hmFrequency.containsKey(la.get(i).word_tag_meaning)){
					hmFrequency.put(la.get(i).word_tag_meaning, 1);
				}else{
					int count = hmFrequency.get(la.get(i).word_tag_meaning);
					hmFrequency.put(la.get(i).word_tag_meaning, ++count);
				}
				
			}
			
		}
	}
	
	/**
	 * 输出解析结果
	 */
	private static void Output_Result(){
		String str_Result = hmFrequency.toString();
		str_Result = str_Result.replace("{", "{\n\t");
		str_Result = str_Result.replace("}", "\n}");
		str_Result = str_Result.replace(", ", "\n\t");
		str_Result = str_Result.replace("=", "\t");
		
		System.out.println("\n\n各词性词语频率统计结果：");
		System.out.println(str_Result);
	}

}
