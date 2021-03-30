package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadComments {
	public static ArrayList<String> subjects = new ArrayList<>();
	public static ArrayList<String> units = new ArrayList<>();
	public static ArrayList<String> comments = new ArrayList<>();
	public static ArrayList<Integer> ratings = new ArrayList<>();

	// Pull and store elements with differing values.
	public void load() {
		try {
			Scanner imprt = new Scanner(new File("commentData.txt"));
			while (imprt.hasNext()) {
				String line = imprt.nextLine();
				try {
					if (line.equals("--__--__--__--__--")) {
						subjects.add(imprt.nextLine());
						units.add(imprt.nextLine());
						comments.add(imprt.nextLine());
						ratings.add(Integer.parseInt(imprt.nextLine()));
					}
				} catch (NumberFormatException e) {
					ratings.add(null);
				} catch (Exception a) {
					a.printStackTrace();
				}
			}
			imprt.close();
		} catch (FileNotFoundException a) {
			File data = new File("commentData.txt");
			a.printStackTrace();
			try {
				data.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void upload() {
		PrintWriter upl = null;
		try {
			upl = new PrintWriter(new File("commentData.txt"));
			for (int j = 0; j < subjects.size(); j++) {

				try {
					if (units.get(j).equals("")) {
						upl.println("--__--__--__--__--");
						upl.println(subjects.get(j));
						upl.println("");
						upl.println("");
						upl.println("");

					} else if (comments.get(j).equals("")) {
						upl.println("--__--__--__--__--");
						upl.println(subjects.get(j));
						upl.println(units.get(j));
						upl.println("");
						upl.println("");

					} else {
						upl.println("--__--__--__--__--");
						upl.println(subjects.get(j));
						upl.println(units.get(j));
						upl.println(comments.get(j));
						upl.println(ratings.get(j));
					}

				} catch (Exception a) {
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		upl.close();
	}
	/*
	 * "Physics 30", "Physics 30", "Physics 30", "Physics 30", "Physics 30",
	 * "Physics 30", "Physics 30", "Math 30-1", "Science 10"
	 * 
	 * "Momentum", "Momentum", "Momentum", "Momentum", "Momentum", "Momentum",
	 * "Light", null, null
	 * 
	 * 
	 * "<|First name|> has demonstrated a(n) proficient/excellent understanding of the scientific principles relating to Momentum."
	 * ,
	 * "<|First name|> has demonstrated a competent/general understanding of the scientific principles relating to Momentum."
	 * ,
	 * "<|First name|> has demonstrated an adequate understanding of the scientific principles relating to Momentum."
	 * ,
	 * "<|First name|> has demonstrated limited understanding of the scientific principles relating to Momentum."
	 * , "Insufficient", "Summary", null, null, null
	 * 
	 * 5, 4, 3, 2, 1, 0, null, null, null
	 */
}
