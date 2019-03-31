package com.polda.ari.ta_kerusakan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Frm_det_wilayah extends FragmentActivity {

    private GoogleMap mMap;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    String id_wilayaah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_det_wilayah);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        final Intent intent = getIntent();
        id_wilayaah = intent.getStringExtra("kode_wilayah");
        if(id_wilayaah.contains("126001")){
            petaLokasi(-5.456850, 105.254330,"Kec. Teluk Betung Barat");
        }else if(id_wilayaah.contains("126002")){
            petaLokasi(-5.443750, 105.284180,"Kec. Teluk Betung Selatan");
        }else if(id_wilayaah.contains("126003")){
            petaLokasi(-0.462520, 100.400400,"Kec. Panjang");
        }else if(id_wilayaah.contains("126004")){
            petaLokasi(-5.408780, 105.268120,"Kec. Tanjung Karang Timur");
        }else if(id_wilayaah.contains("126005")){
            petaLokasi(-5.433920, 105.259640,"Kec. Teluk Betung Utara");
        }else if(id_wilayaah.contains("126006")){
            petaLokasi(-5.412190, 105.250900,"Kec. Tanjung Karang Pusat");
        }else if(id_wilayaah.contains("126007")){
            petaLokasi(-5.397380, 105.239900,"Kec. Tanjung Karang Barat");
        }else if(id_wilayaah.contains("126008")){
            petaLokasi(-5.384630, 105.260437,"Kec. Kedaton");
        }else if(id_wilayaah.contains("126009")){
            petaLokasi(-5.385560, 105.299350,"Kec. Sukarame");
        }else if(id_wilayaah.contains("126010")){
            petaLokasi(-5.398060, 105.206070,"Kec. Kemiling");
        }else if(id_wilayaah.contains("126011")){
            petaLokasi(-5.365960, 105.229439,"Kec. Rajabasa");
        }else if(id_wilayaah.contains("126012")){
            petaLokasi(-5.364660, 105.275940,"Kec. Tanjung Senang");
        }else if(id_wilayaah.contains("126013")){
            petaLokasi(-5.399760, 105.307500,"Kec. Sukabumi");
        }else if(id_wilayaah.contains("126014")){
            petaLokasi(-5.372450, 105.256610,"Kec. Labuhan Ratu");
        }else if(id_wilayaah.contains("126015")){
            petaLokasi(-5.377040, 105.278610,"Kec. Way Halim");
        }else if(id_wilayaah.contains("126016")){
            petaLokasi(-5.416880, 105.282430,"Kec. Kedamaian");
        }else if(id_wilayaah.contains("126017")){
            petaLokasi(-5.419540, 105.260440,"Kec. Enggal");
        }else if(id_wilayaah.contains("126018")){
            petaLokasi(-5.392880, 105.227100,"Kec. Langkapura");
        }else if(id_wilayaah.contains("126019")){
            petaLokasi(-5.443750, 105.278600,"Kec. Bumi Waras");
        }else if(id_wilayaah.contains("126020")){
            petaLokasi(-5.443750, 105.284180,"Kec. Teluk Betung Timur");
        }

    }


    void petaLokasi(final Double lati, final Double longi, final String kecamatan) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-5.397140, 105.266789), 12.0f));

                LatLng ubl = new LatLng(lati, longi);
                //Toast.makeText(getApplication(),"Data "+lats+"\n"+longs,Toast.LENGTH_LONG).show();
                MarkerOptions marker = null;
                marker = new MarkerOptions()
                        .position(ubl)
                        .title(kecamatan)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.gb_wilayah));
                //.title("Posisi Dosen Pembimbing")
                //.snippet(nidn+" - "+nama_pa);

                mMap.addMarker(marker);
                //mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubl, 15));
            }
        });
    }

}
