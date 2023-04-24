import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlParser {

    public XmlParser() throws IOException, SAXException, ParserConfigurationException {
    }

    public static boolean parsEnabled(String value) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        boolean b;
        String s = "";

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String f = node.getNodeName();
            if (f.equals(value)) {
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element employee = (Element) node;
                    s = employee.getElementsByTagName("enabled").item(0).getTextContent();
                }
            }
        }
        return b = Boolean.parseBoolean(s);
    }

    public static String parsFileName(String value) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        String s = "";

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String f = node.getNodeName();
            if (f.equals(value)) {
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element employee = (Element) node;
                    s = employee.getElementsByTagName("fileName").item(0).getTextContent();
                }
            }
        }
        return s;
    }
}
