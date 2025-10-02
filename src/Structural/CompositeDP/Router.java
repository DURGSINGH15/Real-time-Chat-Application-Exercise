package Structural.CompositeDP;
import java.util.*;
//Router class (composite node)
class Router implements NetworkDevice {

    private String name;
    private String ipAddress;
    private List<NetworkDevice> connectedDevices = new ArrayList<>();

    public Router(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    @Override
    public void addDevice(NetworkDevice device) {
        connectedDevices.add(device);
    }
    @Override
    public void removeDevice(NetworkDevice device) {
        connectedDevices.remove(device);
    }

    @Override
    public List<NetworkDevice> getDevices() {
        return connectedDevices;
    }

    @Override
    public void displayDeviceInfo() {
        System.out.println("Router: " + name + " (" + ipAddress + ")");
        for (NetworkDevice device : connectedDevices) {
            device.displayDeviceInfo();
        }
    }

    @Override//router reboot
    public void rebootDevice() {
        System.out.println("Rebooting " + name + "...");
        for (NetworkDevice device : connectedDevices) {
            device.rebootDevice();
        }
    }
}
