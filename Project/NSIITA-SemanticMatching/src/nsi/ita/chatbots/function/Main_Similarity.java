package nsi.ita.chatbots.function;

import nsi.ita.chatbots.utils.Similarity_Util;

public class Main_Similarity {

	public static void main(String[] args) {
		
		Similarity_Util util = new Similarity_Util();
		
		double r = util.similarity("我想查询我的分数", "查成绩");
		
		System.out.println("相似度为：" + r);
	}

}
