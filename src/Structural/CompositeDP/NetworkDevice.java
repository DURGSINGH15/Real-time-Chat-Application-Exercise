package Structural.CompositeDP;
import java.util.*;

//Component interface
interface NetworkDevice {
    void addDevice(NetworkDevice device);
    void removeDevice(NetworkDevice device);
    List<NetworkDevice> getDevices();
    void displayDeviceInfo();
    void rebootDevice();
}

