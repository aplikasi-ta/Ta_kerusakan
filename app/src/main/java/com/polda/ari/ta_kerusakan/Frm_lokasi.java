package com.polda.ari.ta_kerusakan;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class Frm_lokasi extends FragmentActivity{

    private GoogleMap mMap_posisi,mMap_toko;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    Location location;
    LocationManager locationManager;
    private Bitmap bitmap, bitmap2;

    private int PICK_IMAGE_REQUEST = 1;

    private static final int CAMERA_REQUEST_CODE = 7777;
    ImageView f_lokasi;
    TextView txt_lati,txt_longi;
    Button btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lokasi);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        f_lokasi = (ImageView) findViewById(R.id.imageView);
        txt_lati = (TextView) findViewById(R.id.txt_lati);
        txt_longi= (TextView) findViewById(R.id.txt_longi);
        btn_simpan = (Button) findViewById(R.id.btnSimpanLokasi);
        checkLocationPermission();
        petaPosisi();
        Camerapermission();

        f_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }


    void petaPosisi() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap_posisi = googleMap;
                mMap_posisi.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-5.397140, 105.266789), 12.0f));

                //mMap_posisi.addMarker(marker);
                //mMap/..getUiSettings().setCompassEnabled(true);
                mMap_posisi.getUiSettings().setZoomControlsEnabled(true);
                mMap_posisi.getUiSettings().setZoomGesturesEnabled(true);
                mMap_posisi.getUiSettings().setMyLocationButtonEnabled(true);

                //if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUR_DEVELOPMENT) {
                Criteria criteria = new Criteria();
                if ((ActivityCompat.checkSelfPermission(Frm_lokasi.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(Frm_lokasi.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    mMap_posisi.setMyLocationEnabled(true);
                }

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

                if (location != null) {
                    LatLng MyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap_posisi.addMarker(new MarkerOptions().position(MyLocation).title("Posisi Anda").snippet("" + location.getLatitude() + "," + location.getLongitude()).draggable(true));
                    mMap_posisi.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocation, 13));
                    //getData(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                    txt_lati.setText(String.valueOf(location.getLatitude()));
                    txt_longi.setText(String.valueOf(location.getLongitude()));

                } else {
                    Toast.makeText(getApplication(), "Lokasi Tidak Di temukan", Toast.LENGTH_SHORT).show();
                }
                //mMap_posisi.setMyLocationEnabled(true);
                //} else {
                // mMap_posisi.setMyLocationEnabled(true);
                //}


                /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent intent = new Intent(Frm_haver.this, Frm_bimbingan.class);
                        intent.putExtra("nidn", nidn);
                        intent.putExtra("npm", txt_npm.getText().toString().trim());
                        finish();
                        startActivity(intent);
                        return false;
                    }
                });*/

                mMap_posisi.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        Log.d("Marker", "Started");
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        Log.d("Marker", "Dragging");
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        LatLng markerLocation = marker.getPosition();
                        Toast.makeText(Frm_lokasi.this, markerLocation.toString(), Toast.LENGTH_LONG).show();
                        txt_lati.setText(String.valueOf(markerLocation.latitude));
                        txt_longi.setText(String.valueOf(markerLocation.longitude));
                        Log.d("Marker", "finished");
                    }
                });

            }
        });
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Izin diberikan.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap_posisi.setMyLocationEnabled(true);                   }

                } else {

                    // Izin ditolak.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void Camerapermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Frm_lokasi.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Frm_lokasi.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Frm_lokasi.this,
                        new String[]{Manifest.permission.CAMERA},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        switch (requestCode) {
            case(CAMERA_REQUEST_CODE) :
                if(resultCode == Activity.RESULT_OK)
                {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    f_lokasi.setImageBitmap(bitmap);
                }
                break;
        }

    }


    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://pstiubl.com/api_rusak/act_lokasi.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Frm_lokasi.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Frm_lokasi.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image_lokasi = getStringImage(bitmap);

                //Getting Image Name
                String lati = txt_lati.getText().toString().trim();
                String longi = txt_longi.getText().toString().trim();
                String id_user = Frm_menu_akun.txt_id.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("f_lokasi", image_lokasi);
                params.put("lati", lati);
                params.put("longi", longi);
                params.put("id_user", id_user);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



}
