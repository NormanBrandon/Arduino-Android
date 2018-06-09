package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class remote extends AppCompatActivity {

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
    }

    public void back(){
        finish();

    }

    public void horn2() {
        //le manda al arduino una sola nota / sonido , mismo metodo que drive
    }
    public void horn3(){
        //manda una tonada al arduino mismo metodo que drive
    }

    public void horn1(){
        //manda una mentada xd mismo metodo que drive
    }

}
