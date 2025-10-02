package Structural.AdapterDP;

//FancyMenuDisplayService class (Adaptee)
public class FancyMenuDisplayService implements IFancyMenuDisplayService {
    @Override
    public void displayMenus(JsonOrder jsonOrder) {
        System.out.println("Displaying menus using JSON order: " + jsonOrder);
        // Implementation for displaying menus using JSON order
    }

    @Override
    public void displayRecommendations(JsonOrder jsonOrder) {
        System.out.println("Displaying recommendations using JSON order: " + jsonOrder);
        // Implementation for displaying recommendations using JSON order
    }
}

