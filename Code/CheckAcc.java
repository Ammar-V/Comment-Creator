package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CheckAcc {
	// Return true if the entered credentials are correct
	public Boolean verify(String userName, String pass) throws IOException {
		boolean verify = false;
		try {
			Scanner reader = new Scanner(new File("acc0.txt"));

			String[] acc = new String[10];
			verify = false;

			int index = -1;
			while (reader.hasNext()) {
				index++;
				acc[index] = reader.nextLine();
			}

			String user = acc[0];
			String password = acc[1];

			if (user.equals(userName) && password.equals(pass)) {
				verify = true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return verify;
	}
}
