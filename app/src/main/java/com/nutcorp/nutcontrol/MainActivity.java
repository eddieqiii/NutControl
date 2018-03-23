package com.nutcorp.nutcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.icu.util.Output;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    final private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    final private HashMap<MenuItem,String> deviceMACs = new HashMap<>();
    final private UUID SPPUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private InputStream inStream = null;
    private OutputStream outStream = null;
    private BluetoothSocket deviceSocket;

    private Button butt;
    private TextView valText;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar mainSeek = findViewById(R.id.seekBar);
        butt = findViewById(R.id.connectButton);
        valText = findViewById(R.id.valueText);

        final PopupMenu deviceMenu = new PopupMenu(MainActivity.this, butt);

        mainSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar changedSeek, int val, boolean idk) {
                valText.setText(String.valueOf(val));
                if (isConnected) {
                    //Log.d("idk", Integer.toString(val));
                    try {
                        outStream.write((Integer.toString(val) + ";").getBytes());
                    } catch (IOException e) {
                        Log.e("idk", "Write failed", e);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar changedSeek) {
                // TODO
            }

            @Override
            public void onStopTrackingTouch(SeekBar changedSeek) {
                // TODO
            }
        });

        butt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View fromView) {
                if (!isConnected) {
                    populateMenu(deviceMenu);
                    deviceMenu.show();
                } else {
                    deviceMenu.getMenu().clear();
                    deviceMenu.getMenu().add(getString(R.string.disconnect));
                    deviceMenu.show();
                }
            }
        });

        deviceMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == getString(R.string.disconnect)) {
                    disconnect();
                } else {
                    butt.setText(getString(R.string.connecting, menuItem.getTitle()));
                    butt.setEnabled(false);
                    connectToDevice(deviceMACs.get(menuItem));
                }

                return true;
            }
        });
    }

    private void connectToDevice(String macToConnect) {
        BluetoothDevice device = btAdapter.getRemoteDevice(macToConnect);
        BluetoothSocket temp = null;
        try {
            temp = device.createRfcommSocketToServiceRecord(SPPUUID);
        } catch (IOException e) {
            disconnected();
        }
        deviceSocket = temp;
        Log.d("idk", "Socket created");

        try {
            deviceSocket.connect();
            connected(macToConnect);
        } catch (IOException e) {
            Log.e("idk", "Connect failed", e);
            disconnect();
        }

        // Open sockets
        InputStream inTemp = null;
        OutputStream outTemp = null;
        try {
            inTemp = deviceSocket.getInputStream();
            outTemp = deviceSocket.getOutputStream();
        } catch (IOException e) {
            disconnect();
        }
        inStream = inTemp;
        outStream = outTemp;
    }

    private void disconnect() {
        try {
            deviceSocket.close();
        } catch (IOException e) {}
        deviceSocket = null;
        disconnected();
    }

    private void populateMenu(PopupMenu menu) {
        if (btAdapter == null) return;
        if (!btAdapter.isEnabled()) return; // TODO

        menu.getMenu().clear();
        deviceMACs.clear();

        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

        for (BluetoothDevice device : pairedDevices) {
            deviceMACs.put(menu.getMenu().add(device.getName() + " (" + device.getAddress() + ")"),
                    device.getAddress());
        }
    }

    private void connected(String deviceMAC) {
        isConnected = true;
        butt.setEnabled(true);
        butt.setText(getString(R.string.connected, deviceMAC));
    }

    private void disconnected() {
        isConnected = false;
        butt.setEnabled(true);
        butt.setText(getString(R.string.dc_text));
    }
}
