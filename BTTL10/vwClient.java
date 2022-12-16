package BTTL10;


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

//import BTTL10.views.console_chat;


public class vwClient extends JFrame implements ActionListener {
    private JPanel panel;
    private  JLabel  title;
    private JButton enter;
    private JTextField nText;
    static JLabel showChatLabel = new JLabel("");
//    private Client client = null;
    Socket client = null;
    String iAmGhost = null;
    String chatLog="";
    int changePortToName=0;
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
    public JFrame CreateChatWindow(){
        //Chat window
        JFrame newFrame = new JFrame(Integer.toString(changePortToName));
        newFrame.setSize(450, 474);
        newFrame.setLayout(new BorderLayout());
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel showChatField = new JPanel();
        showChatField.add(showChatLabel);
        //showChatField.setSize(450, 900);
        JPanel enterMessageField = new JPanel();
        JTextField enterMessageTextField = new JTextField(30);
        JButton sendMessageButton = new JButton("Send");
        enterMessageField.add(enterMessageTextField);
        enterMessageField.add(sendMessageButton);
        newFrame.add(showChatField, BorderLayout.CENTER);
        newFrame.add(enterMessageField, BorderLayout.SOUTH);
        newFrame.setVisible(true);
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataOutputStream dos = null;
                try {
                    dos = new DataOutputStream(client.getOutputStream());
                    String sms =enterMessageTextField.getText();
                    dos.writeUTF(iAmGhost + ": " + sms);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return newFrame;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == enter){
            try {
                Client clientI = new Client(InetAddress.getLocalHost(), 15797);
                clientI.execute(nText.getText());
                //send dummy to get join
                DataOutputStream dos = null;
                try {
                    dos = new DataOutputStream(client.getOutputStream());
                    String sms ="";
                    dos.writeUTF(iAmGhost + ": " + sms);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            this.CreateChatWindow();
        }
    }
    public static void main(String[] agrs){
        vwClient vv = new vwClient();
    }
    class Client {
        private InetAddress host;
        private int port;

        public Client(InetAddress host, int port) {
            this.host = host;
            this.port = port;
            changePortToName = port;
        }

        public void execute(String name) throws IOException  {
            //Phần bổ sung

//        Scanner sc = new Scanner(System.in);
//        System.out.print("Nhập vào tên của bạn: ");
//
//        String name = sc.nextLine();

            client = new Socket(host, port);
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

                    showChatLabel.setText(sms);
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
                iAmGhost = name;
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
}


//class chatting extends JFrame implements ActionListener {
//    private JPanel panel;
//    private  JLabel  title;
//    private JButton send;
//    private JTextField nText;
//
//    public chatting(){
//        add(CreateUI());
//        setSize(500, 400);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setVisible(true);
//    }
//    public JPanel CreateUI(){
//        panel = new JPanel(new BorderLayout());
//
//        JPanel p = new JPanel(new GridLayout(1,3));
//        title = new JLabel("Enter your name");
//        nText = new JTextField(20);
//        send = new JButton("Send");
//        send.addActionListener(this);
//        p.add(title);
//        p.add(nText);
//        p.add(send);
//        panel.add(p, BorderLayout.NORTH);
//        return panel;
//    }
//    public void actionPerformed(ActionEvent e){
//        if(e.getSource() == send){
//
//            try {
//                Client client = new Client(InetAddress.getLocalHost(), 15797);
//                client.execute(nText.getText());
//            } catch (UnknownHostException ex) {
//                throw new RuntimeException(ex);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//            this.dispose();
//
//        }
//    }
//    public static void main(String[] agrs){
//        chatting vv = new chatting();
//    }
//}