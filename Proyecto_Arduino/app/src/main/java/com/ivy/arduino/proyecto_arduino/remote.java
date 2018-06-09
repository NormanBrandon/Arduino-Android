package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class remote extends AppCompatActivity {
    ConnectedThread MyConexionBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_remote );

        Button btn_conn = (Button)findViewById ( R.id.btn_backremo);

        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );

        Button h1 = (Button)findViewById ( R.id.btn_remo_horn1 );
        Button h2 = (Button)findViewById ( R.id.btn_remo_horn2 );
        Button h3 = (Button)findViewById ( R.id.btn_remo_horn3 );

        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );

        h1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                horn1();
            }
        } );

        h2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                horn2();
            }
        } );

        h3.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                horn3();
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

    public void horn3() {
        //manda una tonada al arduino
        MyConexionBT.write("F");

    }
    public void horn2() {
        //manda una tonada al arduino
        MyConexionBT.write("E");

    }

    public void horn1() {
        //manda una mentada x
        MyConexionBT.write("G");

    }


    public void back(){
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        finish();

    }
    @Override
    public void onPause()
    {
        super.onPause();
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        finish();


    }
    public void onDestroy() {
        super.onDestroy();

        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        finish();

    }


}
