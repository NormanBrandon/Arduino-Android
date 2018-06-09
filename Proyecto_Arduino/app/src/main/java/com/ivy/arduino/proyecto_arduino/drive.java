package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class drive extends AppCompatActivity {
    private boolean presionado = false;
    private String flecha;
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

        final Button up = (Button)findViewById ( R.id.up ) ;
        Button right = (Button)findViewById ( R.id.right ) ;
        Button left = (Button)findViewById ( R.id.left ) ;
        Button down = (Button)findViewById ( R.id.down ) ;


        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flecha="arriba";
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            //AsyncTask que ejecuta Tarea.
                            new AsyncTaskCounter().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        presionado = false;
                        break;
                }
                return true;
            }
        });
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flecha="abajo";
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            //AsyncTask que ejecuta Tarea.
                            new AsyncTaskCounter().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        presionado = false;
                        break;
                }
                return true;
            }
        });
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flecha="izquierda";
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            //AsyncTask que ejecuta Tarea.
                            new AsyncTaskCounter().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        presionado = false;
                        break;
                }
                return true;
            }
        });
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flecha="derecha";
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            //AsyncTask que ejecuta Tarea.
                            new AsyncTaskCounter().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        presionado = false;
                        break;
                }
                return true;
            }
        });


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
    //    MyConexionBT.start();
        MyConexionBT.write("X");


    }
    public class AsyncTaskCounter extends AsyncTask<Void, Void, Void> {//clase para enviar repediamente los datos alv

        @Override
        protected Void doInBackground(Void... arg0) {
            while(presionado) {
                envio();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
            return null;
        }
        protected void envio(){
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
