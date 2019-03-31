package com.polda.ari.ta_kerusakan;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Frm_register extends AppCompatActivity {
    NiftyDialogBuilder dialogs;
    EditText txt_ktp,txt_nama,txt_alamat,txt_telp,txt_username,txt_password;
    Button btn_daftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_register);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_puu);
        getSupportActionBar().setIcon(R.drawable.logo_puu);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        dialogs = NiftyDialogBuilder.getInstance(this);
        txt_ktp = (EditText) findViewById(R.id.txt_ktp);
        txt_nama = (EditText) findViewById(R.id.txt_nama_p);
        txt_alamat = (EditText) findViewById(R.id.txt_alamat);
        txt_telp = (EditText) findViewById(R.id.txt_telp);
        txt_username = (EditText) findViewById(R.id.txt_user);
        txt_password = (EditText) findViewById(R.id.txt_pass);
        btn_daftar = (Button) findViewById(R.id.btnSignUp);

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanRegister(txt_ktp.getText().toString().trim(),txt_nama.getText().toString().trim(),txt_alamat.getText().toString().trim(),
                        txt_telp.getText().toString().trim(),txt_username.getText().toString().trim(),txt_password.getText().toString().trim());
            }
        });

    }

    public void simpanRegister(final String NO_ktp, final String Nama, final String Alamat, final String Telp, final String Username, final String Password){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("no_ktp", NO_ktp));
                nameValuePairs.add(new BasicNameValuePair("nama", Nama));
                nameValuePairs.add(new BasicNameValuePair("alamat", Alamat));
                nameValuePairs.add(new BasicNameValuePair("telp", Telp));
                nameValuePairs.add(new BasicNameValuePair("username", Username));
                nameValuePairs.add(new BasicNameValuePair("password", Password));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://pstiubl.com/api_rusak/act_user.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equalsIgnoreCase("success")){

                    dialogs
                            .withTitle("Informasi")
                            .withMessage("Registrasi Berhasil, Silahkan gunakan Username dan Password anda untuk login")
                            .withDialogColor("#fff200")
                            .withButton1Text("OK")
                            .withEffect(Effectstype.Fall);
                    dialogs.isCancelableOnTouchOutside(false);
                    dialogs.setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                            Intent intent = new Intent(Frm_register.this, Frm_menu.class);
                            startActivity(intent);
                        }
                    });
                    dialogs.show();

                }else{
                    Toast.makeText(getApplication(),"Gagal Registrasi Data",Toast.LENGTH_LONG).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(NO_ktp,Nama,Alamat,Telp,Username,Password);

    }

}
