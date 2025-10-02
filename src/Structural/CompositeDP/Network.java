package Structural.CompositeDP;
import java.util.*;

//Composite node (network or subnet containing other devices)
class Network implements NetworkDevice {
    private String name;
    private List<NetworkDevice> devices = new ArrayList<>();

    public Network(String name) {
        this.name = name;
    }

    @Override
    public void addDevice(NetworkDevice device) {
        devices.add(device);
    }
    @Override
    public void removeDevice(NetworkDevice device) {
        devices.remove(device);
    }

    @Override
    public List<NetworkDevice> getDevices() {
        return devices;
    }

    @Override
    public void displayDeviceInfo() {
        System.out.println("Network: " + name);
        for (NetworkDevice device : devices) {
            device.displayDeviceInfo();
        }
    }
    @Override
    public void rebootDevice() {
        System.out.println("Rebooting devices in " + name + "...");
        for (NetworkDevice device : devices) {
            device.rebootDevice();
        }
    }
}
