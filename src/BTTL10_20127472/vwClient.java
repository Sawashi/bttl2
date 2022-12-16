package BTTL10_20127472;


import BTTL10_20127472.views.console_chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;




public class vwClient extends JFrame implements ActionListener {
    private JPanel panel;
    private  JLabel  title;
    private JButton enter;
    private JTextField nText;

    public vwClient(){
        add(CreateUI());
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public JPanel CreateUI(){
        panel = new JPanel(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(1,3));
        title = new JLabel("Enter your name");
        nText = new JTextField(20);
        enter = new JButton("Send");
        enter.addActionListener(this);
        p.add(title);
        p.add(nText);
        p.add(enter);
        panel.add(p, BorderLayout.NORTH);
        return panel;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == enter){

            try {
                Client client = new Client(InetAddress.getLocalHost(), 15797);
                client.execute(nText.getText());
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();


        }
    }
    public static void main(String[] agrs){
        vwClient vv = new vwClient();
    }
}
class Client {
    private InetAddress host;
    private int port;

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public void execute(String name) throws IOException  {
        //Phần bổ sung

//        Scanner sc = new Scanner(System.in);
//        System.out.print("Nhập vào tên của bạn: ");
//
//        String name = sc.nextLine();

        Socket client = new Socket(host, port);
        ReadClient read = new ReadClient(client);
        read.start();
        WriteClient write = new WriteClient(client, name);
        write.start();
    }

}
class ReadClient extends Thread{
    private Socket client;

    public ReadClient(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(client.getInputStream());
            while(true) {
                String sms = dis.readUTF();
                System.out.println(sms);
            }
        } catch (Exception e) {
            try {
                dis.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }
}

class WriteClient extends Thread{
    private Socket client;
    private String name;

    public WriteClient(Socket client, String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        Scanner sc = null;
        try {
            dos = new DataOutputStream(client.getOutputStream());
            sc = new Scanner(System.in);
            while(true) {
                String sms = sc.nextLine();
                dos.writeUTF(name + ": " + sms);
            }
        } catch (Exception e) {
            try {
                dos.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }

}

class chatting extends JFrame implements ActionListener {
    private JPanel panel;
    private  JLabel  title;
    private JButton send;
    private JTextField nText;

    public chatting(){
        add(CreateUI());
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public JPanel CreateUI(){
        panel = new JPanel(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(1,3));
        title = new JLabel("Enter your name");
        nText = new JTextField(20);
        send = new JButton("Send");
        send.addActionListener(this);
        p.add(title);
        p.add(nText);
        p.add(send);
        panel.add(p, BorderLayout.NORTH);
        return panel;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == send){

            try {
                Client client = new Client(InetAddress.getLocalHost(), 15797);
                client.execute(nText.getText());
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();

        }
    }
    public static void main(String[] agrs){
        chatting vv = new chatting();
    }
}