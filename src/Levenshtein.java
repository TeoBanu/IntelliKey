public class Levenshtein {

	public double distance(String word1, String word2) {
		if (word1.equals(word2)) {
			return 0;
		}

		if (word1.length() == 0) {
			return word2.length();
		}

		if (word2.length() == 0) {
			return word1.length();
		}

		int[] workVector1 = new int[word2.length() + 1];
		int[] workVector2 = new int[word2.length() + 1];
		int[] tempWorkVector;

		for (int i = 0; i < workVector1.length; i++) {
			workVector1[i] = i;
		}

		for (int i = 0; i < word1.length(); i++) {

			workVector2[0] = i + 1;

			for (int j = 0; j < word2.length(); j++) {
				int cost = (word1.charAt(i) == word2.charAt(j)) ? 0 : 1;
				workVector2[j + 1] = Math.min(workVector2[j] + 1,
						Math.min(workVector1[j + 1] + 1, workVector1[j] + cost));
			}

			tempWorkVector = workVector1;
			workVector1 = workVector2;
			workVector2 = tempWorkVector;

		}
		return workVector1[word2.length()];
	}
}
