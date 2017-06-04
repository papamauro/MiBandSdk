package pvsys.mauro.test;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pvsys.mauro.sdk.BTDeviceBounder;
import pvsys.mauro.sdk.BTDeviceDiscovery;
import pvsys.mauro.sdk.Logger;
import pvsys.mauro.sdk.MonitorDevice;
import pvsys.mauro.sdk.ValueListener;


public class TestActivity extends AppCompatActivity {

    private final static Logger LOG = new Logger(AppCompatActivity.class.getSimpleName());

    public static final int REQUEST_ENABLE_BT = 9;

    BluetoothManager btManager;
    BluetoothAdapter btAdapter;

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = (Button)findViewById(R.id.button);
        button.setEnabled(false);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(device!=null) {
                    device.startHeartMonitoring(new ValueListener() {
                        @Override
                        public void update(int value) {
                            button.setText("heartrate = " + value);
                        }
                    });
                }
            }
        });

        startBluetooth();
    }


    private void startBluetooth() {
        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        if (btAdapter != null) {
            if (!btAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }else{
                bluetoothSetupDone();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothSetupDone();
                } else {
                    LOG.error("bluetooth activation not performed");
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private MonitorDevice device;
    private volatile boolean initialized = false;

    private void bluetoothSetupDone(){

        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        if (btAdapter == null || !btAdapter.isEnabled()) {
            LOG.error("bluetooth not available");
        }
        LOG.info("bluetooth available");


        final BTDeviceBounder deviceBounder = new BTDeviceBounder(btAdapter);


        BTDeviceDiscovery discovery = new BTDeviceDiscovery(btAdapter, new BTDeviceDiscovery.DeviceDiscoveryListener() {
            @Override
            public void onDeviceDiscovered(BluetoothDevice btDevice, String type, BTDeviceDiscovery btDeviceDiscovery) {

                LOG.info("Founded:"+ btDevice.getAddress());
                if (btDevice.getAddress().equals("88:0F:10:EC:46:09")){
                    //Mi band 1s: 88:0F:10:EC:46:09
                    //Mi band 2: D3:96:95:3C:A6:50
                    textView.setText("Mi Band: " + btDevice.getAddress());
                    btDeviceDiscovery.stopScan();
                    deviceBounder.boundDevice(btDevice);
                    //start progress
                    device = deviceBounder.getBoundedDevice();
                    //stop progress
                    button.setEnabled(true);
                }

            }
        });
        discovery.start();
    }

/*
        MonitorDevice monitorDevice = deviceBounder.getBoundedDevice();
        if(monitorDevice == null) {
            BTDeviceDiscovery discovery = new BTDeviceDiscovery(btAdapter, new BTDeviceDiscovery.DeviceDiscoveryListener() {
                @Override
                public void onDeviceDiscovered(BluetoothDevice device, String type) {
                    deviceBounder.boundDevice(device, type);
                    handleDeviceFound(deviceBounder.getBoundedDevice());
                }
            });
            discovery.start();
        } else {
            handleDeviceFound(monitorDevice);
        }
        */


}
