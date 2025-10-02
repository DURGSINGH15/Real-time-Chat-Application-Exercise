package Creational.singletondp;

public class SingletonDevTest {
    public static void main(String[] args) {
        SingletonDevice manager = SingletonDevice.getInstance();
        SingletonDevice manager2= SingletonDevice.getInstance();

        PhysicalDevice printer = new PhysicalDevice("Printer");
        PhysicalDevice scanner = new PhysicalDevice("Scanner");
        PhysicalDevice monitor = new PhysicalDevice("Monitor");

        //add devices through 'manager' instance
        manager.addDevice(printer);
        manager.addDevice(scanner);
        manager.addDevice(monitor);

        manager.listDevices();
        //removed a device through 'manager2' instance
        manager2.removeDevice(scanner);
        System.out.println("Change is observed since both the instances share the same instance(soleInst)"
                + ",so change is made in soleInst only (declared static)  ");
        manager.listDevices();
    }
}
