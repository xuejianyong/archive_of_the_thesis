package emdp;

public class ProbabilityEnacted {
	
	public Position sPre;
	public Interaction enacted;
	public Interaction intended;
	public double probability = 0.00;
	

	public Position getsPre() {
		return sPre;
	}
	public void setsPre(Position sPre) {
		this.sPre = sPre;
	}
	public Interaction getEnacted() {
		return enacted;
	}
	public void setEnacted(Interaction enacted) {
		this.enacted = enacted;
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


	public ProbabilityEnacted() {
		// TODO Auto-generated constructor stub
	}

}
