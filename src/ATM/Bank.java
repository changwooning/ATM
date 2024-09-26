package ATM;

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
	
	
	
	private void runMenu(int sel) {
		if(sel == USER_JOIN) {}
		else if(sel == USER_LEAVE) {}
		else if(sel == DEPOSIT) {}
		else if(sel == TRANSFER) {}
		else if(sel == ACCOUNT_OPEN) {}
		else if(sel == ACCOUNT_WITHDRAW) {}
		else if(sel == ACCOUNT_SEARCH) {}
		else if(sel == EXIT) {}
	}
	
}
