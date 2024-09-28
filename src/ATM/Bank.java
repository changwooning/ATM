package ATM;

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
			printMenu();
			int sel = inputNumber("");
			runMenu(sel);
		}
	}
	
	private void printMenu() {
		System.out.println("=== " + BRAND + " ===");
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
			System.out.println(message);
			String input = scan.next();
			number = Integer.parseInt(input);
		}catch(Exception e) {
			System.out.println("숫자만 입력하세요.");
			e.printStackTrace();
		}
		return number;
	}
	
	private String inputString(String message) {
		System.out.println(message);
		return scan.next();
	}
	
	
	
	private void runMenu(int sel) {
		if(sel == USER_JOIN) {
			joinUser();
		}
		else if(sel == USER_LEAVE) {
			leaveUser();
		}
//		else if(sel == DEPOSIT) {
//			deposit();
//		}
//		else if(sel == TRANSFER) {
//			transfer();
//		}
//		else if(sel == ACCOUNT_OPEN) {
//			openAccount();
//		}
//		else if(sel == ACCOUNT_WITHDRAW) {
//			withdraw();
//		}
//		else if(sel == ACCOUNT_SEARCH) {
//			searchAccount();
//		}
		else if(sel == EXIT) {
			isRun = false;
		}
	}
	
	private void joinUser() {
		String name = inputString("이름 : ");
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
			String password = inputString(account.getAccountNumber() + "비밀번호 확인 : ");
			if(!account.equalsPassword(password))
				return false;
		}
		return true;
	}
	
	private void openAccount(User user) {
		int userCode = user.getCode();
		String password = inputString("계좌 비밀번호 : ");
		
		Account account = accountManager.createAccount(userCode, password);
		if(isValidAccount(account)) {
			System.out.println(account.getAccountNumber() + " 개설 완료");
		}
	}
	
	private boolean isValidAccount(Account account) {
		return account.getAccountNumber() != null;
	}
	
}
