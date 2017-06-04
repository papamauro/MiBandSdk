package pvsys.mauro.sdk;


import android.bluetooth.BluetoothDevice;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class MonitorDevice {

    public static final int PERIODIC_WRITE_PERIOD_SEC = 2;

    public abstract void initialize();
    public abstract void startHeartMonitoring(ValueListener listener);
    public abstract void stopHeartMonitoring();
    public abstract void vibrate();

    private final static Logger LOG = new Logger(MonitorDevice.class.getSimpleName());

    protected final BluetoothDevice device;
    protected final BTSequentialClient btClient;
    private ScheduledExecutorService keepAliveScheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture keepAliveScheduledFuture;
    private Map<String, byte[]> periodicWrite = new HashMap<>();

    protected MonitorDevice(final BTSequentialClient btClient, BluetoothDevice device) {
        this.device = device;
        this.btClient = btClient;

        keepAliveScheduledFuture = keepAliveScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for(Map.Entry<String, byte[]> e : periodicWrite.entrySet()) {
                    btClient.writeCharacteristic(e.getKey(), e.getValue());
                }
            }
        }, PERIODIC_WRITE_PERIOD_SEC, PERIODIC_WRITE_PERIOD_SEC, TimeUnit.SECONDS);
    }

    protected void addPeriodicWrite(String uuid, byte[] value) {
        periodicWrite.put(uuid.toLowerCase(), value);
    }

    protected void removePeriodicWrite(String uuid) {
        periodicWrite.remove(uuid.toLowerCase());
    }

}
