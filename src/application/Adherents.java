package application;

import java.sql.Blob;

public class Adherents {
	Integer CodeAdherent;
	String Nom,Prenom;
	String DateDeNaissance;
	String Adresse,CodePostal;
	Integer Tele;
	String Email,Presence;
	Blob Photo;
	String Groupe;
	
	public Adherents(Integer codeAdherent, String nom, String prenom, String datedenaissance, String adresse,
			String codepostal, Integer tele, String email, String presence, Blob photo, String groupe) {
		super();
		this.CodeAdherent = codeAdherent;
		this.Nom = nom;
		this.Prenom = prenom;
		this.DateDeNaissance = datedenaissance;
		this.Adresse = adresse;
		this.CodePostal = codepostal;
		this.Tele = tele;
		this.Email = email;
		this.Presence = presence;
		this.Photo = photo;
		this.Groupe = groupe;
	}
	public Integer getCodeAdherent() {
		return CodeAdherent;
	}
	public void setCodeAdherent(Integer codeAdherent) {
		CodeAdherent = codeAdherent;
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
	public String getDateDeNaissance() {
		return DateDeNaissance;
	}
	public void setDateDeNaissance(String datedenaissance) {
		DateDeNaissance = datedenaissance;
	}
	public String getAdresse() {
		return Adresse;
	}
	public void setAdresse(String adresse) {
		Adresse = adresse;
	}
	public String getCodePostal() {
		return CodePostal;
	}
	public void setCodePostal(String codepostal) {
		CodePostal = codepostal;
	}
	public Integer getTele() {
		return Tele;
	}
	public void setTele(Integer tele) {
		Tele = tele;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPresence() {
		return Presence;
	}
	public void setPresence(String presence) {
		Presence = presence;
	}
	public Blob getPhoto() {
		return Photo;
	}
	public void setPhoto(Blob photo) {
		Photo = photo;
	}
	public String getGroupe() {
		return Groupe;
	}
	public void setGroupe(String groupe) {
		Groupe = groupe;
	}
	

}
