package nsi.ita.chatbots.bean;

public class SimilarBean{
	
	private String Phrases;
	
	private double Similarity;
	
	/**
	 * getters and setters
	 */
	public String getPhrases() {
		return Phrases;
	}

	public void setPhrases(String phrases) {
		Phrases = phrases;
	}

	public double getSimilarity() {
		return Similarity;
	}

	public void setSimilarity(double similarity) {
		Similarity = similarity;
	}
}

