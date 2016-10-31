package com.example.vishalraman.new_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vishal Raman on 10/11/2016.
 */
public class front extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.front_layout);

        Button btn= (Button) findViewById(R.id.bStart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(front.this,dynamicActivity.class);
                startActivity(i);
            }
        });

        Button bHelp= (Button) findViewById(R.id.bHelp);
        bHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(front.this,help.class);
                startActivity(i);
            }
        });

        Button bAbout= (Button) findViewById(R.id.bAbout);

        bAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(front.this,aboutus.class);
                startActivity(i);
            }
        });
    }

}
