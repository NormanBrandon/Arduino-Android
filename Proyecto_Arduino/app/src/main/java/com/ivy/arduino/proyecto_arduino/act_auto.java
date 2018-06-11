package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class act_auto extends AppCompatActivity {
    ConnectedThread MyConexionBT;
    int progressChangedValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_auto );
        Button btn_conn = (Button)findViewById ( R.id.btn_backauto);
        final TextView distancia = (TextView)findViewById(R.id.derecho);
        SeekBar simpleSeekBar1=(SeekBar) findViewById(R.id.bar_auto_distance); // initiate the progress bar
        simpleSeekBar1.setMax(50); // 200 maximum value for the Seek bar
        simpleSeekBar1.setProgress(30);
        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );

        simpleSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                distancia.setText(progress + " cm");
                MyConexionBT.write("K");
                char s=(char)progress;
                MyConexionBT.write(s + "");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Intent intent = getIntent();
        String address = intent.getStringExtra(act_select_mode.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        MyConexionBT = new ConnectedThread(address);
        MyConexionBT.conectar();
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