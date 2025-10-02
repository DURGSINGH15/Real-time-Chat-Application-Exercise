package Structural.AdapterDP;

public class JsonOrder {
    private String jsonContent;

    public JsonOrder(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    @Override
    public String toString() {
        return jsonContent;
    }
}
