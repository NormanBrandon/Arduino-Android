package com.ivy.arduino.proyecto_arduino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class act_select_mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_select_mode );


        Button btn_remote = (Button)findViewById ( R.id.btn_remote );

        btn_remote.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toRemote();
            }
        } );

        Button btn_settings = (Button)findViewById ( R.id.btn_settings );

        btn_settings.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toSettings();
            }
        } );

        Button btn_auto = (Button)findViewById ( R.id.btn_auto );

        btn_auto.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toAuto();
            }
        } );

        Button btn_drive = (Button)findViewById ( R.id.btn_drive);

        btn_drive.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toDrive();
            }
        } );

        Button btn_follow = (Button)findViewById ( R.id.btn_follow );

        btn_follow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                toFollow();
            }
        } );

    }

    private void toSettings() {
        Intent intent = new Intent ( this,  settings.class ) ;
        startActivity ( intent );
    }

    private void toDrive() {
        Intent intent = new Intent ( this,  drive.class ) ;
        startActivity ( intent );
    }

    private void toFollow() {
        Intent intent = new Intent ( this,  act_follow.class ) ;
        startActivity ( intent );
    }

    private void toAuto() {
        Intent intent = new Intent ( this,  act_auto.class ) ;
        startActivity ( intent );
    }

    private void toRemote() {
        Intent intent = new Intent ( this,  remote.class ) ;
        startActivity ( intent );
    }


}
