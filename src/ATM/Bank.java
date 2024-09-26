package ATM;

public class Bank {
	
	public void run() {
		while(true) {
			printMenu();
			int sel = inputNumber("");
			runMenu(sel);
		}
	}
	
}
