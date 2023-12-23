package application;


public class Abonnements {

	Integer CodeAbonnement,CodeAdherent;
	String DateDeCreation,DateExpiration;
	

	public Abonnements(Integer codeAbonnement, Integer codeAdherent, String dateDeCreation, String dateExpiration) {
		super();
		CodeAbonnement = codeAbonnement;
		CodeAdherent = codeAdherent;
		DateDeCreation = dateDeCreation;
		DateExpiration = dateExpiration;
	}


	public Integer getCodeAbonnement() {
		return CodeAbonnement;
	}


	public void setCodeAbonnement(Integer codeAbonnement) {
		CodeAbonnement = codeAbonnement;
	}


	public Integer getCodeAdherent() {
		return CodeAdherent;
	}


	public void setCodeAdherent(Integer codeAdherent) {
		CodeAdherent = codeAdherent;
	}


	public String getDateDeCreation() {
		return DateDeCreation;
	}


	public void setDateDeCreation(String dateDeCreation) {
		DateDeCreation = dateDeCreation;
	}


	public String getDateExpiration() {
		return DateExpiration;
	}


	public void setDateExpiration(String dateExpiration) {
		DateExpiration = dateExpiration;
	}
}
