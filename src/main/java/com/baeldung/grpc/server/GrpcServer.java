package com.grpc.lab1.server;
import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		//build server and ser it to port 8080 and add service implmentation to it
		Server server = ServerBuilder.forPort(8081).addService(new MatrixMultServiceImpl()).build();
		System.out.println("Starting Server...");
		server.start();
		System.out.println("Server Started!");
		server.awaitTermination(); //loops until termination
	}
}

