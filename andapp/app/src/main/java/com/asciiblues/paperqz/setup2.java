package com.asciiblues.paperqz;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;

public class setup2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setup2);
        Button cpscr = findViewById(R.id.cpscr);
        Button bkbt = findViewById(R.id.bkbt);
        Button fnst = findViewById(R.id.fnst);
        fnst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Write setup txt file
                    File file = new File(getApplicationContext().getFilesDir() + "/stp.txt");
                    FileWriter writer = new FileWriter(file.getAbsolutePath());
                    writer.write("Setup complete");
                    writer.close();
                    //and open MainActivity
                    Intent intent = new Intent(setup2.this, MainActivity.class);
                    startActivity(intent);
                }catch (Exception ex){
                    Toast.makeText(setup2.this, "Error :-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        cpscr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Copy scripts to Clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("scr", "pkg update && pkg upgrade -y && pkg install openssh sshpass wget -y && echo 'cd ~ && rm cmd.sh && wget http://localhost:7549/cmd.sh && chmod +x cmd.sh && ./cmd.sh' > ~/.bashrc && echo 'echo Hello word' > cmd.sh && exit");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(setup2.this, "Please Paste the copied text to termux !!!", Toast.LENGTH_LONG).show();
                //Open termux
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.termux");
                if (intent != null) {
                    // We found the activity now start the activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(setup2.this, "Please Paste the copied text to termux", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(setup2.this, "Please install termux form ", Toast.LENGTH_LONG).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.termux&pcampaignid=web_share"));
                    startActivity(browserIntent);
                }
            }
        });
        bkbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to setup1
                Intent intent = new Intent(setup2.this, setup1.class);
                startActivity(intent);
            }
        });
    }
}