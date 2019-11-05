import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * Dictionary pattern
 * assume a-z ----> 1-26
 * User input 3 51
 * Output axz
 * possible combination of characters that form the sum 51 lexicographically
 */
public class DictionaryPattern {
	public static void main (String[] args) throws java.lang.Exception
	{
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String input = br.readLine();
	    int n = Integer.parseInt(input.split("\\s")[0]);
	    long sum = Long.parseLong(input.split("\\s")[1]);
	    char[] ch = new char[n];
	    int pos_Sum = 0;
	    for(int i=0;i<n;i++)
	    {
	    	ch[i] = (char) (i+97);
	    	pos_Sum+=i+1;
	    }
	    System.out.println("Position sum = "+pos_Sum);
	    if(sum>pos_Sum)
	    {
	    	System.out.println(ch);
	    	sum = sum-pos_Sum;
		    int remain_Increment=0;
		    for(int j=ch.length-1;j>=0;j--)
		    {
		    	int remain = 26-(j+1+remain_Increment); //as all characters should be unique
		    	if(remain>sum)
		    	{
		    		remain = (int)sum;
		    	}
		    		ch[j] += remain;
		    		sum=sum-remain;
		    		remain_Increment++;	
		    	
		    }
		    if(sum==0)
		    {
		    	StringBuffer sb = new StringBuffer();
			    for(int i=0;i<ch.length;i++)
			    {
			    	sb.append(ch[i]);
			    }
			    System.out.println("The required combination characters are "+sb);
		    }
		    else
		    {
		    	System.out.println("Not possible");
		    }
	    }
	    else{
	    	System.out.println("Not possible");
	    }
	}
	    
}
