package ie.gmit.sw;

import java.util.TreeSet;

public class FileParser extends Runner{

//ignoreWord will receive just Words
private TreeSet<String> ignoreWord = new TreeSet<String>();

	public FileParser() {

	}

	
	// Adds 1 word to the list each time
	public void addIgnoreWord(String ignoreWords) {
		ignoreWord.add(ignoreWords);
	}

	// Returns words from ignorewords
	public TreeSet<String> getIgnoreWord() {
		return ignoreWord;
	}
}
