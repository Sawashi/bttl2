package FactoryServer;

import java.net.ServerSocket;
import java.net.Socket;

public class FactoryServer {
    public void main (String[] agrs){
        try {
            System.out.println("Writing for client.....");
            ServerSocket ss = new ServerSocket(9806);
            Socket soc = ss.accept();
            System.out.println("Connection established");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
