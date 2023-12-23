package application;

public class Conges {

	Integer CodeConge,CodePersonnel;
	String DateDeDebut,DateDeFin;

	public Integer getCodeConge() {
		return CodeConge;
	}

	public void setCodeConge(Integer codeConge) {
		CodeConge = codeConge;
	}

	public Integer getCodePersonnel() {
		return CodePersonnel;
	}

	public void setCodePersonnel(Integer codePersonnel) {
		CodePersonnel = codePersonnel;
	}

	public String getDateDeDebut() {
		return DateDeDebut;
	}

	public void setDateDeDebut(String dateDeDebut) {
		DateDeDebut = dateDeDebut;
	}

	public String getDateDeFin() {
		return DateDeFin;
	}

	public void setDateDeFin(String dateDeFin) {
		DateDeFin = dateDeFin;
	}

	public Conges(Integer codeConge, Integer codePersonnel, String dateDeDebut, String dateDeFin) {
		super();
		CodeConge = codeConge;
		CodePersonnel = codePersonnel;
		DateDeDebut = dateDeDebut;
		DateDeFin = dateDeFin;
	}
}
