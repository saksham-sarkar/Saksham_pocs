import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//binary conversion way
public class FavorableNumberUsingBinary {
	static Map<String,String> map = new HashMap<>();
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			long input = Long.parseLong(br.readLine());
			System.out.println("You asked for "+input+" the number in series");
			map.put("0", "4");
			map.put("1", "5");
			// lets calculate nth digit number using geometric series 
			long s = input, r=2L;
			long a=2L;
			double num = Math.log(1-((s*(1-r))/a));
			double denom = Math.log(r);
			double div = num/denom;
			//System.out.println(div);
			
			long n = (long)Math.ceil(div);
			//since first number in all sets was not coming correctly
			long first_number_in_Set = (long)Math.pow(2,n+1)-1;
			if(input == first_number_in_Set)
				n = n+1;
			System.out.println(n+"th digit number as one part of mirror");
			//sum of n-1 numbers in the series
		    long prev_sum = (long)(a*(1-Math.pow(r,n-1)))/(1-r);
		    System.out.println("previous sum = "+prev_sum);
		    //position in respective set
		    long position = input - prev_sum;
		    System.out.println("position in set = "+position);
		    
		    long x=position -1;
		    String requiredNumber = getFromBinary(x,n);
			System.out.println("RequiredNumber = "+requiredNumber);
	}

	private static String getFromBinary(long x, long n) {
		String bin = convertToBinary(x,n);
		StringBuffer newBin = new StringBuffer(bin);
		newBin.reverse();
		bin+=newBin.toString();
		System.out.println(bin);
		StringBuffer finalSb = new StringBuffer();
		for(int i=0;i<bin.length();i++)
		{
			finalSb.append(map.get(String.valueOf(bin.charAt(i))));
		}
		
		return finalSb.toString();
	}

	private static String convertToBinary(long x, long n) {
		StringBuffer sb = new StringBuffer("");
		if(x==0)
			sb.append("0");
		while(x!=0)
		{
			long rem = x%2;
			sb.append(rem);
			x/=2;
		}
		System.out.println("-------"+sb+"-------"+n);
		while(sb.length()<n)
			sb.append("0");
		return sb.reverse().toString();
	}
	
}
