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
        if (preferredDeviceAddress!=null) {
            return MonitorDeviceFactory.newMonitorDevice(btAdapter.getRemoteDevice(preferredDeviceAddress));
        }
        return null;
    }


    public void boundDevice(BluetoothDevice device) {
        AppClass.setPreferredDevice(device.getAddress());
        LOG.info("found device: " + device.getName() + " (" + device.getAddress() + ") ");
        device.createBond();
        LOG.info("bonding");
        while(device.getBondState() != device.BOND_BONDED) {
            Thread.yield();
        }
        LOG.info("bonded");
    }


}
