package nsi.ita.chatbots.bean;

/**
 * 语言包
 * @author Administrator
 *
 */
public class LanguageBean{
	
	//语言包名称
	public String Package;
	
	//原句
	public String Phrase;
	
	//肯定回答
	public String Affirmative;
	
	//否定回答
	public String Negative;
	
	/**
	 * 构造函数（实在疲惫，请允许我偷个懒）
	 */
	public LanguageBean(String... params){
		Package = params[0];
		Phrase = params[1];
		Affirmative = params[2];
		Negative = params[3];
	}
	
	/**
	 * 向肯定答案中添加参数
	 * 替换的参数格式为   {index} => 第 index 个参数
	 * 举个栗子：
	 * 		参数 Param 只有1个元素 内容为 “刘伟松”
	 * 		替换前：{1}老师的电话号码是{rst}
	 * 		替换后：刘伟松老师的电话号码是{rst}
	 */
	public String addParamToAffirmative(String[] Param){
		
		//循环替换各参数值
		String result = new String(Affirmative);
		
		if(Param != null)
			for(int i=0;i<Param.length;i++){
				try{
					if(result.contains("{" + (i+1) + "}"))
						result = result.replace("{" + (i+1) + "}", Param[i]);
				}catch(Exception ex){
					/*TODO*/
					System.out.println("语言包 " + Package + " 中的语句 “" + Phrase + "” 在添加参数时发生错误！");
					return null;
				}
			}
		return result;
	}
}
