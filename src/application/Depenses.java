package application;

public class Depenses {

	Integer CodeDepense;
	String DetailDepense;
	Double Somme;
	
	public Integer getCodeDepense() {
		return CodeDepense;
	}
	public void setCodeDepense(Integer codeDepense) {
		CodeDepense = codeDepense;
	}
	public String getDetailDepense() {
		return DetailDepense;
	}
	public void setDetailDepense(String somme) {
		DetailDepense = somme;
	}
	public Double getSomme() {
		return Somme;
	}
	public void setSomme(Double somme) {
		Somme = somme;
	}
	
	public Depenses(Integer codeDepense, String detailDepense, Double somme) {
		super();
		CodeDepense = codeDepense;
		DetailDepense = detailDepense;
		Somme = somme;
	}
}
