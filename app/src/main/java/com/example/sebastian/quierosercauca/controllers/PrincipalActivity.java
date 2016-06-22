package com.example.sebastian.quierosercauca.controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sebastian.quierosercauca.R;

/**
 * Created by sebastian on 18/09/15.
 */
public class PrincipalActivity extends ActionBarActivity implements View.OnClickListener {
    private Button btn_mestizo,btn_afro,btn_indigena,btn_ayuda;
    private TextView txt, usuario;
    private  String txt_user;
    private com.melnykov.fab.FloatingActionButton  info_cauca, info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Bundle bundle=getIntent().getExtras();


        if(bundle!=null) {
          txt_user   = bundle.get("usuario").toString();

        }
        btn_mestizo= (Button) findViewById(R.id.btn_mestizo);
        btn_afro= (Button) findViewById(R.id.btn_afro);
        btn_indigena= (Button) findViewById(R.id.btn_indigena);
        btn_ayuda= (Button) findViewById(R.id.btn_ayuda);
        txt = (TextView) findViewById(R.id.txt_texto);
        usuario = (TextView) findViewById(R.id.txt_usuario);

        btn_mestizo.setOnClickListener(this);
        btn_afro.setOnClickListener(this);
        btn_indigena.setOnClickListener(this);
        btn_ayuda.setOnClickListener(this);
        usuario.setText("Bienvenido "+txt_user);
        Typeface font = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        btn_mestizo.setTypeface(font);
        btn_afro.setTypeface(font);
        btn_indigena.setTypeface(font);
        txt.setTypeface(font);
        usuario.setTypeface(font);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_afro:

                break;
            case R.id.btn_indigena:
                Intent intent = new Intent(this, MapaActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_mestizo:

                break;
            case R.id.btn_ayuda:
                Intent intent2 = new Intent(this,AyudaActivity.class);
                break;

        }
    }
}