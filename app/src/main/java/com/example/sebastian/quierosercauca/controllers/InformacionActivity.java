package com.example.sebastian.quierosercauca.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
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

public class InformacionActivity extends AppCompatActivity {

    Httppostaux post;
    private ProgressDialog pDialog;
    String IP_Server = "192.168.0.119/cauca/pag";
    String URL_connect = "http://"+IP_Server+"/logica/extrae.php";
    String etnia_json;
    String ubcacion_json;
    String economia_json;
    String poblacion_json;
    String gastronomia_json;
    String cultura_json;
    String biodiversidad_json;
    String sitios_json;
    String rquitectura_json;
    String video_json;
    String audio_json;
    WebView myWebView;
    TextView tv_etnia,tv_ubicacion,tv_economia,tv_poblacion,tv_gastronomia,tv_cultura,tv_biodiversidad,tv_sitios,tv_arquitectura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        post = new Httppostaux();
        Intent intent = getIntent();
        tv_etnia= (TextView) findViewById(R.id.etnia);
        tv_ubicacion= (TextView) findViewById(R.id.ubicacion);
        tv_economia= (TextView) findViewById(R.id.economia);
        tv_poblacion= (TextView) findViewById(R.id.poblacion);
        tv_gastronomia= (TextView) findViewById(R.id.gastronomia);
        tv_cultura= (TextView) findViewById(R.id.cultura);
        tv_biodiversidad= (TextView) findViewById(R.id.bio);
        tv_sitios= (TextView) findViewById(R.id.sitios);
        tv_arquitectura= (TextView) findViewById(R.id.arquitectura);


        String PalabraExtra = intent.getStringExtra("PalabraExtra");
        Toast.makeText(this,PalabraExtra,Toast.LENGTH_SHORT).show();
        arranque(PalabraExtra);
    }

        public void arranque(String PalabraExtra){
            new asynclogin().execute(PalabraExtra);
    }

    class asynclogin extends AsyncTask< String, String, String > {
        String palabra;
        protected void onPreExecute() {
            pDialog = new ProgressDialog(InformacionActivity.this);
            pDialog.setMessage("Buscando Informacion....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... params) {
            palabra=params[0];

            if (loginstatus(palabra)==true){
                return "ok"; //login valido
            }else{
                return "err"; //login invalido
            }
        }
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            Log.e("onPostExecute=", "" + result);
            if (result.equals("ok")){
                exito();
            }else{
                err_login();
            }
        }
    }

    public void exito(){
        Toast.makeText(this,etnia_json+ubcacion_json,Toast.LENGTH_SHORT).show();
    }

    public boolean loginstatus(String palabra) {
        int logstatus=2;
        ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("etnia", palabra));

        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);

        if (jdata!=null && jdata.length() > 0){
            JSONObject json_data;
            try {
                json_data = jdata.getJSONObject(0);
                logstatus=1;
                etnia_json = json_data.getString("etnia");
                ubcacion_json = json_data.getString("Ubicacion");
                economia_json = json_data.getString("Economia");
                poblacion_json = json_data.getString("Poblacion");
                gastronomia_json = json_data.getString("Gastronomia");
                cultura_json = json_data.getString("Cultura");
                biodiversidad_json = json_data.getString("Biodiversidad");
                sitios_json = json_data.getString("sitiosTuriaticos");
                rquitectura_json = json_data.getString("arquitectura");
                //Uri video_json = Uri.parse(json_data.getString("video"));
                //audio_json = json_data.getString("audio");

                tv_etnia.setText(etnia_json);
                tv_ubicacion.setText(ubcacion_json);
                tv_economia.setText(economia_json);
                tv_poblacion.setText(poblacion_json);
                tv_gastronomia.setText(gastronomia_json);
                tv_cultura.setText(cultura_json);
                tv_biodiversidad.setText(biodiversidad_json);
                tv_sitios.setText(sitios_json);
                tv_arquitectura.setText(rquitectura_json);

                //myWebView.loadUrl("https://www.youtube.com/watch?v="+video_json);

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

    public void err_login(){
        Toast toast1 = Toast.makeText(getApplicationContext(),"Imposible Consultar Informacion", Toast.LENGTH_SHORT);
        toast1.show();
    }
}