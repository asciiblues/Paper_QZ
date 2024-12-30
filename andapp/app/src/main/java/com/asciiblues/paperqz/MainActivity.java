package com.asciiblues.paperqz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import fi.iki.elonen.NanoHTTPD;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        EditText pswd = findViewById(R.id.pswd);
        CheckBox spwd = findViewById(R.id.shpd);
        Button stpwd = findViewById(R.id.stpwd);
        Spinner drdnqp = findViewById(R.id.drdnqp);
        EditText qzport = findViewById(R.id.qzport);
        Button stdp = findViewById(R.id.stdp);
        EditText ptnm = findViewById(R.id.ptnm);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, info.class);
                startActivity(intent);
            }
        });

        //Check the app is setup or not
        File stp = new File(getApplicationContext().getFilesDir() + "/stp.txt");

        if(stp.exists()){
            //If setup then open the app
        }else {
            //If not setup then setup the app, run the setup
             Intent intent = new Intent(MainActivity.this, setup1.class);
             startActivity(intent);
        }

        //Make the qzport non-editable editText
        qzport.setKeyListener(null);

        //Set array to spinner (dropdown)

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.qzprt,android.R.layout.simple_spinner_dropdown_item
        );
      // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // Apply the adapter to the spinner.
        drdnqp.setAdapter(adapter);

        drdnqp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (drdnqp.getSelectedItem().toString().equals("Select QZ port")){
                   //Noting if Select QZ port
               }else {
                   qzport.setText(drdnqp.getSelectedItem().toString());
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
              //Noting action
            }
        });

        stpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write default password to file
                try {
                    File f = new File(getApplicationContext().getFilesDir() + "/pwd.txt");
                    FileWriter writer = new FileWriter(f.getAbsolutePath());
                    writer.write(pswd.getText().toString());
                    writer.close();
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Error :- " + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Load Default password from file
        try{
            File f = new File(getApplicationContext().getFilesDir() + "/pwd.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                pswd.setText(data);
            }
            sc.close();
        }catch (Exception ex){
            Log.d("pwd","Default password not set");
        }

        //Load default port from file

        try{
            File f = new File(getApplicationContext().getFilesDir() + "/prt.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                qzport.setText(data);
            }sc.close();
        } catch (Exception ex) {
          Log.d("prt","Default port not set");
        }

        //make show / hide password checkbox
        spwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(spwd.isChecked()){
                    pswd.setTransformationMethod(null);
                }else {
                    pswd.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        Button btn = findViewById(R.id.stqz);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please enter port number", Toast.LENGTH_LONG).show();
                int port = 7549; // Port to host the server
                httpsrv server = new httpsrv(port);//Make a ssh command for termux
                server.fileContent = "sshpass -p " + pswd.getText().toString() + " ssh -p " + ptnm.getText().toString() + " -L " + qzport.getText().toString() + ":localhost:8282" + " -o StrictHostKeyChecking=accept-new SSH@serveo.net";
                try {
                    server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
                    Toast.makeText(MainActivity.this, "Server started on port | http://localhost:/" + port, Toast.LENGTH_SHORT).show();
                    //Open termux
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.termux");
                    if (intent != null) {
                        // We found the activity now start the activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Please install termux form ", Toast.LENGTH_LONG).show();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.termux&pcampaignid=web_share"));
                                startActivity(browserIntent);
                            }
                        } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Could not start the server: " + e.getMessage() + "Report this issue to https://github.com/asciiblues/PaperQZ/issues | or nzzz101.3z@gmail.com ", Toast.LENGTH_LONG).show();}
            }
        });
        stdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Write port default to file
                    File f = new File(getApplicationContext().getFilesDir() + "/prt.txt");
                    FileWriter writer = new FileWriter(f.getAbsolutePath());
                    writer.write(qzport.getText().toString());
                    writer.close();
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Error :- " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}