import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    String userName;
    Socket clientSocket;
    DataOutputStream out;
    DataInputStream in;

    Client(String ip, int port,String userName)throws IOException {
        try {
            this.userName = userName;
            clientSocket = new Socket(ip,port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        }
        catch (Exception e){
            System.out.println("\nError in client constructor");
            throw new IOException();
        }
        // if client socket is created successfully
        out.writeUTF(userName);
    }
//    Client

    public  void write(String receiverName,String msg){
        try {
            //Destination username
            out.writeUTF(receiverName);
            //Message
            out.writeUTF(msg);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

