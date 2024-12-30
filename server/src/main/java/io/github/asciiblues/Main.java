package io.github.asciiblues;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

//This is only CLI only

public class Main {
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String ANSI_PRPBG = "\u001B[45m";
    public static final String ANSI_BLU = " \u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static void main(String[] args) {
        System.out.println("Paper QZ server, | The solution of use QZ tray using Android phone form anywhere !");
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
        }
        // SSH command to execute
        String command = "ssh -R 0:localhost:22 serveo.net";

        try {
            // Create ProcessBuilder to execute the PowerShell command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read output from the command
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("This by serveo | https://serveo.net/ | thanks to serveo for this !!!");
                    System.out.println(ANSI_PRPBG + "Enter this port/number to android app and click on start button" + ANSI_RESET);
                    System.out.println(ANSI_BLU + "                                  PORT IS = | \n                                             | \n                                             |  \n                                             |" + ANSI_RESET);
                    System.out.println(line + ANSI_RESET);
                }
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("SSH command exited with code: " + exitCode);

        } catch (Exception ex) {
            System.out.println(ANSI_RED + "Error :-" + ex + ANSI_RED);
        }
    }
}