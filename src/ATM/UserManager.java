package ATM;

import java.util.ArrayList;
import java.util.Random;

public class UserManager {
	
	private ArrayList<User> group;
	private Random ran;
	
	public UserManager() {
		group = new ArrayList<>();
		ran = new Random();
	}
	
	// C : 사용자 생성
	public User createUser(String name, String phone) {
		if(isValidPhone(phone)) {
			int code = generateUserCode();
			User user = new User(code,name,phone);
			return user.clone();
		}
		return new User();
	}
	
	public User findUserByUserPhone(String phone) {
		for(User user : group)
			if(user.getPhone().equals(phone))
				return user.clone();
		return new User();
	}
	
	
	public User findUserByUserCode(int code) {
		for(User user : group)
			if(user.getCode() == code)
				return user.clone();
		return new User();
	}
	
	// 전화번호가 유효한지
	private boolean isValidPhone(String phone) {
		if(!phone.matches("\\d{3}-\\d{4}-\\d{4}"))
			return false;
		
		User user = findUserByUserPhone(phone);
		if(user.getCode() == 0)
			return true;
		return false;
	}
	
	private int generateUserCode() {
		int code = 0;
		while(true) {
			code = ran.nextInt(9000) + 1000;
			
			User user = findUserByUserCode(code);
			if(user.getCode() == 0)
				break;
		}
		return code;
	}

}
