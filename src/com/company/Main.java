package com.company;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(){
        super("Zipper");
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int appHeight = screenHeight/3;
        int appWidth = screenWidth/3;

        this.setBounds((screenWidth-appWidth)/2,(screenHeight-appHeight)/2,appWidth,appHeight);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
