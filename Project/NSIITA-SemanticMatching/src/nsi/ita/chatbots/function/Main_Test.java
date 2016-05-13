package nsi.ita.chatbots.function;

public class Main_Test {

	public static void main(String[] args) {
		PhrasesAnalysis as = new PhrasesAnalysis();
		
		System.out.println(as.toPhrasesAnalysis("请问，刘伟松老师的电话是多少","大学教务"));
		
	}

}
