package Structural.AdapterDP;

//XmlOrder class
public class XmlOrder {
    private String xmlContent;

    public XmlOrder(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    @Override
    public String toString() {
        return "Xml content:"+ xmlContent;
    }
}

