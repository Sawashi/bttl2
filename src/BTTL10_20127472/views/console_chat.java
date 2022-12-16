package BTTL10_20127472.views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class console_chat extends JFrame implements ActionListener {
    private JPanel panel, content;
    private  JLabel  title;
    private JButton button;
    private JTextField nText;
    private JLabel jLabel;
    private JScrollPane scroll;
    public ArrayList<String> mess;

    public console_chat(){
        add(CreateUI());
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
    }
    public JPanel CreateUI(){
        panel = new JPanel(new BorderLayout());
        content = new JPanel(new GridLayout(-1,1));
        mess = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            mess.add("test");
        }
        for(String name : mess){
            JLabel jLabel1 = new JLabel(name );
            content.add(jLabel1);
        }


        scroll = new JScrollPane(content);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel p = new JPanel(new GridLayout(1,2));

        nText = new JTextField(20);
        button = new JButton("Send");
        button.addActionListener(this);


        p.add(nText);
        p.add(button);
        panel.add(p, BorderLayout.SOUTH);
        return panel;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == button){

            mess.add(nText.getText());

        }
    }
    public static void main(String[] args){

            new console_chat();



    }
}
