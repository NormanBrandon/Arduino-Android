package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class remote extends AppCompatActivity {
    ConnectedThread MyConexionBT;
    private boolean presionado = false;
    public String flecha;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    String mensaje;
    float x,y,z;
    int whip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_remote );
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(sensor.TYPE_ACCELEROMETER);
        final TextView xt =(TextView)findViewById(R.id.textView12);

        final TextView zt =(TextView)findViewById(R.id.textView14);

        if (sensor==null){
            finish();

        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                new remote.AsyncTaskCounter().execute();

                x=event.values[0];
                y=event.values[1];
                z=event.values[2];

                if(y<-3){
                    presionado=true;
                    flecha="izquierda";
                    mensaje="Girando a la Izquierda...";
                }
                else if (y>3) {
                    presionado = true;
                    flecha = "derecha";
                    mensaje="Girando a la Derecha....";
                }
                if(z<-1){
                    presionado=true;
                    flecha="abajo";
                    mensaje="Retrocediendo...";
                }
                else if (z>5){
                    presionado=true;
                    flecha="arriba";
                    mensaje="Avanzando...";
                }

                if(z>-1 && z<5 && y>-3 && y<3){
                    presionado=false;
                    mensaje="Detenido";
                }
                xt.setText(mensaje);


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
        Button btn_conn = (Button)findViewById ( R.id.btn_backremo);


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
   //     MyConexionBT.start();
        MyConexionBT.write("X");



    }
    public void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);

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
        stop();
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        finish();

    }
    @Override
    public void onPause()
    {
        super.onPause();
        stop();
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        finish();


    }
    public void onDestroy() {
        super.onDestroy();
        stop();
        MyConexionBT.write("R");
        MyConexionBT.desconectar();
        finish();

    }
    public class AsyncTaskCounter extends AsyncTask<Void, Void, Void> {//clase para enviar repediamente los datos alv

        @Override
        public Void doInBackground(Void... arg0) {
            while(presionado) {
                envio();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        public void envio(){
            switch(flecha){
                case "arriba":
                    up();
                    break;
                case "derecha":
                    right();
                    break;
                case "izquierda":
                    left();
                    break;
                case "abajo":
                    down();
                    break;



            }
        }



    }
    public void up() {
        //le manda al arduino una sola nota / sonido


        MyConexionBT.write("A");

    }

    public void right() {
        //le manda al arduino una sola nota / sonido


        MyConexionBT.write("D");

    }

    public void left() {
        //le manda al arduino una sola nota / sonido


        MyConexionBT.write("C");

    }


    public void down() {
        //le manda al arduino una sola nota / sonido


        MyConexionBT.write("B");

    }


}
