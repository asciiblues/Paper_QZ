package io.github.asciiblues;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class gui extends JFrame{

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        // Create a URL object from the file URL
        URL url = new URL(fileURL);

        // Open a connection to the URL
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        int responseCode = httpConnection.getResponseCode();

        // Check for a successful response code
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConnection.getHeaderField("Content-Disposition");
            String contentType = httpConnection.getContentType();
            int contentLength = httpConnection.getContentLength();

            // Extract file name from the header if available
            if (disposition != null && disposition.contains("filename=")) {
                int index = disposition.indexOf("filename=");
                fileName = disposition.substring(index + 9).replace("\"", "");
            } else {
                // Fallback: Get the name from the URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("Downloading file: " + fileName);

            // Open input and output streams
            try (InputStream inputStream = httpConnection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(saveDir + File.separator + fileName)) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("File downloaded to: " + saveDir + File.separator + fileName);
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConnection.disconnect();
    }

    private JPanel panel;
    private JButton saveTheCertificateForButton;

    public gui() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e);
        }
        setSize(650,650);
        setTitle("Setup up the certificate");
        setContentPane(panel);
        //To open in center of screen
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) size.getWidth() /2 , h = (int) size.getHeight() /2;
        int x = w - 325, y = h - 325;
        setLocation(x,y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        saveTheCertificateForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("certificate file", "crt");
              chooser.setFileFilter(filter);
                int option = chooser.showSaveDialog(panel);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile();
                    String fileURL = "http://localhost:8182/cert/root-ca.crt"; // Replace with your URL
                    String saveDir = file.getParent(); // Replace with your directory path

                    try {
                        downloadFile(fileURL, saveDir);
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Error :- " + ex , "Error" , JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(null, "Saved !");
                }else{
                    JOptionPane.showMessageDialog(null,"operation Abort ... !" , "opration Abort", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        new gui();
    }
}
