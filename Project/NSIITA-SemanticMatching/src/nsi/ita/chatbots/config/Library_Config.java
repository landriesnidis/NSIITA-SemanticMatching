package nsi.ita.chatbots.config;

public class Library_Config {

	// 近义词词库所在文件夹
	public final static String Library_Path = System.getProperty("user.dir")
				+ "\\library\\";
	
	// 近义词词库所在文件夹
	public final static String Synonyms_Path = System.getProperty("user.dir")
			+ "\\library\\Synonyms\\";

	// 自定义词库所在文件夹
	public final static String Lexicon_Path = System.getProperty("user.dir")
			+ "\\library\\Lexicon\\";

	// 短语集所在文件夹
	public final static String Phrases_Path = System.getProperty("user.dir")
			+ "\\library\\Phrases\\";
	
	// 语言包所在文件夹
	public final static String Language_Path = System.getProperty("user.dir")
			+ "\\library\\Language\\";

	// 词库配置文件中单个词语在未手动赋值时的默认值
	public final static int LexiconConfiguratioFile_DefaultWeights = 1000;
	
	// 使用语言包功能开关
	public final static boolean LanguagePackageFunction_Switch = true;
}
