package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    public Main(){
        super("Zipper");
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int appHeight = screenHeight/4;
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

        Action addingAction = new ButtonAction("Add","Add an entry to the archive","ctrl A",new ImageIcon("add.png"));
        Action deletingAction = new ButtonAction("Delete","Delete an entry from the archive","ctrl D",new ImageIcon("delete.png"));
        Action zippingAction = new ButtonAction("Zip","Zip all files","ctrl Z");

        this.setJMenuBar(menuBar);
        JMenu menuFile = menuBar.add(new JMenu("File"));
        JMenuItem openMenu = menuFile.add(addingAction);
        JMenuItem deleteMenu = menuFile.add(deletingAction);
        JMenuItem zipMenu = menuFile.add(zippingAction);

        addButton = new JButton(addingAction);
        deleteButton = new JButton(deletingAction);
        zipButton = new JButton(zippingAction);
        list.setBorder(BorderFactory.createEtchedBorder());

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
                                .addGap(0,10,Short.MAX_VALUE)
                        .addComponent(zipButton)
                )
        );




        this.getContentPane().setLayout(layout);
        //this.pack();

    }


    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    private class ButtonAction extends AbstractAction{

        public ButtonAction(String buttonName,String description, String keybordShortcut){
            this.putValue(Action.NAME,buttonName);
            this.putValue(Action.SHORT_DESCRIPTION,description);
            this.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(keybordShortcut));
        }

        public ButtonAction(String buttonName,String description, String keybordShortcut,Icon icon){
            this(buttonName,description,keybordShortcut);
            this.putValue(Action.SMALL_ICON,icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Add")){
                System.out.println("Dodaj");
            }
            else if(e.getActionCommand().equals("Delete")){
                System.out.println("Usuń");
            }
            else if(e.getActionCommand().equals("Zip")){
                System.out.println("Zipowanie");
            }
        }
    }
}
