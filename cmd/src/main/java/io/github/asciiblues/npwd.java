package io.github.asciiblues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class npwd extends JFrame{
    private JTextField textField1;
    private JButton chnagePaswordButton;
    private JPanel panel;

    public npwd() {
        // Set the Look and Feel to the system's native Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLocation(512,180);
        setSize(400,300);
        setTitle("New password");
        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        chnagePaswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = "net user SSH " + textField1.getText();

                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
                    processBuilder.redirectErrorStream(true);

                    // Start the process
                    Process process = processBuilder.start();

                    // Read output from the command
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            //Write password to C:\pqscrt\pswd.txt
                            File file = new File("C:\\pqzcrt\\pswd.txt");
                            try {
                                FileWriter writer = new FileWriter(file);
                                writer.write(textField1.getText());
                                writer.close();
                                System.out.println("Password was write to C:/pqzcrt/pswd.txt");
                                System.exit(0);
                            }catch (Exception ex){
                                System.out.println("Error :- " + ex);
                            }
                        }
                    }

                    // Wait for the process to complete
                    int exitCode = process.waitFor();

                } catch (Exception ex) {
                    System.out.println("Error :-" + ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        new npwd();
    }
}
