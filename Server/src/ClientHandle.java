import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
public class ClientHandle implements Runnable{
    ClientSocket clientSocketClass;
    DataBase dataBase = new DataBase();
    Socket clientSocket,receiverClientSocket;
    DataInputStream in;
    DataOutputStream out;
    HashMap<String,ClientSocket> onlineUsers;
    String receiverName;
    String userName;
    String msg;
    public ClientHandle(ClientSocket clientSocketClass, HashMap<String,ClientSocket> onlineUsers)throws IOException {
        this.clientSocketClass = clientSocketClass;
        this.clientSocket = clientSocketClass.clientSocket;
        in = new DataInputStream(this.clientSocket.getInputStream());
        out = new DataOutputStream(this.clientSocket.getOutputStream());
        this.onlineUsers = onlineUsers;
        userName=in.readUTF();
        onlineUsers.put(userName,this.clientSocketClass);
    }
    private boolean isReceiverOnline(String receiverName){
        return onlineUsers.containsKey(receiverName);
    }
    public void run(){
        try {
            while (true){
                //When  the user is selected from the panel.....
                receiverName=in.readUTF();
                msg=in.readUTF();
                //Write Message to DB........
                dataBase.insertChatDetails(userName,receiverName,msg);
                if (isReceiverOnline(receiverName)){
                    System.out.println("\nreceiver is online....");
                    //Retrieving the receiver socket....
                    ClientSocket receiverSocketClass=onlineUsers.get(receiverName);
                    //Writing to the receiver...
                    receiverSocketClass.out.writeUTF(userName);
                    receiverSocketClass.out.writeUTF(msg);

//                  DataOutputStream receiverOut=new DataOutputStream()
                }
            }
        } catch (Exception e) {
            System.out.println("\nClient is not connected... so remove..");// Remove the disconnected socket
            onlineUsers.remove(userName,clientSocket);
        }
    }

    private void sendMessageToDB(){

    }
}
