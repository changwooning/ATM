package ATM;

import java.util.ArrayList;
import java.util.Random;

public class UserManager {

	private ArrayList<User> group;
	private Random ran = new Random();

	public UserManager() {
		this.group = new ArrayList<User>();
	}

	// C : 사용자 생성
	public User createUser(String name, String phone) {
		if (isValidPhone(phone)) {
			int code = generateUserCode();
			User user = new User(code, name, phone);
			group.add(user);
			return user.clone();
		}
		return new User();
	}
	
	public ArrayList<User> findUserAll(){
		ArrayList<User> copy = new ArrayList<>();
		
		for(User user : group)
			copy.add(user.clone());
		return copy;
	}

	// R
	public User findUserByUserPhone(String phone) {
		for (User user : group)
			if (user.getPhone().equals(phone))
				return user.clone();
		return new User();
	}

	// R
	public User findUserByUserCode(int code) {
		for (User user : group)
			if (user.getCode() == code)
				return user.clone();
		return new User();
	}

	// U
	public void updateUserPhone(User user, String phone) {
		if (isValidPhone(phone)) {	// 해당 전화번호가 유효하면
			int code = user.getCode();
			User target = getUserByUserCode(code);
			target.setPhone(phone);
		}
	}
	
	// D
	public boolean deleteUser(User user) {
		int code = user.getCode();
		User target = getUserByUserCode(code);
		return group.remove(target);
	}
	
	public int getUserSize() {
		return this.group.size();
	}

	// 전화번호가 유효한지
	private boolean isValidPhone(String phone) {
		if (!phone.matches("\\d{3}-\\d{4}-\\d{4}"))
			return false;

		User user = findUserByUserPhone(phone);
		if (user.getCode() == 0)
			return true;
		return false;
	}

	private User getUserByUserCode(int code) {
		for (User user : group)
			if (user.getCode() == code)
				return user;
		return new User();
	}

	private int generateUserCode() {
		int code = 0;
		while (true) {
			code = ran.nextInt(9000) + 1000;

			User user = findUserByUserCode(code);
			if (user.getCode() == 0)
				break;
		}
		return code;
	}

}
