package gitBTTL2;

/* CLIENT */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Client {
    static String msg = "";
    static int sendSignal=0;
    static JLabel showChatLabel = new JLabel("");
    public static void createUI(){
        //Chat window
        JFrame newFrame = new JFrame("Chatting application");
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
        //newFrame.setVisible(true);

        //Enter name window
        JFrame newFrame2 = new JFrame("Enter name");
        newFrame2.setSize(450, 100);
        newFrame2.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel enterNameWindow = new JPanel();
        enterNameWindow.setLayout(new GridLayout(2, 1));
        JPanel enterNamePanel = new JPanel();
        JLabel enterNameLabel = new JLabel("Name: ");
        JTextField enterNameTextField = new JTextField(30);
        enterNameTextField.setSize(300, 50);
        JButton sendNameButton = new JButton("OK");
        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        enterNamePanel.add(enterNameLabel);
        enterNamePanel.add(enterNameTextField);
        enterNamePanel.add(sendNameButton);
        enterNameWindow.add(enterNamePanel);
        enterNameWindow.add(errorLabel);
        newFrame2.add(enterNameWindow);
        newFrame2.setVisible(true);

        sendNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(enterNameTextField.getText().equals("")){
                    errorLabel.setText("Name can not be null");
                }else{
                    msg="setUser:"+enterNameTextField.getText();
                    sendSignal=1;
                    System.out.println(msg);
                    newFrame2.dispose();
                    newFrame.setVisible(true);
                }
            }
        });
    }
    static class sendThread implements Runnable {
        String thrdName;

        sendThread(String name) {
            thrdName = name;
        }

        @Override
        public void run() {
            Socket client = null;

            // Default port number we are going to use
            int portnumber = 1234;

            //Try connection 10 times
            for(int i=0; i<10; i++){
                try{
                    // Create a client socket
                    client = new Socket(InetAddress.getLocalHost(), portnumber);
                    System.out.println("Send thread is created " + client);
                    break;
                } catch (UnknownHostException e) {
                    System.out.println("I/O error " + e);
                } catch (IOException e) {
                    System.out.println("I/O error " + e);
                }
            }

            try{
                // Create an output stream of the client socket
                OutputStream clientOut = client.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut, true);

                // Create an input stream of the client socket
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(clientIn));

                // Create BufferedReader for a standard input
                BufferedReader stdIn = new BufferedReader(new
                        InputStreamReader(System.in));
                while(true){
                    //showChatLabel.setText(br.readLine());
                    if(sendSignal == 0){
                        Thread.sleep(1000);
                    }else{
                        System.out.print("Hey, i send message to server:  ");
                        System.out.println(msg);
                        pw.println(msg);
                        sendSignal=0;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String args[]) {
        createUI();
        sendThread mt = new sendThread("sendFunction");
        Thread newThrd = new Thread(mt);
        newThrd.start();
    }
}