package BTTL10_20127472.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class vwClient1 extends JFrame implements ActionListener {
    private JPanel panel;
    private  JLabel  title;
    private JButton enter;
    private JTextField nText;
    public  String name="";

    public vwClient1(){
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
    public void ChangeName(String temp){ name = temp;}

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == enter){
            String temp = nText.getText();
            name = new StringBuffer(temp).toString();
            System.out.println(name);
            this.dispose();
        }
    }

    public String getName(){
        System.out.println(name);
        return name;
    }

}
public class vwClient  {

    public static void main(String[] args){

        vwClient1 vw = new vwClient1();

    }
}
