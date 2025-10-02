package Structural.AdapterDP;

//IFoodOrderingApp interface (Target interface)
public interface IFoodOrderingApp {

    void displayMenus(XmlOrder xmlOrder);
    void displayRecommendations(XmlOrder xmlOrder);
}