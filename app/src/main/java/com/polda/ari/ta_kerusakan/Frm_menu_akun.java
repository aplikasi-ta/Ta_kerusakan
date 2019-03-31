package com.polda.ari.ta_kerusakan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Frm_menu_akun extends AppCompatActivity {
    ProgressDialog loading;
    public static TextView txt_id;
    Button btn_akun,btn_rusak,btn_lokasi,btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_menu_akun);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_puu);
        getSupportActionBar().setIcon(R.drawable.logo_puu);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        btn_akun = (Button) findViewById(R.id.btn_akun);
        btn_rusak = (Button) findViewById(R.id.btn_rusak);
        btn_lokasi = (Button) findViewById(R.id.btn_lokasi);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        txt_id = (TextView) findViewById(R.id.txt_id);
        getData(Frm_login.txt_user.getText().toString().trim());

        btn_rusak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu_akun.this,Frm_lapor.class);
                startActivity(intent);
            }
        });

        btn_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu_akun.this,Frm_lapor.class);
                startActivity(intent);
            }
        });

        btn_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu_akun.this,Frm_akun.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu_akun.this, Frm_menu.class);
                startActivity(intent);
            }
        });

    }

    private void getData(final String Id_user){

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = "http://pstiubl.com/api_rusak/det_akun.php?user="+Id_user;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showData(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Frm_menu_akun.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showData(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_user = jo.getString("id_user");
                txt_id.setText(id_user);
                SharedVariabel.ID_PENGGUNA = id_user;
                //Toast.makeText(getApplication(),"Data "+id_user,Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplication(),"Anda harus logout",Toast.LENGTH_LONG).show();
    }


}
