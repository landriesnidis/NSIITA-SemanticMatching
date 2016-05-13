package nsi.ita.chatbots.bean;

public class AnalyticElementBean {
	
	//中文字、词
	public String word = null;
	//词语类型标识
	public int word_tag = 0;
	//角标
	public int index = 0;
	//标识的含义
	public String word_tag_meaning = "";
	
	public String toString(){
		return word + "/" + word_tag + "/" + index + "/" + word_tag_meaning;
	}
}
