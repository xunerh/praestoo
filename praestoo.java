package praestoo;

import praestoo.Model.*;

public class praestoo {
	public static void main(String[] args){
		User user1 = new User(1, "M", "Quentin", "DRUAULT-AUBIN", "qda@praestoo.fr", "33695565865", "33344205409", "qdruault", (float) 10.0);
		// create
		User.save(user1);
		// update
		user1.setRadius((float)15);
		User.save(user1);
		// read
		User user2 = User.read(1);
		user1 = User.read(3);
		// delete
		User.delete(3);
		user1 = User.read(3); 
	}
}