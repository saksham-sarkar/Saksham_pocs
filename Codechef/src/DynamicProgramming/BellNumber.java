package DynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BellNumber {
	public static void main(String args[]) throws IOException
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int n = Integer.parseInt(br.readLine());
	    for(int i=0;i<=n;i++)
	    {
	    	int req = getBellNumber(i);
	    	System.out.println(req+" ");
	    }
	}

	private static int getBellNumber(int n) {
		int bell[][] = new int[n+1][n+1];
		bell[0][0]=1;
		for(int i=1;i<=n;i++)
		{
			bell[i][0]= bell[i-1][i-1];
			for(int j=1;j<=i;j++)
			{
				bell[i][j]=bell[i][j-1]+bell[i-1][j-1];
			}
		}
		 	return bell[n][0];
	}
}
