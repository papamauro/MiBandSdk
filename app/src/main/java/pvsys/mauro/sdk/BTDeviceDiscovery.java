package pvsys.mauro.sdk;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.os.ParcelUuid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.bluetooth.le.ScanSettings.MATCH_MODE_STICKY;
import static android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY;

public class BTDeviceDiscovery {

    private final static Logger LOG = new Logger(BTDeviceDiscovery.class.getSimpleName());

    private BluetoothAdapter btAdapter;
    private ScanCallback leScanCallback;

    private final DeviceDiscoveryListener deviceDiscoveryListener;

    public interface DeviceDiscoveryListener {
        void onDeviceDiscovered(BluetoothDevice device, String type, BTDeviceDiscovery btDeviceDiscovery);
    }


    public BTDeviceDiscovery(BluetoothAdapter btAdapter, DeviceDiscoveryListener deviceDiscoveryListener) {
        this.deviceDiscoveryListener = deviceDiscoveryListener;
        this.btAdapter = btAdapter;
    }


    public void start() {
        if (btAdapter == null || !btAdapter.isEnabled()) {
            LOG.error("bluetooth not available");
        }
        LOG.info("bluetooth available");
        LOG.info("scan");
        leScanCallback = new ScanCallback() {
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                try {
                    ScanRecord scanRecord = result.getScanRecord();
                    if (scanRecord != null) {
                        List<ParcelUuid> serviceUuids = scanRecord.getServiceUuids();
                        if (serviceUuids != null) {
                            LOG.warn(result.getDevice().getName() + ": " + ((scanRecord != null) ? scanRecord.getBytes().length : -1));
                            String deviceType = null;
                            for (String type : MonitorDeviceFactory.DEVICE_TYPES) {
                                if(serviceUuids.contains(new ParcelUuid(MonitorDeviceFactory.DEVICE_TYPE_UUID.get(type)))){
                                    deviceType = type;
                                    break;
                                }
                            }
                            if(deviceType!=null) {
                                deviceDiscoveryListener.onDeviceDiscovered(result.getDevice(), deviceType, BTDeviceDiscovery.this);
                            } else {
                                LOG.error("unexpected device type");
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    LOG.warn("Error handling scan result", e);
                    btAdapter.getBluetoothLeScanner().stopScan(this);
                }
            }
        };
        List<ScanFilter> allFilters = new ArrayList<>();
        for (UUID uuid : MonitorDeviceFactory.DEVICE_TYPE_UUID.values()) {
            allFilters.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(uuid)).build());
        }

        ScanSettings settings = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            settings = new ScanSettings.Builder()
                    .setScanMode(SCAN_MODE_LOW_LATENCY)
                    .setMatchMode(MATCH_MODE_STICKY)
                    .build();
        } else {
            settings = new ScanSettings.Builder()
                    .setScanMode(SCAN_MODE_LOW_LATENCY)
                    .build();
        }

        btAdapter.getBluetoothLeScanner().startScan(allFilters, settings, leScanCallback);
    }

    public void stopScan() {
        btAdapter.getBluetoothLeScanner().stopScan(leScanCallback);
    }



}
