package wordfisher;

import java.io.IOException;

public class WordFisherTester {
	
	public static void main(String[] args) {
		
		try {
			
			WordFisher alice = new WordFisher("texts/carroll-alice.txt", "stopwords.txt");
			WordFisher moby = new WordFisher("texts/moby-dick.txt", "stopwords.txt");
			
			
			System.out.println("Number of words in Alice: " + alice.getWordCount());
			System.out.println("Number of words in Moby: " + moby.getWordCount());
			
			System.out.println("\nNumber of unique words in Alice: " + alice.getNumUniqueWords());
			System.out.println("Number of unique words in Moby: " + moby.getNumUniqueWords());
			
			System.out.println("\nFrequency of the word handkerchief in Alice: " + alice.getFrequency("handkerchief"));
			System.out.println("Frenquency of the word handkerchief in Moby: " + moby.getFrequency("handkerchief"));
			
			alice.pruneVocabulary();
			System.out.println("\nStop words removed from Alice.");
			System.out.println("Number of words in Alice after pruning: " + alice.getWordCount());
			moby.pruneVocabulary();
			System.out.println("Stop words removed from Moby.");
			System.out.println("Number of words in Moby after pruning: " + moby.getWordCount());
			
			
			System.out.println("\nTop 10 words of pruned Alice: " + alice.getTopWords(10));
			System.out.println("Top 10 words of pruned Moby: " + moby.getTopWords(10));
			
			System.out.println("\nCommon popular words of Alice and Moby: " + alice.commonPopularWords(20, moby));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
