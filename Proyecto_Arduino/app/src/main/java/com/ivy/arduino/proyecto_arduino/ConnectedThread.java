package com.ivy.arduino.proyecto_arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.UUID;
import android.R;
import android.R.*;

public class ConnectedThread extends Thread implements Serializable
{
    public InputStream mmInStream;
    public  OutputStream mmOutStream;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    public String save="inicial";
    public String readMessage;

    public boolean flag;

    //private ConnectedThread MyConexionBT;
    public int estado;
    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public ConnectedThread(String address)
    {
              save =address;
       }



    public void conectar(){

        btAdapter = BluetoothAdapter.getDefaultAdapter(); // get Bluetooth adapter
        //  VerificarEstadoBT();
        BluetoothDevice device = btAdapter.getRemoteDevice(save);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            //     Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establece la conexión con el socket Bluetooth.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {}
        }

        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try
        {
            tmpIn = btSocket.getInputStream();
            tmpOut = btSocket.getOutputStream();
        } catch (IOException e) { }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;



    }



    public int read()
    {

        byte[] buffer = new byte[256];
        int bytes=0;
        // Se mantiene en modo escucha para determinar el ingreso de datos
            try {
                bytes = mmInStream.read();

            } catch (IOException e) {
            }

        return bytes;
    }

    public boolean getFlag() {
        return flag;
    }



    public String getReadMessage() {
        return readMessage;
    }

    public void cerrar(){


        try
        { // Cuando se sale de la aplicación esta parte permite
            // que no se deje abierto el socket
            btSocket.close();
        } catch (IOException e2) {}
    }
    public void desconectar(){
        if (btSocket!=null)
        {
            try {btSocket.close();}
            catch (IOException e)
            {
                //Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();;}
        }

    }}
    //Envio de trama
    public void write(String input)
    {
        try {
            mmOutStream.write(input.getBytes());
        }
        catch (IOException e)
        {
            //si no es posible enviar datos se cierra la conexión
            //   Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
            //  finish();
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

}

