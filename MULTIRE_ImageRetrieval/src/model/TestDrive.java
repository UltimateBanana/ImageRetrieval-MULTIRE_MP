package model;

public class TestDrive {
	
	public static void main(String args[]){
		
		int[][] matrix = new int[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				matrix[i][j] = 0;
			}
		}
		matrix[0][0] = 1;
		matrix[0][1] = 1;
		matrix[0][3] = 1;
		matrix[0][4] = 1;
		matrix[0][5] = 1;
		matrix[0][7] = 1;
		
		matrix[1][0] = 1;
		matrix[1][1] = 1;
		matrix[1][3] = 1;
		matrix[1][5] = 1;
		matrix[1][7] = 1;
		
		matrix[2][0] = 1;
		matrix[2][1] = 1;
		matrix[2][2] = 1;
		matrix[2][3] = 1;
		matrix[2][7] = 1;
		
		matrix[3][7] = 1;
		
		matrix[4][0] = 1;
		matrix[4][1] = 1;
		matrix[4][2] = 1;
		matrix[4][3] = 1;
		matrix[4][5] = 1;
		matrix[4][7] = 1;
		
		matrix[5][3] = 1;
		matrix[5][5] = 1;
		matrix[5][7] = 1;
		
		matrix[6][0] = 1;
		matrix[6][1] = 1;
		matrix[6][2] = 1;
		matrix[6][3] = 1;
		matrix[6][7] = 1;
		
		matrix[7][0] = 1;
		matrix[7][1] = 1;
		matrix[7][2] = 1;
		matrix[7][3] = 1;
		matrix[7][5] = 1;
		matrix[7][6] = 1;
		matrix[7][7] = 1;
		
		ColorCoherence ccv = new ColorCoherence(matrix, 6);
		ccv.coherence();
	}

}
