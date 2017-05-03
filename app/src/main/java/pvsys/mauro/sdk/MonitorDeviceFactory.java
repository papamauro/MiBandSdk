package pvsys.mauro.sdk;

import android.bluetooth.BluetoothDevice;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MonitorDeviceFactory {
    private final static Logger LOG = new Logger(MonitorDeviceFactory.class.getSimpleName());

    public static final String TYPE_MIBAND = "MIBAND";
    public static final String TYPE_MIBAND2 = "MIBAND2";
    public static final String[] DEVICE_TYPES = {TYPE_MIBAND, TYPE_MIBAND2};
    public static final Map<String, UUID> DEVICE_TYPE_UUID = new HashMap<>();

    static {
        DEVICE_TYPE_UUID.put(TYPE_MIBAND, MiBandService.UUID_SERVICE_MIBAND_SERVICE);
        DEVICE_TYPE_UUID.put(TYPE_MIBAND2, MiBandService.UUID_SERVICE_MIBAND2_SERVICE);
    }

    public static MonitorDevice newMonitorDevice(BluetoothDevice device, String deviceType) {
        MonitorDevice monitorDevice = null;
        if (TYPE_MIBAND.equals(deviceType)) {
            monitorDevice = new MiBand1MonitorDevice(device);
        }
//        else if(TYPE_MIBAND2.equals(deviceType)) { TODO
//            return new MiBand2(device);
//        }
        if(monitorDevice==null) {
            throw new RuntimeException("unexpected device type");
        }
        monitorDevice.initialize();
        return monitorDevice;
    }
}
