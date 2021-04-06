//client rather than server this time
package com.grpc.lab1.client;

//request and response message from proto
import com.grpc.lab1.multiplyBlockRequest;
import com.grpc.lab1.multiplyBlockResponse;
//proto builds matrixServiceGrpc
import com.grpc.lab1.MatrixMultServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class GrpcClient {
	public static void main(String[] args)
	{
		//"localhost" is the IP of the server we want to connect to - 8080 is it's port
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081).usePlaintext().build();
		//create a stub and pass the channel in as a variable
		MatrixMultServiceGrpc.MatrixMultServiceBlockingStub stub = MatrixMultServiceGrpc.newBlockingStub(channel);

		int A[][] = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};

        int B[][] = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};	
				
		//Add
		multiplyBlockResponse ans0 = stub.addBlock(multiplyBlockRequest.newBuilder().clearMatrixA().clearMatrixB().addAllMatrixA(TwoDimArrayToTwoDimList(A)).addAllMatrixB(TwoDimArrayToTwoDimList(B)).build());
		System.out.println("Answer:\n");
		
		List<com.grpc.lab1.array> EndMatrix0 = ans0.getMatrixCList();
		int[][] EndMatrixArray0 = listUnpack(EndMatrix0);
		
		for(int x = 0; x<(EndMatrixArray0.length); x++)
        {
			System.out.println(Arrays.toString(EndMatrixArray0[x])+"\n");
        }
				
				
				
		//get a response by calling the multiplyBlockRequest from the stub
		multiplyBlockResponse ans = stub.multiplyBlock(multiplyBlockRequest.newBuilder().clearMatrixA().clearMatrixB().addAllMatrixA(TwoDimArrayToTwoDimList(A)).addAllMatrixB(TwoDimArrayToTwoDimList(B)).build());
		System.out.println("Answer:\n");
		
		List<com.grpc.lab1.array> EndMatrix = ans.getMatrixCList();
		int[][] EndMatrixArray = listUnpack(EndMatrix);
		
		for(int x = 0; x<(EndMatrixArray.length); x++)
        {
			System.out.println(Arrays.toString(EndMatrixArray[x])+"\n");
        }
		channel.shutdown();
	}
	
	static List<com.grpc.lab1.array> TwoDimArrayToTwoDimList(int A[][])
    {
		List<com.grpc.lab1.array> listA = new ArrayList<com.grpc.lab1.array>();
		/* multiplyBlockResponse.Builder response = multiplyBlockResponse.newBuilder();
		
		for(int[] innerArray : multProduct)
		{
			com.grpc.lab1.array.Builder arraySubSet = com.grpc.lab1.array.newBuilder();
			arraySubSet.addAllItem(array2List(innerArray));
			response.addMatrixC(arraySubSet.build());
		} */
		
		for(int[] innerArray : A)
		{
			com.grpc.lab1.array.Builder arraySubSet = com.grpc.lab1.array.newBuilder();
			arraySubSet.addAllItem(array2List(innerArray));
			listA.add(arraySubSet.build());
		}
		
        return listA;
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
}

	/*
	static List<List<String>> TwoDimArrayToTwoDimList(int A[][])
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
	*/
