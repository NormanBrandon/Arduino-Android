package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class settings extends AppCompatActivity {
    ConnectedThread MyConexionBT;
    TextView frontal,izquierdo,derecho, ldr1,ldr2,dht11,termistor;
    String mensaje;
    String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_settings );
        Button btn_conn = (Button)findViewById ( R.id.btn_backsett);

        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );
        frontal = (TextView)findViewById(R.id.central2);
        izquierdo = (TextView)findViewById(R.id.izquierdo2);
        derecho = (TextView)findViewById(R.id.derecho2);
        ldr1 = (TextView)findViewById(R.id.ldr1);
        ldr2 = (TextView)findViewById(R.id.ldr2);
        dht11 = (TextView)findViewById(R.id.dht11);
        termistor = (TextView)findViewById(R.id.termistor2);


        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        String address = intent.getStringExtra(act_select_mode.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        //<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        MyConexionBT = new ConnectedThread(address);
        MyConexionBT.conectar();
        MyConexionBT.start();
        new AsyncTaskCounter().execute();

        MyConexionBT.write("L");
    }
    public class AsyncTaskCounter extends AsyncTask<Void, Void, Void> {//clase para enviar repediamente los datos alv

        @Override
        public Void doInBackground(Void... arg0) {

            termistor.setText("hola: " + MyConexionBT.getReadMessage());


            return null;
        }

    }


    public void back(){
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        MyConexionBT.stop();

        finish();

    }
    @Override
    public void onPause()
    {
        super.onPause();
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        MyConexionBT.stop();

        finish();


    }
    public void onDestroy() {
        super.onDestroy();
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        MyConexionBT.stop();

        finish();

    }
}
