package nsi.ita.chatbots.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nsi.ita.chatbots.config.Library_Config;

/**
 * 这个工具类的作用是：
 * 			从一条句子里查找出一个或多个特定的命令格式，将这
 * 		些文本整理排序。
 * 			检测每一段文本中特定的文本格式，判断是否为约定的
 * 		脚本格式，如果是则执行它。
 * 			功能类似于标签语言。
 * 
 * 脚本格式：{script:<功能>[参数1][...]#}
 */
public class CustomScript_Util {
	
	//完整的一句话（其中可能夹带脚本）
	String Text;

	/**
	 * 构造函数
	 */
	public CustomScript_Util(String Text) {
		this.Text = new String(Text);
	}
	
	public String execute(){
		String rst = getScript(this.Text,"\\{script:", "#\\}");
		return rst;
	}
	
	
	/**
	 * 使用正则表达式截取所需要的内容
     * 描述：提取所有strBegin与strEnd中的内容
	 * @param Text		要解析的文档内容
	 * @param strBegin	开始标记
	 * @param strEnd	结束标记
	 * @return			解析结果，可以多次匹配，每次匹配添加进哈希表
	 */
    private String getScript(String Text,String strBegin,String strEnd) {
        
        //匹配strBegin开头，strEnd结尾的文档
        Pattern p = Pattern.compile(strBegin + "([^" + strEnd + "]*)");
        //开始编译
        Matcher m = p.matcher(Text);
        //循环获取所有内容
        while (m.find()) {
        	//获取被匹配的部分
        	String ss = m.group(1);
        	String Old = "{script:" + m.group(1) + "#}";
        	String New = new ScriptBean(ss).execute();
        	if(New == null){
        		New = "{无数据}";
        	}
        	Text = Text.replace(Old, New);
        }
        return Text;
    }
}



/**
 * 独立的一条脚本语句
 */
class ScriptBean{
	
	//脚本
	private String Script;
	
	//命令语句
	private String Command;
	
	//参数
	private List<String> Param;
	
	/**
	 * 构造函数
	 */
	public ScriptBean(String cmd) {
		Script = new String(cmd);
		analysis(Script);
	}
	
	/**
	 * 解析语句
	 * 1.识别命令类型
	 * 2.获取参数
	 */
	private void analysis(String script){
		Command = getContextOnly(Script, "\\<", "\\>");
		Param = getContext(Script, "\\[", "\\]");
		execute();
	}
	
	/**
	 * 执行脚本
	 * @return
	 */
	public String execute(){
		
		String cmd = new String(Command);
		
		//转小写
		cmd = cmd.toLowerCase();
		
		//依照使用频率依次比较，找出对应的函数名称
		if(cmd.equals("gethash")){					//==========查询键值表中的指定项
			//路径是Language文件夹下的指定文件，包含文件名。
			String Path = Library_Config.Language_Path;
			return new TableFile_KeyValue_Util(Path + Param.get(0)).getValue(Param.get(1));
			
		}else if(cmd.equals("doc")){				//==========http请求并将内容替换
			
			//路径是Language文件夹下的指定文件，包含文件名。
			String Path = Library_Config.Language_Path;
			String[] lines = new RWfile_Util(Path).ReadFile(Param.get(0));
			//如果文件不存在
			if(lines == null)
				return null;
			//拼接字符串
			String Text = "";
			for(String line:lines){
				Text += ("\n" + line);
			}
			return Text;
			
		}else if(cmd.equals("wget")){				//==========http请求并将内容替换
			
			//参数数量：1
			//参数描述：请求的地址
			String result = HTTP_Request.SendHttp_Get(Param.get(0));
			return result;
			
		}else if(cmd.equals("doget")){				//==========发送http请求
			
			//参数数量：1
			//参数描述：请求的地址
			HTTP_Request.SendHttp_Get(Param.get(0));
			return "";
			
		}else{										//==========无效命令
			return null;
		}
	}

	/**
	 * 使用正则表达式截取所需要的内容
     * 描述：提取所有strBegin与strEnd中的内容
	 * @param Text		要解析的文档内容
	 * @param strBegin	开始标记
	 * @param strEnd	结束标记
	 * @return			解析结果，可以多次匹配，每次匹配的结果按文档中出现的先后顺序添加进结果List
	 */
    private List<String> getContext(String Text,String strBegin,String strEnd) {
        
    	//定义一个字符串列表
    	List<String> resultList = new ArrayList<String>();
        //匹配strBegin开头，strEnd结尾的文档
        Pattern p = Pattern.compile(strBegin + "([^" + strEnd + "]*)");
        //开始编译
        Matcher m = p.matcher(Text);
        //循环获取所有内容
        while (m.find()) {
        	//获取被匹配的部分
            resultList.add(m.group(1));
        }
        return resultList;
    }
    
	/**
	 * 使用正则表达式截取所需要的内容
     * 描述：提取第一个strBegin与strEnd中的内容
	 * @param Text		要解析的文档内容
	 * @param strBegin	开始标记
	 * @param strEnd	结束标记
	 * @return			解析结果
	 */
    private String getContextOnly(String Text,String strBegin,String strEnd) {
    	//匹配strBegin开头，strEnd结尾的文档
        Pattern p = Pattern.compile(strBegin + "([^" + strEnd + "]*)");
        //开始编译
        Matcher m = p.matcher(Text);
        String rst = null;
        while (m.find()) {
        	rst = m.group(1);
        }
        return rst;
    }
}