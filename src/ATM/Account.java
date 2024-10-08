package ATM;

public class Account {
	
	private int userCode;
	private String accountNumber;
	private String password;
	private int balance;
	
	public Account() {}
	
	public Account(int userCode,String accountNumber,String password) {
		this.userCode = userCode;
		this.accountNumber = accountNumber;
		this.password = password;
	}
	
	public Account(int userCode,String accountNumber,String password,int balance) {
		this.userCode = userCode;
		this.accountNumber = accountNumber;
		this.password = password;
		this.balance = balance;
	}
	
	public int getUserCode() {
		return this.userCode;
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public boolean equalsPassword(String password) {
		return this.password.equals(password);
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public Account clone() {
		return new Account(this.userCode,this.accountNumber,this.password,this.balance);
	}

}
