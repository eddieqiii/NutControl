package com.nutcorp.nutcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar mainSeek = findViewById(R.id.seekBar);
        final TextView valText = findViewById(R.id.valueText);

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
    }
}
