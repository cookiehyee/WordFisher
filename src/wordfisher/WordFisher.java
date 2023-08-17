package wordfisher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class WordFisher {

	// Please note these variables. they are the state of the object.
	public HashMap<String, Integer> vocabulary;
	public List<String> stopwords; // User ArrayList for initialization
	private String inputTextFile;
	private String stopwordsFile;

	WordFisher(String inputTextFile, String stopwordsFile) throws IOException {
		this.inputTextFile = inputTextFile;
		this.stopwordsFile = stopwordsFile;

		buildVocabulary();
		getStopwords();
	}

	public void buildVocabulary() throws IOException {
		vocabulary = new HashMap<String, Integer>();

		String reader = new String(Files.readAllBytes(Paths.get(inputTextFile)));
		String[] allWords = reader.split("\\s+");
		

		for(int i = 0; i < allWords.length; i++) {
			allWords[i] = allWords[i].replaceAll("[^a-zA-Z0-9 ]", "");
			allWords[i] = allWords[i].toLowerCase();
		}
	
		
		for(int i = 0; i < allWords.length; i++) {
			if (vocabulary.containsKey(allWords[i])) {
				vocabulary.replace(allWords[i], vocabulary.get(allWords[i]) + 1);
			}
			else {
				vocabulary.put(allWords[i], 1);
			}
		}
	

	}

	public void getStopwords() {
		stopwords = new ArrayList<String>();
		String word;

		try {
			BufferedReader input = new BufferedReader(new FileReader(stopwordsFile));
			while ((word = input.readLine()) != null) {
				stopwords.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getWordCount() {
		
		int wordCount = 0;
		
		for(String key : vocabulary.keySet()) {	
			wordCount += vocabulary.get(key);
		}
		
		return wordCount - vocabulary.get("");
	}

	public int getNumUniqueWords() {
		return vocabulary.size();
	}

	public int getFrequency(String word) {
		if(vocabulary.containsKey(word)){
			return vocabulary.get(word);
		}
		return -1;
	}

	public void pruneVocabulary() {
		for(int i = 0; i < stopwords.size(); i++) {
			vocabulary.remove(stopwords.get(i));
		}
	}
	
	public class WordNode {
		String word;
		int frequency;
		
		WordNode(String word, int frequency) {
			this.word = word;
			this.frequency = frequency;
		}

	}
	
	public class WordNodeComparator implements Comparator<WordNode> {
		@Override
		public int compare(WordNode o1, WordNode o2) {
			return o2.frequency - o1.frequency;
		}
	}

	public ArrayList<String> getTopWords(int n) {
		ArrayList<String> topWords = new ArrayList<String>();
		PriorityQueue<WordNode> wordNodes = new PriorityQueue<WordNode>(new WordNodeComparator());
		
		for(String word : vocabulary.keySet()) {
			if(!word.equals("")) {
				wordNodes.add(new WordNode(word, vocabulary.get(word)));
			}
		}
		
		for(int i = 0; i < n; i++) {
			topWords.add(wordNodes.poll().word);
		}
		
		return topWords;
	}

	public ArrayList<String> commonPopularWords(int n, WordFisher other) {
		ArrayList<String> commonPopWords = new ArrayList<String>();
		ArrayList<String> thisWordFisher = new ArrayList<String>();
		ArrayList<String> otherWordFisher = new ArrayList<String>();
		
		thisWordFisher = this.getTopWords(n);
		otherWordFisher = other.getTopWords(n);
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(thisWordFisher.get(i).equals(otherWordFisher.get(j))) {
					commonPopWords.add(thisWordFisher.get(i));
				}
			}
		}
		
		return commonPopWords;
	}

}
