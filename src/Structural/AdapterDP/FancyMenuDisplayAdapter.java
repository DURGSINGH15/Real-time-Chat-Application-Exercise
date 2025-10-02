package Structural.AdapterDP;

//FancyMenuDisplayAdapter class (Adapter)
public class FancyMenuDisplayAdapter implements IFoodOrderingApp {
    private final IFancyMenuDisplayService fancyMenuDisplayService;

    public FancyMenuDisplayAdapter(IFancyMenuDisplayService fancyMenuDisplayService) {
        this.fancyMenuDisplayService = fancyMenuDisplayService;
    }

    @Override
    public void displayMenus(XmlOrder xmlOrder) {
        JsonOrder jsonOrder = convertXmlToJson(xmlOrder);
        fancyMenuDisplayService.displayMenus(jsonOrder);
    }

    @Override
    public void displayRecommendations(XmlOrder xmlOrder) {
        JsonOrder jsonOrder = convertXmlToJson(xmlOrder);
        fancyMenuDisplayService.displayRecommendations(jsonOrder);
    }

    private JsonOrder convertXmlToJson(XmlOrder xmlOrder) {
        // Dummy conversion logic for demonstration purposes
        String jsonContent = "{\"content\":\"" + xmlOrder.toString() + "\"}";
        return new JsonOrder(jsonContent);
    }
}
