package driver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RandMatrixCreator {

	public static Integer[] getTextualMatrix(){
		
		Integer[] arr = new Integer[36];
		
		for(int i=0;i<=35;++i) arr[i]=Integer.valueOf(i);

		List<Integer> in = Arrays.asList(arr);
		
		Collections.shuffle(in);
		
		in.toArray(arr);
		
		return arr;
	}
	
	
	public static int[][] getColorMatrix(int n){
		
		int colorMatrix[][] = new int[n][n];
		
		
		
		
		int temp=0;
		for(int p=0;p<n;++p)
			for(int q=0;q<n;++q)
			{
				int[] list={1,2,3,4};
				
					for(int s=0;s<n;++s) 
					{
					temp = colorMatrix[s][q];
					if(temp!=0) list[temp-1]=-1;
					
					}
					for(int s=0;s<n;++s) 
					{
					temp = colorMatrix[p][s];
					if(temp!=0) list[temp-1]=-1;
					
					}
					
					for(int r=0;r<n;++r)
						if(list[r]>0) {colorMatrix[p][q]=list[r]; break;}
						
					
			}
			
			return colorMatrix;
		
	}
	
	
	
	
}
