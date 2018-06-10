package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class act_auto extends AppCompatActivity {
    ConnectedThread MyConexionBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_auto );
        Button btn_conn = (Button)findViewById ( R.id.btn_backauto);
        TextView distancia = (TextView)findViewById(R.id.derecho);

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
      //  MyConexionBT.start();
        MyConexionBT.write("U");


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