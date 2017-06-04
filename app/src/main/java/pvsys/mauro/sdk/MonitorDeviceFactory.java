package pvsys.mauro.sdk;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MonitorDeviceFactory {
    private final static Logger LOG = new Logger(MonitorDeviceFactory.class.getSimpleName());

    public static final String TYPE_MIBAND = "MIBAND";
    public static final String TYPE_MIBAND2 = "MIBAND2";
    public static final String[] DEVICE_TYPES = {TYPE_MIBAND, TYPE_MIBAND2};
    public static final Map<String, UUID> DEVICE_TYPE_UUID = new HashMap<>();
    public static BTSequentialClient btClient;

    static {
        DEVICE_TYPE_UUID.put(TYPE_MIBAND, MiBandService.UUID_SERVICE_MIBAND_SERVICE);
        DEVICE_TYPE_UUID.put(TYPE_MIBAND2, MiBandService.UUID_SERVICE_MIBAND2_SERVICE);
    }

    public static MonitorDevice newMonitorDevice(BluetoothDevice device) {
        btClient = new BTSequentialClient(device);
        Map<String, BluetoothGattCharacteristic> characteristicMap = btClient.getCharacteristicsMap();
        MonitorDevice monitorDevice = null;
        String deviceType = null;

        if(characteristicMap.containsKey(MiBand2MonitorDevice.UUIDS.UUID_CHARACTERISTIC_3_CONFIGURATION.toString().toLowerCase())){
            monitorDevice = new MiBand2MonitorDevice(btClient, device);
            deviceType="MIBAND2";
        }else{
            monitorDevice = new MiBand1MonitorDevice(btClient, device);
            deviceType="MIBAND";
        }



        if(monitorDevice==null) {
            throw new RuntimeException("unexpected device type");
        }
        monitorDevice.initialize();
        return monitorDevice;
    }

}
