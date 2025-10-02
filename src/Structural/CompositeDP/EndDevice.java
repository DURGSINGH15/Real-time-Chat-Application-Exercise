package Structural.CompositeDP;

import java.util.Collections;
import java.util.List;

//Leaf node (e.g., a switch, computer, or other end device)
class EndDevice implements NetworkDevice {

    private String name;
    public EndDevice(String name) {
        this.name = name;
    }

    @Override
    public void addDevice(NetworkDevice device) {}
    // End devices cannot have child devices
    @Override
    public void removeDevice(NetworkDevice device) {}
    // End devices cannot have child devices
    @Override
    public List<NetworkDevice> getDevices() {
        return Collections.emptyList();
    }

    @Override
    public void displayDeviceInfo() {
        System.out.println("End Device: " + name);
    }

    @Override
    public void rebootDevice() {
        System.out.println("Rebooting " + name + "...");
        // end device reboot logic here
    }
}

