/* package whatever; // don't place package name! */

/*
 * 
Input
2
8
jobs
jobs
songs
cats
videos
cats
jobs
top 3
10
videos
songs
songs
top 1
videos
dogs
jobs
movies
dogs
top 5

Output:
jobs cats songs
songs
dogs songs videos jobs movies
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

/* Name of the class has to be "Main" only if the class is public. */
public class Stockroom
{
	static ArrayList<String> al = new ArrayList<String>();
	static ArrayList<Integer> ar = new ArrayList<Integer>();
	static int sm=0;
	public static void doTestCase(Scanner s)
	{
		Map<String, Integer> words = new HashMap<>();
 
		int numEntries = Integer.parseInt(s.nextLine());
		String cmd = null;
		
		for (int i = 0; i < numEntries; i++) {
			String word = s.nextLine();
			if(word.split(" ")[0].equals("top"))
			{
				cmd = word;
				String[] parts = cmd.split(" ");
				if (parts.length == 2 && parts[0].equals("top")) {
					int cn = Integer.parseInt(parts[1]);
					List<Map.Entry<String, Integer>> sortedEntries = words.entrySet().stream()
                            .sorted(Entry.<String, Integer>comparingByValue().reversed()
                                         .thenComparing(Entry::getKey))
                            .collect(Collectors.toList());
					long limit = cn;
					sm+=Integer.parseInt(parts[1]);
					ar.add(sm-1);
					for (Map.Entry<String, Integer> stringIntegerEntry : sortedEntries) {
					    if (limit-- == 0) break;
					    al.add(stringIntegerEntry.getKey());
					    
					}
				
				}
			}
			else{
			Integer cnt = words.get(word);
    		if (cnt == null ) words.put(word, 1);
    		else words.put(word, cnt + 1);	
			}
		}
	}
	public static void main (String[] args) throws java.lang.Exception
	{
		Scanner s = new Scanner(System.in);
 
		int numTestCases = Integer.parseInt(s.nextLine());
		for (int i = 0; i < numTestCases; i++) {
			doTestCase(s);
		}
		int c=0;
		for(int i=0;i<al.size();i++)
		{
			if(i==ar.get(c))
			{
				c++;
				System.out.println(al.get(i));
			}
			else
			{
				System.out.print(al.get(i)+" ");
			}
				
			
		}
		
	}

}



