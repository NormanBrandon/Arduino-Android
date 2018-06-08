package com.ivy.arduino.proyecto_arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;

public class act_select_mode extends AppCompatActivity implements Serializable{

    //1)
    Button IdEncender, IdApagar,IdDesconectar;
    TextView IdBufferIn;
    public ConnectedThread MyConexionBT;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    //-------------------------------------------
    // String para la direccion MAC
    private  String address = null;
    //-------------------------------------------
    public ConnectedThread getConexion(){
        return MyConexionBT;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_select_mode );


        Button btn_remote = (Button)findViewById ( R.id.btn_remote );

        btn_remote.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toRemote();
            }
        } );

        Button btn_settings = (Button)findViewById ( R.id.btn_settings );

        btn_settings.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toSettings();
            }
        } );

        Button btn_auto = (Button)findViewById ( R.id.btn_auto );

        btn_auto.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toAuto();
            }
        } );

        Button btn_drive = (Button)findViewById ( R.id.btn_drive);

        btn_drive.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toDrive();
            }
        } );

        Button btn_follow = (Button)findViewById ( R.id.btn_follow );

        btn_follow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toFollow();
            }
        } );
        //2)
        //Enlaza los controles con sus respectivas vistas
        Button btn_desconectar = (Button)findViewById ( R.id.IdDesconectar );
        btn_desconectar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toDesconectar();
            }
        } );
        //Consigue la direccion MAC desde DeviceListActivity via intent
        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        address = intent.getStringExtra(MainActivity.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        MyConexionBT = new ConnectedThread(address);
        MyConexionBT.conectar();
        MyConexionBT.start();
    }


    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }



    private void toSettings() {
        Intent intent = new Intent ( this,  settings.class ) ;
        startActivity ( intent );
    }

    private void toDrive() {
        Intent intent = new Intent ( this,  drive.class ) ;
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
        MyConexionBT.write("X");
        MyConexionBT.write("A");
        MyConexionBT.desconectar();
        startActivity ( intent );
    }

    private void toFollow() {

        Intent intent = new Intent ( this,  act_follow.class ) ;
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
        MyConexionBT.write("R");
        MyConexionBT.write("S");
        startActivity ( intent );

    }

    private void toAuto() {
        Intent intent = new Intent ( this,  act_auto.class ) ;
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
        MyConexionBT.write("R");
        MyConexionBT.write("U");
        startActivity ( intent );

    }

    private void toRemote() {
        Intent intent = new Intent ( this,  remote.class ) ;
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

        MyConexionBT.write("X");
        MyConexionBT.write("A");
        startActivity ( intent );

    }
    private void toDesconectar() {
        MyConexionBT.desconectar();
        finish();

    }



}
