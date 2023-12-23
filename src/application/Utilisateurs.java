package application;

public class Utilisateurs {
	
	Integer CodeUtilisateur;
	String Email,MDP;
	Integer PermissionLevel;

	public Integer getCodeUtilisateur() {
		return CodeUtilisateur;
	}
	public void setCodeUtilisateur(Integer codeUtilisateur) {
		CodeUtilisateur = codeUtilisateur;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getMDP() {
		return MDP;
	}
	public void setMDP(String mDP) {
		MDP = mDP;
	}
	public Integer getPermissionLevel() {
		return PermissionLevel;
	}
	public void setPermissionLevel(Integer permissionLevel) {
		PermissionLevel = permissionLevel;
	}
	public Utilisateurs(Integer codeUtilisateur, String email, String mDP, Integer permissionLevel) {
		super();
		CodeUtilisateur = codeUtilisateur;
		Email = email;
		MDP = mDP;
		PermissionLevel = permissionLevel;
	}
}
