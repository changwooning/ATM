package ATM;

public class User {
	
	private int code;
	private String name;
	private String phone;
	
	public User() {}
	
	public User(int code, String name,String phone) {
		this.code = code;
		this.name = name;
		this.phone = phone;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public User clone() {
		return new User(this.code,this.name,this.phone);
	}
	
}
