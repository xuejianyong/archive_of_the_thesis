package emdp;

public class ProbabilityState {
	
	public Position sPre;
	public Position sPost;
	public Interaction intended;
	public double probability = 0.00;
	
	public Position getsPre() {
		return sPre;
	}
	public void setsPre(Position sPre) {
		this.sPre = sPre;
	}
	public Position getsPost() {
		return sPost;
	}
	public void setsPost(Position sPost) {
		this.sPost = sPost;
	}
	public Interaction getIntended() {
		return intended;
	}
	public void setIntended(Interaction intended) {
		this.intended = intended;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}

	
	public ProbabilityState() {
		// TODO Auto-generated constructor stub
	}

}
