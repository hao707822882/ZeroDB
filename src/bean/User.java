package bean;

public class User {

	String name;
	String passwd;

	public User() {

	}

	public String getName() {
		return name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public User(String name, String passwd) {
		super();
		this.name = name;
		this.passwd = passwd;
	}

}
