package roy.com.urls_frequent_words;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FrequentWords {
	//file that contain list of urls
	private final static String URLS_FILE = "src\\main\\java\\roy\\com\\urls_frequent_words\\urls";
	
	// max words to find
	private final static int MAX_WORDS = 10;
	
	public static void getFrequentWords() {
		Map<String, Word> countMap = wordsMap();   // get map of words
		List<String> words = mostFrequentWords(countMap); // get most frequent words from the map
		printList(words); // print the list
	}
	
	// Get List of Urls from a file	
	private static List<String> getUrlsFromFile() {
        try {
        	
        	List<String> urls = new ArrayList<String>(); 

            File f = new File(URLS_FILE);

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                urls.add(readLine);
            }
            
            b.close();
            
            return urls;

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
	}
	
	// get a map of words objects
	private static Map<String, Word> wordsMap() {
    	List<String> urls = getUrlsFromFile();
    	Map<String, Word> countMap = new HashMap<String, Word>();
    	String delimiters = "\\s+|,|\\.|\\|:|\\||\\|\"|[0-9]+|-|%|>>";
    	
    	for(String url:urls) {
	        Document doc;
			try {
				doc = Jsoup.connect(url).get();
		        
		        //Get the actual text from the page, excluding the HTML
		        String text = doc.body().text();
		        
		        //Create BufferedReader so the words can be counted
		        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
		        String line;
		        while ((line = reader.readLine()) != null) {
		        	// split the line to words 
		            String[] words = line.split(delimiters);
		            for (String word : words) {
		                if ("".equals(word)) {
		                    continue;
		                }
		                
		                // get only words with alphabetic characters
		                String pattern = "(.*)([א-תa-zA-Z])(.*)";
		
		                // Create a Pattern object
		                Pattern r = Pattern.compile(pattern);
		
		                // Now create matcher object.
		                Matcher m = r.matcher(word);
		                if (m.find( )) {
		
		                Word wordObj = countMap.get(word);
		                
		                //if the word doesn't exist on the map, add it to the map
		                if (wordObj == null) {
		                    wordObj = new Word();
		                    wordObj.word = word;
		                    wordObj.count = 0;
		                    countMap.put(word, wordObj);
		                }
		
		                wordObj.count++;
		            }
		            }
		        }
		        reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
    	}
		return countMap;		
	}
	
	/* get from the map the frequent word and put it on a list, sort the list by
	   the length of the words */ 
	private static List<String> mostFrequentWords(Map<String, Word> countMap) {
        SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
        int i = 0;
        int maxWords = MAX_WORDS;

        List<String> list=new ArrayList<String>();

        for (Word word : sortedWords) {
            if (i >= maxWords) { 
                break;
            }          
            list.add(word.word);
            i++;
        }
        
        //sort the list by the length of the words
        Collections.sort(list, new Comparator<String>() {

            public int compare(String o1, String o2) {
                if(o1.length()>o2.length()){
                    return 1;
                }
                else{
                    return 
                    		-1;
                }
            }
        });
        
        return list;
	}
	
	private static void printList(List<String> list) {
        for(String str:list) {
        	System.out.println("length " + str.length() + ": " + str );
        }
		
	}
}
