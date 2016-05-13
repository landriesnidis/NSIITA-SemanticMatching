package nsi.ita.chatbots.bean;

public class PhrasesBean {

	//主体
	private String Phrases;
	
	//参数
	private String[] Param;
	
	//参数数量
	private int size;
	
	/**
	 * 构造函数
	 * @param strItem
	 */
	public PhrasesBean(String strItem){
		String[] temp = strItem.split("\\|");
		size = temp.length - 1;
		Phrases = temp[0];
		if(size != 0){
			Param = new String[size];
			for(int i=0;i<size;i++){
				Param[i] = temp[i+1];
			}
		}
	}
	
	public String getPhrases() {
		return Phrases;
	}

	public String[] getParamAll() {
		return Param;
	}
	
	public String getParam(int index) {
		return Param[index];
	}

	public int size() {
		return size;
	}

	
	
}
