///Assignment 1
///PartTwo.java
///Students { Michael Hofmeister, Azim Javedali Saiyed, Timothy Shay }


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class PartTwo {

	public static void main(String[] args) {

		PartTwo two = new PartTwo();
		HashSet<User> set1 = new HashSet<User>();
		HashSet<User> set2 = new HashSet<User>();
		two.loadUserList("userList1.txt", set1);
		two.loadUserList("userList2.txt", set2);
		
		//Find the overlapping record between the two sets
		set1.retainAll(set2);
		
		ArrayList<User> duplicates = new ArrayList<User>();
		duplicates.addAll(set1);

		System.out.println(String.format("%d overlapping records found", duplicates.size()));
		two.printDuplicates(duplicates);

	}

	public void printDuplicates(ArrayList<User> users) {

		Collections.sort(users, new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				if (o1.equals(o2)) {
					return 0;
				}

				return Integer.compare(o1.getAge(), o2.getAge());
			}
		});

		for (User user : users) {
			System.out.println(user);
		}
	}

	public void loadUserList(String filename, HashSet<User> userList) {

		// Lets make sure the file path is not empty or null
		if (filename == null || filename.isEmpty()) {
			System.out.println("Invalid File Path");

		}

		BufferedReader inputStream = null;
		// We need a try catch block so we can handle any potential IO errors
		try {
			try {
				inputStream = new BufferedReader(new FileReader(filename));
				String lineContent = null;
				// Loop will iterate over each line within the file.
				// It will stop when no new lines are found.

				while ((lineContent = inputStream.readLine()) != null) {
					User user = new User(lineContent);
					userList.add(user);
				}

			}
			// Make sure we close the buffered reader.
			finally {
				if (inputStream != null)
					inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
