
public class User {
	String u;
	String p;
	String e;
	int id;
	double bal;

	public User(int UserID, String username, String password, String email, Double balance) {
		id = UserID;
		bal = balance;
		u = username;
		e = email;
		p = password;
	}
	
	String getUsername() {
		return u;
	}
	String getPassword() {
		return p;
	}
	String getEmail() {
		return e;
	}
	int getID() {
		return id;
	}
	double getBal() {
		return bal;
	}
}
