import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WordSimilarities {

	private Set<String> dictionaryWords;
	private FileHandler fh;
	// private static final double SIMILARITY_THRESHOLD = 0.7;
	private double accuracy;
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public void setMaxSuggestions(int maxSuggestions) {
		this.maxSuggestions = maxSuggestions;
	}

	private int maxSuggestions;

	public WordSimilarities(double accuracy, int maxSuggestions) {
		fh = new FileHandler();
		dictionaryWords = fh.loadDictionary();
		this.accuracy = accuracy;
		this.maxSuggestions = maxSuggestions;
	}

	public List<String> findSimilarities(String word) {

		List<String> similars = new ArrayList<>();
		Levenshtein l = new Levenshtein();

		// Iterate through the entire list of words from the input file.
		boolean maxReached = false;
		for (Iterator<String> it = dictionaryWords.iterator(); it.hasNext() && !maxReached;) {
			String w = it.next();
			// Use Levenshtein distance algorithm and divide the result by the
			// longest string ==> the distance is a value in [0,1] interval -> 0
			// is the word, 1 no similarity
			double d = l.distance(w, word) / Math.max(w.length(), word.length());

			// The similarity values are swapped. 0 = no similarity, 1 = the
			// words are identical
			double similarity = 1 - d;

			// Threshold for similarity is reached
			if (similarity > accuracy) {
				similars.add(w);
				if (similars.size() >= maxSuggestions) {
					maxReached = true;
				}
			}
		}

		return similars;

	}
	
	public static void main(String[] args) {
		WordSimilarities ws = new WordSimilarities(0.7, 10);
		System.out.println(ws.findSimilarities("a"));
	}

}
