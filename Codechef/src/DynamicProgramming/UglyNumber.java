package DynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Ugly numbers are numbers whose only prime factors are 2, 3 or 5. 
 * The sequence 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, … shows the first 11 ugly numbers. 
 * By convention, 1 is included.
 * Given a number n, the task is to find n’th Ugly number.
 * Input  : n = 7
 * Output : 8
 * Input  : n = 10
 * Output : 12
 */

public class UglyNumber {
	public static void main(String args[]) throws IOException
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int n = Integer.parseInt(br.readLine());
	    int res = getNthUglyNumber(n);
	    System.out.println(res);
	}

	private static int getNthUglyNumber(int n) {
		int uglyNumber[] = new int[n];
		uglyNumber[0] = 1;
		int count =1;
		int i=1;
		while(count<n)
		{ 
			i++;
			if(isUgly(i)==1)
				count++;
			
		}
		return i;
	}

	private static int isUgly(int a) {
		 a = checkIfUgly(a,2);
		 a= checkIfUgly(a,3);
		 a = checkIfUgly(a,5);
				
		return (a==1)?1:0;
	}

	private static int checkIfUgly(int a, int b) {
		while(a%b==0)
			a=a/b;
		return a;
	}
}
