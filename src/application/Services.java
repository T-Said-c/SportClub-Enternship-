package application;

public class Services {

	Integer CodeService;
	String Detail;
	Double Paiement;
	
	public Integer getCodeService() {
		return CodeService;
	}
	public void setCodeService(Integer codeService) {
		CodeService = codeService;
	}
	public String getDetail() {
		return Detail;
	}
	public void setDetail(String detail) {
		Detail = detail;
	}
	public Double getPaiement() {
		return Paiement;
	}
	public void setPaiement(Double paiement) {
		Paiement = paiement;
	}
	public Services(Integer codeService, String detail, Double paiement) {
		super();
		CodeService = codeService;
		Detail = detail;
		Paiement = paiement;
	}
}
