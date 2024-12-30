package com.asciiblues.paperqz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);
        FloatingActionButton bfab = findViewById(R.id.bfab);
        EditText listb = findViewById(R.id.listb);
        Button gihbt = findViewById(R.id.gihbt);
        gihbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open github
                Intent github = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/asciiblues/Paper_QZ"));
                startActivity(github);
            }
        });
        //LISTB as non-editable edittext
        listb.setKeyListener(null);
        bfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}