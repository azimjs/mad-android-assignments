
///Assignment 1
///PartOne.java
///Students { Michael Hofmeister, Azim Javedali Saiyed, Timothy Shay }

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class PartOne {

	public static void main(String[] args) throws IOException {
		PartOne one = new PartOne();
		HashMap<User, Integer> userList = new HashMap<User, Integer>();
		ArrayList<User> duplicates = one.loadUserList("userList1.txt", userList);
		one.printDuplicates(duplicates);
		System.in.read();
	}
	
	public void printDuplicates(ArrayList<User> users){
		
		Collections.sort(users, new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				if(o1.equals(o2)){
					return 0;
				}
				
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		
		for(User user : users){
			System.out.println(user);
		}
	}

	public ArrayList<User> loadUserList(String filename, HashMap<User, Integer> userList) {

		ArrayList<User> duplicates = new ArrayList<User>();
 
		// Lets make sure the file path is not empty or null
		if (filename == null || filename.isEmpty()) {
			System.out.println("Invalid File Path");
			return null;
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
					if(userList.containsKey(user)){
						duplicates.add(user);
						int count = userList.get(user);
						userList.replace(user, count++);
					}else{
						userList.put(user, new Integer(1));
					}
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
		return duplicates;
	}

}
