package omg.medvedomg.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button visible;
    private Button list;
    private Switch onOffSwitcher;

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visible = (Button) findViewById(R.id.button3);
        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible, 0);
            }
        });
        list = (Button) findViewById(R.id.button4);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pairedDevices = BA.getBondedDevices();

                ArrayList list = new ArrayList();
                for (BluetoothDevice bt : pairedDevices) {
                    list.add(bt.getName());
                    Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

                    final ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                    lv.setAdapter(adapter);
                }
            }
        });

        BA = BluetoothAdapter.getDefaultAdapter(); // class to communicate with Bluetooth


        onOffSwitcher = (Switch) findViewById(R.id.switch1);
        onOffSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    on();
                } else {
                    off();
                }
            }
        });

        if (onOffSwitcher.isChecked()) {
            on();
        } else {
            off();
        }

        lv = (ListView) findViewById(R.id.listview);

    }

    private void off() {
        BA.disable();
        Toast.makeText(this, "bluetooth disabled", Toast.LENGTH_SHORT).show();
    }

    public void on() {
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(this, "bluetooth enabled", Toast.LENGTH_SHORT);
        }
    }


}
