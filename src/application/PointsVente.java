package application;

public class PointsVente {

	Integer CodePoint;
	String Designation;
	Double BeneficeParJour;
	
	public Integer getCodePoint() {
		return CodePoint;
	}
	public void setCodePoint(Integer codePoint) {
		CodePoint = codePoint;
	}
	public String getDesignation() {
		return Designation;
	}
	public void setDesignation(String designation) {
		Designation = designation;
	}
	public Double getBeneficeParJour() {
		return BeneficeParJour;
	}
	public void setBeneficeParJour(Double beneficeParJour) {
		BeneficeParJour = beneficeParJour;
	}
	public PointsVente(Integer codePoint, String designation, Double beneficeParJour) {
		super();
		CodePoint = codePoint;
		Designation = designation;
		BeneficeParJour = beneficeParJour;
	}
}
