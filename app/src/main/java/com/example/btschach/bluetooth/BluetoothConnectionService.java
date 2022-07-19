package com.example.btschach.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class BluetoothConnectionService {
    private static final String appName = "BT Schach";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    private ProgressDialog mProgressDialogue;

    private final BluetoothAdapter mBluetoothAdapter;
    private Context mContext;

    private ConnectedThread mConnectedThread;

    public BluetoothConnectionService(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }

    private class AcceptThread extends Thread {
        // local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket temp = null;

            // creating a new listening server socket
            try {
                temp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);
            } catch (IOException e) {e.printStackTrace();}
            mmServerSocket = temp;
        }

        public void run() {
            BluetoothSocket socket = null;
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {e.printStackTrace();}
            if (socket != null) {
                connected(socket, mmDevice);
            }
        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run() {
            BluetoothSocket temp = null;

            //Get a Bluetooth Socket to connect to the provided device
            try {
                temp = mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {e.printStackTrace();}
            mmSocket = temp;

            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException e1) {e1.printStackTrace();}
            }

            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    // start AcceptThread to begin a session in listening (server) mode
    public synchronized void start() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    // AcceptThread starts and waits. Then ConnectThread starts and tries to connect to the other device's AcceptThread
    public void startClient(BluetoothDevice device, UUID uuid) {
        mProgressDialogue = ProgressDialog.show(mContext, "Connecting Bluetooth", "Please Wait...", true);
        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            try {
                mProgressDialogue.dismiss();
            } catch (NullPointerException e) {e.printStackTrace();}

            try {
                tempIn = mmSocket.getInputStream();
                tempOut = mmSocket.getOutputStream();

                Intent switchScreenIntent = new Intent("SwitchScreen");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(switchScreenIntent);
            } catch (IOException e) {e.printStackTrace();}
            mmInStream = tempIn;
            mmOutStream = tempOut;
        }

        public void run() {
            byte[] buffer  =  new byte[16384];

            int bytes;

            ArrayList<byte[]> gameStateBuilder = new ArrayList<>();

            while (true) {
                try {

                    bytes = mmInStream.read(buffer);

                    byte[] gameStateFragment = new byte[bytes];
                    for (int i = 0; i < bytes; i++) {
                        gameStateFragment[i] = buffer[i];
                    }
                    gameStateBuilder.add(gameStateFragment);

                    String incomingMessage = new String(buffer, 0, bytes);

                    if (incomingMessage.equals("GameLost")) {
                        Intent gameLostIntent = new Intent("GameLost");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(gameLostIntent);
                        gameStateBuilder.clear();
                    } else if (incomingMessage.equals("GameDraw")) {
                        Intent gameDrawIntent = new Intent("GameDraw");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(gameDrawIntent);
                        gameStateBuilder.clear();
                    } else if (incomingMessage.equals("OfferDraw")) {
                        Intent offerDrawIntent = new Intent("OfferDraw");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(offerDrawIntent);
                        gameStateBuilder.clear();
                    } else if (incomingMessage.equals("OfferDrawReply")) {
                        Intent offerDrawReplyIntent = new Intent("OfferDrawReply");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(offerDrawReplyIntent);
                        gameStateBuilder.clear();
                    } else if (incomingMessage.equals("Forfeit")) {
                        Intent forfeitIntent = new Intent("Forfeit");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(forfeitIntent);
                        gameStateBuilder.clear();
                    } else if (incomingMessage.equals("MainMenu")) {
                        Intent mainMenuIntent = new Intent("MainMenu");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(mainMenuIntent);
                        gameStateBuilder.clear();
                    } else {
                        if (bytes != 990) {
                            byte[] gameState = {};
                            for (byte[] fragment: gameStateBuilder) {
                                for (byte fragmentByte: fragment) {
                                    gameState = ArrayUtils.add(gameState, fragmentByte);
                                }
                            }
                            Intent gameStateIntent = new Intent("GameState");
                            gameStateIntent.putExtra("currentState", gameState);
                            LocalBroadcastManager.getInstance(mContext).sendBroadcast(gameStateIntent);
                            gameStateBuilder.clear();
                        }
                    }
                } catch (IOException e) {break;}
            }
        }

        // sends data to connected device
        public void write(byte[] bytes) {
            //String text = new String(bytes, Charset.defaultCharset());
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {e.printStackTrace();}
        }

        // shuts the connection down
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    // write to ConnectedThread (unsynchronized)
    public void write(byte[] out) {
        mConnectedThread.write(out);
    }

    public void cancel() {
        try {
            mInsecureAcceptThread.cancel();
        } catch (Exception e) {e.printStackTrace();}
        try {
            mConnectThread.cancel();
        } catch (Exception e) {e.printStackTrace();}
        try {
            mConnectedThread.cancel();
        } catch (Exception e) {e.printStackTrace();}

    }
}
