package Structural.AdapterDP;

//FoodOrderingApp class (Adapter Client)
public class FoodOrderingApp implements IFoodOrderingApp {
    @Override
    public void displayMenus(XmlOrder xmlOrder) {
        System.out.println("Displaying menus using XML order: " + xmlOrder);
        // Implementation for displaying menus using XML order
    }

    @Override
    public void displayRecommendations(XmlOrder xmlOrder) {
        System.out.println("Displaying recommendations using XML order: " + xmlOrder);
        // Implementation for displaying recommendations using XML order
    }
}
