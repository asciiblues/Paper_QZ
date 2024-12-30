package io.github.asciiblues;

import javax.swing.*;
import java.awt.*;

public class port extends JFrame{
    private JPanel panel;
    public port(){
        // Set the Look and Feel to the system's native Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentPane(panel);
        setTitle("Your port");
        setSize(800,400);
        setLocation(512,180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new port();
    }
}
