public class HelloWorld{

    public static void main(String []args){
        int A[][][] = {
                {
                        {1, 2},
                        {7, 8}
                },
                {
                        {3, 4},
                        {9, 10}
                },
                {
                        {5, 6},
                        {11, 12}
                },
                {
                        {13, 14},
                        {19, 20}
                },
                {
                        {15, 16},
                        {21, 22}
                },
                {
                        {17, 18},
                        {23, 24}
                },
                {
                        {25, 26},
                        {31, 32}
                },
                {
                        {27, 28},
                        {33, 34}
                },
                {
                        {29, 30},
                        {35, 36}
                }
        };

        int B[][][] = {
                {
                        {36, 35},
                        {30, 29}
                },
                {
                        {34, 33},
                        {28, 27}
                },
                {
                        {32, 31},
                        {26, 25}
                },
                {
                        {24, 23},
                        {18, 17}
                },
                {
                        {22, 21},
                        {16, 15}
                },
                {
                        {20, 19},
                        {14, 13}
                },
                {
                        {12, 11},
                        {6, 5}
                },
                {
                        {10, 9},
                        {4, 3}
                },
                {
                        {8, 7},
                        {2, 1}
                }
        };

        int B1[][] = {
                {},
                {},
                {},
                {},
        };



        System.out.println(dynamicMatrixMultiplication(A,B));

        System.out.println(multiplyMatrixBlockAux());
    }

    //dynamicMatrixMultiplication
    static int[][][] dynamicMatrixMultiplication(int blocksA[][][], int blocksB[][][]) {
        int dim = (int) Math.sqrt(blocksA.length);
        int[][][] blockMultResult = new int[blocksA.length][blocksA[0].length][blocksA[0][0].length];

        for(int i = 0; i < blocksA.length; i++){
            for(int j = 0; j < dim; j++){
                blockMultResult[i] = addBlockAux(blockMultResult[i], multiplyBlockAux(blocksA[j + (dim * (int) Math.floor(i/dim))], blocksB[(j * dim) + (i % dim)]));
            }
        }
        return blockMultResult;
    }

    // multiplyBlock
    static int[][] multiplyBlockAux(int A[][], int B[][]) {
        int MAX = 4;
        int C[][]= new int[MAX][MAX];
        C[0][0]=A[0][0]*B[0][0]+A[0][1]*B[1][0];
        C[0][1]=A[0][0]*B[0][1]+A[0][1]*B[1][1];
        C[1][0]=A[1][0]*B[0][0]+A[1][1]*B[1][0];
        C[1][1]=A[1][0]*B[0][1]+A[1][1]*B[1][1];
        return C;
    }

    //addBlock
    static int[][] addBlockAux(int A[][], int B[][]) {
        int MAX = 4;
        int C[][]= new int[MAX][MAX];

        for (int i=0;i<C.length;i++)
        {
            for (int j=0;j<C.length;j++)
            {
                C[i][j]=A[i][j]+B[i][j];
            }
        }
        return C;
    }

        //multiplyMatrixBlockAux - provided in lab 1
	static int[][] multiplyMatrixBlockAux(int A[][], int B[][]) {
		int MAX = 6;
		int bSize = 6;
		int[][] A1 = new int[MAX][MAX];
		int[][] A2 = new int[MAX][MAX];
		int[][] A3 = new int[MAX][MAX];
		int[][] B1 = new int[MAX][MAX];
		int[][] B2 = new int[MAX][MAX];
		int[][] B3 = new int[MAX][MAX];
		int[][] C1 = new int[MAX][MAX];
		int[][] C2 = new int[MAX][MAX];
		int[][] C3 = new int[MAX][MAX];
		int[][] D1 = new int[MAX][MAX];
		int[][] D2 = new int[MAX][MAX];
		int[][] D3 = new int[MAX][MAX];
		int[][] res= new int[MAX][MAX];
		for (int i = 0; i < bSize; i++)
		{
			for (int j = 0; j < bSize; j++)
			{
				A1[i][j]=A[i][j];
				A2[i][j]=B[i][j];
			}
		}
		for (int i = 0; i < bSize; i++)
		{
			for (int j = bSize; j < MAX; j++)
			{
				B1[i][j-bSize]=A[i][j];
				B2[i][j-bSize]=B[i][j];
			}
		}
		for (int i = bSize; i < MAX; i++)
		{
			for (int j = 0; j < bSize; j++)
			{
				C1[i-bSize][j]=A[i][j];
				C2[i-bSize][j]=B[i][j];
			}
		}
		for (int i = bSize; i < MAX; i++)
		{
			for (int j = bSize; j < MAX; j++)
			{
				D1[i-bSize][j-bSize]=A[i][j];
				D2[i-bSize][j-bSize]=B[i][j];
			}
		}
		A3=addBlockAux(multiplyBlockAux(A1,A2),multiplyBlockAux(B1,C2));
		B3=addBlockAux(multiplyBlockAux(A1,B2),multiplyBlockAux(B1,D2));
		C3=addBlockAux(multiplyBlockAux(C1,A2),multiplyBlockAux(D1,C2));
		D3=addBlockAux(multiplyBlockAux(C1,B2),multiplyBlockAux(D1,D2));
		for (int i = 0; i < bSize; i++)
		{
			for (int j = 0; j < bSize; j++)
			{
				res[i][j]=A3[i][j];
			}
		}
		for (int i = 0; i < bSize; i++)
		{
			for (int j = bSize; j < MAX; j++)
			{
				res[i][j]=B3[i][j-bSize];
			}
		}
		for (int i = bSize; i < MAX; i++)
		{
			for (int j = 0; j < bSize; j++)
			{
				res[i][j]=C3[i-bSize][j];
			}
		}
		for (int i = bSize; i < MAX; i++)
		{
			for (int j = bSize; j < MAX; j++)
			{
				res[i][j]=D3[i-bSize][j-bSize];
			}
		}
		for (int i=0; i<MAX; i++)
		{
			for (int j=0; j<MAX;j++)
			{
				System.out.print(res[i][j]+" ");
			}
			System.out.println("");
		}
		return res;
	}

}