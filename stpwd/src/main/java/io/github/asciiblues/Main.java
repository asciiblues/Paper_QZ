package io.github.asciiblues;

import javax.swing.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        String username = "SSH";
        String fullName = "SSH";
        String password = JOptionPane.showInputDialog(null, "Enter the password for Paper QZ only");
        try {
            //Write the password to the file
            try{
                File file = new File("C:/pqzcrt/pswd.txt");
                file.createNewFile();
                FileWriter writer = new FileWriter(file.getAbsolutePath());
                writer.write(password);
                writer.close();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Error while saving the password :- " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error while saving the password :-" + ex.getMessage());
            }
            // Command to create a user
            String createUserCommand = String.format("net user %s %s /add", username, password);
            executeCommand(createUserCommand);

            // Command to set full name
            String setFullNameCommand = String.format("wmic useraccount where name='%s' set fullname='%s'", username, fullName);
            executeCommand(setFullNameCommand);

            // Command to set the password to never expire
            String setPasswordNeverExpires = String.format("wmic useraccount where name='%s' set PasswordExpires=False", username);
            executeCommand(setPasswordNeverExpires);

            System.out.println("User created successfully with the specified details.");
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private static void executeCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Command failed with exit code: " + exitCode);
        }
    }
}