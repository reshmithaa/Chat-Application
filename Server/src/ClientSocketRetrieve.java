import javax.xml.crypto.Data;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientSocketRetrieve{
    public static ClientSocket returnClientSocket(Socket clientSocket){
            return new ClientSocket(clientSocket);
    }
}
class ClientSocket{
    Socket clientSocket;
    DataOutputStream out;
    DataInputStream in;
    ClientSocket(Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        }
        catch (Exception e){
            System.out.println("\nerror in client socket class");
        }
    }
}


