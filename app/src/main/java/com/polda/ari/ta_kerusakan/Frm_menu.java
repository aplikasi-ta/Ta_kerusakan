package com.polda.ari.ta_kerusakan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class Frm_menu extends AppCompatActivity {
Button btn_daftar,btn_kebijakan,btn_informasi,btn_wilayah,btn_laporan,btn_masuk,btn_keluar;
    NiftyDialogBuilder dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_menu);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_puu);
        getSupportActionBar().setIcon(R.drawable.logo_puu);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        dialogs = NiftyDialogBuilder.getInstance(this);

        btn_daftar = (Button) findViewById(R.id.btn_daftar);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);
        btn_wilayah = (Button) findViewById(R.id.btn_wilayah);
        btn_kebijakan = (Button) findViewById(R.id.btn_kebijakan);
        btn_keluar = (Button) findViewById(R.id.btn_keluar);

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu.this,Frm_register.class);
                startActivity(intent);
            }
        });

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu.this,Frm_login.class);
                startActivity(intent);
            }
        });

        btn_wilayah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu.this,Frm_wilayah.class);
                startActivity(intent);
            }
        });

        btn_kebijakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_menu.this,Frm_kebijakan.class);
                startActivity(intent);
            }
        });

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs
                        .withTitle("Informasi")
                        .withMessage("Anda yakin keluar aplikasi ?")
                        .withDialogColor("#0064b7")
                        .withButton1Text("Ya")
                        .withButton2Text("Tidak")
                        .withEffect(Effectstype.Shake);
                dialogs.isCancelableOnTouchOutside(true);
                dialogs.setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                dialogs.setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogs.dismiss();
                    }
                });
                dialogs.show();

            }
        });

    }
}
