package com.example.usuario.control_vehicular;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Group;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, nuevo.OnFragmentInteractionListener,
        visitante.OnFragmentInteractionListener,
        acerca_de.OnFragmentInteractionListener{
    private FirebaseDatabase database;
    private String MESSAGE_CHILD = "message";

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private TextView lNombre, lVehiculo,lPlacas,lColor,lLicenciatura,lTipo,lObser,lHora;
    long ahora = System.currentTimeMillis();
    Date date = new Date();
    private Group menuopciones;
    //database
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase database;
        // Se genera una referencia a la base de datos
        database = FirebaseDatabase.getInstance();
        String MESSAGE_CHILD = "message";
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         lNombre =(TextView) findViewById(R.id.lNombre);
        lVehiculo =(TextView) findViewById(R.id.lVehiculo);
        lPlacas =(TextView) findViewById(R.id.lPlacas);
        lColor =(TextView) findViewById(R.id.lColor);
        lLicenciatura =(TextView) findViewById(R.id.lLicenciatura);
        lTipo =(TextView) findViewById(R.id.lTipo);
        lObser =(TextView) findViewById(R.id.lObs);
        lHora=(TextView)findViewById(R.id.lHora);
        menuopciones= (Group) findViewById(R.id.menuopciones);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            nuevo nuevof = new nuevo();
            FragmentTransaction transition =  getSupportFragmentManager().beginTransaction();
            transition.replace(R.id.contenedor, nuevof);
            transition.commit();
        } else if (id == R.id.nav_gallery) {
            visitante visitantef = new visitante();
            FragmentTransaction transition =  getSupportFragmentManager().beginTransaction();
            transition.replace(R.id.contenedor, visitantef);
            transition.commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            acerca_de acerca= new acerca_de();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contenedor,acerca);
            transaction.commit();
        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, QrCodeActivity.class);
        startActivityForResult(i, REQUEST_CODE_QR_SCAN);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //instancia a la base de datos de firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String tipousuario = "";
        String licenciatura = "";

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta", Toast.LENGTH_SHORT).show();
            String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (resultado != null) {
                Toast.makeText(getApplicationContext(), "No se pudo escanear el código QR", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {

            if (data != null) {
                String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                //Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Datos encontrados", Toast.LENGTH_SHORT).show();

                String[] items = lectura.split(",");
                for (String item : items)
                {
                    System.out.println("item = " + item);
                }

                lNombre.setText(items[1].toString());
                String nombre_user = lNombre.getText().toString();

                if (items[5].toString().contains("1")){
                    lLicenciatura.setText("Ing. en Software");
                    licenciatura = lLicenciatura.getText().toString();
                }else{
                    if (items[5].toString().contains("2")){
                        lLicenciatura.setText("Ing. en Plásticos");
                        licenciatura = lLicenciatura.getText().toString();
                    }else{
                        if (items[5].toString().contains("3")){
                            lLicenciatura.setText("Ing. en Prod. Industrial");
                            licenciatura = lLicenciatura.getText().toString();
                        }else{

                            if (items[5].toString().contains("4")) {
                                lLicenciatura.setText("Lic. Seg. Ciudadana");
                                licenciatura = lLicenciatura.getText().toString();
                            }
                        }
                    }
                }

                if (items[4].toString().contains("1")){
                    lTipo.setText("Alumno");
                    tipousuario = lTipo.getText().toString();
                }else{
                    if (items[4].toString().contains("2")){
                        lTipo.setText("Docente");
                        tipousuario = lTipo.getText().toString();
                    }else{
                        if (items[4].toString().contains("3")){
                            lTipo.setText("Administrativo");
                            tipousuario = lTipo.getText().toString();
                        }
                    }
                }
                lVehiculo.setText(items[2].toString());
                String vehiculo = lVehiculo.getText().toString();
                lColor.setText(items[3].toString());
                String color = lColor.getText().toString();
                lPlacas.setText(items[0].toString());
                String placas = lPlacas.getText().toString();
                lObser.setText(items[6].toString());
                String observaciones = lObser.getText().toString();

                //Caso 1: obtener la hora y salida por pantalla con formato:
                DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                System.out.println("Hora: "+hourFormat.format(date));
                lHora.setText(hourFormat.format(date));
                String hora_entrada = lHora.getText().toString();
                String hora_salida = lHora.getText().toString();

                IOData entrada_salida = new IOData(nombre_user,licenciatura,vehiculo,color,placas,
                        hora_entrada,hora_salida);
                //carga datos
                mDatabase.child("entrada/salida").child(nombre_user).setValue(nombre_user);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}