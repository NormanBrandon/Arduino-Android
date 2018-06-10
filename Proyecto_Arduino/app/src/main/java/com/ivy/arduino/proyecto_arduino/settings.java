package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class settings extends AppCompatActivity {
    ConnectedThread MyConexionBT;
    public TextView termistor;
    String mensaje="";
    int ascii;
    public boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_settings );
        Button btn_conn = (Button)findViewById ( R.id.btn_backsett);
        Button actualizar= (Button)findViewById ( R.id.button2);

        termistor = (TextView)findViewById(R.id.termistor2);

        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTaskCounter().execute();
                MyConexionBT.write("J");


                if(ascii==36){
                termistor.setText(mensaje);
                mensaje="";

            }





            }
        });

        Intent intent = getIntent();
        String address = intent.getStringExtra(act_select_mode.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        MyConexionBT = new ConnectedThread(address);
        MyConexionBT.conectar();
        MyConexionBT.write("L");




    }
    public class AsyncTaskCounter extends AsyncTask<Void, Void, Void> {//clase para enviar repediamente los datos alv

        @Override
        public Void doInBackground(Void... arg0) {
            while(ascii!=35){
                ascii=MyConexionBT.read();
            }
            while (ascii!=36) {
                ascii = MyConexionBT.read();
                    mensaje = mensaje + (char) ascii;
            }

            return null;
        }

    }

public void onResume() {
    super.onResume();

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
