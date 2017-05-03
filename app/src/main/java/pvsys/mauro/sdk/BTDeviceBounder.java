package pvsys.mauro.sdk;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class BTDeviceBounder {

    private final static Logger LOG = new Logger(BTDeviceBounder.class.getSimpleName());

    private BluetoothAdapter btAdapter;

    public BTDeviceBounder(BluetoothAdapter btAdapter) {
        this.btAdapter = btAdapter;
    }

    public MonitorDevice getBoundedDevice(){
        String preferredDeviceAddress = AppClass.getPreferredDeviceAddress();
        String preferredDeviceType = AppClass.getPreferredDeviceType();
        if (preferredDeviceAddress!=null && preferredDeviceType!=null) {
            return MonitorDeviceFactory.newMonitorDevice(btAdapter.getRemoteDevice(preferredDeviceAddress), preferredDeviceType);
        }
        return null;
    }


    public void boundDevice(BluetoothDevice device, String deviceType) {
        AppClass.setPreferredDevice(device.getAddress(), deviceType);
        LOG.info("found device: " + device.getName() + " (" + device.getAddress() + ") ");
        device.createBond();
        LOG.info("bonding");
        while(device.getBondState() != device.BOND_BONDED) {
            Thread.yield();
        }
        LOG.info("bonded");
    }


}
