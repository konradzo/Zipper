package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    public Main(){
        super("Zipper");
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int appHeight = screenHeight/5;
        int appWidth = screenWidth/4;
        this.setBounds((screenWidth-appWidth)/2,(screenHeight-appHeight)/2,appWidth,appHeight);

        initComponents();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private JList list = new JList();
    private JButton addButton;
    private JButton deleteButton;
    private JButton zipButton;
    private JMenuBar menuBar = new JMenuBar();

    public void initComponents(){

        Action addingAction = new ButtonAction("Add");
        Action deletingAction = new ButtonAction("Delete");
        Action zippingAction = new ButtonAction("Zip");

        this.setJMenuBar(menuBar);
        JMenu menuFile = menuBar.add(new JMenu("File"));
        JMenuItem openMenu = menuFile.add(addingAction);
        JMenuItem deleteMenu = menuFile.add(deletingAction);
        JMenuItem zipMenu = menuFile.add(zippingAction);

        addButton = new JButton(addingAction);
        deleteButton = new JButton(deletingAction);
        zipButton = new JButton(zippingAction);

        GroupLayout layout = new GroupLayout(this.getContentPane());

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addComponent(list,150,150,Short.MAX_VALUE)
                .addContainerGap(0,Short.MAX_VALUE)
                .addGroup(
                        layout.createParallelGroup()
                        .addComponent(addButton)
                        .addComponent(deleteButton)
                        .addComponent(zipButton)
                )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                .addComponent(list,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
                .addGroup(
                        layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addComponent(deleteButton)
                                .addGap(10,50,Short.MAX_VALUE)
                        .addComponent(zipButton)
                )
        );




        this.getContentPane().setLayout(layout);

    }


    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    private class ButtonAction extends AbstractAction{

        public ButtonAction(String buttonName){
            this.putValue(ButtonAction.NAME,buttonName);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
