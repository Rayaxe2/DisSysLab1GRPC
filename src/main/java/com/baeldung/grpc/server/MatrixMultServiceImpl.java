//location of file
package com.grpc.lab1.server;

//Name of proto request and repsonse messages
import com.grpc.lab1.multiplyBlockRequest;
import com.grpc.lab1.multiplyBlockResponse;

//matrixMultServiceImpBase generated from proto file 
import com.grpc.lab1.MatrixMultServiceGrpc.MatrixMultServiceImplBase;

import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class MatrixMultServiceImpl extends MatrixMultServiceImplBase
{
	public static int MAX = 4;
	//overide the multiplyBlock function
	@Override
	public void multiplyBlock(multiplyBlockRequest request, StreamObserver<multiplyBlockResponse> reply)
		//StreamObserver<multiplyBlockResponse> reply lets you do async communication if you want
		{
		//List<List<String>> listA = Arrays.asList(request.getMatrixAList());
		//List<List<String>> listB = Arrays.asList(request.getMatrixBList());
		
		//int[][] Array1 = TwoDimStrListToTwoDimIntArray(listA);
		//int[][] Array2 = TwoDimStrListToTwoDimIntArray(listB);
		
		List<com.grpc.lab1.array> listA = request.getMatrixAList();
		List<com.grpc.lab1.array> listB = request.getMatrixBList();
		
		int[][] Array1 = listUnpack(listA);
		int[][] Array2 = listUnpack(listB);
		
		int[][] multProduct = multiplyMatrixBlockAux(Array1, Array2);
			
		//Print out a message saying you received a message from the client
		System.out.println("Multiplication Request recieved from client:\n" +request);
		
		/*
		System.out.println("More stats 1:\n"+listA);
		System.out.println("More stats 2:\n"+listB);
		System.out.println("More stats 3:\n"+Arrays.deepToString(Array1));
		System.out.println("More stats 4:\n"+Arrays.deepToString(Array2));
		*/
		
		//System.out.println("More stats 4:\n"+Arrays.deepToString(multProduct));
		
		
		//build a response
		multiplyBlockResponse.Builder response = multiplyBlockResponse.newBuilder();
		
		for(int[] innerArray : multProduct)
		{
			com.grpc.lab1.array.Builder arraySubSet = com.grpc.lab1.array.newBuilder();
			arraySubSet.addAllItem(array2List(innerArray));
			response.addMatrixC(arraySubSet.build());
		}
		
		/*
			.clearMatrixC()
			.addAllMatrixC(listRepack(multProduct))
			.build();
		*/	
			
		//matrixC - matrixC value is defined in proto file as the only value of the reply
		reply.onNext(response.build());
		reply.onCompleted();
	}
	
	@Override
	public void addBlock(multiplyBlockRequest request, StreamObserver<multiplyBlockResponse> reply)
	{
		List<com.grpc.lab1.array> listA = request.getMatrixAList();
		List<com.grpc.lab1.array> listB = request.getMatrixBList();
		
		int[][] Array1 = listUnpack(listA);
		int[][] Array2 = listUnpack(listB);
		int[][] addProduct = addBlockAux(Array1, Array2);
		
		System.out.println("Add Request recieved from client:\n" +request);
		
		multiplyBlockResponse.Builder response = multiplyBlockResponse.newBuilder();
		for(int[] innerArray : addProduct)
		{
			com.grpc.lab1.array.Builder arraySubSet = com.grpc.lab1.array.newBuilder();
			arraySubSet.addAllItem(array2List(innerArray));
			response.addMatrixC(arraySubSet.build());
		}
		reply.onNext(response.build());
		reply.onCompleted();
	}
	
	static List<Integer> array2List(int A[])
    {
        List<Integer> listA = new ArrayList<Integer>();
        for(int i = 0; i<(A.length); i++)
        {
            listA.add(A[i]);
        }
        return listA;
    }
	
	static List<List<String>> listRepack(int A[][])
    {
        List<List<String>> listA = new ArrayList<List<String>>();
        for(int i = 0; i<(A.length); i++)
        {
            List<String> listB = new ArrayList<String>();
            for(int x = 0; x<(A[i].length); x++)
            {
                listB.add(Integer.toString(A[i][x]));
            }
            listA.add(listB);
        }
        return listA;
    }
	
	static int[][] listUnpack(List<com.grpc.lab1.array> A)
    {
        int[][] ArrayA = new int[A.size()][A.get(0).getItemCount()];

		for (int x = 0; x < A.size(); x++)
        {
             for (int y = 0; y < A.get(x).getItemCount(); y++)
			{
				 ArrayA[x][y] = A.get(x).getItem(y);
			}
        }

        return ArrayA;
	}
	
	/*
	static int[][] listUnpack(List<com.grpc.lab1.array> A)
    {
        int[][] ArrayA = new int[A.size()][A.get(0).getItemCount()];
        String[][] ArrayB = new String[A.size()][]; 

        int i = 0;
        for (com.grpc.lab1.array nestedList : A)
        {
            ArrayB[i++] = nestedList.getItemList().toArray(new String[nestedList.getItemCount()]);
        }

        for (int x = 0; x<(ArrayB.length); x++)
        {
            for (int y = 0; y<(ArrayB[x].length); y++)
            {
                ArrayA[x][y] = Integer.parseInt(ArrayB[x][y]);
            }
        }
        return ArrayA;
    } */
	
	// multiplyBlock and addBlock
    static int[][] multiplyBlockAux(int A[][], int B[][])
    {
        int C[][]= new int[MAX][MAX];
        C[0][0]=A[0][0]*B[0][0]+A[0][1]*B[1][0];
        C[0][1]=A[0][0]*B[0][1]+A[0][1]*B[1][1];
        C[1][0]=A[1][0]*B[0][0]+A[1][1]*B[1][0];
        C[1][1]=A[1][0]*B[0][1]+A[1][1]*B[1][1];
        return C;
    }
    static int[][] addBlockAux(int A[][], int B[][])
    {
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

    static int[][] multiplyMatrixBlockAux( int A[][], int B[][])
    {
        int bSize=2;
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

/*
	
	static int[][] TwoDimStrListToTwoDimIntArray(List<List<String>> A)
    {
        int[][] ArrayA = new int[A.size()][A.get(0).size()];
        String[][] ArrayB = new String[A.size()][]; 

        int i = 0;
        for (List<String> nestedList : A)
        {
            ArrayB[i++] = nestedList.toArray(new String[nestedList.size()]);
        }

        for (int x = 0; x<(ArrayB.length); x++)
        {
            for (int y = 0; y<(ArrayB[x].length); y++)
            {
                ArrayA[x][y] = Integer.parseInt(ArrayB[x][y]);
            }
        }
        return ArrayA;
    }
	
	static List<List<String>> TwoDimArrayToTwoDimList2(int A[][])
    {
        List<List<String>> listA = new ArrayList<List<String>>();
        for(int i = 0; i<(A.length); i++)
        {
            List<String> listB = new ArrayList<String>();
            for(int x = 0; x<(A[i].length); x++)
            {
                listB.add(Integer.toString(A[i][x]));
            }
            listA.add(listB);
        }
        return listA;
    }
	
	static List<com.grpc.lab1.array> listRepack(int A[][])
    {
        List<com.grpc.lab1.array> listA = new ArrayList<com.grpc.lab1.array>();
        for(int i = 0; i<(A.length); i++)
        {
            List<String> listB = new ArrayList<String>();
            for(int x = 0; x<(A[i].length); x++)
            {
                listB.add(Integer.toString(A[i][x]));
            }
            listA.add(listB);
        }
        return listA;
    }	
	*/
