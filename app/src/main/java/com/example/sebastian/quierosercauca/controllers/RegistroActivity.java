package com.example.sebastian.quierosercauca.controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastian.quierosercauca.R;
import com.example.sebastian.quierosercauca.net.Httppostaux;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sebastian on 18/09/15.
 */
public class RegistroActivity extends ActionBarActivity implements  View.OnClickListener
{

    private Button b_guardar,b_otro;
    private EditText ed_nombre,ed_apellido,ed_edad,ed_pais,ed_departamento,ed_municipio,ed_usuario,ed_contrasena,ed_correo;
    private TextView txt_info, txt_residencia, txt_sesion;

    Httppostaux post;
    String IP_Server = "192.168.0.119/cauca/pag/logica";
    String URL_connect = "http://"+IP_Server+"/adduser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        post = new Httppostaux();

        ed_nombre = (EditText) findViewById(R.id.edt_nombre_reg);
        ed_apellido = (EditText) findViewById(R.id.edt_apellido_reg);
        ed_pais = (EditText) findViewById(R.id.edt_pais_reg);
        ed_edad = (EditText) findViewById(R.id.edt_edad_reg);
        ed_departamento = (EditText) findViewById(R.id.edt_departamento_reg);
        ed_municipio = (EditText) findViewById(R.id.edt_municipio_reg);
        ed_usuario = (EditText) findViewById(R.id.edt_usuario_reg);
        ed_contrasena = (EditText) findViewById(R.id.edt_contraseña_reg);
        ed_correo = (EditText) findViewById(R.id.edt_correo);
        b_guardar = (Button) findViewById(R.id.btn_grdr_registro);
        b_otro = (Button) findViewById(R.id.btn_hacia_loguin);
        txt_info = (TextView) findViewById(R.id.txt_informacion);
        txt_residencia = (TextView) findViewById(R.id.txt_informacion);
        txt_sesion = (TextView) findViewById(R.id.txt_informacion);
        b_guardar.setOnClickListener(this);
        b_otro.setOnClickListener(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        ed_nombre.setTypeface(font);
        ed_apellido.setTypeface(font);
        ed_pais.setTypeface(font);
        ed_edad.setTypeface(font);
        ed_departamento.setTypeface(font);
        ed_municipio.setTypeface(font);
        ed_usuario.setTypeface(font);
        ed_contrasena.setTypeface(font);
        b_guardar.setTypeface(font);
        b_otro.setTypeface(font);
        txt_info.setTypeface(font);
        txt_residencia.setTypeface(font);
        txt_sesion.setTypeface(font);
        b_guardar.setTypeface(font);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_grdr_registro:
                String var_nombre = ed_nombre.getText().toString();
                String var_apellido = ed_apellido.getText().toString();
                String var_edad = ed_edad.getText().toString();
                String var_departamento = ed_departamento.getText().toString();
                String var_municipio = ed_municipio.getText().toString();
                String var_pais = ed_pais.getText().toString();
                String var_correo = ed_correo.getText().toString();
                String var_usuario = ed_usuario.getText().toString();
                String var_contrasena = ed_contrasena.getText().toString();

                if(validar(var_nombre, var_apellido,var_edad, var_pais,var_departamento,var_municipio,var_correo, var_usuario, var_contrasena)==false){
                    Toast.makeText(this, "Debe Llenar Todos Los Campos", Toast.LENGTH_LONG).show();
                }else {


                    new asynclogin().execute(var_nombre,var_apellido,var_edad,var_pais,var_departamento,var_municipio,var_correo ,var_usuario,var_contrasena);
                }
                break;

            case R.id.btn_hacia_loguin:
                Intent ir_log = new Intent().setClass(RegistroActivity.this, LoginActivity.class);
                startActivity(ir_log);
                finish();
                break;
        }
    }

    public Boolean validar(String nombre,String apellido,String edad,String pais,String departamento,String municipio,String correo,  String usuario, String contraseña) {
        if (nombre.equals("") || apellido.equals("") || edad.equals("") || pais.equals("") || departamento.equals("") || municipio.equals("")  || correo.equals("") || usuario.equals("") || contraseña.equals("")) {
            return false;
        }else {
            return true;
        }
    }

    class asynclogin extends AsyncTask< String, String, String > {


        String nombre;
        String apellido;
        String edad;
        String pais;
        String departamento;
        String municipio;
        String correo;

        String usuario;
        String passw;



        protected String doInBackground(String... params) {
            nombre=params[0];
            apellido=params[1];

            edad=params[2];
            pais=params[3];
            departamento=params[4];
            municipio=params[5];
            correo=params[6];

            usuario=params[7];
            passw=params[8];
            if (loginstatus(nombre,apellido,edad,pais,departamento,municipio,correo,usuario,passw)==true){
                return "ok";
            }else{
                return "err";
            }
        }
        protected void onPostExecute(String result) {
            Log.e("onPostExecute=", "" + result);
            if (result.equals("ok")){
                Intent ir_log = new Intent().setClass(RegistroActivity.this, PrincipalActivity.class);
                startActivity(ir_log);
                finish();
            }else{
                err();
            }
        }
    }

    public void notificar(){
        Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
    }
    public void err(){
        Toast.makeText(getApplicationContext(), "NO Se Pudo Registrar, Intente de Nuevo con otro usuario y contraseña", Toast.LENGTH_SHORT).show();
    }

    public boolean loginstatus(String nombre, String apellido, String edad, String pais, String departamento,String municipio,String correo,String usuario,String passw) {
        int cambio=5;

        ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("nombre",nombre));
        postparameters2send.add(new BasicNameValuePair("apellido",apellido));
        postparameters2send.add(new BasicNameValuePair("edad",edad));
        postparameters2send.add(new BasicNameValuePair("pais",pais));
        postparameters2send.add(new BasicNameValuePair("departamento",departamento));
        postparameters2send.add(new BasicNameValuePair("municipio",municipio));

        postparameters2send.add(new BasicNameValuePair("correo",correo));
        postparameters2send.add(new BasicNameValuePair("usuario",usuario));
        postparameters2send.add(new BasicNameValuePair("password",passw));

        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);

        if (jdata!=null && jdata.length() > 0){
            JSONObject json_data;
            try {
                json_data = jdata.getJSONObject(0);
                Log.e("muestra","logstatus= "+cambio);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (cambio==0){
                Log.e("loginstatus ", "invalido");
                return false;
            }
            if (cambio==2){
                Log.e("loginstatus ", "invalido");
                return false;
            }
            else{
                Log.e("loginstatus ", "valido");
                return true;
            }
        }else{
            Log.e("JSON ", "ERROR");

            return false;
        }
    }


}
