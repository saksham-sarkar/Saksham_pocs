package DynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * Dynamic programming approach
 */
public class UglyNumber_Efficient {
	public static void main(String args[]) throws IOException
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int n = Integer.parseInt(br.readLine());
	    System.out.println(getNthUglyNumber(n));
	}

	private static int getNthUglyNumber(int n) {
		int uglyNumber[] = new int[n];
		uglyNumber[0]=1;
		int i2=0,i3=0,i5=0;
		int next_multipleOf2 = uglyNumber[i2]*2;
		int next_multipleOf3 = uglyNumber[i3]*3;
		int next_multipleOf5 = uglyNumber[i5]*5;
		int nextUglyNumber = 0;
		for(int i =1; i<n;i++)
		{
			nextUglyNumber = Math.min(next_multipleOf2, Math.min(next_multipleOf3, next_multipleOf5));
			uglyNumber[i]=nextUglyNumber;
			if(nextUglyNumber == next_multipleOf2)
			{
				i2=i2+1;
				next_multipleOf2 = uglyNumber[i2]*2;
			}
			if(nextUglyNumber == next_multipleOf3)
			{
				i3=i3+1;
				next_multipleOf3 = uglyNumber[i3]*3;
			}
			if(nextUglyNumber == next_multipleOf5)
			{
				i5=i5+1;
				next_multipleOf5 = uglyNumber[i5]*5;
			}
		}
		
		return uglyNumber[n-1];
	}
}
