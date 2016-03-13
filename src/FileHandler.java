import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class FileHandler {
	private Set<String> dictionary;
	
	public Set<String> loadDictionary() {
		File file = new File(Constants.DICTIONARY_PATH);
		dictionary = new TreeSet<String>();
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				dictionary.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return dictionary;
	}
	
	public boolean addToDictionary(String word) {
		//TODO write to file too
		return dictionary.add(word);
	}
	
}
