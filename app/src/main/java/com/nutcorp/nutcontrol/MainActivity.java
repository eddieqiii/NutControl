package com.nutcorp.nutcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    final private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar mainSeek = findViewById(R.id.seekBar);
        Button butt = findViewById(R.id.connectButton);
        final TextView valText = findViewById(R.id.valueText);

        final PopupMenu deviceMenu = new PopupMenu(MainActivity.this, butt);

        mainSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar changedSeek, int val, boolean idk) {
                valText.setText(String.valueOf(val));
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
                populateMenu(deviceMenu);
                deviceMenu.show();
            }
        });

        deviceMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return true;
            }
        });
    }

    private void populateMenu(PopupMenu menu) {
        if (btAdapter == null) return;
        if (!btAdapter.isEnabled()) return; // TODO

        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        menu.getMenu().clear();
        for (BluetoothDevice device : pairedDevices) {
            menu.getMenu().add(device.getName() + " (" + device.getAddress() + ")");
        }
    }
}
