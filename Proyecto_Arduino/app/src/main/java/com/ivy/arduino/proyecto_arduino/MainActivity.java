package com.ivy.arduino.proyecto_arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.zip.CheckedOutputStream;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set pairedDevices;
    ListView devicelist;
    Boolean connected = false;
    BluetoothDevice robott;
    ArrayList <String> direc = new ArrayList<String >();

   public InputStream indata;
   public OutputStream outdata;

    public  InputStream getIndata() {
        return indata;
    }

    public OutputStream getOutdata() {
        return outdata;
    }

    private static final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address = null;


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
                direc.add(bd.getAddress());

                //98:D3:32:70:64:B0
                if(bd.getAddress().equals("98:D3:32:70:64:B0")){
                    robott =bd;
                }
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

        if (mBluetoothAdapter == null) {
            Context context = getApplicationContext ();
            CharSequence text = "El dispositivo no soporta Bluetooth";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText ( context, text, duration );
            toast.show ();
        }else{

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            connected = true;
        }

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView av, View v, int arg2, long arg3)
        {


                    }
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
        if(connected) {

            /*
            try {

            BluetoothSocket socket = null;
                socket = robott.createRfcommSocketToServiceRecord(PORT_UUID);

                socket.connect();

                outdata= socket.getOutputStream();

                indata =socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
            Context context = getApplicationContext ();
            CharSequence text = "CONECTADO!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText ( context, text, duration );
            toast.show ();

            //98:D3:32:70:64m=socket.getInputStr:B0



            Intent intent = new Intent ( this, act_select_mode.class );
            startActivity ( intent );

            /*Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
            */

        }else{
            Context context = getApplicationContext ();
            CharSequence text = "Error - No conectado";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText ( context, text, duration );
            toast.show ();
        }
    }
}
