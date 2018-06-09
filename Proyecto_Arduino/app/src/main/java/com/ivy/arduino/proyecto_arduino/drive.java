package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class drive extends AppCompatActivity {

    // android:screenOrientation="landscape" ---- manifest
    ConnectedThread MyConexionBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_drive );
        Button btn_conn = (Button)findViewById ( R.id.btn_backdriv );
        Button h1 = (Button)findViewById ( R.id.btn_driv_horn1 );
        Button h2 = (Button)findViewById ( R.id.btn_driv_horn2 );
        Button h3 = (Button)findViewById ( R.id.btn_driv_horn3 );

        h1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                horn1();
            }
        } );

        h1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                horn2();
            }
        } );

        h2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                horn3();
            }
        } );

        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );
        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        String address = intent.getStringExtra(act_select_mode.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        //<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        MyConexionBT = new ConnectedThread(address);
        MyConexionBT.conectar();
        MyConexionBT.start();

    }

    public void horn2() {
        //le manda al arduino una sola nota / sonido


        MyConexionBT.write("E");

    }

    public void horn3() {
        //manda una tonada al arduino
       MyConexionBT.write("F");

    }


    public void horn1() {
        //manda una mentada x
        MyConexionBT.write("G");

    }

    public void back(){
        finish();

    }
    @Override
    public void onPause()
    {
        super.onPause();
        MyConexionBT.desconectar();

    }
    public void onDestroy() {
        super.onDestroy();

        MyConexionBT.desconectar();

    }

}
