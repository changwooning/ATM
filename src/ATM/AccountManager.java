package ATM;

import java.util.ArrayList;
import java.util.Random;

public class AccountManager {
	
	private final int LIMIT = 3;
	
	private ArrayList<Account> list;
	private Random ran;
	
	
	public AccountManager() {
		list = new ArrayList<>();
		ran = new Random();
	}
	
	public Account createAccount(int userCode,String password) {
		if(isValidUserCode(userCode) & !isExceedLimit(userCode)) { // 사용자 코드가 유효하고 계좌 개수 제한을 넘지않을때
			String accountNumber = generateAccountNumber();
			Account account = new Account(userCode,accountNumber,password);
			list.add(account);
			return account.clone();
		}
		return new Account();
	}
	
	// R : 사용자 코드로 모든 계좌 찾기
	public ArrayList<Account> findAccountAllByUserCode(int userCode){
		ArrayList<Account> accounts = new ArrayList<>();
		for(Account account : list) {
			if(account.getUserCode() == userCode)
				accounts.add(account.clone());
		}
		return accounts;
	}
	
	// 계좌 번호로 계좌 찾기
	public Account findAccountByAccountNumber(String accountNumber) {
		for(Account account : list)
			if(account.getAccountNumber().equals(accountNumber))
				return account;
		return new Account();
	}
	
	// 코드가 유효한지
	private boolean isValidUserCode(int userCode) {
		return userCode != 0;
	}
	
	// 계좌번호가 유효한지
	private boolean isValidAccountNumber(String accountNumber) {
		Account account = findAccountByAccountNumber(accountNumber);
		
		if(account.getAccountNumber() == null)
			return true;
		return false;
	}
	
	// 계좌번호 생성
	private String generateAccountNumber() {
		String accountNumber = "";
		while(true) {
			int head = ran.nextInt(9000) + 1000;
			int body = ran.nextInt(9000) + 1000;
			int tail = ran.nextInt(9000) + 1000;
			
			accountNumber = String.format("%d-%d-%d\n", head,body,tail);
			
			if(isValidAccountNumber(accountNumber))
				break;
		}
		return accountNumber;
	}
	
	// 계좌 개수제한 확인메서드
	private boolean isExceedLimit(int userCode) {
		ArrayList<Account> accounts = findAccountAllByUserCode(userCode);
		if(accounts.size() < LIMIT)
			return false;
		return true;
	}

}
