import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String fileName = "data.xml";
        String jsonName = "data.json";

        toCreateFile(fileName);

        List<Employee> list = parseXML(fileName);
    }

    public static void toCreateFile(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element staff = document.createElement("staff");
            document.appendChild(staff);
            Element employee = document.createElement("employee");
            employee.setAttribute("id", "1");
            employee.setAttribute("firstName", "John");
            employee.setAttribute("lastName", "Smith");
            employee.setAttribute("country", "USA");
            employee.setAttribute("age", "25");
            staff.appendChild(employee);
            Element employee2 = document.createElement("employee");
            employee2.setAttribute("id", "2");
            employee2.setAttribute("firstName", "Ivan");
            employee2.setAttribute("lastName", "Petrov");
            employee2.setAttribute("country", "RU");
            employee2.setAttribute("age", "23");
            staff.appendChild(employee2);

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(
                    new File(fileName));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.fillInStackTrace();
        }
    }

    public static List<Employee> parseXML(String fileName) {
        List<Employee> list = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fileName);

            Node staff = document.getDocumentElement();
            NodeList nodeList = staff.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.ELEMENT_NODE == node.getNodeType()) {
                    Element employee = (Element) node;

                    long id = Long.parseLong(employee.getElementsByTagName("id").item(0).getTextContent());
                    String firstName = employee.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = employee.getElementsByTagName("lastName").item(0).getTextContent();
                    String country = employee.getElementsByTagName("country").item(0).getTextContent();
                    int age = Integer.parseInt(employee.getElementsByTagName("age").item(0).getTextContent());

                    list.add(new Employee(id, firstName, lastName, country, age));

                }
            }
            return list;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.fillInStackTrace();
        }
        return null;
    }

}
