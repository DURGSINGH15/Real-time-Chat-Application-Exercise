package Structural.AdapterDP;

//Main class
public class MainXmlToJson {
    public static void main(String[] args) {
        String xmlOrderContent = "<order><item>Pizza</item></order>";
        XmlOrder xmlOrder = new XmlOrder(xmlOrderContent);

        // Using FoodOrderingApp
        IFoodOrderingApp foodOrderingApp = new FoodOrderingApp();
        foodOrderingApp.displayMenus(xmlOrder);
        foodOrderingApp.displayRecommendations(xmlOrder);

        // Using FancyMenuDisplayAdapter
        IFancyMenuDisplayService fancyMenuDisplayService = new FancyMenuDisplayService();
        IFoodOrderingApp fancyMenuAdapter = new FancyMenuDisplayAdapter(fancyMenuDisplayService);
        fancyMenuAdapter.displayMenus(xmlOrder);
        fancyMenuAdapter.displayRecommendations(xmlOrder);
    }
}