/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package predictivetext;

import java.io.*;
import java.util.*;
import java.lang.Math.*;

class Regression{
	public static void main(String args[])throws Exception
	{
		Scanner sc=new Scanner(new File("E:/power.txt"));
		int p=sc.nextInt();
                System.out.println(p);
		//2sc=new Scanner(new File("E:/input.csv"));
		double arr[][]=new double[p+1][p+1];
		double rhs[][]=new double[p+1][1];
		int k=0;
		int count=-1;
		for(int i=0;i<p+1;i++)
		{
			for(int j=0;j<p+1;j++)
			{
				count++;
                                sc=new Scanner(new File("E:/input.txt"));
				while(sc.hasNextLine())
				{
					String input=sc.nextLine();
                                        String data[]=input.split(",");
                                        double x=Double.parseDouble(data[1]);
					double y=Double.parseDouble(data[2]);
					System.out.println(x+"    "+y);
					if(i==0 && j==0)
					{
						k++;
						arr[i][j]=k;
						rhs[i][j]=rhs[i][j]+y;
					}
					else
					{
						arr[i][j]=arr[i][j]+Math.pow(x,count);
                                                System.out.println(count);
						if(j==0)
						{
                                                    rhs[i][j]=rhs[i][j]+Math.pow(y,count);
						}
					}
				}
			}
			count=count-p;
		}
                System.out.println("ARRAY:");
                for(int i=0;i<p+1;i++)
        {
            for(int j=0;j<p+1;j++)
            {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }//correct till here
        double inv[][]=invert(arr);
         System.out.println("INVERSE:");
                for(int i=0;i<p+1;i++)
        {
            for(int j=0;j<p+1;j++)
            {
                System.out.print(inv[i][j]+" ");
            }
            System.out.println();
        }//correct till here
        double ans[][]=new double[p+1][1];
        double sum=0.0;
        for(int i=0;i<p+1;i++)
        {
            for(int j=0;j<1;j++)
            {
                for(int w=0;w<p+1;w++)
                {
                    sum=sum+inv[i][w]*rhs[w][j];
                }
                ans[i][j]=sum;
                sum=0;
            }
        }
        System.out.println("Enter the value of x : ");
	Scanner scan=new Scanner(System.in);
        String ip=scan.next();
        double dayval=Double.parseDouble(ip);
		double b[][]=new double[p+1][1];
		double predicted=0.0;
		for(int m=0;m<p+1;m++)
		{
			b[m][0]=ans[m][0];
			predicted=predicted+(b[m][0]*Math.pow(dayval,m));
		}		
        System.out.println("\nPredicted Price on Day "+dayval+" is : "+predicted);
		
	}
	
	public static double[][] invert(double a[][]) 
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
 // Transform the matrix into an upper triangle
        gaussian(a, index);
 
 // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]-= a[index[j]][i]*b[index[i]][k];
 
 // Perform backward substitutions
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
 
// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
 
    public static void gaussian(double a[][], int index[]) 
    {
        int n = index.length;
        double c[] = new double[n];
 
 // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
 // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
 // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
   // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
 
 // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
 // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
}