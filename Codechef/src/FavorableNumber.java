/* package codechef; // don't place package name! */
/**
 * series 44,55,4444,4454,......
 * create a permutation series containing only 4 or 5 palindromic and even digits only
 * user will give the position of the number in this series as input
 * Result should be the number at that position
 * eg. Input 4
 * Output 4454
 * Input 14
 * Output
 * 555555
 */
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/* Name of the class has to be "Main" only if the class is public. */
public class FavorableNumber
{
	static HashSet<String> set =new LinkedHashSet<>();
	public static void main (String[] args) throws java.lang.Exception
	{
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Map<Long,Long> map = new LinkedHashMap<>();
		long input = Long.parseLong(br.readLine());
		System.out.println("You asked for "+input+" th number in series");
		long sm=0;
		long prev_sum=0L;
		long i=2L;
		long a=2L;
		long index=0L;
		while(true)
		{
		    long power = i/2L;
			double pw = Math.pow(2,power);
			long lpw = (long)pw;
		    map.put(i,lpw);
		    sm+=map.get(i);
		    if(sm>=input)
		    {
				double d = -a*(1-Math.pow(2L,power-1));
		        //get sum of numbers less than i's value(2^i)
		         prev_sum = (long)d;
		        index = input - prev_sum;// means required number is the indexth position in i digit number
		        break;
		    }
		    i+=2;
		}
		//i will denote the digits count
		System.out.println(" The number will come in the series of "+i+"digit numbers");
		System.out.println("The position of the number in the "+i+"digit numbers is = "+index);

		repeat("4",i);
		repeat("5",i);
		int counter=0;
		for(String str : set)
		{
			if(++counter==index)
			{
				System.out.println(str);
				break;
			}
		}
	}
	
	static void repeat(String k, long dig)
	{
		
		if(k.length()== dig)
		{
			if(checkIfPalindrome(k))
				set.add(k);
			return;
		}
		repeat(k+4,dig);
		repeat(k+5,dig);
	}

	private static boolean checkIfPalindrome(String k) {
		int cnt=0;
		int len = k.length();
		int mid = len/2;
		for(int j=len-1;j>=mid;j--)
		{
			if(k.charAt(j)==k.charAt(cnt))
				cnt++;
		}
		if(cnt == mid)
		{
			return true;
		}
		return false;
	}
}
