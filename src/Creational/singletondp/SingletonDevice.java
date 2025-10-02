package Creational.singletondp;

import java.util.ArrayList;
import java.util.List;

class PhysicalDevice {
    private String name;

    public PhysicalDevice(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

public class SingletonDevice {
    private static SingletonDevice soleInst = new SingletonDevice();
    //soleInst is made static so that any change made in static variable(through one instance) affects value in all other instances.
    private List<PhysicalDevice> devices;

    private SingletonDevice() {
        devices = new ArrayList<>();
    }
    //getInstance() can be called directly using the class-name as the only instance is made private.
    //static -> associated with class
    public static SingletonDevice getInstance() {
        return soleInst;
    }

    public void addDevice(PhysicalDevice device) {
        devices.add(device);
    }

    public void removeDevice(PhysicalDevice device) {
        devices.remove(device);
    }

    public void listDevices() {
        if (devices.isEmpty()) {
            System.out.println("No devices are registered.");
        } else {
            System.out.println("Registered devices:");
            for (PhysicalDevice device : devices) {
                System.out.println("- " + device.getName());
            }
        }
    }
}

