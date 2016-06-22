package com.example.sebastian.quierosercauca.controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.sebastian.quierosercauca.R;

public class MapaActivity extends AppCompatActivity implements View.OnClickListener {

    public Button b_coconuco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        b_coconuco = (Button) findViewById(R.id.btn_coconuco);
        Typeface font = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        b_coconuco.setTypeface(font);
        b_coconuco.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_coconuco:
                Intent i=new Intent(this,InformacionActivity.class);
                i.putExtra("PalabraExtra", "coconuco");
                startActivity(i);
                finish();
                break;
        }
    }
}