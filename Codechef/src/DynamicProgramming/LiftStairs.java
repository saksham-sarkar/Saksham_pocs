package DynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LiftStairs {
	
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    
			System.out.println("Enter total number of floors");
			int m = Integer.parseInt(br.readLine());
			
			
			
			
			int stairs[]=new int[m-1];
			int lift[]=new int[m-1];
			System.out.println("Enter the time in each floors using stairs");
			for(int i=0;i<m-1;i++)
				stairs[i] = Integer.parseInt(br.readLine());
			System.out.println("Enter the time in each floors using lift");
			for(int i=0;i<m-1;i++)
				lift[i] = Integer.parseInt(br.readLine());
			System.out.println("Enter the lift waiting overhead");
		    int waitingOverhead = Integer.parseInt(br.readLine());
		 	System.out.println("Enter which floor you want to reach");
		    int n = Integer.parseInt(br.readLine());
		int[][] f = new int[2][n];
		f[0][0]=0;
		f[1][0]=0;
		f[0][1] = f[0][0]+stairs[0];
		f[1][1] = f[1][0]+lift[0]+waitingOverhead;
		System.out.println(f[0][0]+" "+f[1][0]);
		System.out.println(f[0][1]+" "+f[1][1]);
		for(int x=2;x<n;x++)
		{
			f[0][x]= Math.min(f[0][x-1]+stairs[x-1],f[0][x-1]+lift[x-1]+waitingOverhead);
			f[1][x] = Math.min(f[1][x-1]+stairs[x-1],f[1][x-1]+lift[x-1]);
			System.out.println(f[0][x]+" "+f[1][x]);
		}
			
		System.out.println(Math.min(f[0][n-1], f[1][n-1]));
	}

}
