package Structural.CompositeDP;

public class NetworkTest {
    public static void main(String[] args) {
        // Create individual network devices
        NetworkDevice switch1 = new EndDevice("Switch1");
        NetworkDevice computer1 = new EndDevice("Computer1");
        NetworkDevice computer2 = new EndDevice("Computer2");

        // Create routers as composite nodes
        Router router1 = new Router("Router1", "192.168.1.1");
        Router router2 = new Router("Router2", "192.168.2.1");

        // Connect devices to router1
        router1.addDevice(switch1);
        router1.addDevice(computer1);

        // Connect devices to router2
        router2.addDevice(computer2);

        // Create a network
        Network network = new Network("MyNetwork");
        network.addDevice(router1); // Add router1 as a composite node

        // Create a subnet within the network
        Network subnet = new Network("Subnet1");
        subnet.addDevice(router2); // Add router2 as a composite node
        network.addDevice(subnet);

        // Display device information
        network.displayDeviceInfo();
        System.out.println();

        // Reboot devices
        network.rebootDevice();
        System.out.println();

    }
}
