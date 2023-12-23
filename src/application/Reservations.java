package application;

public class Reservations {

	Integer CodeReservation,CodeAdherent;
	String HeureDebut,HeureFin,Date;
	
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Integer getCodeReservation() {
		return CodeReservation;
	}
	public void setCodeReservation(Integer codeReservation) {
		CodeReservation = codeReservation;
	}
	public Integer getCodeAdherent() {
		return CodeAdherent;
	}
	public void setCodeAdherent(Integer codeAdherent) {
		CodeAdherent = codeAdherent;
	}
	public String getHeureDebut() {
		return HeureDebut;
	}
	public void setHeureDebut(String heureDebut) {
		HeureDebut = heureDebut;
	}
	public String getHeureFin() {
		return HeureFin;
	}
	public void setHeureFin(String heureFin) {
		HeureFin = heureFin;
	}
	public Reservations(Integer codeReservation, Integer codeAdherent, String heureDebut, String heureFin, String date) {
		super();
		CodeReservation = codeReservation;
		CodeAdherent = codeAdherent;
		HeureDebut = heureDebut;
		HeureFin = heureFin;
		Date = date;
	}
}
