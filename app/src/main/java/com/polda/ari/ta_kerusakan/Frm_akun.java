package com.polda.ari.ta_kerusakan;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Frm_akun extends AppCompatActivity {
EditText txt_ktp,txt_nama,txt_alamat,txt_telp;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_akun);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_puu);
        getSupportActionBar().setIcon(R.drawable.logo_puu);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        txt_ktp = (EditText) findViewById(R.id.txt_ktp_akun);
        txt_nama = (EditText) findViewById(R.id.txt_nama_p_akun);
        txt_alamat = (EditText) findViewById(R.id.txt_alamat_akun);
        txt_telp= (EditText) findViewById(R.id.txt_telp_akun);
        getData(SharedVariabel.ID_PENGGUNA);

        txt_ktp.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_telp.setEnabled(false);
    }

    private void getData(final String Id_user){

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = "http://pstiubl.com/api_rusak/det_akunku.php?id_user="+Id_user;

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
                        Toast.makeText(Frm_akun.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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
                String nama = jo.getString("nama");
                String alamat = jo.getString("alamat");
                String telp = jo.getString("telp");
                String no_ktp = jo.getString("no_ktp");
                txt_nama.setText(nama);
                txt_alamat.setText(alamat);
                txt_telp.setText(telp);
                txt_ktp.setText(no_ktp);
                //Toast.makeText(getApplication(),"Data "+id_user,Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
