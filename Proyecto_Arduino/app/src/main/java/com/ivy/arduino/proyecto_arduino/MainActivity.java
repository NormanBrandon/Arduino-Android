package com.ivy.arduino.proyecto_arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothDevice;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;
    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set pairedDevices;
    ListView devicelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        devicelist = (ListView)findViewById(R.id.listView);

        Button btn_conn = (Button)findViewById ( R.id.btn_conn );
        btn_conn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                connect();
            }
        } );

        Button btn_start = (Button)findViewById (R.id.btn_start );
        btn_start.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                startbt();
                showpaired();
                scandevices();

            }
        } );



    }

    private void scandevices() {
    }

    private void showpaired() {
        pairedDevices = mBluetoothAdapter.getBondedDevices ();
        ArrayList<String> list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(Object bt : pairedDevices)
            {
                BluetoothDevice bd = (BluetoothDevice)bt;
                list.add(bd.getName() + "\n" + bd.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

    private void startbt() {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView av, View v, int arg2, long arg3)
        {
          //SE EJECUTA AL HACER CLICK EN UNO DE LOS ELEMENTOS DE LA LISTA


            \        }
    };

    /*  @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }

                if (!mBluetoothAdapter.isDiscovering()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(enableBtIntent, REQUEST_DISCOVERABLE_BT);
                }

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                AbstractList<String> mArrayAdapter = null;
                    // If there are paired devices
                if (pairedDevices.size() > 0) {

                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        // Add the name and address to an array adapter to show in a ListView

                        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }

                //FALTA IMPRIMIR PAIRED DEVICES

            }
        } );
*/

    public void connect() {
        Context context = getApplicationContext();
        CharSequence text = "CONECTADO!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


        Intent intent = new Intent ( this,  act_select_mode.class ) ;
        startActivity ( intent );

    }
}
