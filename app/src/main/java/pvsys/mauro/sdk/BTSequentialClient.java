package pvsys.mauro.sdk;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BTSequentialClient extends BluetoothGattCallback implements Closeable {


    private final static Logger LOG = new Logger(BTSequentialClient.class.getSimpleName());

    public static final UUID UUID_DESCRIPTOR_GATT_CLIENT_CHARACTERISTIC_CONFIGURATION = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final long CONNECTION_TIMEOUT_MS = 10000;
    public static final long OP_TIMEOUT_MS = 5000;

    protected final BluetoothDevice device;
    protected final BluetoothGatt bluetoothGatt;
    private final Map<String, BluetoothGattCharacteristic> characteristicsMap = new HashMap<>();
    private SequentialOperation sequentialOperation = new SequentialOperation();

    public BTSequentialClient(BluetoothDevice device) {
        LOG.info("connecting to device " + device.getName() + " address " + device.getAddress());
        this.device = device;
        this.bluetoothGatt = device.connectGatt(AppClass.getContext(), false, this);
        sequentialOperation.waitForOperation("connection", CONNECTION_TIMEOUT_MS);

        LOG.info("discovering services for device " + device.getName() + " address " + device.getAddress());
        bluetoothGatt.discoverServices();
        sequentialOperation.waitForOperation("service discovery", CONNECTION_TIMEOUT_MS);
    }

    @Override
    public final void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
        try {
            LOG.info("state changed to " + newState + " for " + gatt.getDevice().getName() + " (" + gatt.getDevice().getAddress() + ") ");
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                LOG.info("connected successfully to" + gatt.getDevice().getName() + " (" + gatt.getDevice().getAddress() + ") ");
                sequentialOperation.notifyCompleted(null);
            }
        } catch (RuntimeException ex) {
            sequentialOperation.notifyError(ex);
        }

    }

    @Override
    public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
        try{
            LOG.info("services discovered for " + gatt.getDevice().getName() + " (" + gatt.getDevice().getAddress() + "): ");
            List<BluetoothGattService> services = bluetoothGatt.getServices();
            for (BluetoothGattService service : services) {
                LOG.debug("service discovered " + service.getUuid());
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for (BluetoothGattCharacteristic c : characteristics) {
                    LOG.debug("Characteristic discovered " + c.getUuid());
                    characteristicsMap.put(c.getUuid().toString().toLowerCase(), c);
                }
            }
            sequentialOperation.notifyCompleted(null);
        } catch (RuntimeException ex) {
            sequentialOperation.notifyError(ex);
        }
    }


    public void writeCharacteristic(final UUID uuid, final byte[] value){
        writeCharacteristic(uuid.toString(), value);
    }

    public void writeCharacteristic(final String uuidStr, final byte[] value){
        BluetoothGattCharacteristic characteristics = characteristicsMap.get(uuidStr.toLowerCase());
        if(characteristics == null) {
            throw new RuntimeException("writing error for characteristic " + uuidStr.toLowerCase() + ": characteristic undefined");
        }
        if(!characteristics.setValue(value)){
            throw new RuntimeException("writing error for characteristic " + uuidStr.toLowerCase() + ": setValue method returned false");
        }
        if(!bluetoothGatt.writeCharacteristic(characteristics)) {
            throw new RuntimeException("writing error for characteristic " + uuidStr.toLowerCase() + ": writeCharacteristic method returned false");
        }
        sequentialOperation.waitForOperation("write characteristic " + uuidStr, OP_TIMEOUT_MS);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            sequentialOperation.notifyCompleted(null);
            LOG.info("confirmed write characteristic " + characteristic.getUuid() + " val " + Arrays.toString(characteristic.getValue()));
        } else {
            sequentialOperation.notifyError(new RuntimeException("writing error for characteristic " + characteristic.getUuid().toString().toLowerCase() + ": gatt notified operation failure"));
        }
    }

    public void writeCharacteristicNoWait(final UUID uuid, final byte[] value){
        writeCharacteristicNoWait(uuid.toString(), value);
    }

    public void writeCharacteristicNoWait(final String uuidStr, final byte[] value){
        BluetoothGattCharacteristic characteristics = characteristicsMap.get(uuidStr.toLowerCase());
        if(characteristics == null) {
            throw new RuntimeException("writing error for characteristic " + uuidStr.toLowerCase() + ": characteristic undefined");
        }
        if(!characteristics.setValue(value)){
            throw new RuntimeException("writing error for characteristic " + uuidStr.toLowerCase() + ": setValue method returned false");
        }
        if(!bluetoothGatt.writeCharacteristic(characteristics)) {
            throw new RuntimeException("writing error for characteristic " + uuidStr.toLowerCase() + ": writeCharacteristic method returned false");
        }
    }



    public void readCharacteristic(final UUID uuid){
        readCharacteristic(uuid.toString());
    }

    public void readCharacteristic(final String uuidStr) {
        BluetoothGattCharacteristic characteristics = characteristicsMap.get(uuidStr.toLowerCase());
        if(characteristics == null) {
            throw new RuntimeException("reading error for characteristic " + uuidStr.toLowerCase() + ": characteristic undefined");
        }
        if(!bluetoothGatt.readCharacteristic(characteristics)){
            throw new RuntimeException("reading error for characteristic " + uuidStr.toLowerCase() + ": readCharacteristic returned false");
        }
        sequentialOperation.waitForOperation("read characteristic " + uuidStr, OP_TIMEOUT_MS);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            sequentialOperation.notifyCompleted(characteristic);
            LOG.info("confirmed read characteristic " + characteristic.getUuid() + " val " + Arrays.toString(characteristic.getValue()));
        } else {
            sequentialOperation.notifyError(new RuntimeException("reading error for characteristic " + characteristic.getUuid().toString().toLowerCase() + ": gatt notified operation failure"));
        }
    }

    public void registerToCharacteristic(final UUID uuid, final boolean enableFlag){
        registerToCharacteristic(uuid.toString(), enableFlag);
    }

    public void registerToCharacteristic(final String uuidStr, final boolean enableFlag){
        BluetoothGattCharacteristic characteristics = characteristicsMap.get(uuidStr.toLowerCase());
        if(characteristics == null) {
            throw new RuntimeException("error registering to characteristic " + uuidStr.toLowerCase() + ": characteristic undefined");
        }
        if(!bluetoothGatt.setCharacteristicNotification(characteristics, enableFlag)){
            throw new RuntimeException("error registering to characteristic " + uuidStr.toLowerCase() + ": setCharacteristicNotification returned false");
        }
        int properties = characteristics.getProperties();
        if ((properties & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            LOG.debug("registering to characteristic " + uuidStr.toLowerCase() + ": using NOTIFICATION");
            writeDescriptor(uuidStr, UUID_DESCRIPTOR_GATT_CLIENT_CHARACTERISTIC_CONFIGURATION.toString(), enableFlag ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        } else if ((properties & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
            LOG.debug("registering to characteristic " + uuidStr.toLowerCase() + ": using INDICATION");
            writeDescriptor(uuidStr, UUID_DESCRIPTOR_GATT_CLIENT_CHARACTERISTIC_CONFIGURATION.toString(), enableFlag ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        } else {
            throw new RuntimeException("error registering to characteristic " + uuidStr.toLowerCase() + ": missing notification property in characteristic");
        }
    }

    public void writeDescriptor(String uuidStrCharacteristic, String uuidStrDescriptor, final byte[] value){
        BluetoothGattCharacteristic characteristics = characteristicsMap.get(uuidStrCharacteristic.toLowerCase());
        if(characteristics == null) {
            throw new RuntimeException("error writing descriptor" + uuidStrDescriptor.toLowerCase() + ": characteristic undefined: " + uuidStrCharacteristic);
        }
        BluetoothGattDescriptor descriptor = characteristics.getDescriptor(UUID_DESCRIPTOR_GATT_CLIENT_CHARACTERISTIC_CONFIGURATION);
        if(descriptor == null) {
            throw new RuntimeException("error writing descriptor " + uuidStrDescriptor.toLowerCase() + ": descriptor not found in characteristic: "+ uuidStrCharacteristic);
        }
        descriptor.setValue(value);
        if(!bluetoothGatt.writeDescriptor(descriptor)) {
            throw new RuntimeException("error writing descriptor " + uuidStrDescriptor.toLowerCase() + ": writeDescriptor returned false");
        }
        sequentialOperation.waitForOperation("write descriptor" + uuidStrCharacteristic + "/" + uuidStrDescriptor, OP_TIMEOUT_MS);
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            sequentialOperation.notifyCompleted(descriptor);
            LOG.info("confirmed write descriptor " + descriptor.getUuid() + " val " + Arrays.toString(descriptor.getValue()));
        } else {
            sequentialOperation.notifyError(new RuntimeException("writing error for descriptor " + descriptor.getUuid().toString().toLowerCase() + ": gatt notified operation failure"));
        }
    }

    public void readDescriptor(String uuidStrCharacteristic, String uuidStrDescriptor, final byte[] value){
        BluetoothGattCharacteristic characteristics = characteristicsMap.get(uuidStrCharacteristic.toLowerCase());
        if(characteristics == null) {
            throw new RuntimeException("error reading descriptor" + uuidStrDescriptor.toLowerCase() + ": characteristic undefined: " + uuidStrCharacteristic);
        }
        BluetoothGattDescriptor descriptor = characteristics.getDescriptor(UUID_DESCRIPTOR_GATT_CLIENT_CHARACTERISTIC_CONFIGURATION);
        if(descriptor == null) {
            throw new RuntimeException("error reading descriptor " + uuidStrDescriptor.toLowerCase() + ": descriptor not found in characteristic: "+ uuidStrCharacteristic);
        }
        if(!bluetoothGatt.readDescriptor(descriptor)) {
            throw new RuntimeException("error reading descriptor " + uuidStrDescriptor.toLowerCase() + ": readDescriptor returned false");
        }
        sequentialOperation.waitForOperation("reading descriptor" + uuidStrCharacteristic + "/" + uuidStrDescriptor, OP_TIMEOUT_MS);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            sequentialOperation.notifyCompleted(descriptor);
            LOG.info("confirmed read descriptor " + descriptor.getUuid() + " val " + Arrays.toString(descriptor.getValue()));
        } else {
            sequentialOperation.notifyError(new RuntimeException("reading error for descriptor " + descriptor.getUuid().toString().toLowerCase() + ": gatt notified operation failure"));
        }
    }

    @Override
    public void close() throws IOException {
        this.bluetoothGatt.close();
    }

    private Handler onCharacteristicChangedHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) msg.obj;
            LOG.info(Thread.currentThread().getName() + "] notifying characteristic change " + characteristic.getUuid());
            for(CharacteristicChangedListener l : characteristicChangedListeners) {
                l.onCharacteristicChanged(characteristic);
            }
        }
    };

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        onCharacteristicChangedHandler.obtainMessage(1, characteristic).sendToTarget();
    }

    public interface CharacteristicChangedListener {
        void onCharacteristicChanged(BluetoothGattCharacteristic characteristic);
    }
    private Set<CharacteristicChangedListener> characteristicChangedListeners = new HashSet<>();
    public void addCharacteristicChangedListener(CharacteristicChangedListener listener) {
        this.characteristicChangedListeners.add(listener);
    }
    public void removeCharacteristicChangedListener(CharacteristicChangedListener listener) {
        this.characteristicChangedListeners.remove(listener);
    }

    public void wait(int ms) {
        try {Thread.sleep(ms);} catch (InterruptedException e) {}
    }
}
