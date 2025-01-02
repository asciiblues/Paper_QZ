package io.github.asciiblues;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Set the Look and Feel to the system's native Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Welcome to Paper QZ CMD");
        System.out.println("<< --- List of commands --- >> \n [view qz tray port] == 'vprt' \n [change password] == 'chwd' \n [Get & save certificate file] == 'gcrt' \n [view password] == 'vpsd'");
         System.out.println("Enter the command here");
        //Open qz tray
        File file = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\QZ Tray\\QZ Tray.lnk");

        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        Scanner inp = new Scanner(System.in);
        String cmd = inp.nextLine().toString();
        switch (cmd){
            case "vprt":
                if (Desktop.isDesktopSupported()) {

                    //making a desktop object
                    Desktop desktop1 = Desktop.getDesktop();
                    try {
                        URI uri = new URI("http://localhost:8182");
                        desktop1.browse(uri);
                        new port();
                    } catch (IOException excp) {
                        excp.printStackTrace();
                    } catch (URISyntaxException excp) {
                        excp.printStackTrace();
                    }
                }
                break;
            case "chwd":
                new npwd();
                break;
            case "gcrt":
                File file1 = new File("C:/pqzcrt/");
                if(file1.exists()){
                    // download command to execute
                    String command1 = "cd C:/pqzcrt/ && C: && curl http://localhost:8182/cert/root-ca/root-ca.crt -O";

                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command1);
                        processBuilder.redirectErrorStream(true);

                        // Start the process
                        Process process = processBuilder.start();

                        // Read output from the command
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                        }

                        // Wait for the process to complete
                        int exitCode = process.waitFor();

                        Runtime.getRuntime().exec("explorer.exe C:\\pqzcrt\\");

                    } catch (Exception ex) {
                        System.out.println("Error :-" + ex);
                    }
                }else {
                    file1.mkdir();
                   // download command to execute
                    String command2 = "cd C:/pqzcrt/ && C: && curl http://localhost:8182/cert/root-ca/root-ca.crt -O";

                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command2);
                        processBuilder.redirectErrorStream(true);

                        // Start the process
                        Process process = processBuilder.start();

                        // Read output from the command
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                             System.out.println(line);
                            }
                        }

                        // Wait for the process to complete
                        int exitCode = process.waitFor();
                        Runtime.getRuntime().exec("explorer.exe C:\\pqzcrt\\");

                    } catch (Exception ex) {
                        System.out.println("Error :-" + ex);
                    }
                }
              break;
            case "vpsd":
                try {
                    String userProfile = System.getenv("USERPROFILE");
                    Path filePath = Paths.get(userProfile, "pwd.txt");
                    String pswd = Files.readString(filePath);
                    System.out.println("password = " + pswd);
                    JOptionPane.showMessageDialog(null, "Password = " + pswd, "Password", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    System.out.println("An error occurred." + e);
                    e.printStackTrace();
                }
                break;
        }
    }
    }
}