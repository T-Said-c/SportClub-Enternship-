package application;

public class Sales {

	public Integer getCodeSale() {
		return CodeSale;
	}
	public void setCodeSale(Integer codeSale) {
		CodeSale = codeSale;
	}
	public Double getNombre() {
		return Nombre;
	}
	public void setNombre(Double nombre) {
		Nombre = nombre;
	}
	public String getAnnee() {
		return Annee;
	}
	public void setAnnee(String annee) {
		Annee = annee;
	}
	public String getMois() {
		return Mois;
	}
	public void setMois(String mois) {
		Mois = mois;
	}
	public Sales(Integer codeSale, Double nombre, String annee, String mois) {
		super();
		CodeSale = codeSale;
		Nombre = nombre;
		Annee = annee;
		Mois = mois;
	}
	Integer CodeSale;
	Double Nombre;
	String Annee,Mois;
}
