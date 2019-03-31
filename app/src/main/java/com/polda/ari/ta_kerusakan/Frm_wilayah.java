package com.polda.ari.ta_kerusakan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Frm_wilayah extends AppCompatActivity {
ListView listView;
String JSON_STRING,id_wilayah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_wilayah);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_puu);
        getSupportActionBar().setIcon(R.drawable.logo_puu);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        listView = (ListView) findViewById(R.id.listViewWilayah);
        getJSON();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Frm_wilayah.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                //showSekolah();
                showWilayah();
                //Toast.makeText(getApplication(),"Data "+s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest("http://jendela.data.kemdikbud.go.id/api/index.php/CWilayah/wilayahGET?mst_kode_wilayah=126000");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    private void showWilayah() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("data");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_wilayah = jo.getString("kode_wilayah");
                String nama_wilayah = jo.getString("nama");

                HashMap<String, String> employees = new HashMap<>();
                employees.put("kode_wilayah", id_wilayah);
                employees.put("nama", nama_wilayah);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),"Data Salah "+e,Toast.LENGTH_LONG).show();
        }

        ListAdapter adapter = new SimpleAdapter(
                Frm_wilayah.this, list, R.layout.list_wilayah,
                new String[]{"kode_wilayah","nama"},
                new int[]{R.id.txt_id,R.id.txt_nama_wilayah});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(), adapter.getItem(position),Toast.LENGTH_SHORT).show();
                id_wilayah = ((TextView) view.findViewById(R.id.txt_nama_wilayah)).getText().toString();
                Intent intent = new Intent(Frm_wilayah.this, Frm_lapor.class);
                intent.putExtra("nama", id_wilayah);
                finish();
                startActivity(intent);
            }
        });


    }

}
