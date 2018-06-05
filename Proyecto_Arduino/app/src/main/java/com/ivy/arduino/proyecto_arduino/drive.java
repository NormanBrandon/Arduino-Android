package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class drive extends AppCompatActivity {

    // android:screenOrientation="landscape" ---- manifest

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_drive );
        Button btn_conn = (Button)findViewById ( R.id.btn_backdriv );

        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                back();
            }
        } );
    }

    public void back() {
        Intent intent = new Intent ( this,  act_select_mode.class ) ;
        startActivity ( intent );

    }
}
