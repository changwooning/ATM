package ATM;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
	
	private final int USER_JOIN = 1;
	private final int USER_LEAVE = 2;
	private final int DEPOSIT = 3;
	private final int TRANSFER = 4;
	private final int ACCOUNT_OPEN = 5;
	private final int ACCOUNT_WITHDRAW = 6;
	private final int ACCOUNT_SEARCH = 7;
	private final int EXIT = 0;

	private final String BRAND;
	private boolean isRun;
	
	private Scanner scan = new Scanner(System.in);
	private DecimalFormat dcf = new DecimalFormat();
	
	private UserManager userManager = new UserManager();
	private AccountManager accountManager = new AccountManager();
	
	
	public Bank(String BRAND) {
		this.BRAND = BRAND;
		this.isRun = true;
	}
	
	public void run() {
		// 기능
		// 회원가입
		// 회원탈퇴
		// 입금
		// 이체
		// 계좌 개설	 (1~3)까지
		// 계좌 철회	(계좌 전체 철회)
		// 계좌 조회
		// 종료
		while(isRun) {
			printStatus();
			printMenu();
			int sel = inputNumber("");
			runMenu(sel);
		}
	}
	
	private void printStatus() {
		int userSize = userManager.getUserSize();
		int accountSize = accountManager.getAccountSize();
		String status = String.format("User size : %d\nAccount Size : %d", userSize,accountSize);
		
		System.out.println("===============");
		System.out.println(status);
		
		printDataAll();
		printDataSummery();
	}
	
	private void printDataAll() {
		int accountSize = accountManager.getAccountSize();
		
		if(accountSize > 0) {
			System.out.println("===============");
		}
		
		for(int i=0; i<accountSize; i++) {
			Account account = accountManager.findAccountByIndex(i);
			String info = String.format("%d) %s : %s\n", account.getUserCode(),account.getAccountNumber(),toStringMoney(account.getBalance()));
			System.out.println(info);
		}
	}
	
	private void printDataSummery() {
		ArrayList<User> userList = userManager.findUserAll();
		for(User user : userList) {
			int userCode = user.getCode();
			ArrayList<Account> accList = accountManager.findAccountAllByUserCode(userCode);
			String message = String.format("%d %s는 계좌를 %d개 가지고 있다.\n", userCode,user.getName(),accList.size());
			System.out.println(message);
		}
	}
	
	private void printMenu() {
		System.out.println("===== " + BRAND + " =====");
		System.out.println("1.회원가입");
		System.out.println("2.회원탈퇴");
		System.out.println("3.입금");
		System.out.println("4.이체");
		System.out.println("5.계좌 개설");
		System.out.println("6.계좌 철회");
		System.out.println("7.계좌 조회");
		System.out.println("0.종료");
	}
	
	private int inputNumber(String message) {
		int number = -1;
		try {
			System.out.print(message + " : ");
			String input = scan.next();
			number = Integer.parseInt(input);
		}catch(Exception e) {
			System.out.println("숫자만 입력하세요.");
		}
		return number;
	}
	
	private String inputString(String message) {
		System.out.print(message + " : ");
		return scan.next();
	}
	
	
	
	private void runMenu(int sel) {
		if(sel == USER_JOIN) {
			joinUser();
		}
		else if(sel == USER_LEAVE) {
			leaveUser();
		}
		else if(sel == DEPOSIT) {
			deposit();
		}
		else if(sel == TRANSFER) {
			transfer();
		}
		else if(sel == ACCOUNT_OPEN) {
			openAccount();
		}
		else if(sel == ACCOUNT_WITHDRAW) {
			withdraw();
		}
		else if(sel == ACCOUNT_SEARCH) {
			searchAccount();
		}
		else if(sel == EXIT) {
			System.out.println("시스템을 종료합니다.");
			isRun = false;
		}
	}
	
	private void joinUser() {
		String name = inputString("이름");
		String phone = inputString("phone (###-####-####)");
		
		User user = userManager.createUser(name, phone);
		openAccount(user);
		welcomeMessage(user);
	}
	
	private void welcomeMessage(User user) {
		String message = user.getCode() != 0 ? String.format("%s 회원님 환영합니다.\n", user.getName()) : "회원가입실패";
		System.out.println(message);
	}
	
	private void leaveUser() {
		String phone = inputString("phone (###-####-####)");
		
		User user = userManager.findUserByUserPhone(phone);
		int userCode = user.getCode();
		ArrayList<Account> accounts = accountManager.findAccountAllByUserCode(userCode);
		
		boolean result = false;
		if(withdrawFullAccount(accounts)) 
			result = userManager.deleteUser(user);
		
		String message = result ? "회원탈퇴완료" : "회원탈퇴실패";
		System.out.println(message);
	}
	
	private void deposit() {
		Account account = findAccount();	// 계좌를 찾고
		
		if(isValidAccount(account)) {	// 유효하면
			int money = inputNumber("입금 금액");
			int balance = account.getBalance();
			
			if(money > 0) {
				accountManager.updateAccountBalance(account, balance+money);
				System.out.println("입금완료");
			}else
				System.out.println("입금실패");
		}
	}
	
	private void transfer() {
		Account myAccount = findAccount("본인 계좌");
		String password = inputString("비밀번호");
		
		Account whoAccount = findAccount("상대 계좌");
		int money = inputNumber("이체 금액");
		
		Account[] accounts = new Account[] {myAccount,whoAccount};
		
		if(isValidAccount(accounts) && myAccount.equalsPassword(password)) {
			int balance = myAccount.getBalance();
			
			if(money > 0 && balance - money >= 0) {
				accountManager.updateAccountBalance(myAccount, balance - money);
				accountManager.updateAccountBalance(whoAccount, whoAccount.getBalance() + money);
				System.out.println("이체 완료");
			}else
				System.out.println("이체 실패");
		}
	}
	
	private void openAccount() {
		User user = findUser();	// 사용자 찾고
		openAccount(user);	// 계좌 개설
	}
	
	private void withdraw() {
		Account account = findAccount();
		String password = inputString("계좌 비밀번호");
		
		if(account.equalsPassword(password)) {
			accountManager.deleteAccount(account);
			System.out.println("계좌철회완료");
		}else
			System.out.println("계좌철회실패");
	}
	
	private void searchAccount() {
		Account account = findAccount();
		String password = inputString("계좌 비밀번호");
		
		if(account.equalsPassword(password)) {
			System.out.println(toStringMoney(account.getBalance()) + "조회완료");
		}else {
			System.out.println("비밀번호가 불일치 합니다.");
			return;
		}
	}
	
	// 금액을 형식에 맞게 문자열로 변환함
	private String toStringMoney(int money) {
		String message = dcf.format(money) + "원";
		return message;
	}
	
	// 모든 계좌를 철회하는 메서드
	private boolean withdrawFullAccount(ArrayList<Account> accounts) {
		if(checkAllAccountPassword(accounts)) {	// 모든 계좌의 비밀번호 확인
			for(Account account : accounts)
				accountManager.deleteAccount(account);
			return true;
		}
		return false;
	}
	
	// 비밀번호 확인
	private boolean checkAllAccountPassword(ArrayList<Account> accounts) {
		for(Account account : accounts) {
			String password = inputString(account.getAccountNumber() + "비밀번호 확인");
			if(!account.equalsPassword(password))
				return false;
		}
		return true;
	}
	
	private User findUser() {
		String phone = inputString("phone (###-####-####)");
		User user = userManager.findUserByUserPhone(phone);	// 전화번호로 유저찾기
		return user;
	}
	
	// 특정 계좌찾기
	private Account findAccount(String message) {
		String accountNumber = inputString(message + "계좌번호 (####-####-####)");
		Account account = accountManager.findAccountByAccountNumber(accountNumber);
		return account;
	}
	
	private Account findAccount() {
		String accountNumber = inputString("계좌번호 (####-####-####)");
		Account account = accountManager.findAccountByAccountNumber(accountNumber);
		return account;
	}
	
	private void openAccount(User user) {
		int userCode = user.getCode();
		String password = inputString("계좌 비밀번호");
		
		Account account = accountManager.createAccount(userCode, password);
		if(isValidAccount(account)) {
			System.out.println(account.getAccountNumber() + " 개설 완료");
		}
	}
	
	private boolean isValidAccount(Account[] accounts) {
		for(Account account : accounts)
			if(account.getAccountNumber() == null)
				return false;
		
		int code = accounts[0].getUserCode();
		if(accounts[1].getUserCode() == code)
			return false;
		
		return true;
	}
	
	private boolean isValidAccount(Account account) {
		return account.getAccountNumber() != null;
	}
	
}
