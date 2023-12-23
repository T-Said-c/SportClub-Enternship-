package application;


public class Recettes {
	
	Integer CodeRecette,CodeAdherent;
	String ModePaiement;
	Double Montant;
	String DateEcheance;
	
	public Integer getCodeRecette() {
		return CodeRecette;
	}
	public void setCodeRecette(Integer codeRecette) {
		CodeRecette = codeRecette;
	}
	public Integer getCodeAdherent() {
		return CodeAdherent;
	}
	public void setCodeAdherent(Integer codeAdherent) {
		CodeAdherent = codeAdherent;
	}
	public String getModePaiement() {
		return ModePaiement;
	}
	public void setModePaiement(String modePaiement) {
		ModePaiement = modePaiement;
	}
	public Double getMontant() {
		return Montant;
	}
	public void setMontant(Double montant) {
		Montant = montant;
	}
	public String getDateEcheance() {
		return DateEcheance;
	}
	public void setDateEcheance(String dateEcheance) {
		DateEcheance = dateEcheance;
	}
	public Recettes(Integer codeRecette, Integer codeAdherent, String modePaiement, Double montant,
			String dateEcheance) {
		super();
		CodeRecette = codeRecette;
		CodeAdherent = codeAdherent;
		ModePaiement = modePaiement;
		Montant = montant;
		DateEcheance = dateEcheance;
	}
}
