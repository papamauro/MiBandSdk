package pvsys.mauro.sdk;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static pvsys.mauro.sdk.MiBand1MonitorDevice.UIDS.lookup;

public class GattCharacteristic {

    public static final String BASE_UUID = "0000%s-0000-1000-8000-00805f9b34fb";
    
    public static final UUID UUID_CHARACTERISTIC_ALERT_LEVEL = UUID.fromString((String.format(BASE_UUID, "2A06")));

    public static final byte NO_ALERT = 0x0;
    public static final byte MILD_ALERT = 0x1;
    public static final byte HIGH_ALERT = 0x2;

    public static String toString(BluetoothGattCharacteristic characteristic) {
        return characteristic.getUuid() + " (" + lookup(characteristic.getUuid(), "unknown") + ")";
    }
}
