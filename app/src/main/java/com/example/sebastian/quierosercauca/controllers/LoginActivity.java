package com.example.sebastian.quierosercauca.controllers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sebastian.quierosercauca.R;
import com.example.sebastian.quierosercauca.net.Httppostaux;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private SharedPreferences prefs;
    private Button btn_validar,btn_registro;
    Httppostaux post;
    String IP_Server = "192.168.0.119/cauca/pag";
    String URL_connect = "http://"+IP_Server+"/logica/acces.php";
    private ProgressDialog pDialog;
    EditText user, password;
    private String intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);
        post = new Httppostaux();
        prefs = getSharedPreferences("datos", Context.MODE_PRIVATE);
        btn_validar = (Button) findViewById(R.id.btn_validar);
        btn_registro = (Button) findViewById(R.id.btn_registro);
        user = (EditText) findViewById(R.id.edt_usuario);
        password = (EditText) findViewById(R.id.edt_contraseña);
        btn_validar.setOnClickListener(this);
        btn_registro.setOnClickListener(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        user.setTypeface(font);
        password.setTypeface(font);
        btn_validar.setTypeface(font);
        btn_registro.setTypeface(font);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_validar:
                SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putBoolean("validar_sesion", true);
                editor.commit();
                String usuario = user.getText().toString();
                String passw = password.getText().toString();
                new asynclogin().execute(usuario,passw);

                break;
            case R.id.btn_registro:
                Intent ir_reg = new Intent().setClass(LoginActivity.this, RegistroActivity.class);
                startActivity(ir_reg);
                finish();
                break;
        }
    }

    class asynclogin extends AsyncTask< String, String, String > {
        String user,pass;
        protected void onPreExecute() {

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Autenticando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... params) {
            user=params[0];
            pass=params[1];

            if (loginstatus(user,pass)==true){
                return "ok"; //login valido
            }else{
                return "err"; //login invalido
//    			return "ok"; //login invalid
            }
        }
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            Log.e("onPostExecute=", "" + result);
            if (result.equals("ok")){
                Intent i = new Intent(LoginActivity.this, PrincipalActivity.class);
                i.putExtra("usuario",user.toString());
                startActivity(i);
                finish();
            }else{
                err_login();
            }
        }
    }


    public boolean loginstatus(String user, String passwo ) {
        int logstatus=2;

        ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("usuario",user));
        postparameters2send.add(new BasicNameValuePair("password",passwo));

        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);

        if (jdata!=null && jdata.length() > 0){
            JSONObject json_data;
            try {
                json_data = jdata.getJSONObject(0);
                logstatus=json_data.getInt("logstatus");
                Log.e("muestra","logstatus= "+logstatus);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (logstatus==0){// [{"logstatus":"0"}]
                Log.e("loginstatus ", "invalido");
                return false;
            }
            else{// [{"logstatus":"1"}]
                Log.e("loginstatus ", "valido");
                return true;
            }
        }else{
            Log.e("pablo", "ERROR");
            return false;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Salir")
                .setMessage("Estás seguro?").setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //Salir
                        LoginActivity.this.finish();
                    }
                }).show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

    public void err_login(){
        Toast toast1 = Toast.makeText(getApplicationContext(),"Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT);
        toast1.show();
    }
}
