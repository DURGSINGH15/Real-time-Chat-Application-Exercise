package Structural.AdapterDP;

//IFancyMenuDisplayService interface (Adaptee interface)
public interface IFancyMenuDisplayService {

    void displayMenus(JsonOrder jsonOrder);
    void displayRecommendations(JsonOrder jsonOrder);
}
