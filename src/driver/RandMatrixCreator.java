package driver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

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
		
		for(int p=0;p<n;++p)
			for(int q=0;q<n;++q)
				colorMatrix[p][q]=0;
		
		GridCell arr[] = new GridCell[16];
		
		for(int p=0;p<n;++p)
			for(int q=0;q<n;++q)
				arr[(p*n)+q] = new GridCell(p,q);
		
		
		
		
		
		int temp=0;
	
		/*	
		 List<GridCell> ls = Arrays.asList(arr);
		Collections.shuffle(ls);	
			ListIterator<GridCell> 	lIter = ls.listIterator();
		GridCell colorItem = lIter.next();
				int p= colorItem.row;
				int q=colorItem.column;
			while(lIter.hasNext())		
			{
			*/
				
				
			for(int p=0;p<n;++p)
					for(int q=0;q<n;++q){	
				
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
			
			for(int i=0;i<4;++i) swapColorMatrix(colorMatrix,n);
			
		
			
			return colorMatrix;
		
	}
	
	
	public static void swapColorMatrix(int[][] colorMatrix,int n){
		
		int list1 = (int) (Math.random() * 4);
		int list2 = list1;
		while(list2==list1){
			
			list2=(int) (Math.random() * 4);
		}
		
		int subArrType = (int) (Math.random()*2);
		
		int tempArr[] = new int[n];
		
		if(subArrType==0) { 
			
			for(int i=0;i<n;++i) tempArr[i] = colorMatrix[list1][i]; 
	
		for(int i=0;i<n;++i) colorMatrix[list1][i] = colorMatrix[list2][i];
		
		for(int i=0;i<n;++i) colorMatrix[list2][i] = tempArr[i];
					
		}
		else {
			for(int i=0;i<n;++i) tempArr[i] = colorMatrix[i][list1];
			
			for(int i=0;i<n;++i) colorMatrix[i][list1] = colorMatrix[i][list2];
			
			for(int i=0;i<n;++i) colorMatrix[i] [list2]= tempArr[i];
			
		}
		
	}
	
}
