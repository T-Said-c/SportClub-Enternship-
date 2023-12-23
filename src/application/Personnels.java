package application;


public class Personnels {
	
	Integer CodePersonnel;
	String Nom,Prenom;
    Double Salaire;
	String FaitsMarquants;
	
    public Integer getCodePersonnel() {
		return CodePersonnel;
	}
	public void setCodePersonnel(Integer codePersonnel) {
		CodePersonnel = codePersonnel;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public Double getSalaire() {
		return Salaire;
	}
	public void setSalaire(Double salaire) {
		Salaire = salaire;
	}
	public String getFaitsMarquants() {
		return FaitsMarquants;
	}
	public void setFaitsMarquants(String faitsMarquants) {
		FaitsMarquants = faitsMarquants;
	}
	public Personnels(Integer codePersonnel, String nom, String prenom, Double salaire, String faitsMarquants) {
		super();
		CodePersonnel = codePersonnel;
		Nom = nom;
		Prenom = prenom;
		Salaire = salaire;
		FaitsMarquants = faitsMarquants;
	}
	}
