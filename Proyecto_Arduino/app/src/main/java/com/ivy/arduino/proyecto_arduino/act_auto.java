package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class act_auto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_auto );
        Button btn_conn = (Button)findViewById ( R.id.btn_backauto);
        ConnectedThread MyConexionBT;

        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );
    }

    public void back(){
        finish();

    }
}