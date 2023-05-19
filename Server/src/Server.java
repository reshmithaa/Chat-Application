import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends JFrame {
     ClientSocket clientSocketClass;
     JLabel start,status;
    ServerSocket serverSocket;
    Socket clientSocket;
    HashMap<String,ClientSocket> onlineUsers= new HashMap<>();
    ExecutorService executor;
    int port;
    Server(int port) throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        start = new JLabel("Server started...");
        add(start);
        status = new JLabel("Client connected...");
        status.setEnabled(false);
        add(status);
        this.port = port;
        serverSocket = new ServerSocket(this.port);
        add(start);
        executor = Executors.newCachedThreadPool();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        clientSocket = serverSocket.accept();
                        status.setEnabled(true);
                        clientSocketClass = ClientSocketRetrieve.returnClientSocket(clientSocket);
                        executor.execute(new ClientHandle(clientSocketClass, onlineUsers));
                    }
                     catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        setSize(new Dimension(500,500));
        setVisible(true);
    }
    public static void main(String[] args) throws IOException{
        new Server(80);
    }
}

