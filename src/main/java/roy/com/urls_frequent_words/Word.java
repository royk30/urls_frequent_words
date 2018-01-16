package roy.com.urls_frequent_words;

public class Word implements Comparable<Word> {

	String word;
	int count;

	@Override
	public int hashCode()
	{
		return word.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return word.equals(((Word)obj).word);
	}
	
	public int compareTo(Word b)
	{
		return b.count - count;
	}

}
